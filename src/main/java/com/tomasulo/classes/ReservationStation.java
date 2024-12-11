package com.tomasulo.classes;

import java.util.Vector;

public class ReservationStation {

    private Vector<ReservationStationEntry> entries;
    private int freeSpaces;
    private ReservationStationType type;
    private int size;

    public ReservationStation(int size, ReservationStationType type) {
        this.entries = new Vector<>(size);
        for (int i = 0; i < size; i++) {
            switch (type) {
                case ADDSUB:
                    entries.add(new ReservationStationEntry("A" + i));
                    break;
                case MULDIV:
                    entries.add(new ReservationStationEntry("M" + i));
                    break;
                case STORE:
                    entries.add(new ReservationStationEntry("S" + i));

                    break;
                case LOAD:
                    entries.add(new ReservationStationEntry("L" + i));

                    break;
                case INTEGER:
                    entries.add(new ReservationStationEntry("I" + i));

                    break;

            }

        }
        this.size = size;
        this.type = type;
        this.freeSpaces = size;
        // System.out.println("Reservation Station initialized with capacity " + size);

    }

    public ReservationStationType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getFreeSpaces() {
        return freeSpaces;
    }

    public boolean hasSpace() {
        return freeSpaces > 0;
    }

    public boolean isEmpty() {
        return (freeSpaces == this.size);
    }

    public Vector<ReservationStationEntry> getEntries() {
        return entries;
    }

    public int addEntry(int address, double vj, double vk, String qj, String qk,
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
                    // entries.get(i).setTag(ReservationStationPrefix + i);
                    entries.get(i).setIssueTime(entries.get(i).currInstruction.issueTime);
                    entries.get(i).setFunctionalUnit(functionalUnit);
                    freeSpaces--;
                    return i;

                }
            }
            return 0;
        }
    }

    public boolean canStartExecution() {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).qj.equals("0") && entries.get(i).qk.equals("0")
                    && entries.get(i).currInstruction.getIssueTime() != Simulator.clockCycle && entries.get(i).busy
                    && entries.get(i).currInstruction.getStartTime() == -1) {
                entries.get(i).currInstruction.setStartTime(Simulator.clockCycle);
                int endExecutionTime = 0;
                int latency = 0;
                double src1 = 0;
                double src2 = 0;
                String insType = entries.get(i).currInstruction.getType();
                switch (insType) {
                    case InstructionType.ADD_DOUBLE_PRECISION:
                    case InstructionType.ADD_SINGLE_PRECISION:
                        src1 = entries.get(i).vj;
                        src2 = entries.get(i).vk;
                        entries.get(i).functionalUnit.execute(src1, src2, insType);
                        latency = UserInputValues.getAddLatency();
                        break;
                    case InstructionType.SUB_DOUBLE_PRECISION:
                    case InstructionType.SUB_SINGLE_PRECISION:
                        src1 = entries.get(i).vj;
                        src2 = entries.get(i).vk;
                        entries.get(i).functionalUnit.execute(src1, src2, insType);
                        latency = UserInputValues.getSubLatency();
                        break;
                    case InstructionType.MULTIPLY_DOUBLE_PRECISION:
                    case InstructionType.MULTIPLY_SINGLE_PRECISION:
                        src1 = entries.get(i).vj;
                        src2 = entries.get(i).vk;
                        entries.get(i).functionalUnit.execute(src1, src2, insType);
                        latency = UserInputValues.getMulLatency();
                        break;
                    case InstructionType.DIVIDE_DOUBLE_PRECISION:
                    case InstructionType.DIVIDE_SINGLE_PRECISION:
                        src1 = entries.get(i).vj;
                        src2 = entries.get(i).vk;
                        entries.get(i).functionalUnit.execute(src1, src2, insType);
                        latency = UserInputValues.getDivLatency();
                        break;
                    case InstructionType.ADD_IMMEDIATE:
                        src1 = entries.get(i).vj;
                        src2 = entries.get(i).vk;
                        entries.get(i).functionalUnit.execute(src1, src2, insType);
                        latency = UserInputValues.getAddIntegerLatency();
                        break;
                    case InstructionType.SUB_IMMEDIATE:
                        src1 = entries.get(i).vj;
                        src2 = entries.get(i).vk;
                        entries.get(i).functionalUnit.execute(src1, src2, insType);
                        latency = UserInputValues.getSubIntegerLatency();
                        break;
                    case InstructionType.BRANCH_EQUAL:
                    case InstructionType.BRANCH_NOT_EQUAL:
                        src1 = entries.get(i).vj;
                        src2 = entries.get(i).vk;
                        entries.get(i).functionalUnit.execute(src1, src2, insType);
                        latency = UserInputValues.getBranchLatency();
                        break;
                    case InstructionType.LOAD_WORD:
                    case InstructionType.LOAD_DOUBLE_WORD:
                    case InstructionType.LOAD_DOUBLE_PRECISION:
                    case InstructionType.LOAD_SINGLE_PRECISION:
                        int address = entries.get(i).address;
                        entries.get(i).functionalUnit.execute(address, 0.0, insType);
                        latency = UserInputValues.getLoadLatency();
                        break;
                    // S.D F10, 0
                    case InstructionType.STORE_WORD:
                    case InstructionType.STORE_DOUBLE_WORD:
                    case InstructionType.STORE_DOUBLE_PRECISION:
                    case InstructionType.STORE_SINGLE_PRECISION:
                        double value = entries.get(i).vj;
                        int addressStore = entries.get(i).address;
                        entries.get(i).functionalUnit.execute(addressStore, value, insType);
                        latency = UserInputValues.getStoreLatency();
                        break;
                }
                endExecutionTime = entries.get(i).currInstruction.getStartTime() + latency - 1;
                entries.get(i).currInstruction.setEndTime(endExecutionTime);
                System.out.println("Instruction " + entries.get(i).currInstruction.type
                        + " started execution at clock cycle" + Simulator.clockCycle);

                return true;
            }
        }
        return false;
    }

    public void setFreeSpaces(int freeSpaces) {
        this.freeSpaces = freeSpaces;
    }

    @Override
    public String toString() {
        return "ReservationStation [entries=" + entries + ", freeSpaces=" + freeSpaces + "]";
    }
}
