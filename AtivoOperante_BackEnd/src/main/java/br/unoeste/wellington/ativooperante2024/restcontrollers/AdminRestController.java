package br.unoeste.wellington.ativooperante2024.restcontrollers;


import br.unoeste.wellington.ativooperante2024.db.entities.*;
import br.unoeste.wellington.ativooperante2024.db.repositories.DenunciaRepository;
import br.unoeste.wellington.ativooperante2024.db.repositories.OrgaoRepository;
import br.unoeste.wellington.ativooperante2024.db.repositories.TipoRepository;
import br.unoeste.wellington.ativooperante2024.db.repositories.UsuarioRepository;
import br.unoeste.wellington.ativooperante2024.security.AccessFilter;
import br.unoeste.wellington.ativooperante2024.security.JWTTokenProvider;
import br.unoeste.wellington.ativooperante2024.services.*;
import io.jsonwebtoken.Claims;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("apis/adm/")
public class AdminRestController {

    @Autowired
    OrgaoService orgaoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    DenunciaService denunciaService;

    @Autowired
    TipoService tipoService;

    @PostMapping("teste-conexao")
    public ResponseEntity<Object> testeConexao(@RequestParam(value="username") String email) {

        String token ="não autenticado";
        token= JWTTokenProvider.getToken(email,""+0);

        System.out.println(token);
        System.out.println(JWTTokenProvider.verifyToken(token));
        return ResponseEntity.ok(token);
    }

    @PostMapping("verifica-nivel")
    public ResponseEntity<Object> verificaNivel(@RequestHeader("Authorization") String token) {

        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

        if(claims!=null)
            if(claims.get("nivel").equals("1"))
                return new ResponseEntity<>("",HttpStatus.OK);
        return new ResponseEntity<>("",HttpStatus.UNAUTHORIZED);
    }



    // ------------------------- FEEDBACK ---------------------------//

    //REGISTRAR FEEDBACK EM DENUNCIA
    @PostMapping("add-feedback")
    public ResponseEntity<Object> addFeedback(@RequestParam(value="feedback") String texto,
                                              @RequestParam(value="id") Long id, @RequestHeader("Authorization") String token)
    {
        System.out.println(id);
        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

        if(claims!=null)
            if(claims.get("nivel").equals("1"))
            {
                Feedback feedback = new Feedback();
                feedback.setTexto(texto);
                feedback.setDenuncia(denunciaService.getById(id));

                return new ResponseEntity<>(feedbackService.save(feedback), HttpStatus.OK);
            }
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }


    @PostMapping("delete-feedback")
    public ResponseEntity<Object> excluirFeedback(@RequestParam(value="id") Long id, @RequestHeader("Authorization") String token)
    {
        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

        if(claims!=null)
            if(claims.get("nivel").equals("1"))
                if(feedbackService.delete(feedbackService.getDenFeed(id).getId()))
                    return new ResponseEntity<>("",HttpStatus.OK);
        return new ResponseEntity<>("",HttpStatus.UNAUTHORIZED);
    }


    @GetMapping("get-feedback")
    public ResponseEntity<Object> buscarUmFeedback(@RequestParam(value="id") Long id)
    {
        Feedback feedback = feedbackService.getDenFeed(id); //Caso tenha um feedback com o ID dessa denuncia retorna ele.

        if(feedback==null)
            feedback=new Feedback();

        return new ResponseEntity<>(feedback,HttpStatus.OK); // se não achar retorna um json sem dado nenhum, vazio...
    }
    //DELETAR UM USUARIO
    @GetMapping("delete-usuario")
    public ResponseEntity<Object> excluirUsuario(@RequestParam(value="id") Long id)
    {
        if(usuarioService.delete(id))
            return new ResponseEntity<>("",HttpStatus.OK);
        else
            return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
    }

    // ------------------------- ORGAOS ---------------------------//
    @PostMapping("delete-orgao")
    public ResponseEntity<Object> excluirOrgao(@RequestParam(value="id") Long id, @RequestHeader("Authorization") String token)
    {
        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

        if(claims!=null)
            if(claims.get("nivel").equals("1"))
            {
                if(orgaoService.delete(id))
                    return new ResponseEntity<>("",HttpStatus.OK);
                else
                    return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
            }
        return new ResponseEntity<>("",HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("add-orgao")
    public ResponseEntity<Object> salvarOrgao(@RequestBody Orgao orgao, @RequestHeader("Authorization") String token)
    {  token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

        if(claims!=null)
            if(claims.get("nivel").equals("1"))
                return new ResponseEntity<>(orgaoService.save(orgao), HttpStatus.OK);// retorna null caso de errado, ai da pra fazer a verificação
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);// retorna null caso de errado, ai da pra fazer a verificação

    }

    @PostMapping("alterar-orgao")
    public ResponseEntity<Object> alterarOrgao(@RequestParam(value="id") Long id, @RequestParam(value="nome") String nome,
                                              @RequestHeader("Authorization") String token)
    {
        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

        Orgao orgao = orgaoService.getById(id);
        orgao.setNome(nome);

        if(claims!=null)
            if(claims.get("nivel").equals("1"))
                return new ResponseEntity<>(orgaoService.save(orgao),HttpStatus.OK);
        return new ResponseEntity<>("",HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("get-orgao")
    public ResponseEntity<Object> buscarUmOrgao(@RequestParam(value="id") Long id)
    {
        Orgao orgao=orgaoService.getById(id);

        if(orgao==null)
            orgao=new Orgao();

        return new ResponseEntity<>(orgao,HttpStatus.OK); // se não achar retorna um json sem dado nenhum, vazio...
    }

    @GetMapping("get-all-orgaos")
    public ResponseEntity<Object> buscarTodosOrgao()
    {
        return new ResponseEntity<>(orgaoService.getAll(),HttpStatus.OK);
    }


    // ------------------------- DENUNCIAS ---------------------------//

    @GetMapping("delete-denuncia")
    public ResponseEntity<Object> excluirDenuncia(@RequestParam(value="id") Long id, @RequestHeader("Authorization") String token)
    {

        Feedback feedback = feedbackService.getDenFeed(id); //Caso tenha um feedback com o ID dessa denuncia retorna ele.

        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

        if(claims!=null)
            if(claims.get("nivel").equals("1"))
            {
                if(feedback!=null)//Se for diferente de null é porque tem um feedback cadastrado para essa denuncia, entao vou excluir ele.
                    feedbackService.delete(feedback.getId());

                if(denunciaService.delete(id))
                    return new ResponseEntity<>("",HttpStatus.OK);

                return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
            }
        return new ResponseEntity<>("",HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("add-denuncia")
    public ResponseEntity<Object> salvarDenuncia(@RequestBody Denuncia denuncia)
    {
        return new ResponseEntity<>(denunciaService.save(denuncia), HttpStatus.OK);
    }

    @GetMapping("get-denuncia")
    public ResponseEntity<Object> buscarUmaDenuncia(@RequestParam(value="id") Long id)
    {

        Denuncia denuncia = denunciaService.getById(id);

        if(denuncia==null)
            denuncia=new Denuncia();

        return new ResponseEntity<>(denuncia, HttpStatus.OK);

        /*
        if(denuncia==null)
        return new ResponseEntity<>(denunciarepo.findById(id).
                orElse(new Denuncia()),HttpStatus.OK); // se não achar retorna um json sem dado nenhum, vazio...*/
    }

    @GetMapping("get-all-denuncias")
    public ResponseEntity<Object> buscarTodasDenuncias(@RequestHeader("Authorization") String token)
    {
        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);
        if(claims!=null)
            if(claims.get("nivel").equals("1"))
                return new ResponseEntity<>(denunciaService.getAll(),HttpStatus.OK);
        return new ResponseEntity<>("",HttpStatus.UNAUTHORIZED);
    }


    // ------------------------- TIPOS ---------------------------//

    @PostMapping("delete-tipo")
    public ResponseEntity<Object> excluirTipo(@RequestParam(value="id") Long id, @RequestHeader("Authorization") String token)
    {
        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

        if(claims!=null)
            if(claims.get("nivel").equals("1"))
            {
                if(tipoService.delete(id))
                    return new ResponseEntity<>("",HttpStatus.OK);
                else
                    return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
            }
        return new ResponseEntity<>("",HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("add-tipo")
    public ResponseEntity<Object> salvarTipo(@RequestBody Tipo tipo, @RequestHeader("Authorization") String token)
    {
        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

        if(claims!=null)
            if(claims.get("nivel").equals("1"))
                return new ResponseEntity<>(tipoService.save(tipo), HttpStatus.OK);
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("alterar-tipo")
    public ResponseEntity<Object> alterarTipo(@RequestParam(value="id") Long id, @RequestParam(value="nome") String nome,
                                              @RequestHeader("Authorization") String token)
    {
        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

        Tipo tipo = tipoService.getById(id);
        tipo.setNome(nome);

        if(claims!=null)
            if(claims.get("nivel").equals("1"))
                return new ResponseEntity<>(tipoService.save(tipo),HttpStatus.OK);
        return new ResponseEntity<>("",HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("get-tipo")
    public ResponseEntity<Object> buscarUmTipo(@RequestParam(value="id") Long id)
    {
        Tipo tipo = tipoService.getById(id);

        if(tipo==null)
            tipo = new Tipo();

        return new ResponseEntity<>(tipo, HttpStatus.OK);
    }

    @GetMapping("get-all-tipos")
    public ResponseEntity<Object> buscarTodosTipos()
    {
        return new ResponseEntity<>(tipoService.getAll(),HttpStatus.OK);
    }
}
