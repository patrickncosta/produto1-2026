package br.ifmg.produto1_2026.repositories;

import br.ifmg.produto1_2026.entities.Usuario;
import br.ifmg.produto1_2026.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByEmail(String email);

    @Query(nativeQuery = true,
            value = """
                    SELECT u.email as username,
                    u.senha as password,
                    p.id as roleId,
                    p.autoridade as authority
                    FROM tb_usuario u
                    INNER JOIN tb_usuario_role up ON up.id_usuario = u.id
                    INNER JOIN tb_role p ON p.id = up.id_perfil
                    WHERE u.email = :username
                    """)
    List<UserDetailsProjection> loadUserByUserName(String username);
}