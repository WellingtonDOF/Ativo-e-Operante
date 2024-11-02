package br.unoeste.wellington.ativooperante2024.services;

import br.unoeste.wellington.ativooperante2024.db.entities.Denuncia;
import br.unoeste.wellington.ativooperante2024.db.entities.Tipo;
import br.unoeste.wellington.ativooperante2024.db.entities.Usuario;
import br.unoeste.wellington.ativooperante2024.db.repositories.DenunciaRepository;
import br.unoeste.wellington.ativooperante2024.db.repositories.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoService {

    @Autowired
    TipoRepository tiporepo;

    public Tipo save(Tipo tipo)
    {
        return tiporepo.save(tipo);
    }

    public Tipo getById(Long id)
    {
        return tiporepo.findById(id).get();
    }

    public List<Tipo> getAll()
    {
        return tiporepo.findAll();
    }
    public boolean delete(Long id)
    {
        // vai tentar deletar o orgao caso de certo ele nao entra no catch e não retorna falso, apenas retorna true no final;
        // se ele entrar no catch é porque ele não conseguiu deletar e então retorna falso para poder tratar isso, dar alguma resposta.
        try{
            tiporepo.deleteById(id);
        }catch(Exception e) {
            return false;
        }
        return true;
    }
}
