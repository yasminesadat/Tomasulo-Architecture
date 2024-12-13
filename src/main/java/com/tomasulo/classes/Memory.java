package com.tomasulo.classes;
public class Memory {

    private final byte[] memory;

    public Memory(int size) {
        this.memory = new byte[size];
    }

    public int getSize() {
        return memory.length;
    }

    public byte readByte(int address) {
        return memory[address];
    }

    public void writeByte(int address, byte value) {
        memory[address] = value;
    }

    public void writeInt(int address, int value) {
        for (int i = 0; i < 4; i++) {
            memory[address + i] = (byte) (value >>> (24 - i * 8));
        }
    }

    public void writeFloat(int address, float value) {
        writeInt(address, Float.floatToIntBits(value));
    }

    public void writeDouble(int address, double value) {
        long bits = Double.doubleToLongBits(value);
        for (int i = 0; i < 8; i++) {
            memory[address + i] = (byte) (bits >>> (56 - i * 8));
        }
    }

    public void displayMemory() {
        System.out.println("Memory Contents:");
        for (int i = 0; i < memory.length; i++) {
            // Print address and value in hexadecimal format
            System.out.printf("Address 0x%04X: 0x%02X%n", i, memory[i]);
        }
    }
}
