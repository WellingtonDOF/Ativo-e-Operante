package br.unoeste.wellington.ativooperante2024.restcontrollers;


import br.unoeste.wellington.ativooperante2024.db.entities.Denuncia;
import br.unoeste.wellington.ativooperante2024.db.entities.Tipo;
import br.unoeste.wellington.ativooperante2024.db.entities.Usuario;
import br.unoeste.wellington.ativooperante2024.db.repositories.UsuarioRepository;
import br.unoeste.wellington.ativooperante2024.security.JWTTokenProvider;
import br.unoeste.wellington.ativooperante2024.services.DenunciaService;
import br.unoeste.wellington.ativooperante2024.services.OrgaoService;
import br.unoeste.wellington.ativooperante2024.services.TipoService;
import br.unoeste.wellington.ativooperante2024.services.UsuarioService;
import io.jsonwebtoken.Claims;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@CrossOrigin
@RestController
@RequestMapping("apis/cidadao/")
public class CidadaoRestController {

    @Autowired
    OrgaoService orgaoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    TipoService tipoService;

    @Autowired
    DenunciaService denunciaService;

    @GetMapping("teste-conexao")
    public String testeConexao()
    {
        return "conectado";
    }


    @PostMapping("teste")
    public ResponseEntity<Object> testeConexao2(@RequestParam(value="username") String email,
                                                @RequestHeader("Authorization") String token) {
        System.out.println("TOKEN AQUUI OLHA SO   "+token);

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime finalTime = agora.plusMinutes(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String agoraFormatado = agora.format(formatter);
        String finalFormatado = finalTime.format(formatter);

        System.out.println(agoraFormatado);
        System.out.println(finalFormatado);

        System.out.println(JWTTokenProvider.verifyToken(token.substring(7)));
        return ResponseEntity.ok(token);
    }

    //LISTAR ORGAOS COMPETENTES
    @GetMapping("get-all-orgaos")
    public ResponseEntity<Object> buscarTodosOrgao(@RequestHeader("Authorization") String token)
    {
        System.out.println("teste");
        return new ResponseEntity<>(orgaoService.getAll(),HttpStatus.OK);
    }

    //LISTAR TIPOS DE PROBLEMAS
    @GetMapping("get-all-problemas")
    public ResponseEntity<Object> buscarTodosProblemas(@RequestHeader("Authorization") String token)
    {
        return new ResponseEntity<>(tipoService.getAll(),HttpStatus.OK);
    }

    @GetMapping("get-all-denuncias")
    public ResponseEntity<Object> buscarTodasDenuncias(@RequestHeader("Authorization") String token)
    {
        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);
        System.out.println(claims);

        if(claims!=null)
        {

            if(claims.get("nivel").equals("2"))
            {
                Usuario usuario = usuarioService.getByCPF(claims.getSubject());
                return new ResponseEntity<>(denunciaService.getAllByUsuario(usuario),HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("",HttpStatus.UNAUTHORIZED);
    }

    // ------------------------- USUARIOS ---------------------------//




    // !-------------------------------------------------------------------------------------------------------------!
    @GetMapping("get-usuario-teste")// Esse required false é que não é obrigatoria chegar os dois, mas confirmo no js que sempre chega 1
    public ResponseEntity<Object> buscarUsuarioIdEmail(@RequestParam(value="id", required=false) Long id,
                                                       @RequestParam(value="email", required = false) String email)
    {
        if(id!=null)
            return new ResponseEntity<>(usuarioService.getById(id),HttpStatus.OK);
        return new ResponseEntity<>(usuarioService.getByEmail(email),HttpStatus.OK);
    }
    // !-------------------------------------------------------------------------------------------------------------!

    @PostMapping(value="add-denuncia")
    public ResponseEntity<Object> salvarDenuncia(@RequestParam(value="denName") String nameT,
                                                 @RequestParam(value="orgaoCompetente") String orgao,
                                                 @RequestParam(value="tipoProblema") String tipoP,
                                                 @RequestParam(value="denUrgency") String urgency,
                                                 @RequestParam(value="denText") String text,
                                                 @RequestParam(value="file_upload") MultipartFile file_upload,
                                                 @RequestHeader("Authorization") String token ) {

        token = token.substring(7);//Pega apenas a string do caracter 7 para frente ou seja elimina Bearer' '
        Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);
        System.out.println(token);
        if(claims!=null)
        {
            try {

                System.out.println(nameT + " " + orgao + " " + tipoP + " " + urgency + " " + text + " " + file_upload.getOriginalFilename());
                System.out.println("teste");

                Denuncia denuncia = new Denuncia();

                String nomePasta = "/image";
                String denConcat;

                System.out.println("chegou");
                File folderImage = new File(usuarioService.getStaticPath() + nomePasta);

                if (!folderImage.exists())
                    folderImage.mkdir();


                System.out.println(usuarioService.getStaticPath());
                denConcat = nomePasta + "/" + usuarioService.tirarEspaco(file_upload.getOriginalFilename());
                System.out.println(usuarioService.getStaticPath() + denConcat);


                System.out.println(orgaoService.getById(Long.parseLong(orgao)));


                denuncia.setTitulo(nameT);
                denuncia.setTexto(text);
                denuncia.setUrgencia(Integer.parseInt(urgency));
                denuncia.setOrgId(orgaoService.getById(Long.parseLong(orgao)));
                denuncia.setData(LocalDate.now());
                denuncia.setTipId(tipoService.getById(Long.parseLong(tipoP)));
                denuncia.setData(LocalDate.now());
                denuncia.setUsId(usuarioService.getByCPF(claims.getSubject()));//PARTE DE TESTE, não sei como obter o ID do usuario ainda!!!!
                denuncia.setImageName(denConcat);

                denConcat = usuarioService.tirarEspaco(file_upload.getOriginalFilename());

                System.out.println(denuncia.getImageName());

                //Esse try aqui é pra garantir que se não salvar a denuncia no banco não salva a imagem na parte static
                try {
                    denunciaService.save(denuncia);

                    Files.copy(file_upload.getInputStream(),
                            Paths.get(folderImage.getAbsolutePath()).resolve(denConcat), StandardCopyOption.REPLACE_EXISTING);

                } catch (Exception e) {
                    return ResponseEntity.badRequest().body("Erro " + e.getMessage());
                }
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Erro " + e.getMessage());
            }

            return ResponseEntity.ok("INSERIDO");
        }
        return new ResponseEntity<>("",HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("add-usuario")
    public ResponseEntity<Object> salvarUsuario(@RequestBody Usuario usuario)
    {
        System.out.println(usuario.getEmail());

        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.OK);// retorna null caso de errado, ai da pra fazer a verificação
    }

    @GetMapping("get-usuario")
    public ResponseEntity<Object> buscarUmUsuario(@RequestParam(value="id") Long id)
    {
        return new ResponseEntity<>(usuarioService.getById(id), HttpStatus.OK);
    }

    @GetMapping("get-all-usuarios")
    public ResponseEntity<Object> buscarTodosUsuarios()
    {
        return new ResponseEntity<>(usuarioService.getAll(),HttpStatus.OK);
    }

}
