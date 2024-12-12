package com.tomasulo.classes;

import javafx.css.SizeUnits;

public class WriteBack {

    public static void writeBack() {
        // write the value back and update kol el mo3tamedeen 3aleha
        // set busy to 0 (aka remove it from the reservation station)
        // set the value of the register to the value of the reservation station
        // set the Q of the register to 0
        int maxWaiting = 0;
        ReservationStationEntry writingBackEntry = null;
        ReservationStation desiredStation = null;
        for (ReservationStationEntry entry : Simulator.addSubReservationStation.getEntries()) {

            if (entry.canWrite()) {
                if (Simulator.getWaitingStation(entry.getTag()) >= maxWaiting) {
                    maxWaiting = Simulator.getWaitingStation(entry.getTag());
                    writingBackEntry = entry;
                    desiredStation = Simulator.addSubReservationStation;
                }
                System.out.println("Writing back: " + entry.tag + "pc:  " + entry.getCurrInstruction().pc);

            }

        }
        for (ReservationStationEntry entry : Simulator.mulDivReservationStation.getEntries()) {
            if (entry.canWrite()) {
                if (Simulator.getWaitingStation(entry.getTag()) >= maxWaiting) {
                    maxWaiting = Simulator.getWaitingStation(entry.getTag());
                    writingBackEntry = entry;
                    desiredStation = Simulator.mulDivReservationStation;
                }

            }

        }
        for (ReservationStationEntry entry : Simulator.integerReservationStation.getEntries()) {

            if (entry.canWrite()) {

                if (!entry.getCurrInstruction().getType().equals("BEQ")
                        && !entry.getCurrInstruction().getType().equals("BNE")) {
                    if (Simulator.getWaitingStation(entry.getTag()) >= maxWaiting) {
                        maxWaiting = Simulator.getWaitingStation(entry.getTag());
                        writingBackEntry = entry;
                        desiredStation = Simulator.integerReservationStation;

                    }
                } else {
                    // branch
                    System.out.println("hiiiiii" + entry.getCurrInstruction().getType());
                    System.out.println("hiiiiii" + entry.getFunctionalUnit().result);
                    if (entry.getCurrInstruction().getType() == "BEQ") {
                        if (entry.getFunctionalUnit().result == 0)
                            Simulator.pc = entry.getCurrInstruction().immediate;

                    }

                    if (entry.getCurrInstruction().getType() == "BNE") {
                        if (entry.getFunctionalUnit().result != 0)
                            System.out.println("Updating pc to " + Simulator.pc);
                        Simulator.pc = entry.getCurrInstruction().immediate;

                    }
                    Simulator.isBranchTaken = false;
                    entry.setBusy(false);
                    Simulator.integerReservationStation
                            .setFreeSpaces(Simulator.integerReservationStation.getFreeSpaces() + 1);
                    entry.currInstruction.setStartTime(-1);

                }

            }
        }
        for (ReservationStationEntry entry : Simulator.loadBuffer.getEntries()) {
            if (entry.canWrite()) {
                if (Simulator.getWaitingStation(entry.getTag()) >= maxWaiting) {
                    maxWaiting = Simulator.getWaitingStation(entry.getTag());
                    writingBackEntry = entry;
                    desiredStation = Simulator.loadBuffer;

                }

            }

        }
        if (writingBackEntry != null) {
            System.out.println("Writing back right now in cycle : " + Simulator.clockCycle + writingBackEntry.tag);
            writingBackEntry.setBusy(false);
            writingBackEntry.getCurrInstruction().setWriteTime(Simulator.clockCycle);
            desiredStation.setFreeSpaces(desiredStation.getFreeSpaces() + 1);
            Simulator.bus.setTag(writingBackEntry.tag);
            Simulator.bus.setValue(writingBackEntry.functionalUnit.result);
            writingBackEntry.currInstruction.setStartTime(-1);
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
