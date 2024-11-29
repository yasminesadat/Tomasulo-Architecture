package com.tomasolu.classes;

public class Issuer {
    public static boolean issue() {
        Instruction instruction = Simulator.instructionQueue.peekInstruction();

        String instructionType = instruction.getType();
        switch (instructionType) {
            case InstructionType.ADD_DOUBLE_PRECISION: // ADD R1 R2 R3
            case InstructionType.ADD_SINGLE_PRECISION:
            case InstructionType.SUB_DOUBLE_PRECISION:
            case InstructionType.SUB_SINGLE_PRECISION:
                if (!Simulator.addSubReservationStation.hasSpace()) {
                    System.out.println("No space in reservation station");
                    return false;
                } else {

                    Simulator.instructionQueue.dequeueInstruction();
                    instruction.setIssueTime(Simulator.clockCycle);
                    Register Source1 = Simulator.registerFile
                            .getFloatRegister(Integer.parseInt(instruction.getRs().substring(1)));
                    Register Source2 = Simulator.registerFile
                            .getFloatRegister(Integer.parseInt(instruction.getRt().substring(1)));
                    Register Destination = Simulator.registerFile
                            .getFloatRegister(Integer.parseInt(instruction.getRd().substring(1)));

                    int index = Simulator.addSubReservationStation.addEntry(
                            "A", // prefix for tag
                            -1, // value for address unused here
                            Source1.getValue(),
                            Source2.getValue(),
                            Source1.getQ(),
                            Source2.getQ(),
                            instruction,
                            null);
                    Destination.setQ("A" + index); // register file updating

                }
                break;
            case InstructionType.MULTIPLY_DOUBLE_PRECISION:
            case InstructionType.MULTIPLY_SINGLE_PRECISION:
            case InstructionType.DIVIDE_DOUBLE_PRECISION:
            case InstructionType.DIVIDE_SINGLE_PRECISION:
                if (!Simulator.addSubReservationStation.hasSpace()) {
                    return false;
                } else {
                    Simulator.instructionQueue.dequeueInstruction();

                    instruction.setIssueTime(Simulator.clockCycle);
                    Register Source1 = Simulator.registerFile
                            .getFloatRegister(Integer.parseInt(instruction.getRs().substring(1)));
                    Register Source2 = Simulator.registerFile
                            .getFloatRegister(Integer.parseInt(instruction.getRt().substring(1)));
                    Register Destination = Simulator.registerFile
                            .getFloatRegister(Integer.parseInt(instruction.getRd().substring(1)));

                    int index = Simulator.mulDivReservationStation.addEntry(
                            "M", // prefix for tag
                            -1, // value for address unused here
                            Source1.getValue(),
                            Source2.getValue(),
                            Source1.getQ(),
                            Source2.getQ(),
                            instruction,
                            null);
                    Destination.setQ("M" + index); // register file updating

                }
                break;
            case InstructionType.ADD_IMMEDIATE: // ADDI R1 R1 100
            case InstructionType.SUB_IMMEDIATE:
            case InstructionType.BRANCH_EQUAL: // BEQ R1 R2 100
            case InstructionType.BRANCH_NOT_EQUAL:

                break;
            case InstructionType.LOAD_WORD: // LW R1 100
            case InstructionType.LOAD_DOUBLE_WORD:
            case InstructionType.LOAD_DOUBLE_PRECISION:
            case InstructionType.LOAD_SINGLE_PRECISION:
                if (!Simulator.addSubReservationStation.hasSpace()) {
                    return false;
                } else {

                }
                break;
            case InstructionType.STORE_WORD: // SW R1 100
            case InstructionType.STORE_DOUBLE_WORD:
            case InstructionType.STORE_DOUBLE_PRECISION:
            case InstructionType.STORE_SINGLE_PRECISION:

                break;

        }
        return true;
    }
}
