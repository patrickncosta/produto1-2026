package br.ifmg.produto1_2026.dto;

import br.ifmg.produto1_2026.entities.Role;

public class RoleDTO {
    private Long id;
    private String autoridade;

    public RoleDTO() {
    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.autoridade = role.getAutoridade();
    }

    public RoleDTO(Long id, String autoridade) {
        this.id = id;
        this.autoridade = autoridade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutoridade() {
        return autoridade;
    }

    public void setAutoridade(String autoridade) {
        this.autoridade = autoridade;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", autoridade='" + autoridade + '\'' +
                '}';
    }
}

