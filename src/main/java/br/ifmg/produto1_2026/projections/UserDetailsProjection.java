package br.ifmg.produto1_2026.projections;

public interface UserDetailsProjection {

    String getUsername();
    String getPassword();
    String getRoleId();
    String getAuthority();
}
