package com.tomasolu.classes;

import java.util.Vector;

public class ReservationStation {

    private Vector<ReservationStationEntry> entries;
    private int freeSpaces;

    public ReservationStation(int size) {
        this.entries = new Vector<>(size);
        for (int i = 0; i < size; i++) {
            entries.add(new ReservationStationEntry());

        }
        this.freeSpaces = size;
        // System.out.println("Reservation Station initialized with capacity " + size);

    }

    public boolean hasSpace() {

        return freeSpaces > 0;
    }

    public int addEntry(String ReservationStationPrefix, int address, double vj, double vk, String qj, String qk,
            Instruction currInstruction, FunctionalUnit functionalUnit) {
        {
            for (int i = 0; i < entries.size(); i++) {
                if (entries.get(i).busy == false) {

                    entries.get(i).setAddress(address);
                    if (qj.equals("0")) {
                        entries.get(i).setVj(vj);
                    }
                    if (qk.equals("0")) {
                        entries.get(i).setVk(vk);
                    }
                    entries.get(i).setQj(qj);
                    entries.get(i).setQk(qk);
                    entries.get(i).setCurrInstruction(currInstruction);
                    entries.get(i).setBusy(true);
                    entries.get(i).setTag(ReservationStationPrefix + i);
                    entries.get(i).setIssueTime(entries.get(i).currInstruction.issueTime);
                    entries.get(i).setFunctionalUnit(functionalUnit);
                    freeSpaces--;
                    return i;

                }
            }
            return 0;
        }
    }

    public Vector<ReservationStationEntry> getEntries() {
        return entries;
    }

    public boolean canStartExecution() {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).qj.equals("0") && entries.get(i).qk.equals("0")
                    && entries.get(i).currInstruction.getIssueTime() != Simulator.clockCycle) {
                entries.get(i).currInstruction.setStartTime(Simulator.clockCycle);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "ReservationStation [entries=" + entries + ", freeSpaces=" + freeSpaces + "]";
    }
}
