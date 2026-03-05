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
            RiskLevel.HIGHT,
            lastInspection,
            LocalDate.now()
        );

        assertEquals(Priority.P1, result);

    }


}
