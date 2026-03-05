package br.com.gvi.portinspection.domain.vessel;

import java.time.LocalDate;

public class Vessel {

    private String imo;
    private String name;
    private String flag;
    private int yearBuilt;
    private String type;
    private int length;
    private int beam;
    private RiskLevel riskLevel;
    private Priority priority;
    private LocalDate lastInspectionDate;
    private boolean active;

    public Vessel(String name, int length, int beam) {
        this.name = name;
        this.length = length;
        this.beam = beam;
        this.active = true;
    }

    public String getImo() {
        return imo;
    }
    public void setImo(String imo) {
        this.imo = imo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public int getYearBuilt() {
        return yearBuilt;
    }
    public void setYearBuilt(int yearBuilt) {
        this.yearBuilt = yearBuilt;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getBeam() {
        return beam;
    }
    public void setBeam(int beam) {
        this.beam = beam;
    }
    public RiskLevel getRiskLevel() {
        return riskLevel;
    }
    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }
    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public LocalDate getLastInspectionDate() {
        return lastInspectionDate;
    }
    public void setLastInspectionDate(LocalDate lastInspectionDate) {
        this.lastInspectionDate = lastInspectionDate;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public void deactivate() {
        this.active = false;
    }

    @Override
    public String toString() {
    return "Vessel{" +
            "imo='" + imo + '\'' +
            ", name='" + name + '\'' +
            ", flag='" + flag + '\'' +
            ", type='" + type + '\'' +
            '}';
}

}
