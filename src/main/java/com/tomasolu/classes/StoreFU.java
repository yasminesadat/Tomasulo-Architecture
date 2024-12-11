package com.tomasolu.classes;

public class StoreFU extends FunctionalUnit {

    @Override
    public void execute(double address, double value, String type) {
        Simulator.memory[(int) address] = value;
        System.out.println("Storing: " + value + " at address: " + address);
    }

}
