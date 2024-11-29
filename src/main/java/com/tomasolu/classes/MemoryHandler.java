package com.tomasolu.classes;

public class MemoryHandler {

    public void load(int address, int value) {
        Simulator.memory.memory[address] = value;
    }

    public void store(int address) {

    }
}
