package br.unoeste.wellington.ativooperante2024.services;


import br.unoeste.wellington.ativooperante2024.db.entities.Orgao;
import br.unoeste.wellington.ativooperante2024.db.entities.Usuario;
import br.unoeste.wellington.ativooperante2024.db.repositories.OrgaoRepository;
import br.unoeste.wellington.ativooperante2024.db.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userrepo;

    @Autowired
    private ResourceLoader resourceLoader;

    public Usuario save(Usuario user)
    {
        return userrepo.save(user);
    }

    public Usuario getById(Long id)
    {
        //Verifica o ID se ele existir retorna o usuario, caso não exista retorna um usuario vazio
        Usuario usuario = userrepo.findById(id).orElse(new Usuario());
        return usuario;
    }

    public Usuario getByEmail(String email)
    {
        //Não da pra usar o orElse porque o retorno não é um optional, apenas NULL ou o proprio objeto caso encontre.
        Usuario usuario = userrepo.findByEmail(email);

        return usuario;
    }

    public Usuario getByCPF(String cpf)
    {
        //Não da pra usar o orElse porque o retorno não é um optional, apenas NULL ou o proprio objeto caso encontre.
        Usuario usuario = userrepo.findByCpf(cpf);
        return usuario;
    }

    public List<Usuario> getAll()
    {
        return userrepo.findAll();
    }

    public boolean delete(Long id)
    {
        // vai tentar deletar o orgao caso de certo ele nao entra no catch e não retorna falso, apenas retorna true no final;
        // se ele entrar no catch é porque ele não conseguiu deletar e então retorna falso para poder tratar isso, dar alguma resposta.
        try{
            userrepo.deleteById(id);
        }catch(Exception e) {
            return false;
        }
        return true;
    }

    public String getStaticPath() throws IOException {
        String staticPath = null;
        if(staticPath==null)
            System.out.println("[e null");
        staticPath = resourceLoader.getResource("classpath:static").getFile().getAbsolutePath();


        return staticPath;
    }
    public String tirarEspaco(String texto)
    {
        return texto.replaceAll("\\s","");
    }

    public String extensaoArq(String extensao)
    {
        int lastIndex = extensao.lastIndexOf(".");
        return  extensao.substring(lastIndex + 1);
    }

}
