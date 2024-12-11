package com.tomasolu.classes;

public class WriteBack {

    public static void writeBack() {
        // write the value back and update kol el mo3tamedeen 3aleha
        // set busy to 0 (aka remove it from the reservation station)
        // set the value of the register to the value of the reservation station
        // set the Q of the register to 0
        int maxWaiting = 0;
        ReservationStationEntry writingBackEntry = null;

        for (ReservationStationEntry entry : Simulator.addSubReservationStation.getEntries()) {
            if (entry.canWrite()) {
                if (Simulator.getWaitingStation(entry.getTag()) > maxWaiting) {
                    maxWaiting = Simulator.getWaitingStation(entry.getTag());
                    writingBackEntry = entry;
                }

            }

        }
        for (ReservationStationEntry entry : Simulator.mulDivReservationStation.getEntries()) {
            if (entry.canWrite()) {
                if (Simulator.getWaitingStation(entry.getTag()) > maxWaiting) {
                    maxWaiting = Simulator.getWaitingStation(entry.getTag());
                    writingBackEntry = entry;
                }

            }

        }
        for (ReservationStationEntry entry : Simulator.integerReservationStation.getEntries()) {
            if (entry.canWrite()) {
                if (Simulator.getWaitingStation(entry.getTag()) > maxWaiting) {
                    maxWaiting = Simulator.getWaitingStation(entry.getTag());
                    writingBackEntry = entry;
                }

            }

        }
        for (ReservationStationEntry entry : Simulator.loadBuffer.getEntries()) {
            if (entry.canWrite()) {
                if (Simulator.getWaitingStation(entry.getTag()) > maxWaiting) {
                    maxWaiting = Simulator.getWaitingStation(entry.getTag());
                    writingBackEntry = entry;
                }

            }

        }
        if (writingBackEntry != null) {
            writingBackEntry.setBusy(false);
            writingBackEntry.getCurrInstruction().setWriteTime(Simulator.clockCycle);
        }

    }
}
