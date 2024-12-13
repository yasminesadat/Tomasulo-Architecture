package com.tomasulo.classes;

public class InstructionStatus {
    private String type;
    private String rd;
    private String issueTime;
    private String startTime;
    private String endTime;
    private String writeTime;
    private String remainingTime;
    private String instructionType;

    public InstructionStatus(String type, String rd, String issueTime, String startTime, String endTime, String writeTime, String instructionType) {
        this.type = type;
        this.rd = rd;
        this.issueTime = issueTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.writeTime = writeTime;
        this.remainingTime = calculateRemainingTime(startTime, endTime);
        this.instructionType = instructionType;
    }


    public static String getInitialRemainingTime(String instructionType) {
        String remainingTime = "Null";
        switch (instructionType) {
            case "LW":
            case "LD":
            case "L.D":
            case "L.S":
            remainingTime= String.valueOf(UserInputValues.loadLatency);
            break;
            
            case "S.S":
            case "S.D":
            case "SW":
            case "SD":
            remainingTime= String.valueOf(UserInputValues.storeLatency);
            break;
            case "DADDI":
            case "DSUBI":
            remainingTime= "1";
            break;
            case "MUL.S":
            case "MUL.D":
            remainingTime= String.valueOf(UserInputValues.mulLatency);
            break;
            case "DIV.S":
            case "DIV.D":
            remainingTime= String.valueOf(UserInputValues.divLatency);
            break;
            case "BEQ":
            case "BNE":
            remainingTime= "1";
            break;
            case "ADD.S":
            case "ADD.D":
            remainingTime= String.valueOf(UserInputValues.addLatency);
            break;
            case "SUB.S":
            case "SUB.D":
            remainingTime= String.valueOf(UserInputValues.subLatency);
            break;
        }

        return remainingTime; 
    }

    private String calculateRemainingTime(String startTime, String endTime) {
        if (this.issueTime.equals(String.valueOf(Simulator.clockCycle))) {
            return "Null";
        }
        try {
            // if(instructionType.equals("LW") || instructionType.equals("LD") || instructionType.equals("L.D") || instructionType.equals("L.S")
            // || instructionType.equals("S.S") || instructionType.equals("S.D") || instructionType.equals("SW") || instructionType.equals("SD")) {
                
            // }

            int start = Integer.parseInt(startTime);
            int end = Integer.parseInt(endTime);
            return String.valueOf(end- start );
        } catch (NumberFormatException e) {
            return "Null";
        }
    }

    // Getter methods


    public String getType() {
        return type;
    }

    public String getRd() {
        return rd;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getWriteTime() {
        return writeTime;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }



}