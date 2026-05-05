package br.ifmg.produto1_2026.dto;

import br.ifmg.produto1_2026.entities.Categoria;
import br.ifmg.produto1_2026.entities.Produto;
import br.ifmg.produto1_2026.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDTO {
    private Long id;
    @NotBlank(message="campo nome obrigatório")
    private String nome;
    private String telefone;
    @NotBlank(message = "Email obrigatório")
    @Email(message = "Email invalido")
    private String email;

    private List<RoleDTO> perfis = new ArrayList<RoleDTO>();

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.telefone = usuario.getTelefone();
        this.email = usuario.getEmail();

        usuario.getPerfis().forEach(
                perf -> this.perfis.add(new RoleDTO(perf))
        );
    }

    public UsuarioDTO(Long id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<RoleDTO> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<RoleDTO> perfis) {
        this.perfis = perfis;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

