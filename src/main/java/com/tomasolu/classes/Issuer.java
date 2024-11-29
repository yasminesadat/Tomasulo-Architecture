package com.tomasolu.classes;

public class Issuer {
    public static boolean issue() {
        Instruction instruction = Simulator.instructionQueue.peekInstruction();
        String instructionType = instruction.getType();

        switch (instructionType) {
            case InstructionType.ADD_DOUBLE_PRECISION:
            case InstructionType.ADD_SINGLE_PRECISION:

                return handleAddSub(instruction, Simulator.addSubReservationStation, Simulator.Adder);
            case InstructionType.SUB_DOUBLE_PRECISION:
            case InstructionType.SUB_SINGLE_PRECISION:
                return handleAddSub(instruction, Simulator.addSubReservationStation, Simulator.Subtractor);
            case InstructionType.MULTIPLY_DOUBLE_PRECISION:
            case InstructionType.MULTIPLY_SINGLE_PRECISION:

                return handleMulDiv(instruction, Simulator.mulDivReservationStation, Simulator.Multiplier);
            case InstructionType.DIVIDE_DOUBLE_PRECISION:
            case InstructionType.DIVIDE_SINGLE_PRECISION:
                return handleMulDiv(instruction, Simulator.mulDivReservationStation, Simulator.Divider);

            case InstructionType.ADD_IMMEDIATE:
            case InstructionType.SUB_IMMEDIATE:
            case InstructionType.BRANCH_EQUAL:
            case InstructionType.BRANCH_NOT_EQUAL:
                // return handleIntegerOperations(instruction,
                // Simulator.integerReservationStation);

            case InstructionType.LOAD_WORD:
            case InstructionType.LOAD_DOUBLE_WORD:
            case InstructionType.LOAD_DOUBLE_PRECISION:
            case InstructionType.LOAD_SINGLE_PRECISION:
                return handleLoad(instruction, Simulator.loadBuffer);

            case InstructionType.STORE_WORD:
            case InstructionType.STORE_DOUBLE_WORD:
            case InstructionType.STORE_DOUBLE_PRECISION:
            case InstructionType.STORE_SINGLE_PRECISION:
                // return handleStore(instruction, Simulator.storeBuffer);
        }

        return true;
    }

    private static boolean handleAddSub(Instruction instruction, ReservationStation reservationStation,
            FunctionalUnit functionalUnit) {
        if (!reservationStation.hasSpace()) {
            System.out.println("No space in reservation station");
            return false;
        } else {
            executeCommonLogic(instruction);
            Register source1 = getRegister(instruction.getRs());
            Register source2 = getRegister(instruction.getRt());
            Register destination = getRegister(instruction.getRd());

            int index = reservationStation.addEntry("A", -1, source1.getValue(), source2.getValue(),
                    source1.getQ(), source2.getQ(), instruction, functionalUnit);
            destination.setQ("A" + index); // Register file update
        }
        return true;
    }

    private static boolean handleMulDiv(Instruction instruction, ReservationStation reservationStation,
            FunctionalUnit functionalUnit) {
        if (!reservationStation.hasSpace()) {
            System.out.println("No space in reservation station");
            return false;
        } else {
            executeCommonLogic(instruction);
            Register source1 = getRegister(instruction.getRs());
            Register source2 = getRegister(instruction.getRt());
            Register destination = getRegister(instruction.getRd());

            int index = reservationStation.addEntry("M", -1, source1.getValue(), source2.getValue(),
                    source1.getQ(), source2.getQ(), instruction, functionalUnit);
            destination.setQ("M" + index); // Register file update
        }
        return true;
    }

    private static boolean handleIntegerOperations(Instruction instruction, ReservationStation reservationStation) {
        if (!reservationStation.hasSpace()) {
            System.out.println("No space in reservation station");
            return false;
        } else {
            executeCommonLogic(instruction);
            // Perform other logic specific to Integer operations (e.g., immediate values)
        }
        return true;
    }

    private static boolean handleLoad(Instruction instruction, ReservationStation reservationStation) {
        if (!reservationStation.hasSpace()) {
            System.out.println("No space in load buffer");
            return false;
        } else {
            executeCommonLogic(instruction);
            Register destination = getRegister(instruction.getRd());
            int immediate = instruction.getImmediate();
            int index = reservationStation.addEntry("L", immediate, 0, 0, "0", "0", instruction, null);
            destination.setQ("L" + index);
        }
        return true;
    }

    private static boolean handleStore(Instruction instruction, ReservationStation reservationStation) {
        if (!reservationStation.hasSpace()) {
            System.out.println("No space in store buffer");
            return false;
        } else {
            executeCommonLogic(instruction);
            Register source = getRegister(instruction.getRs());
            int immediate = instruction.getImmediate();
            int index = reservationStation.addEntry("S", immediate, 0, 0, "0", "0", instruction, null);
        }
        return true;
    }

    // happens always if we're issuing
    private static void executeCommonLogic(Instruction instruction) {
        Simulator.instructionQueue.dequeueInstruction();
        instruction.setIssueTime(Simulator.clockCycle);
    }

    // gets register from the F12 / R26 values etc
    private static Register getRegister(String register) {
        return Simulator.registerFile.getFloatRegister(Integer.parseInt(register.substring(1)));
    }
}
