package com.tomasolu.classes;

public class Instruction {
    String type;
    String rs;
    String rt;
    String rd;
    int immediate;
    int startTime;
    int endTime;
    int issueTime;
    int writeTime;

    public String getType() {
        return type;
    }

    public String getRs() {
        return rs;
    }

    public String getRt() {
        return rt;
    }

    public String getRd() {
        return rd;
    }

    public int getImmediate() {
        return immediate;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getIssueTime() {
        return issueTime;
    }

    public int getWriteTime() {
        return writeTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public void setImmediate(int immediate) {
        this.immediate = immediate;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setIssueTime(int issueTime) {
        this.issueTime = issueTime;
    }

    public void setWriteTime(int writeTime) {
        this.writeTime = writeTime;
    }

    @Override
    public String toString() {
        return "Instruction [type=" + type + ", rs=" + rs + ", rt=" + rt + ", rd=" + rd + ", immediate=" + immediate
                + ", startTime=" + startTime + ", endTime=" + endTime + ", issueTime=" + issueTime + ", writeTime="
                + writeTime + "]";
    }

}
