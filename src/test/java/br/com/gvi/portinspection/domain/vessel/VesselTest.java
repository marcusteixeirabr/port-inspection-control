package br.com.gvi.portinspection.domain.vessel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class VesselTest {

    Vessel vessel = new Vessel(
        "9998767",
        "MSC ANNA", 
        "Panama", 
        2021, 
        "Container Carrier", 
        300, 
        48, 
        RiskLevel.HIGH, 
        LocalDate.of(2025, 4, 5));
    
    @Test
    void whenCreateVesselPriorityShoudBeCalculated() {
        assertEquals(Priority.P1, vessel.getPriority());
    }

    @Test
    void newVesselShouldBeActive() {
        assertTrue(vessel.isActive());
    }

    @Test
    void shouldDeactivateVessel() {
        vessel.deactivate();

        assertFalse(vessel.isActive());
    }

}
