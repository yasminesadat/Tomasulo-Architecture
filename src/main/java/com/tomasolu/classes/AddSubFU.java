package com.tomasolu.classes;

public class AddSubFU extends FunctionalUnit {
    @Override
    public void execute(double src1, double src2, String type) {
        switch (type) {
            case InstructionType.ADD_DOUBLE_PRECISION:
            case InstructionType.ADD_SINGLE_PRECISION:
            case InstructionType.ADD_IMMEDIATE:
                this.result = src1 + src2;
                break;
            case InstructionType.SUB_DOUBLE_PRECISION:
            case InstructionType.SUB_SINGLE_PRECISION:
            case InstructionType.BRANCH_EQUAL:
            case InstructionType.BRANCH_NOT_EQUAL:
            case InstructionType.SUB_IMMEDIATE:
                this.result = src1 - src2;
                break;
            // update pc dont forget affects issuing and life
        }
    }
}
