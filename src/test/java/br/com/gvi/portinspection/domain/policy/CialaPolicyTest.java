package br.com.gvi.portinspection.domain.policy;

import br.com.gvi.portinspection.domain.vessel.Priority;
import br.com.gvi.portinspection.domain.vessel.RiskLevel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class CialaPolicyTest {

    private final CialaPolicy policy = new CialaPolicy();

    @Test
    void highRiskWithFiveMonthsShoudBePriority1() {

        LocalDate lastInspection = LocalDate.now().minusMonths(5);

        Priority result = policy.calculatePriority(
            RiskLevel.HIGH,
            lastInspection,
            LocalDate.now()
        );

        assertEquals(Priority.P1, result);
    }

    @Test
    void standardRiskWithSevenMonthsShoudBePriority2() {

        LocalDate lastInspection = LocalDate.now().minusMonths(7);

        Priority result = policy.calculatePriority(
            RiskLevel.STANDARD,
            lastInspection,
            LocalDate.now()
        );

        assertEquals(Priority.P2, result);
    }

    @Test
    void lowRiskWithFourMonthsShoudBePriority0() {
        
        LocalDate lastInspection = LocalDate.now().minusMonths(4);

        Priority result = policy.calculatePriority(
            RiskLevel.LOW,
            lastInspection,
            LocalDate.now()
        );

        assertEquals(Priority.P0, result);
    }

    @Test
    void vesselNeverInspectedShoudBePriority1() {
        
        Priority result = policy.calculatePriority(
            RiskLevel.HIGH,
            null,
            LocalDate.now()
        );

        assertEquals(Priority.P1, result);
    }

}
