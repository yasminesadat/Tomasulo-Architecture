package com.tomasolu.classes;

import java.io.IOException;

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
    int pc = 0;
    // public static AddFU Adder;
    // public static SubFU Subtractor;
    // public static MulFU Multiplier;
    // public static DivFU Divider;
    // public static MemoryHandler memoryHandler;

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
            instructionQueue = new InstructionQueue(
                    InstructionParser.parseInstructions("src/main/resources/com/tomasolu/instructions.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        memory = new double[1000];
        instructionQueue.printInstructions();

    }

    public static void getUserInputs() {
        System.out.println("Welcome to the Tomasulo Simulator Configuration!");
        UserInputValues.initializeFromInput();
    }

    public int getWaitingStation(String qi) {
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

    public static void main(String[] args) throws IOException {

        getUserInputs();
        init();
        while (instructionQueue.size() > 0) {
            Issuer.issue();

            // // checkCanStartExecution(); // Check if any instruction can start execution
            // and
            // // decrement cycles in currently executing instructions
            // //

            clockCycle++;
        }
        registerFile.displayRegisterFiles();
        displayReservationStations();

    }
}
