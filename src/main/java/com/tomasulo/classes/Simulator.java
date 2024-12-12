package com.tomasulo.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simulator {

    public static int clockCycle = 1;
    public static RegisterFile registerFile;
    public static InstructionQueue instructionQueue;
    public static ReservationStation addSubReservationStation;
    public static ReservationStation loadBuffer;
    public static ReservationStation storeBuffer;
    public static ReservationStation mulDivReservationStation;
    public static ReservationStation integerReservationStation;
    public static double[] memory;
    public static Cache cache;
    public static Bus bus;
    public static int pc = 0;
    public static boolean isBranchTaken = false;

    public static int getClockCycle() {
        return clockCycle;
    }

    public static RegisterFile getRegisterFile() {
        return registerFile;
    }

    public static InstructionQueue getInstructionQueue() {
        InstructionQueue clonedQueue = new InstructionQueue();
        InstructionQueue originalQueue = instructionQueue;

        // Create a temporary list to hold the instructions
        List<Instruction> instructionList = new ArrayList<>();

        // Dequeue all instructions from the original queue and add them to the list
        while (originalQueue.size() > 0) {
            Instruction instruction = originalQueue.dequeueInstruction();
            instructionList.add(instruction);
        }

        // Requeue all instructions back to the original queue
        for (Instruction instruction : instructionList) {
            originalQueue.enqueueInstruction(instruction);
        }

        // Add all instructions from the list to the cloned queue
        for (Instruction instruction : instructionList) {
            clonedQueue.enqueueInstruction(instruction);
        }

        return clonedQueue;
    }

    public static ReservationStation getAddSubReservationStation() {
        return addSubReservationStation;
    }

    public static ReservationStation getLoadBuffer() {
        return loadBuffer;
    }

    public static ReservationStation getStoreBuffer() {
        return storeBuffer;
    }

    public static ReservationStation getMulDivReservationStation() {
        return mulDivReservationStation;
    }

    public static ReservationStation getIntegerReservationStation() {
        return integerReservationStation;
    }

    public static double[] getMemory() {
        return memory;
    }

    public static Cache getCache() {
        return cache;
    }

    public static Bus getBus() {
        return bus;
    }

    public int getPc() {
        return pc;
    }

    public static void init() {
        registerFile = new RegisterFile();
        addSubReservationStation = new ReservationStation(UserInputValues.getReservationStationAddSubSize(),
                ReservationStationType.ADDSUB);
        loadBuffer = new ReservationStation(UserInputValues.getLoadBufferSize(), ReservationStationType.LOAD);
        storeBuffer = new ReservationStation(UserInputValues.getStoreBufferSize(), ReservationStationType.STORE);
        mulDivReservationStation = new ReservationStation(UserInputValues.getReservationStationMulDivSize(),
                ReservationStationType.MULDIV);
        integerReservationStation = new ReservationStation(
                UserInputValues.getReservationStationAddSubIntegerSize(), ReservationStationType.INTEGER);
        try {
            instructionQueue = new InstructionQueue();
            instructionQueue.loadInstruction(
                    InstructionParser.parseInstructions("src/main/resources/com/tomasulo/instructions.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bus = new Bus();
        memory = new double[1000];
        instructionQueue.printInstructions();

    }

    public static void getUserInputs() {
        System.out.println("Welcome to the Tomasulo Simulator Configuration!");
        UserInputValues.initializeFromInput();
    }

    public static int getWaitingStation(String qi) {
        int waitingStations = 0;
        for (ReservationStationEntry entry : addSubReservationStation.getEntries()) {
            String qj = entry.getQj();
            String qk = entry.getQk();
            if (qi.equals(qj) || qi.equals(qk)) {
                waitingStations++;
            }
        }
        for (ReservationStationEntry entry : mulDivReservationStation.getEntries()) {
            String qj = entry.getQj();
            String qk = entry.getQk();
            if (qi.equals(qj) || qi.equals(qk)) {
                waitingStations++;
            }
        }
        for (ReservationStationEntry entry : storeBuffer.getEntries()) {
            String qj = entry.getQj();

            if (qi.equals(qj)) {
                waitingStations++;
            }
        }

        for (ReservationStationEntry entry : integerReservationStation.getEntries()) {
            String qj = entry.getQj();
            String qk = entry.getQk();
            if (qi.equals(qj) || qi.equals(qk)) {
                waitingStations++;
            }
        }

        return waitingStations;
    }

    /**
     * Main method to run the simulator.
     * 
     * @param args
     * @throws IOException
     */
    public static void displayReservationStations() {
        System.out.println("------------------------------------------------------------------------------------");

        System.out.println(addSubReservationStation);
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println(mulDivReservationStation);
        System.out.println("------------------------------------------------------------------------------------");

        System.out.println(integerReservationStation);
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println(loadBuffer);
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println(storeBuffer);
        System.out.println("------------------------------------------------------------------------------------");
    }

    public static boolean endSystem() {
        boolean noMoreInstructions = instructionQueue.size() <= Simulator.pc;
        boolean allStationsAndBuffersEmpty = addSubReservationStation.isEmpty() && mulDivReservationStation.isEmpty()
                && storeBuffer.isEmpty() && loadBuffer.isEmpty()
                && integerReservationStation.isEmpty();

        return noMoreInstructions && allStationsAndBuffersEmpty;
    }

    public static boolean executeNextCycle() {

        System.out.println("size" + instructionQueue.size());
        if (instructionQueue.size() > Simulator.pc && !isBranchTaken) {
            Issuer.issue();
        }

        Executer.execute();
        WriteBack.writeBack();
        System.out.println("Clock Cycle: " + clockCycle + "PC is " + pc);
        // displayReservationStations();
        // registerFile.displayRegisterFiles();

        clockCycle++;

        // stopping condition for system
        return (endSystem());

    }

    public static void main(String[] args) throws IOException {

        // getUserInputs();
        // init();
        // // executeNextCycle();
        // while (clockCycle < 20) {

        // if (instructionQueue.size() > Simulator.pc) {
        // Issuer.issue();
        // }
        // Executer.execute();
        // WriteBack.writeBack();
        // System.out.println("Clock Cycle: " + clockCycle);
        // displayReservationStations();
        // System.out.println("End of Clock Cycle: " + clockCycle);
        // clockCycle++;

        // }
        // System.out.println("Done: " + clockCycle);
        // registerFile.displayRegisterFiles();
        // displayReservationStations();

    }
}
