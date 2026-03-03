package br.com.gvi.portinspection.domain.vessel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class RiskLevelTest {

    @Test
    void highRiskAfterFiveMonthsShoudBePriority1() {
        LocalDate lastInspection = LocalDate.now().minusMonths(5);

        Priority priority = RiskLevel.HIGHT
            .calculatePriority(lastInspection, LocalDate.now());

        assertEquals(Priority.P1, priority);
    }

    @Test
    void standardRiskWithSevenMonthsShouldBePriority2() {
        LocalDate lastInspection = LocalDate.now().minusMonths(7);

        Priority priority = RiskLevel.STANDARD
            .calculatePriority(lastInspection, LocalDate.now());
        
            assertEquals(Priority.P2, priority);
    }

    @Test
    void lowRiskWithFourMonthsShouldBePriority0() {
        LocalDate lastInspection = LocalDate.now().minusMonths(4);

        Priority priority = RiskLevel.LOW
            .calculatePriority(lastInspection, LocalDate.now());

        assertEquals(Priority.P0, priority);
    }

    @Test
    void neverInspectedShouldBePriority1() {

        Priority priority = RiskLevel.HIGHT
            .calculatePriority(null, LocalDate.now());
        
        assertEquals(Priority.P1, priority);
    }

}
