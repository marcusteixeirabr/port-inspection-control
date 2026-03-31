package br.com.gvi.portinspection.domain.dto;

import br.com.gvi.portinspection.domain.enums.TipoNavio;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * NavioForm — DTO (Data Transfer Object) de ENTRADA.
 *
 * Esta classe representa exatamente o que o usuário preenche no formulário.
 * Ela NÃO é uma entidade de banco — é apenas um veículo de transporte de
 * dados do HTML até o Controller.
 *
 * Padrão DTO:
 *   - Separar o objeto do formulário (entrada) do objeto de domínio (negócio)
 *   - Permite validar os dados antes de qualquer processamento
 *   - Evita expor entidades de banco diretamente para o usuário
 *
 * Bean Validation (@NotBlank, @NotNull, etc.):
 *   - As anotações definem as regras de validação
 *   - O Spring as executa automaticamente quando o Controller
 *     usa @Valid no parâmetro do método
 *   - Se a validação falhar, o Spring rejeita a requisição e
 *     devolve o formulário com as mensagens de erro
 */
public class NavioForm {

    /**
     * Nome do navio.
     * @NotBlank: não pode ser nulo nem string vazia/somente espaços.
     */
    @NotBlank(message = "O nome do navio é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    private String nome;

    /**
     * Tipo do navio — vem do enum TipoNavio.
     * No HTML, o <select> envia o nome da constante (ex: "GRANELEIRO")
     * e o Spring converte automaticamente para o enum correspondente.
     * @NotNull: o usuário precisa selecionar um tipo.
     */
    @NotNull(message = "Selecione o tipo do navio.")
    private TipoNavio tipo;

    /**
     * Ano de construção do navio.
     * @Min / @Max: restringe a um intervalo plausível.
     */
    @NotNull(message = "O ano de construção é obrigatório.")
    @Min(value = 1900, message = "Ano de construção inválido (mínimo: 1900).")
    @Max(value = 2100, message = "Ano de construção inválido.")
    private Integer anoConstrucao;

    /**
     * Data da última inspeção PSC realizada pelo CIALA.
     * @NotNull: campo obrigatório.
     * @PastOrPresent: a inspeção não pode ter ocorrido no futuro.
     */
    @NotNull(message = "A data da última inspeção é obrigatória.")
    @PastOrPresent(message = "A data da inspeção não pode ser futura.")
    private LocalDate ultimaInspecao;

    // ── Getters e Setters ─────────────────────────────────────
    // O Thymeleaf e o Spring precisam dos getters para ler os valores
    // e dos setters para preencher o objeto com os dados do formulário.

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public TipoNavio getTipo() { return tipo; }
    public void setTipo(TipoNavio tipo) { this.tipo = tipo; }

    public Integer getAnoConstrucao() { return anoConstrucao; }
    public void setAnoConstrucao(Integer anoConstrucao) {
        this.anoConstrucao = anoConstrucao;
    }

    public LocalDate getUltimaInspecao() { return ultimaInspecao; }
    public void setUltimaInspecao(LocalDate ultimaInspecao) {
        this.ultimaInspecao = ultimaInspecao;
    }
}
