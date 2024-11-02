package br.unoeste.wellington.ativooperante2024.services;


import br.unoeste.wellington.ativooperante2024.db.entities.Feedback;
import br.unoeste.wellington.ativooperante2024.db.entities.Usuario;
import br.unoeste.wellington.ativooperante2024.db.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedback;

    public Feedback save(Feedback feed)
    {
        return feedback.save(feed);
    }

    public Feedback getById(Long id)
    {
        Feedback feedback1 = feedback.findById(id).orElse(new Feedback());
        return feedback1;
    }

    public List<Feedback> getAll()
    {
        return feedback.findAll();
    }

    public boolean delete(Long id)
    {
        // vai tentar deletar o orgao caso de certo ele nao entra no catch e não retorna falso, apenas retorna true no final;
        // se ele entrar no catch é porque ele não conseguiu deletar e então retorna falso para poder tratar isso, dar alguma resposta.
        try{
            feedback.deleteById(id);
        }catch(Exception e) {
            return false;
        }
        return true;
    }
    public Feedback getDenFeed(Long id){return feedback.findByDenunciaId(id);}
}
