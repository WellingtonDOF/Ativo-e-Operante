package br.unoeste.wellington.ativooperante2024.db.repositories;

import br.unoeste.wellington.ativooperante2024.db.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByEmail(String email);
    public Usuario findByCpf(String cpf);

}
