package com.tomasolu.classes;

public class InstructionType {
    static final String LOAD_WORD = "LW";
    static final String LOAD_DOUBLE_WORD = "LD";
    static final String LOAD_DOUBLE_PRECISION = "L.D";
    static final String LOAD_SINGLE_PRECISION = "L.S";

    static final String STORE_SINGLE_PRECISION = "S.S";
    static final String STORE_DOUBLE_PRECISION = "S.D";
    static final String STORE_WORD = "SW";
    static final String STORE_DOUBLE_WORD = "SD";

    static final String ADD_IMMEDIATE = "DADDI";
    static final String SUB_IMMEDIATE = "DSUBI";

    static final String MULTIPLY_SINGLE_PRECISION = "MUL.S";
    static final String MULTIPLY_DOUBLE_PRECISION = "MUL.D";

    static final String DIVIDE_SINGLE_PRECISION = "DIV.S";
    static final String DIVIDE_DOUBLE_PRECISION = "DIV.D";

    static final String BRANCH_EQUAL = "BEQ";
    static final String BRANCH_NOT_EQUAL = "BNE";

    static final String ADD_SINGLE_PRECISION = "ADD.S";
    static final String ADD_DOUBLE_PRECISION = "ADD.D";
    static final String SUB_SINGLE_PRECISION = "SUB.S";
    static final String SUB_DOUBLE_PRECISION = "SUB.D";

}
