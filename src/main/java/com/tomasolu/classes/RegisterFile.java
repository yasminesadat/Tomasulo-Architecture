package com.tomasolu.classes;

public class RegisterFile {
    // 2 lists of registers (object) of size 32 each
    Register[] R;
    Register[] F;

    public RegisterFile() {
        R = new Register[32];
        F = new Register[32];
        for (int i = 0; i < 32; i++) {
            R[i] = new Register(0, "0"); // integer value reg
            F[i] = new Register(0.0, "0"); // float value reg
        }
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
}
