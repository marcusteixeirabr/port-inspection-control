package br.com.gvi.portinspection.domain.enums;

/**
 * Prioridade de inspeção do navio.
 *
 * A prioridade combina o GrauRisco com o tempo desde a última
 * inspeção realizada pelo CIALA / Acordo de Viña del Mar.
 *
 * CRITÉRIOS (meses desde a última inspeção):
 *
 *   ALTO RISCO:
 *     P0 → 0 a 2 meses   (recém inspecionado, pode aguardar)
 *     P2 → 2 a 4 meses   (atenção)
 *     P1 → mais de 4 meses (prioridade de inspeção)
 *
 *   RISCO PADRÃO:
 *     P0 → 0 a 5 meses
 *     P2 → 5 a 10 meses
 *     P1 → mais de 10 meses
 *
 *   BAIXO RISCO:
 *     P0 → 0 a 9 meses
 *     P2 → 9 a 18 meses
 *     P1 → mais de 18 meses
 *
 * Nota: P1 = maior prioridade de inspeção (mais urgente)
 *        P2 = prioridade média
 *        P0 = sem prioridade imediata
 */
public enum Prioridade {

    P1(
        "Prioridade I",
        "Inspeção altamente recomendada. Intervalo máximo excedido.",
        "prioridade-1"
    ),
    P2(
        "Prioridade II",
        "Inspeção recomendada. Navio se aproxima do intervalo máximo.",
        "prioridade-2"
    ),
    P0(
        "Sem Prioridade",
        "Navio inspecionado recentemente. Dentro do intervalo permitido.",
        "prioridade-0"
    );

    // ── campos ────────────────────────────────────────────────
    private final String label;
    private final String descricao;
    private final String classeCss;

    // ── construtor ────────────────────────────────────────────
    Prioridade(String label, String descricao, String classeCss) {
        this.label     = label;
        this.descricao = descricao;
        this.classeCss = classeCss;
    }

    // ── getters ───────────────────────────────────────────────
    public String getLabel()     { return label; }
    public String getDescricao() { return descricao; }
    public String getClasseCss() { return classeCss; }
}