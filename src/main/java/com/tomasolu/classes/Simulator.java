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
    public static Memory memory;
    public static Cache cache;
    public static boolean canIssue = true;
    public static AddFU Adder;
    public static SubFU Subtractor;
    public static MulFU Multiplier;
    public static DivFU Divider;
    public static MemoryHandler memoryHandler;

    public static void init() {
        registerFile = new RegisterFile();
        addSubReservationStation = new ReservationStation(UserInputValues.getReservationStationAddSubSize());
        loadBuffer = new ReservationStation(UserInputValues.getLoadBufferSize());
        storeBuffer = new ReservationStation(UserInputValues.getStoreBufferSize());
        mulDivReservationStation = new ReservationStation(UserInputValues.getReservationStationMulDivSize());
        integerReservationStation = new ReservationStation(
                UserInputValues.getReservationStationAddSubIntegerSize());
        try {
            instructionQueue = new InstructionQueue(
                    InstructionParser.parseInstructions("src/main/resources/com/tomasolu/instructions.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        memory = new Memory(); // 1000 memory locations
        instructionQueue.printInstructions();
        // cache= new
        Adder = new AddFU();
        Subtractor = new SubFU();
        Multiplier = new MulFU();
        Divider = new DivFU();

    }

    public static void getUserInputs() {
        System.out.println("Welcome to the Tomasulo Simulator Configuration!");
        UserInputValues.initializeFromInput();

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

            if (canIssue) {
                canIssue = Issuer.issue();
            }

            // checkCanStartExecution(); // Check if any instruction can start execution and
            // decrement cycles in currently executing instructions
            // check has finished

            clockCycle++;
        }
        displayReservationStations();

    }
}
