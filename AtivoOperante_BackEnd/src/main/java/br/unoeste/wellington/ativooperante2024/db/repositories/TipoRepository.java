package br.unoeste.wellington.ativooperante2024.db.repositories;

import br.unoeste.wellington.ativooperante2024.db.entities.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoRepository extends JpaRepository<Tipo, Long> {
}
