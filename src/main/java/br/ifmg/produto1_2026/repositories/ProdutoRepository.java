package br.ifmg.produto1_2026.repositories;

import br.ifmg.produto1_2026.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Categoria, Long> {

}