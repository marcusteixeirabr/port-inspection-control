package br.com.gvi.portinspection.domain.dto;

import br.com.gvi.portinspection.domain.enums.GrauRisco;
import br.com.gvi.portinspection.domain.enums.Prioridade;
import br.com.gvi.portinspection.domain.enums.TipoNavio;

import java.time.LocalDate;

/**
 * NavioResultado — DTO (Data Transfer Object) de SAÍDA.
 *
 * Esta classe transporta os dados calculados do Service para a View.
 * Ela combina:
 *   - Os dados originais do formulário (para exibição na página de resultado)
 *   - Os dados calculados pelo RiscoService (GrauRisco, Prioridade, etc.)
 *
 * Por que não reusar o NavioForm?
 *   Porque o form é a entrada e o resultado é a saída — misturá-los
 *   viola o princípio de responsabilidade única (SRP) e tornaria
 *   o código confuso quando as duas classes precisarem evoluir
 *   de formas diferentes.
 */
public class NavioResultado {

    // ── dados originais (vindos do formulário) ────────────────
    private String nome;
    private TipoNavio tipo;
    private Integer anoConstrucao;
    private LocalDate ultimaInspecao;

    // ── dados calculados (produzidos pelo RiscoService) ───────
    private int idadeAnos;
    private long mesesDesdeInspecao;
    private GrauRisco grauRisco;
    private Prioridade prioridade;

    // ── construtor completo ───────────────────────────────────
    // Usar um construtor com todos os campos força o Service a
    // fornecer todos os dados de uma vez, evitando objetos
    // parcialmente inicializados (estado inconsistente).
    public NavioResultado(String nome,
                          TipoNavio tipo,
                          Integer anoConstrucao,
                          LocalDate ultimaInspecao,
                          int idadeAnos,
                          long mesesDesdeInspecao,
                          GrauRisco grauRisco,
                          Prioridade prioridade) {
        this.nome               = nome;
        this.tipo               = tipo;
        this.anoConstrucao      = anoConstrucao;
        this.ultimaInspecao     = ultimaInspecao;
        this.idadeAnos          = idadeAnos;
        this.mesesDesdeInspecao = mesesDesdeInspecao;
        this.grauRisco          = grauRisco;
        this.prioridade         = prioridade;
    }

    // ── getters ───────────────────────────────────────────────
    // Somente getters — o resultado é imutável após criado.
    public String getNome()                    { return nome; }
    public TipoNavio getTipo()                 { return tipo; }
    public Integer getAnoConstrucao()          { return anoConstrucao; }
    public LocalDate getUltimaInspecao()       { return ultimaInspecao; }
    public int getIdadeAnos()                  { return idadeAnos; }
    public long getMesesDesdeInspecao()        { return mesesDesdeInspecao; }
    public GrauRisco getGrauRisco()            { return grauRisco; }
    public Prioridade getPrioridade()          { return prioridade; }
}