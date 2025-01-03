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

    public void canStartExecution() {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).qj.equals("0") && entries.get(i).qk.equals("0")
                    && entries.get(i).currInstruction.getIssueTime() != Simulator.clockCycle && entries.get(i).busy
                    && entries.get(i).currInstruction.getStartTime() == -1) {
                String insType = entries.get(i).currInstruction.getType();
                if(Simulator.stallLoad && (insType.equals( InstructionType.LOAD_WORD) || insType.equals(InstructionType.LOAD_DOUBLE_PRECISION) || insType.equals(InstructionType.LOAD_DOUBLE_WORD)
                || insType.equals(InstructionType.LOAD_SINGLE_PRECISION ) || insType.equals(InstructionType.STORE_DOUBLE_PRECISION) || insType.equals(InstructionType.STORE_DOUBLE_WORD) 
                || insType.equals(InstructionType.STORE_SINGLE_PRECISION)|| insType.equals(InstructionType.STORE_WORD))) {continue;}
                entries.get(i).currInstruction.setStartTime(Simulator.clockCycle);
                int endExecutionTime = 0;
                int latency = 0;
                double src1 = 0;
                double src2 = 0;
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
                        if (!Simulator.stallLoad){
                        int address = 0;
                        if (entries.get(i).currInstruction.rs.contains("R")){
                            address = (int)entries.get(i).vj;
                        }
                        else{
                            address= entries.get(i).address;
                        }
                        LoadFU functionalUnit = (LoadFU)entries.get(i).functionalUnit;
                        boolean hit = functionalUnit.execute(address, insType);
                        if (!hit){
                            Simulator.stallLoad = true ;
                            System.out.println("first load so miss");
                        }
                        latency = hit? UserInputValues.getLoadLatency(): UserInputValues.getLoadLatency()+4;
                    }
                        break;
                    // S.D F10, 0
                    case InstructionType.STORE_WORD:
                    case InstructionType.STORE_DOUBLE_WORD:
                    case InstructionType.STORE_DOUBLE_PRECISION:
                    case InstructionType.STORE_SINGLE_PRECISION:
                    if (!Simulator.stallLoad){
                        int address = 0;
                        if (entries.get(i).currInstruction.rd.contains("R")){
                            address = (int)entries.get(i).vk;
                        }
                        else{
                            address= entries.get(i).address;
                        }
                        String accessType = "";
                        switch(insType) {
                            case "SW": 
                            accessType = "LW"; 
                            break;
                            case "S.S":
                            accessType = "L.S";
                            break;
                            case "SD":
                            accessType = "LD"; 
                            break;
                            case "S.D":
                            accessType = "L.D"; 
                            break;
                        }
                        StoreFU functionalUnit = (StoreFU)entries.get(i).functionalUnit;
                        boolean hit = functionalUnit.tryAccess(address, accessType);
                        if (!hit){
                            Simulator.stallLoad = true ;
                            System.out.println("first store so miss and no load before ");
                        }
                        latency = hit? UserInputValues.getStoreLatency(): UserInputValues.getStoreLatency()+4;
                    }
                        
                        break;
                }
                endExecutionTime = entries.get(i).currInstruction.getStartTime() + latency - 1;
                entries.get(i).currInstruction.setEndTime(endExecutionTime);
                System.out.println("Instruction " + entries.get(i).currInstruction.type
                        + " started execution at clock cycle" + Simulator.clockCycle + " pc "
                        + entries.get(i).currInstruction.pc);

            }
        }

    }

    public void setFreeSpaces(int freeSpaces) {
        this.freeSpaces = freeSpaces;
    }

    @Override
    public String toString() {
        return "ReservationStation [entries=" + entries + ", freeSpaces=" + freeSpaces + "]";
    }
}
