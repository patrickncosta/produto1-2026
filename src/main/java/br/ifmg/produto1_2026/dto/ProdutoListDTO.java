package br.ifmg.produto1_2026.dto;

import br.ifmg.produto1_2026.entities.Categoria;
import br.ifmg.produto1_2026.entities.Produto;
import br.ifmg.produto1_2026.projections.ProdutoProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

public class ProdutoListDTO extends RepresentationModel<ProdutoListDTO> {
    @Schema(description = "identificador unico do sistema")
    private Long id;
    @Schema(description = "nome do produto")
    @Size(min=2,max=100,message = "nome do produto deve ter entre 2 e 100 caracteres")
    private  String nome;
    @Schema(description = "valor em reais do produto")
    @Positive(message = "o preço do produto deve ser positivo")
    private Double price;
    @Schema(description = "endereço eletronico da imagem")
    private String imgURL;

    @Schema(description = "lista das categorias que o produto pertence")
    private List<CategoriaDTO> categorias = new ArrayList<CategoriaDTO>();

    public ProdutoListDTO() {
    }

    public ProdutoListDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.price = produto.getPreco();
        this.imgURL = produto.getImgUrl();


    }

    public ProdutoListDTO(ProdutoProjection produto) {
        this.id = produto.getID();
        this.nome = produto.getNome();
        this.price = produto.getPreco();
        this.imgURL = produto.getImgUrl();


    }

    public ProdutoListDTO(Long id, String nome, String description, Double price, String imgURL) {
        this.id = id;
        this.nome = nome;
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
        return "ProdutoListDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", price=" + price +
                ", imgURL='" + imgURL + '\'' +
                '}';
    }
}

