package br.unoeste.wellington.ativooperante2024.db.repositories;

import br.unoeste.wellington.ativooperante2024.db.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Feedback findByDenunciaId(Long denunciaId);
}
