package com.tomasolu.classes;

public class ReservationStationEntry {
    // type (operation) already in instruction no need here -- ingnore
    // attributes
    String tag;
    boolean busy;
    int address;
    double vj;
    double vk;
    String qj;
    String qk;
    Instruction currInstruction;
    int issueTime;
    double result;
    FunctionalUnit functionalUnit;

    public ReservationStationEntry(String tag) {
        this.tag = tag;
        this.busy = false;
        this.address = 0;
        this.vj = 0;
        this.vk = 0;
        this.qj = "";
        this.qk = "";
        this.currInstruction = null;

    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public double getVj() {
        return vj;
    }

    public void setVj(double vj) {
        this.vj = vj;
    }

    public double getVk() {
        return vk;
    }

    public void setVk(double vk) {
        this.vk = vk;
    }

    public String getQj() {
        return qj;
    }

    public void setQj(String qj) {
        this.qj = qj;
    }

    public String getQk() {
        return qk;
    }

    public void setQk(String qk) {
        this.qk = qk;
    }

    public Instruction getCurrInstruction() {
        return currInstruction;
    }

    public void setCurrInstruction(Instruction currInstruction) {
        this.currInstruction = currInstruction;
    }

    public FunctionalUnit getFunctionalUnit() {
        return functionalUnit;
    }

    public void setFunctionalUnit(FunctionalUnit functionalUnit) {
        this.functionalUnit = functionalUnit;
    }

    public boolean canWrite() {
        if (this.qj.equals("0") && this.qk.equals("0")
                && this.currInstruction.getEndTime() < Simulator.clockCycle
                && this.currInstruction.issueTime != Simulator.clockCycle && this.busy)

        {
            System.out.println("True Remaining time now is" + (this.currInstruction.endTime - Simulator.clockCycle));
            return true;

        }
        if (this.busy) {
            System.out.println(" Remaining time now is" + (this.currInstruction.endTime - Simulator.clockCycle));
        }
        return false;
    }

    @Override
    public String toString() {
        return " [tag=" + tag + ", issueTime=" + issueTime + ", busy=" + busy + ", address=" + address + ", vj=" + vj
                + ", vk=" + vk + ", qj=" + qj + ", qk=" + qk + "]";
    }

    public void setIssueTime(int issueTime) {
        this.issueTime = issueTime;
    }

}
