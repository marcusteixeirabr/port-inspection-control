package br.com.gvi.portinspection.domain.portcall;

import br.com.gvi.portinspection.domain.vessel.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PortCallTest {

    private final Vessel vessel = new Vessel("Vessel Name", 100, 30);

    private final PortCall portCall = new PortCall(vessel);

    @Test
    void whenCreatePortCallShouldHaveVessel() {

        assertEquals(portCall.getVessel(), vessel);
    }

    @Test
    void whenVesselHasRiskLevelShouldSetRiskLevelSnapshot() {
        vessel.setRiskLevel(Vessel.RiskLevel.HIGH);

        assertEquals(portCall.getRiskLevelSnapshot(), RiskLevel.HIGH);
    }

    @Test
    void whenVesselHasPriorityShouldSetPrioritySnapshot() {
        vessel.setPriority(Vessel.Priority.LOW);

        assertEquals(portCall.getPrioritySnapshot(), Vessel.Priority.LOW);
    }

    @Test
    void whenSetDeparturePredictionShouldStoreDepartureDate() {
        portCall.setArrivalPrediction("2024-06-01T10:00:00Z");
        portCall.setDeparturePrediction("2024-06-05T15:00:00Z");

        assertEquals(portCall.getDeparturePrediction(), "2024-06-05T15:00:00Z");
    }

    @Test
    void whenUpdateTerminalShouldChangeTerminal() {
        portCall.setTerminal("Terminal A");
        portCall.setTerminal("Terminal B");

        assertEquals(portCall.getTerminal(), "Terminal B");
    }
    
    @Test
    void whenCreatePortCallWithNullVesselShouldThrowException() {

        assertThrows(IllegalArgumentException.class, () -> new PortCall(null));
    }

    @Test
    void whenCreatePortCallWithNullArrivalPredictionShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PortCall pc = new PortCall(vessel);
            pc.setArrivalPrediction(null);
        });
    }



}
