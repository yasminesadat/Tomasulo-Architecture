package com.tomasulo.classes;

public class InstructionStatus {
    private int iteration;
    private String type;
    private String rd;
    private String issueTime;
    private String startTime;
    private String endTime;
    private String writeTime;
    private String remainingTime;

    public InstructionStatus(int iteration, String type, String rd, String issueTime, String startTime, String endTime, String writeTime) {
        this.iteration = iteration;
        this.type = type;
        this.rd = rd;
        this.issueTime = issueTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.writeTime = writeTime;
        this.remainingTime = calculateRemainingTime(startTime, endTime);
    }

    private String calculateRemainingTime(String startTime, String endTime) {
        if ("Null".equals(startTime) || "Null".equals(endTime)) {
            return "Null";
        }
        try {
            int start = Integer.parseInt(startTime);
            int end = Integer.parseInt(endTime);
            return String.valueOf(end- start );
        } catch (NumberFormatException e) {
            return "Null";
        }
    }

    // Getter methods
    public int getIteration() {
        return iteration;
    }

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