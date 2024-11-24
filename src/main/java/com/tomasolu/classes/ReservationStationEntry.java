package com.tomasolu.classes;

public class ReservationStationEntry {
    // type (operation) already in instruction no need here -- ingnore
    // attributes
    String tag;
    boolean busy;
    int address;
    double vj;
    double vk;
    String qj;
    String qk;
    Instruction currInstruction;
    FunctionalUnit functionalUnit; // executes this unit's kind of op
}
