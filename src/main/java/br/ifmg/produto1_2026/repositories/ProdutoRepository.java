package br.ifmg.produto1_2026.repositories;

import br.ifmg.produto1_2026.entities.Produto;
import br.ifmg.produto1_2026.projections.ProdutoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(nativeQuery = true,
    value = """
            SELECT
                 p.id,
                 p.nome,
                 p.preco,
                 p.img_url
             FROM tb_produto p\s
             INNER JOIN tb_produto_categoria pc ON pc.id_produto=p.id
             INNER JOIN tb_categoria c ON c.id = pc.id_categoria
             WHERE( (1,2,3) is NULL
                             OR
                     pc.id_categoria in (1,2,3)
                     )
                     AND
                     (
                     LOWER(p.nome) LIKE LOWER(CONCAT('%','BICI','%'))
                     )
            """,

    countName = """
            SELECT  COUNT(*)
            FROM(
            SELECT 
                p.id,
                p.nome,
                p.preco,
                p.img_url
            FROM tb_produto p 
            INNER JOIN tb_produto_categoria pc ON pc.id_produto=p.id
            INNER JOIN tb_categoria c ON c.id = pc.id_categoria
            WHERE( (1,2,3) is NULL
                            OR
                    pc.id_categoria in (1,2,3)
                    )
                    AND
                    (
                    LOWER(p.nome) LIKE LOWER(CONCAT('%','BICI','%'))
                    )
            ) as tb_result
            """)
    Page<ProdutoProjection> searchProdutos(List<Long> categoriaDTO, String name, Pageable pageable);
}