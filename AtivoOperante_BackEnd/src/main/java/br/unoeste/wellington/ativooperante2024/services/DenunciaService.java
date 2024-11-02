package br.unoeste.wellington.ativooperante2024.services;


import br.unoeste.wellington.ativooperante2024.db.entities.Denuncia;
import br.unoeste.wellington.ativooperante2024.db.entities.Orgao;
import br.unoeste.wellington.ativooperante2024.db.entities.Usuario;
import br.unoeste.wellington.ativooperante2024.db.repositories.DenunciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DenunciaService {

    @Autowired
    private DenunciaRepository denunciarepo;

    public Denuncia save(Denuncia denuncia)
    {
        return denunciarepo.save(denuncia);
    }

    public Denuncia getById(Long id)
    {
        return denunciarepo.findById(id).get();
    }

    public List<Denuncia> getAll()
    {
        return denunciarepo.findAll();
    }

    public List<Denuncia> getAllByUsuario(Usuario usuario)
    {
        return denunciarepo.findAllByUsuario(usuario);
    }

    public boolean delete(Long id)
    {
        // vai tentar deletar o orgao caso de certo ele nao entra no catch e não retorna falso, apenas retorna true no final;
        // se ele entrar no catch é porque ele não conseguiu deletar e então retorna falso para poder tratar isso, dar alguma resposta.
        try{

            denunciarepo.deleteById(id);
        }catch(Exception e) {
            return false;
        }
        return true;
    }
}
