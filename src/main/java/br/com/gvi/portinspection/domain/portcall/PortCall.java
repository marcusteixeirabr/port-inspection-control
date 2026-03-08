package br.com.gvi.portinspection.domain.portcall;

import br.com.gvi.portinspection.domain.vessel.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class PortCall {

    private static final AtomicInteger NEXT_PORT_CALL_ID = new AtomicInteger(1);

    private final int portCallID;
    private Vessel vessel;
    private String terminal;
    private VesselStatus vesselStatus;
    private PortCallStatus portCallStatus;
    private LocalDate estimatedArrivalDate;
    private LocalTime estimatedArrivalTime;
    private LocalDate estimatedDepartureDate;
    private LocalTime estimatedDepartureTime;
    private LocalDate actualArrival;
    private LocalDate actualDeparture;
    private RiskLevel riskLevelSnapshot;
    private Priority prioritySnapshot;
       
    public PortCall(Vessel vessel, String terminal, VesselStatus vesselStatus,
            LocalDate estimatedArrivalDate, LocalTime estimatedArrivalTime, LocalDate estimatedDepartureDate,
            LocalTime estimatedDepartureTime) {

        if (vessel == null || estimatedArrivalDate == null) {
            throw new IllegalArgumentException("Vessel or estimated arrival date cannot be null at PortCall creation");
        }
        
        this.portCallID = NEXT_PORT_CALL_ID.getAndIncrement();
        this.portCallStatus = PortCallStatus.PLANNED;
        this.riskLevelSnapshot = vessel.getRiskLevel();
        this.prioritySnapshot = vessel.getPriority();
        this.vessel = vessel;
        this.terminal = terminal;
        this.vesselStatus = vesselStatus;
        this.estimatedArrivalDate = estimatedArrivalDate;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.estimatedDepartureDate = estimatedDepartureDate;
        this.estimatedDepartureTime = estimatedDepartureTime;
    }

    public boolean isCurrentlyDocked () {
        return getVesselStatus().equals(VesselStatus.BERTHED);
    }

    public boolean isCurrentlyDeparted () {
        return (!isCurrentlyDocked() && getActualArrival() != null);
    }
    
    public void registerArrival(LocalDate estimatedArrivalDate) {
        if (isCurrentlyDocked()) {
             if (getActualArrival() == null) {
                registerActualArrival(estimatedArrivalDate);
            } else {
                throw new IllegalStateException("Vessel is already docked");
            }
        } else {
            updateEstimatedArrivalDate(estimatedArrivalDate);
        }
    }

    public void registerDeparture(LocalDate estimatedDepartureDate) {
        if (isCurrentlyDeparted()) {
            if (getActualDeparture() == null) {
                registerActualDeparture(estimatedDepartureDate);
            } else {
                throw new IllegalStateException("Vessel has already departed");
            }
        } else {
            updateEstimatedDepartureDate(estimatedDepartureDate);
        }
    }
    
    public Vessel getVessel() {
        return vessel;
    }

    public int getPortCallID() {
        return portCallID;
    }
    
    // public void setVessel(Vessel vessel) {
    //    this.vessel = vessel;
    //}
    
    public String getTerminal() {
        return terminal;
    }
    
    public void changeTerminal(String newTerminal) {
        this.terminal = newTerminal;
    }
    
    public VesselStatus getVesselStatus() {
        return vesselStatus;
    }
    
    public void updateVesselStatus(VesselStatus newVesselStatus) {
        this.vesselStatus = newVesselStatus;
    }
    
    public PortCallStatus getPortCallStatus() {
        return portCallStatus;
    }

    public void changePortCallStatus() {
        if (isCurrentlyDocked()) {
            this.portCallStatus = PortCallStatus.BERTHED;
        }
        if (!isCurrentlyDocked() && getActualArrival() != null) {
            this.portCallStatus = PortCallStatus.CLOSED;
        }
    }

    public LocalDate getEstimatedArrivalDate() {
        return estimatedArrivalDate;
    }

    public void updateEstimatedArrivalDate(LocalDate newEstimatedArrivalDate) {
        this.estimatedArrivalDate = newEstimatedArrivalDate;
    }

    public LocalTime getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    public void updateEstimatedArrivalTime(LocalTime newEstimatedArrivalTime) {
        this.estimatedArrivalTime = newEstimatedArrivalTime;
    }

    public LocalDate getEstimatedDepartureDate() {
        return estimatedDepartureDate;
    }

    public void updateEstimatedDepartureDate(LocalDate newEstimatedDepartureDate) {
        this.estimatedDepartureDate = newEstimatedDepartureDate;
    }

    public LocalTime getEstimatedDepartureTime() {
        return estimatedDepartureTime;
    }

    public void updateEstimatedDepartureTime(LocalTime newEstimatedDepartureTime) {
        this.estimatedDepartureTime = newEstimatedDepartureTime;
    }

    public LocalDate getActualArrival() {
        return actualArrival;
    }

    public void registerActualArrival(LocalDate actualArrival) {
        this.actualArrival = actualArrival;
    }

    public LocalDate getActualDeparture() {
        return actualDeparture;
    }

    public void registerActualDeparture(LocalDate actualDeparture) {
        this.actualDeparture = actualDeparture;
    }

    public RiskLevel getRiskLevelSnapshot() {
        return riskLevelSnapshot;
    }

    public void registerRiskLevelSnapshot(RiskLevel riskLevelSnapshot) {
        if (getRiskLevelSnapshot() == null) {
            this.riskLevelSnapshot = riskLevelSnapshot;
        } else {
            throw new IllegalStateException("Risk level snapshot has already been set");
        }
    }

    public Priority getPrioritySnapshot() {
        return prioritySnapshot;
    }

    public void registerPrioritySnapshot(Priority prioritySnapshot) {
        if (getPrioritySnapshot() == null) {
            this.prioritySnapshot = prioritySnapshot;
        } else {
            throw new IllegalStateException("Priority snapshot has already been set");
        }
    }

    @Override
    public String toString() {
        return "PortCall [portCallID=" + portCallID + ", vessel=" + vessel + ", terminal=" + terminal + ", vesselStatus=" + vesselStatus
                + ", portCallStatus=" + portCallStatus + ", estimatedArrivalDate=" + estimatedArrivalDate
                + ", estimatedArrivalTime=" + estimatedArrivalTime + ", estimatedDepartureDate="
                + estimatedDepartureDate + ", estimatedDepartureTime=" + estimatedDepartureTime + ", actualArrival="
                + actualArrival + ", actualDeparture=" + actualDeparture + ", riskLevelSnapshot=" + riskLevelSnapshot
                + ", prioritySnapshot=" + prioritySnapshot + ", isCurrentlyDocked()=" + isCurrentlyDocked()
                + ", isCurrentlyDeparted()=" + isCurrentlyDeparted() + ", getClass()=" + getClass() + ", getVessel()="
                + getVessel() + ", getTerminal()=" + getTerminal() + ", getVesselStatus()=" + getVesselStatus()
                + ", getPortCallStatus()=" + getPortCallStatus() + ", getEstimatedArrivalDate()="
                + getEstimatedArrivalDate() + ", getEstimatedArrivalTime()=" + getEstimatedArrivalTime()
                + ", getEstimatedDepartureDate()=" + getEstimatedDepartureDate() + ", hashCode()=" + hashCode()
                + ", getEstimatedDepartureTime()=" + getEstimatedDepartureTime() + ", getActualArrival()="
                + getActualArrival() + ", getActualDeparture()=" + getActualDeparture() + ", getRiskLevelSnapshot()="
                + getRiskLevelSnapshot() + ", getPrioritySnapshot()=" + getPrioritySnapshot() + ", toString()="
                + super.toString() + "]";
    }

}
