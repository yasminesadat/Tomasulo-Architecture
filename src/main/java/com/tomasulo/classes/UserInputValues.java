package com.tomasulo.classes;

import java.util.Scanner;

public class UserInputValues {

    // Static variables to store the configuration values
    public static int storeLatency;
    public static int loadLatency;
    public static int addLatency;
    public static int mulLatency;
    public static int subLatency;
    public static int divLatency;
    public static int addIntegerLatency = 1;
    public static int subIntegerLatency = 1;
    public static int branchLatency = 1;
    public static int cacheBlockSize;
    public static int cacheSize;
    public static int reservationStationAddSubSize;
    public static int reservationStationMulDivSize;
    public static int reservationStationAddSubIntegerSize;
    public static int loadBufferSize;
    public static int storeBufferSize;

    /**
     * Method to take user input and initialize the values.
     */
    public static void initializeFromInput() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Store Latency: ");
        storeLatency = sc.nextInt();

        System.out.print("Enter Load Latency: ");
        loadLatency = sc.nextInt();

        System.out.print("Enter Add Latency: ");
        addLatency = sc.nextInt();

        System.out.print("Enter Mul Latency: ");
        mulLatency = sc.nextInt();

        System.out.print("Enter Sub Latency: ");
        subLatency = sc.nextInt();

        System.out.print("Enter Div Latency: ");
        divLatency = sc.nextInt();
        System.out.print("Enter Cache Block Size (in bytes): ");
        cacheBlockSize = sc.nextInt();

        System.out.print("Enter Cache Size (in bytes): ");
        cacheSize = sc.nextInt();

        System.out.print("Enter ADD/SUB Reservation Station Size: ");
        reservationStationAddSubSize = sc.nextInt();

        System.out.print("Enter MUL/DIV Reservation Station Size: ");
        reservationStationMulDivSize = sc.nextInt();

        System.out.print("Enter ADDI/SUBI Reservation Station Size: ");
        reservationStationAddSubIntegerSize = sc.nextInt();

        System.out.print("Enter Load Buffer Size: ");
        loadBufferSize = sc.nextInt();

        System.out.print("Enter Store Buffer Size: ");
        storeBufferSize = sc.nextInt();

        sc.close();

        System.out.println("\nConfiguration initialized successfully!");
    }

    public static int getStoreLatency() {
        return storeLatency;
    }

    public static int getLoadLatency() {
        return loadLatency;
    }

    public static int getAddLatency() {
        return addLatency;
    }

    public static int getMulLatency() {
        return mulLatency;
    }

    public static int getSubLatency() {
        return subLatency;
    }

    public static int getDivLatency() {
        return divLatency;
    }

    public static int getAddIntegerLatency() {
        return addIntegerLatency;
    }

    public static int getSubIntegerLatency() {
        return subIntegerLatency;
    }

    public static int getBranchLatency() {
        return branchLatency;
    }

    public static int getCacheBlockSize() {
        return cacheBlockSize;
    }

    public static int getCacheSize() {
        return cacheSize;
    }

    public static int getReservationStationAddSubSize() {
        return reservationStationAddSubSize;
    }

    public static int getReservationStationMulDivSize() {
        return reservationStationMulDivSize;
    }

    public static int getReservationStationAddSubIntegerSize() {
        return reservationStationAddSubIntegerSize;
    }

    public static int getLoadBufferSize() {
        return loadBufferSize;
    }

    public static int getStoreBufferSize() {
        return storeBufferSize;
    }

    public static void printValues() {
        System.out.println("Store Latency: " + storeLatency);
        System.out.println("Load Latency: " + loadLatency);
        System.out.println("Add Latency: " + addLatency);
        System.out.println("Mul Latency: " + mulLatency);
        System.out.println("Sub Latency: " + subLatency);
        System.out.println("Div Latency: " + divLatency);
        System.out.println("ADDI Latency: " + addIntegerLatency);
        System.out.println("SUBI Latency: " + subIntegerLatency);
        System.out.println("Branch Latency: " + branchLatency);
        System.out.println("Cache Block Size: " + cacheBlockSize + " bytes");
        System.out.println("Cache Size: " + cacheSize + " bytes");
        System.out.println("ADD/SUB Reservation Station Size: " + reservationStationAddSubSize);
        System.out.println("MUL/DIV Reservation Station Size: " + reservationStationMulDivSize);
        System.out.println("ADDI/SUBI Reservation Station Size: " + reservationStationAddSubIntegerSize);
        System.out.println("Load Buffer Size: " + loadBufferSize);
        System.out.println("Store Buffer Size: " + storeBufferSize);
    }
}
