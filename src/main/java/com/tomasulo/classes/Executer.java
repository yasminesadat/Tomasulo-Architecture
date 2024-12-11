package com.tomasulo.classes;

public class Executer {

    public static void execute() {
        Simulator.addSubReservationStation.canStartExecution();
        Simulator.mulDivReservationStation.canStartExecution();
        Simulator.integerReservationStation.canStartExecution();
        Simulator.loadBuffer.canStartExecution();
        Simulator.storeBuffer.canStartExecution();
        Simulator.integerReservationStation.canStartExecution();
    }
}
