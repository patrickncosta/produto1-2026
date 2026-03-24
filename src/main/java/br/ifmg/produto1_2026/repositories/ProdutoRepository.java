package br.ifmg.produto1_2026.repositories;

import br.ifmg.produto1_2026.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}