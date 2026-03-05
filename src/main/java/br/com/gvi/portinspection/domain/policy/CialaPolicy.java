package br.com.gvi.portinspection.domain.policy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import br.com.gvi.portinspection.domain.vessel.Priority;
import br.com.gvi.portinspection.domain.vessel.RiskLevel;

/**
 * Implementa a política fixa de cálculo de prioridade do CIALA.
 *
 * Esta classe contém exclusivamente regra de negócio regulatória.
 * Não depende de Spring, banco ou qualquer framework.
 *
 * É puro domínio.
 */
public class CialaPolicy {

    /**
     * Calcula a prioridade do navio com base:
     * - no nível de risco
     * - na data da última inspeção
     * - na data de referência (normalmente hoje)
     */
    public Priority calculatePriority(
        RiskLevel riskLevel,
        LocalDate lastInspectionDate,
        LocalDate referenceDate
    ) {
        // Se nunca foi inspecionado, consideramos prioridade máxima (P1)
        if (lastInspectionDate == null) {
            return Priority.P1;
        }

        // Calcula quantos meses se passaram desde a última inspeção
        long monthsSinceInspection =
            ChronoUnit.MONTHS.between(lastInspectionDate, referenceDate);

        // A decisão foi delegada para método específico por risco
        return switch (riskLevel) {
            case LOW -> calculateLowRisk(monthsSinceInspection);
            case STANDARD -> calculateStandardRisk(monthsSinceInspection);
            case HIGH -> calculateHighRisk(monthsSinceInspection);
        };
    }

    /**
     * Regras para risco BAIXO:
     * 0–9 meses  -> P0
     * 10–18 meses -> P2
     * >=19 meses -> P1
     */
    private Priority calculateLowRisk(long months) {
        if (months <= 9) {
            return Priority.P0;
        } else if (months <= 18) {
            return Priority.P2;
        } else {
            return Priority.P1;
        }
    }

    /**
     * Regras para risco PADRÃO:
     * 0–5 meses  -> P0
     * 6–8 meses  -> P2
     * >=9 meses -> P1
     */
    private Priority calculateStandardRisk(long months) {
        if (months <= 5) {
            return Priority.P0;
        } else if (months <= 8) {
            return Priority.P2;
        } else {
            return Priority.P1;
        }
    }

    /**
     * Regras para risco ALTO:
     * 0–2 meses  -> P0
     * 3–4 meses  -> P2
     * >=5 meses -> P1
     */
    private Priority calculateHighRisk(long months) {
        if (months <= 2) {
            return Priority.P0;
        } else if (months <= 4) {
            return Priority.P2;
        } else {
            return Priority.P1;
        }
    }
}
