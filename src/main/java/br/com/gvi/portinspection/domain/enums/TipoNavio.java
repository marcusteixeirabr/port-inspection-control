package br.com.gvi.portinspection.domain.enums;

/**
 * Tipos de navio reconhecidos pelo regime PSC / CIALA.
 *
 * Cada tipo tem um label em português para exibição no formulário
 * e na página de resultado.
 *
 * Por que enum e não uma lista no banco?
 * Porque os tipos de navio para fins de PSC são definidos pelas
 * convenções IMO — eles não mudam com frequência e faz sentido
 * tê-los como constantes de domínio, não como dados configuráveis.
 */
public enum TipoNavio {

    GRANELEIRO("Graneleiro (Bulk Carrier)"),
    PETROLEIRO("Petroleiro (Oil Tanker)"),
    QUIMICO("Químico / Produto (Chemical Tanker)"),
    PORTA_CONTEINERES("Porta-Contêineres (Container Ship)"),
    CARGA_GERAL("Carga Geral (General Cargo)"),
    ROLL_ON_OFF("Ro-Ro / Roll-on Roll-off"),
    PASSAGEIROS("Navio de Passageiros"),
    FRIGORIFICO("Frigorífico (Reefer)"),
    REBOCADOR("Rebocador (Tug)"),
    DRAGA("Draga (Dredger)"),
    OUTROS("Outros");

    // ── campo ─────────────────────────────────────────────────
    private final String label;

    // ── construtor ────────────────────────────────────────────
    TipoNavio(String label) {
        this.label = label;
    }

    // ── getter ────────────────────────────────────────────────
    public String getLabel() { return label; }
}