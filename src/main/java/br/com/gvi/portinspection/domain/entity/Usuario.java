package br.com.gvi.portinspection.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Usuario — entidade JPA para autenticação.
 *
 * CONCEITO: separar autenticação de domínio
 * Usuario cuida de login/senha — é quem o Spring Security conhece.
 * Inspetor cuida dos dados profissionais — é o domínio da aplicação.
 * São coisas diferentes e merecem tabelas diferentes.
 *
 * O Hibernate vai criar:
 *   CREATE TABLE usuarios (
 *       id       BIGINT PRIMARY KEY AUTO_INCREMENT,
 *       username VARCHAR(50)  NOT NULL UNIQUE,
 *       password VARCHAR(255) NOT NULL,
 *       role     VARCHAR(20)  NOT NULL
 *   );
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome de usuário para login.
     * unique = true: não pode ter dois usuários iguais.
     */
    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * Senha já com hash BCrypt.
     * NUNCA armazenamos senha em texto puro.
     * O BCrypt gera strings longas (~60 chars) — por isso VARCHAR(255).
     */
    @NotBlank
    @Column(nullable = false, length = 255)
    private String password;

    /**
     * Papel do usuário no sistema.
     * Por simplicidade usamos uma string: "ROLE_USER" ou "ROLE_ADMIN".
     * Em sistemas maiores isso viraria uma tabela separada.
     */
    @Column(nullable = false, length = 20)
    private String role = "ROLE_USER";

    // ── Construtores ──────────────────────────────────────────
    protected Usuario() {}

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        this.role     = "ROLE_USER";
    }

    // ── Getters ───────────────────────────────────────────────
    public Long getId()         { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }

    public void setPassword(String password) { this.password = password; }
}