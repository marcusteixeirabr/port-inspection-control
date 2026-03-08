package br.com.gvi.portinspection.domain.portcall;

import br.com.gvi.portinspection.domain.vessel.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class PortCallTest {

    private final Vessel vessel = new Vessel("Vessel Name", 100, 30);

    private final PortCall portCall = new PortCall(vessel, "Terminal A", VesselStatus.NAVIGATING, LocalDate.of(2024, 6, 1), null, null, null);

    @Test
    void whenCreatePortCallThrowExceptionIfVesselIsNull() {

        assertThrows(IllegalArgumentException.class, () -> new PortCall(null,  "PNAVE", VesselStatus.NAVIGATING, LocalDate.of(2024, 6, 1), null, null, null));
    }

    @Test
    void whenCreatePortCallThrowExceptionIfEstimatedArrivalDateIsNull() {

        assertThrows(IllegalArgumentException.class, () -> new PortCall(vessel, "JBS", VesselStatus.DRIFTING, null, null, null, null));
    }

    @Test
    void whenPortCallIsCreatedShouldCaptureVesselRiskSnapshot() {
        vessel.setRiskLevel(RiskLevel.LOW);
        portCall.registerRiskLevelSnapshot(vessel.getRiskLevel());

        assertEquals(portCall.getRiskLevelSnapshot(), RiskLevel.LOW);
    }

    @Test
    void whenPortCallIsCreatedShouldCaptureVesselPrioritySnapshot() {
        vessel.setPriority(Priority.P1);
        portCall.registerPrioritySnapshot(vessel.getPriority());

        assertEquals(portCall.getPrioritySnapshot(), Priority.P1);
    }

    @Test
    void whenSetEstimatedArrivalDateShouldStoreArrivalDate() {
        portCall.updateVesselStatus(VesselStatus.BERTHED);
        portCall.registerArrival(LocalDate.of(2024, 6, 5));

        assertEquals(portCall.getActualArrival(), LocalDate.of(2024, 6, 5));
    }


    @Test
    void whenSetEstimatedDepartureDateShouldStoreDepartureDate() {
        portCall.updateVesselStatus(VesselStatus.BERTHED);
        portCall.registerArrival(LocalDate.of(2024, 6,5));
        portCall.updateVesselStatus(VesselStatus.MANEUVRING);
        portCall.registerDeparture(LocalDate.of(2024, 6, 10));

        assertEquals(portCall.getActualDeparture(), LocalDate.of(2024, 6, 10));
    }

}
