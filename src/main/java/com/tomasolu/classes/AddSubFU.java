package com.tomasolu.classes;

public class AddSubFU extends FunctionalUnit {
    @Override
    public void execute(double src1, double src2, String type) {
        switch (type) {
            case InstructionType.ADD_DOUBLE_PRECISION:
            case InstructionType.ADD_SINGLE_PRECISION:
                this.result = src1 + src2;
                break;
            case InstructionType.SUB_DOUBLE_PRECISION:
            case InstructionType.SUB_SINGLE_PRECISION:
                this.result = src1 - src2;
                break;

        }
    }
}
