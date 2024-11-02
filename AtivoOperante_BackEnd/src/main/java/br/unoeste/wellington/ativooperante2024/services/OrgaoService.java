package br.unoeste.wellington.ativooperante2024.services;

import br.unoeste.wellington.ativooperante2024.db.entities.Orgao;
import br.unoeste.wellington.ativooperante2024.db.repositories.OrgaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgaoService {

    @Autowired
    private OrgaoRepository repo;

    public Orgao save(Orgao orgao)
    {
        return repo.save(orgao);
    }

    public Orgao getById(Long id)
    {
        return repo.findById(id).get();
    }

    public List<Orgao> getAll()
    {
        return repo.findAll();
    }

    public boolean delete(Long id)
    {
        // vai tentar deletar o orgao caso de certo ele nao entra no catch e não retorna falso, apenas retorna true no final;
        // se ele entrar no catch é porque ele não conseguiu deletar e então retorna falso para poder tratar isso, dar alguma resposta.
        try{
            repo.deleteById(id);
        }catch(Exception e) {
            return false;
        }
        return true;
    }
}
