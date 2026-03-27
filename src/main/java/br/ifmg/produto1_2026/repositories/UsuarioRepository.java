package br.ifmg.produto1_2026.repositories;

import br.ifmg.produto1_2026.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}