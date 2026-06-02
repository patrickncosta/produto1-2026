package br.ifmg.produto1_2026.dto;

import br.ifmg.produto1_2026.entities.Role;

public class RoleDTO {
    private Long id;
    private String nome;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.nome = role.getAuthority();
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

    public void setNome(String autoridade) {
        this.nome = autoridade;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}

