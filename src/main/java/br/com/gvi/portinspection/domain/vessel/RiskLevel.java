package br.com.gvi.portinspection.domain.vessel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum RiskLevel {

    LOW(9, 18),
    STANDARD(5, 8),
    HIGHT(2, 4);

    private final int validtyMonths;
    private final int priority2LimitMonths;

    RiskLevel(int validityMonths, int priority2LimitMonths) {
        this.validtyMonths = validityMonths;
        this.priority2LimitMonths = priority2LimitMonths;
    }

    public Priority calculatePriority(LocalDate lastInspectionDate, LocalDate today) {
        
        if (lastInspectionDate == null) {
            return Priority.P1; // nunca inspecionado = prioridade máxima
        }

        long months = ChronoUnit.MONTHS.between(lastInspectionDate, today);

        if (months <= validtyMonths) {
            return Priority.P0;
        }

        if (months <= priority2LimitMonths) {
            return Priority.P2;
        }

        return Priority.P1;
    }
}
