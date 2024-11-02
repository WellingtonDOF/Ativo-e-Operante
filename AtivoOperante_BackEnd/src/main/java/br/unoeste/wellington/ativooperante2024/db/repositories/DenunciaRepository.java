package br.unoeste.wellington.ativooperante2024.db.repositories;

import br.unoeste.wellington.ativooperante2024.db.entities.Denuncia;
import br.unoeste.wellington.ativooperante2024.db.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DenunciaRepository extends JpaRepository<Denuncia,Long> {
    public List<Denuncia> findAllByUsuario(Usuario usuario);//Retorna as denuncias que o usuario tem
}
