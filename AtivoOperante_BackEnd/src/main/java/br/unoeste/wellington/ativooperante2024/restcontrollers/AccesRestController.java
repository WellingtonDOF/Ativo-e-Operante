package br.unoeste.wellington.ativooperante2024.restcontrollers;


import br.unoeste.wellington.ativooperante2024.db.entities.Usuario;
import br.unoeste.wellington.ativooperante2024.security.JWTTokenProvider;
import br.unoeste.wellington.ativooperante2024.services.UsuarioService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value="apis/security/")
public class AccesRestController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("logar/")
    public ResponseEntity<Object> logar(@RequestParam(value="cpf") String cpf,
                                        @RequestParam(value="pass") int senha)
    {
        String token ="não autenticado";
        System.out.println(cpf+" "+senha);
        Usuario usuario = usuarioService.getByCPF(cpf);

        Map<String, Object> response = new HashMap<>();

        response.put("token",token);//coloca o token no map e caso futuramente dê errado ele vai estar como nao autenticado.

        if(usuario==null)//cpf nao encontrado
        {
            System.out.println("entrou aqui firme");
            response.put("success", false); //cria e define o campo sucesso como falso ou seja não deu certo.
            response.put("message","CPF não encontrado");//esse campo informa onde foi o erro, email, senha, status..
            response.put("usuario", null);//não retorna o objeto porque nao achou.

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);// retorna o objeto criado e um status 404 (não encontrado)
        }
        else//encontrou o cpf
        {
            response.put("usuario",usuario);//define o campo  com o objeto para retornar ele

            if(usuario.getSenha()==senha)//se a senha estiver correta entra.
            {
                usuario.setSenha(0);//colocar a senha para null para não expor a senha de quem acessar quando retornar o objeto.

                response.put("success",true);//seta o campo succcess para verdadeiro indicando que deu certo o login.
                response.put("message","Login bem-sucedido");

                token= JWTTokenProvider.getToken(usuario.getCpf(), ""+usuario.getNivel());
                response.put("token",token);// tem que adicionar o token ao map novamente pq o valor dele foi atualizado.
                //return ResponseEntity.ok(token); // em vez de retornar o .ok(token) eu retorno ele dentro do response
                return new ResponseEntity<>(response, HttpStatus.OK);//status 200, (deu certo)
            }
            else
            {
                usuario.setSenha(0);
                response.put("success",false);
                response.put("message","Senha incorreta");

                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);//status 401, acesso não autorizado erro de credencial
            }
        }
    }

    @PostMapping("get-usuario-email")
    public ResponseEntity<Object> buscarUsuarioEmail(@RequestBody Usuario usuario)
    {
        if(usuarioService.getByCPF(usuario.getCpf())==null)
            return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.OK);// retorna null caso de errado, ai da pra fazer a verificação

        return new ResponseEntity<>(usuario,HttpStatus.BAD_REQUEST);
    }


    @PostMapping("token-Supremo")
    public ResponseEntity<Object> tokenSupremo(@RequestHeader("Authorization") String token)
    {
        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

        System.out.println("testestestestestes");
        if(claims!=null && JWTTokenProvider.verifyToken(token))
            return new ResponseEntity<>("",HttpStatus.OK);

        return new ResponseEntity<>("",HttpStatus.UNAUTHORIZED);
    }
}
