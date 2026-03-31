package br.com.gvi.portinspection.domain.enums;

/**
 * Grau de risco de um navio baseado na sua idade.
 *
 * Enum é perfeito aqui porque os graus são fixos e conhecidos.
 * Cada constante carrega seus próprios dados (label, descrição,
 * cor CSS) — assim a lógica de apresentação não fica espalhada
 * pelo código.
 *
 * CRITÉRIOS DE IDADE:
 *   ALTO    → mais de 20 anos
 *   PADRAO  → entre 10 e 20 anos
 *   BAIXO   → menos de 10 anos
 */
public enum GrauRisco {

    // nome(labelExibido, descricao, classeCss, idadeMinima, idadeMaxima)
    ALTO(
        "Alto Risco",
        "Navio com mais de 20 anos. Sujeito a inspeção expandida obrigatória.",
        "risco-alto",
        20, Integer.MAX_VALUE
    ),
    PADRAO(
        "Risco Padrão",
        "Navio entre 10 e 20 anos. Inspeção padrão conforme regime CIALA.",
        "risco-padrao",
        10, 19
    ),
    BAIXO(
        "Baixo Risco",
        "Navio com menos de 10 anos. Elegível para inspeção simplificada.",
        "risco-baixo",
        0, 9
    );

    // ── campos ────────────────────────────────────────────────
    private final String label;
    private final String descricao;
    private final String classeCss;   // usada diretamente no HTML via Thymeleaf
    private final int idadeMin;
    private final int idadeMax;

    // ── construtor ────────────────────────────────────────────
    GrauRisco(String label, String descricao, String classeCss,
              int idadeMin, int idadeMax) {
        this.label     = label;
        this.descricao = descricao;
        this.classeCss = classeCss;
        this.idadeMin  = idadeMin;
        this.idadeMax  = idadeMax;
    }

    // ── getters ───────────────────────────────────────────────
    public String getLabel()     { return label; }
    public String getDescricao() { return descricao; }
    public String getClasseCss() { return classeCss; }

    /**
     * Fábrica estática: dado a idade do navio, retorna o GrauRisco correto.
     * Percorre todos os valores do enum até encontrar o que cobre aquela idade.
     *
     * Usar um método estático no enum mantém a lógica de classificação
     * encapsulada — quem precisar saber o grau de risco chama isso,
     * não reimplementa a regra espalhada pelo código.
     */
    public static GrauRisco calcular(int idadeAnos) {
        for (GrauRisco grau : values()) {
            if (idadeAnos >= grau.idadeMin && idadeAnos <= grau.idadeMax) {
                return grau;
            }
        }
        // fallback de segurança — não deveria acontecer com validação no form
        return ALTO;
    }
}