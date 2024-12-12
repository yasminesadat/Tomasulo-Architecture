package com.tomasulo.classes;

import java.util.ArrayList;
import java.util.List;

public class InstructionQueue {

    // List to hold the instructions
    private List<Instruction> instructions;

    /**
     * Constructor to initialize the InstructionQueue with an existing list.
     *
     * @param instructionsList The list of instructions to initialize the queue
     *                         with.
     */
    public InstructionQueue() {
        this.instructions = new ArrayList<Instruction>(); // Create a new list based on the input
    }
    public Instruction getPCInstruction(int address){
        Instruction instruction = instructions.get(address);
        Instruction newInstruction=new Instruction();
        newInstruction.setPc(instruction.getPc());
        newInstruction.setType(instruction.getType());
        newInstruction.setEndTime(-1);
        newInstruction.setStartTime(-1);
        newInstruction.setIssueTime(-1);
        newInstruction.setWriteTime(-1);
        newInstruction.setRd(instruction.getRd());
        newInstruction.setRs(instruction.getRs());
        newInstruction.setRt(instruction.getRt());
        newInstruction.setImmediate(instruction.getImmediate());
        return newInstruction;
    }

    /**
     * Removes and returns the first instruction from the queue.
     *
     * @return The first instruction, or null if the queue is empty.
     */
    public Instruction dequeueInstruction() {
        if (!instructions.isEmpty()) {
            return instructions.remove(0);
        }
        return null;
    }

    public Instruction peekInstruction() {
        if (!instructions.isEmpty()) {
            return instructions.get(0);
        }
        return null;
    }
    public void enqueueInstruction(Instruction instruction) {
             instructions.add(instruction);
    }

    public void loadInstruction(List<Instruction> instructionsList){
        this.instructions=new ArrayList<Instruction>(instructionsList);
    }

    /**
     * Gets the current size of the queue.
     *
     * @return The number of instructions in the queue.
     */
    public int size() {
        return instructions.size();
    }

    /**
     * Displays all instructions in the queue.
     */
    public void printInstructions() {
        System.out.println("Current Instructions in Queue:");
        for (Instruction instruction : instructions) {
            System.out.println(instruction);
        }
    }
    
}
