package br.com.gvi.portinspection.domain.vessel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VesselTest {

    @Test
    void shouldCreateVessel() {

        Vessel vessel = new Vessel(
            "MSC ANNA",
            300,
            48
        );

        assertEquals("MSC ANNA", vessel.getName());
        assertEquals(300, vessel.getLength());
        assertEquals(48, vessel.getBeam());
    }

    @Test
    void newVesselShouldBeActive() {
        Vessel vessel = new Vessel(
            "ANNA EXPRESS",
            200,
            32
        );

        assertTrue(vessel.isActive());
    }

    @Test
    void shouldDeactivateVessel() {
        Vessel vessel = new Vessel(
            "HMM ANNA",
            198,
            30
        );

        vessel.deactivate();

        assertFalse(vessel.isActive());
    }

}
