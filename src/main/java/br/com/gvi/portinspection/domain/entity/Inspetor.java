package br.com.gvi.portinspection.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Inspetor — entidade JPA para dados dos inspetores.
 *
 * Separada de Usuario por responsabilidade:
 *   Usuario → quem pode entrar no sistema (autenticação)
 *   Inspetor → dados profissionais cadastrados (domínio)
 *
 * O Hibernate vai criar:
 *   CREATE TABLE inspetores (
 *       id      BIGINT PRIMARY KEY AUTO_INCREMENT,
 *       nome    VARCHAR(100) NOT NULL,
 *       apelido VARCHAR(50)  NOT NULL
 *   );
 */
@Entity
@Table(name = "inspetores")
public class Inspetor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "Nome muito longo.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O apelido é obrigatório.")
    @Size(max = 50, message = "Apelido muito longo.")
    @Column(nullable = false, length = 50)
    private String apelido;

    // ── Construtores ──────────────────────────────────────────
    protected Inspetor() {}

    public Inspetor(String nome, String apelido) {
        this.nome    = nome;
        this.apelido = apelido;
    }

    // ── Getters e Setters ─────────────────────────────────────
    public Long getId()              { return id; }

    public String getNome()          { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getApelido()             { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }
}