package com.tomasolu.classes;

import java.io.IOException;
import java.util.Vector;

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

    public static void main(String[] args) throws IOException {

        getUserInputs();
        init();
        while (instructionQueue.size() > 0) {

            if (canIssue) {
                canIssue = Issuer.issue();
            }

            clockCycle++;
        }
        System.out.println("------------------------------------------------------------------------------------");

        System.out.println(addSubReservationStation);
        System.out.println("------------------------------------------------------------------------------------");

        System.out.println(mulDivReservationStation);
        System.out.println("------------------------------------------------------------------------------------");

    }
}
