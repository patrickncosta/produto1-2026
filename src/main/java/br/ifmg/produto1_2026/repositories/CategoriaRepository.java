package br.ifmg.produto1_2026.repositories;

import br.ifmg.produto1_2026.entities.Categoria;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
