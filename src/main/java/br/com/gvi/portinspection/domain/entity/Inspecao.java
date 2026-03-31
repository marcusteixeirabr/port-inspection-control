package br.com.gvi.portinspection.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Inspecao — entidade JPA.
 *
 * CONCEITO NOVO: @Entity
 * Uma classe anotada com @Entity é mapeada automaticamente
 * para uma tabela no banco de dados pelo Hibernate (JPA).
 *
 * Comparando com o que você já conhece:
 *   NavioForm.java    → DTO de entrada (dados do formulário)
 *   NavioResultado    → DTO de saída (dados calculados)
 *   Inspecao.java     → Entity (dados que PERSISTEM no banco)
 *
 * O Hibernate lê as anotações e cria a tabela assim:
 *
 *   CREATE TABLE inspecoes (
 *       id            BIGINT PRIMARY KEY AUTO_INCREMENT,
 *       nome_navio    VARCHAR(100) NOT NULL,
 *       data_inspecao DATE         NOT NULL
 *   );
 */
@Entity                          // marca como entidade JPA
@Table(name = "inspecoes")       // nome da tabela no banco
public class Inspecao {

    /**
     * Chave primária gerada automaticamente pelo banco.
     *
     * @Id: este campo é a chave primária
     * @GeneratedValue: o banco gera o valor (auto increment)
     * IDENTITY: estratégia compatível com H2 e PostgreSQL
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do navio inspecionado.
     *
     * @Column: personaliza o nome da coluna no banco.
     * nullable = false: equivalente ao NOT NULL do SQL.
     * length = 100: VARCHAR(100)
     *
     * As anotações de validação (@NotBlank etc.) funcionam
     * tanto para validar o formulário (Bean Validation)
     * quanto para documentar as regras do campo.
     */
    @NotBlank(message = "O nome do navio é obrigatório.")
    @Size(max = 100, message = "Nome muito longo (máximo 100 caracteres).")
    @Column(name = "nome_navio", nullable = false, length = 100)
    private String nomeNavio;

    /**
     * Data da inspeção.
     *
     * LocalDate: apenas data, sem hora — perfeito para inspeções.
     * @PastOrPresent: inspeção não pode ser futura.
     */
    @NotNull(message = "A data da inspeção é obrigatória.")
    @PastOrPresent(message = "A data não pode ser futura.")
    @Column(name = "data_inspecao", nullable = false)
    private LocalDate dataInspecao;

    // ── Construtores ──────────────────────────────────────────
    // JPA exige um construtor sem argumentos (pode ser protected)
    protected Inspecao() {}

    // Construtor de conveniência para criar uma inspeção nova
    public Inspecao(String nomeNavio, LocalDate dataInspecao) {
        this.nomeNavio    = nomeNavio;
        this.dataInspecao = dataInspecao;
    }

    // ── Getters e Setters ─────────────────────────────────────
    public Long getId()                    { return id; }

    public String getNomeNavio()           { return nomeNavio; }
    public void setNomeNavio(String n)     { this.nomeNavio = n; }

    public LocalDate getDataInspecao()     { return dataInspecao; }
    public void setDataInspecao(LocalDate d) { this.dataInspecao = d; }
}