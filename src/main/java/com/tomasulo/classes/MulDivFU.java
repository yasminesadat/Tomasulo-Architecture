package com.tomasulo.classes;

public class MulDivFU extends FunctionalUnit {

    @Override
    public void execute(double src1, double src2, String type) {

        switch (type) {
            case InstructionType.MULTIPLY_DOUBLE_PRECISION:
            case InstructionType.MULTIPLY_SINGLE_PRECISION:
                this.result = src1 * src2;
                break;
            case InstructionType.DIVIDE_DOUBLE_PRECISION:
            case InstructionType.DIVIDE_SINGLE_PRECISION:
                this.result = src1 / src2;
                break;
        }

    }

}
