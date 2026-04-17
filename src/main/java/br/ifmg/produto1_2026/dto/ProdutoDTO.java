package br.ifmg.produto1_2026.dto;

import br.ifmg.produto1_2026.entities.Categoria;
import br.ifmg.produto1_2026.entities.Produto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDTO extends RepresentationModel<ProdutoDTO> {
    @Schema(description = "identificador unico do sistema")
    private Long id;
    @Schema(description = "nome do produto")
    private  String nome;
    @Schema(description = "descrição detalhada do produto")
    private String description;
    @Schema(description = "valor em reais do produto")
    private Double price;
    @Schema(description = "endereço eletronico da imagem")
    private String imgURL;

    @Schema(description = "lista das categorias que o produto pertence")
    private List<CategoriaDTO> categorias = new ArrayList<CategoriaDTO>();

    public ProdutoDTO() {
    }

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.description = produto.getDescricao();
        this.price = produto.getPreco();
        this.imgURL = produto.getImgUrl();

        produto.getCategorias().forEach(
                cat -> this.categorias.add(new CategoriaDTO(cat))
        );
    }

    public ProdutoDTO(Long id, String nome, String description, Double price, String imgURL) {
        this.id = id;
        this.nome = nome;
        this.description = description;
        this.price = price;
        this.imgURL = imgURL;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imgURL='" + imgURL + '\'' +
                '}';
    }
}

