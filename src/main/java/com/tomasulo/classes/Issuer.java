package com.tomasulo.classes;

public class Issuer {
    public static boolean issue() {
        // issue instruction with id=pc if its at the front of the queue else go fetch
        // from instrution memory

        Instruction instruction = Simulator.instructionQueue.getPCInstruction(Simulator.pc);
        System.out.println("issuing now in cycle" + Simulator.clockCycle + " pc: " +
                instruction.pc);
        String instructionType = instruction.getType();
        System.out.println("Instruction type: " + instructionType);
        System.out.println("PC value is" + Simulator.pc);
        switch (instructionType) {
            case InstructionType.ADD_DOUBLE_PRECISION:
            case InstructionType.ADD_SINGLE_PRECISION:
            case InstructionType.SUB_DOUBLE_PRECISION:
            case InstructionType.SUB_SINGLE_PRECISION:
                return handleAddSub();
            case InstructionType.MULTIPLY_DOUBLE_PRECISION:
            case InstructionType.MULTIPLY_SINGLE_PRECISION:
            case InstructionType.DIVIDE_DOUBLE_PRECISION:
            case InstructionType.DIVIDE_SINGLE_PRECISION:
                return handleMulDiv();

            case InstructionType.ADD_IMMEDIATE:
            case InstructionType.SUB_IMMEDIATE:
                return handleIntegerOperations();
            case InstructionType.BRANCH_EQUAL:
            case InstructionType.BRANCH_NOT_EQUAL:
                return handleBranchOperations();

            case InstructionType.LOAD_WORD:
            case InstructionType.LOAD_DOUBLE_WORD:
            case InstructionType.LOAD_DOUBLE_PRECISION:
            case InstructionType.LOAD_SINGLE_PRECISION:
                return handleLoad();

            case InstructionType.STORE_WORD:
            case InstructionType.STORE_DOUBLE_WORD:
            case InstructionType.STORE_DOUBLE_PRECISION:
            case InstructionType.STORE_SINGLE_PRECISION:
                return handleStore();
        }

        System.out.println("PC NEW " + Simulator.pc);
        return true;
    }

    private static boolean handleAddSub() {
        ReservationStation reservationStation = Simulator.addSubReservationStation;
        if (!reservationStation.hasSpace()) {
            System.out.println("No space in reservation station");
            return false;
        } else {
            Instruction instruction = Simulator.instructionQueue.getPCInstruction(Simulator.pc);
            instruction.setIssueTime(Simulator.clockCycle);
            Register source1 = getRegister(instruction.getRs());
            Register source2 = getRegister(instruction.getRt());
            Register destination = getRegister(instruction.getRd());
            FunctionalUnit functionalUnit = new AddSubFU();
            int index = reservationStation.addEntry(-1, source1.getValue(), source2.getValue(),
                    source1.getQ(), source2.getQ(), instruction, functionalUnit);
            destination.setQ("A" + index); // Register file update
            Simulator.pc++;
        }
        return true;
    }

    private static boolean handleMulDiv() {
        ReservationStation reservationStation = Simulator.mulDivReservationStation;
        if (!reservationStation.hasSpace()) {
            System.out.println("No space in reservation station");
            return false;
        } else {

            Instruction instruction = Simulator.instructionQueue.getPCInstruction(Simulator.pc);
            instruction.setIssueTime(Simulator.clockCycle);
            Register source1 = getRegister(instruction.getRs());
            Register source2 = getRegister(instruction.getRt());
            Register destination = getRegister(instruction.getRd());
            FunctionalUnit functionalUnit = new MulDivFU();

            int index = reservationStation.addEntry(-1, source1.getValue(), source2.getValue(),
                    source1.getQ(), source2.getQ(), instruction, functionalUnit);
            destination.setQ("M" + index); // Register file update
            Simulator.pc++;
        }
        return true;
    }

    private static boolean handleIntegerOperations() {
        System.out.println("Handling integer operations");
        ReservationStation reservationStation = Simulator.integerReservationStation;
        if (!reservationStation.hasSpace()) {
            System.out.println("No space in reservation station");
            return false;
        } else {
            Instruction instruction = Simulator.instructionQueue.getPCInstruction(Simulator.pc);
            instruction.setIssueTime(Simulator.clockCycle);
            Register source1 = getRegister(instruction.getRs());
            int immediate = instruction.getImmediate();
            Register destination = getRegister(instruction.getRd());
            FunctionalUnit functionalUnit = new AddSubFU();
            int index = reservationStation.addEntry(-1, source1.getValue(), immediate,
                    source1.getQ(), "0", instruction, functionalUnit);
            destination.setQ("I" + index); // Register file update
            Simulator.pc++;

        }
        return true;
    }

    private static boolean handleBranchOperations() {
        ReservationStation reservationStation = Simulator.integerReservationStation;
        if (!reservationStation.hasSpace()) {
            System.out.println("No space in reservation station");
            return false;
        } else {
            Simulator.isBranchTaken = true;
            Instruction instruction = Simulator.instructionQueue.getPCInstruction(Simulator.pc);
            instruction.setIssueTime(Simulator.clockCycle);

            Register source1 = getRegister(instruction.getRs());
            Register source2 = getRegister(instruction.getRt());
            int address = instruction.getImmediate();
            FunctionalUnit functionalUnit = new AddSubFU();
            int index = reservationStation.addEntry(address, source1.getValue(), source2.getValue(),
                    source1.getQ(), source2.getQ(), instruction, functionalUnit);
            Simulator.pc++;

        }
        return true;
    }

    private static boolean handleLoad() {
        ReservationStation reservationStation = Simulator.loadBuffer;

        if (!reservationStation.hasSpace()) {
            System.out.println("No space in load buffer");
            return false;
        } else {
            Instruction instruction = Simulator.instructionQueue.getPCInstruction(Simulator.pc);
            instruction.setIssueTime(Simulator.clockCycle);
            Register destination = getRegister(instruction.getRd());
            int immediate = instruction.getImmediate();
            FunctionalUnit functionalUnit = new LoadFU();
            int index = reservationStation.addEntry(immediate, 0, 0, "0", "0", instruction, functionalUnit);
            destination.setQ("L" + index);
            Simulator.pc++;
        }
        return true;
    }

    private static boolean handleStore() {
        ReservationStation reservationStation = Simulator.storeBuffer;
        if (!reservationStation.hasSpace()) {
            System.out.println("No space in store buffer");
            return false;
        } else {
            Instruction instruction = Simulator.instructionQueue.getPCInstruction(Simulator.pc);
            instruction.setIssueTime(Simulator.clockCycle);
            Register source = getRegister(instruction.getRs());
            int immediate = instruction.getImmediate();
            FunctionalUnit functionalUnit = new StoreFU();

            int index = reservationStation.addEntry(immediate, source.getValue(), 0, source.getQ(), "0",
                    instruction, functionalUnit);
            Simulator.pc++;
        }
        return true;
    }

    // gets register from the F12 / R26 values etc
    public static Register getRegister(String register) {
        if (register.startsWith("R"))
            return Simulator.registerFile.getIntRegister(Integer.parseInt(register.substring(1)));
        else {
            return Simulator.registerFile.getFloatRegister(Integer.parseInt(register.substring(1)));
        }
    }
}
