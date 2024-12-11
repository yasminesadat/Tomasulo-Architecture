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
            Simulator.bus.setTag(writingBackEntry.tag);
            Simulator.bus.setValue(writingBackEntry.functionalUnit.result);
            sniffBus();
            // sniff bus

        }

    }

    public static void sniffBus() {

        Bus dataBus = Simulator.bus;

        for (ReservationStationEntry entry : Simulator.addSubReservationStation.getEntries()) {
            if (entry.getQj().equals(dataBus.tag)) {
                entry.setQj("0");
                entry.setVj(dataBus.value);
            }
            if (entry.getQk().equals(dataBus.tag)) {
                entry.setQk("0");
                entry.setVk(dataBus.value);
            }

        }

        for (ReservationStationEntry entry : Simulator.mulDivReservationStation.getEntries()) {
            if (entry.getQj().equals(dataBus.tag)) {
                entry.setQj("0");
                entry.setVj(dataBus.value);
            }
            if (entry.getQk().equals(dataBus.tag)) {
                entry.setQk("0");
                entry.setVk(dataBus.value);
            }

        }
        for (ReservationStationEntry entry : Simulator.integerReservationStation.getEntries()) {

            if (entry.getQj().equals(dataBus.tag)) {
                entry.setQj("0");
                entry.setVj(dataBus.value);
            }
            if (entry.getQk().equals(dataBus.tag)) {
                entry.setQk("0");
                entry.setVk(dataBus.value);
            }
        }

        for (ReservationStationEntry entry : Simulator.storeBuffer.getEntries()) {
            if (entry.getQj().equals(dataBus.tag)) {
                entry.setQj("0");
                entry.setVj(dataBus.value);
            }

        }
        for (Register register : Simulator.registerFile.F) {
            if (register.getQ().equals(dataBus.tag)) {
                register.setValue(dataBus.value);
                register.setQ("0");
            }

        }
        for (Register register : Simulator.registerFile.R) {
            if (register.getQ().equals(dataBus.tag)) {
                register.setValue(dataBus.value);
                register.setQ("0");
            }

        }

    }
}
