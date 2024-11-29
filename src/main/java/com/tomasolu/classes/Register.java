package com.tomasolu.classes;

public class Register {
    double value;
    String Q;
    // has value and Q field , value either double or integer depending
    // name is index

    public Register(double value, String Q) {
        this.value = value;
        this.Q = Q;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    public boolean isReady() {
        return Q.equals("0");
    }

}
