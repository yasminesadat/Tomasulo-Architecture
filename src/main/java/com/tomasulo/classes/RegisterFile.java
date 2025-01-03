package com.tomasulo.classes;

import java.util.Arrays;

public class RegisterFile {
    // 2 lists of registers (object) of size 32 each
    Register[] R;
    Register[] F;

    public RegisterFile() {
        R = new Register[32];
        F = new Register[32];
        for (int i = 0; i < 32; i++) {
            R[i] = new Register(0, "0"); // integer value reg
            F[i] = new Register(getRandomNumber(), "0"); // float value reg

        }
        // R[5].setValue(47);
    }

    public double getRandomNumber() {

        double random = (Math.random() * 50);
        return Math.round(random * 10.0) / 10.0;
    }

    public Register getIntRegister(int index) { // name of reg is its index in arrau
        return R[index];
    }

    public Register getFloatRegister(int index) {
        return F[index];
    }

    public void setFloatRegister(int index, Register value) {
        F[index] = value;
        F[index].setQ("0");
    }

    public void setIntegerRegister(int index, Register value) {
        R[index] = value;
        R[index].setQ("0");
    }

    public Register[] getR() {
        return R;
    }

    public void setR(Register[] r) {
        R = r;
    }

    public Register[] getF() {
        return F;
    }

    public void setF(Register[] f) {
        F = f;
    }

    public void updateIntegerRegister(int index, double value, String Q) {
        if (R[index].isReady()) {
            R[index].setValue(value);
        } else {
            R[index].setQ(Q);
        }
    }

    public void updateFloatingRegister(int index, double value, String Q) {
        if (F[index].isReady()) {
            F[index].setValue(value);
        } else {
            F[index].setQ(Q);
        }
    }

    public void displayRegisterFiles() {
        for (int i = 0; i < 32; i++) {
            System.out.println("R" + i + " : " + R[i].toString());
        }
        for (int i = 0; i < 32; i++) {
            System.out.println("F" + i + " : " + F[i].toString());
        }
    }

}
