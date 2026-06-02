package br.ifmg.produto1_2026.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class PasswordRecover {
    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Instant expiration;

    public PasswordRecover(Long id, String token, String email, Instant expiration) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.expiration = expiration;
    }

    public PasswordRecover(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "PasswordRecover{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", email='" + email + '\'' +
                ", expiration=" + expiration +
                '}';
    }
}
