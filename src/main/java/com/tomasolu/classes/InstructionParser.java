package com.tomasolu.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InstructionParser {

    /**
     * Reads instructions from the file line by line and stores each line in a list.
     *
     * @param filePath The path to the file containing instructions.
     * @return A list of strings, each representing a line from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static List<Instruction> parseInstructions(String filePath) throws IOException {
        List<Instruction> instructions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String operation = parts[0];
                Instruction currentInstruction = new Instruction();
                currentInstruction.setEndTime(-1);
                currentInstruction.setIssueTime(-1);
                currentInstruction.setStartTime(-1);
                currentInstruction.setWriteTime(-1);
                currentInstruction.setType(operation);

                switch (operation) {
                    case InstructionType.ADD_DOUBLE_PRECISION: // ADD R1 R2 R3
                    case InstructionType.ADD_SINGLE_PRECISION:
                    case InstructionType.SUB_DOUBLE_PRECISION:
                    case InstructionType.SUB_SINGLE_PRECISION:
                    case InstructionType.MULTIPLY_DOUBLE_PRECISION:
                    case InstructionType.MULTIPLY_SINGLE_PRECISION:
                    case InstructionType.DIVIDE_DOUBLE_PRECISION:
                    case InstructionType.DIVIDE_SINGLE_PRECISION:
                        currentInstruction.setRd(parts[1]);
                        currentInstruction.setRs(parts[2]);
                        currentInstruction.setRt(parts[3]);
                        break;
                    case InstructionType.ADD_IMMEDIATE: // ADDI R1 R1 100
                    case InstructionType.SUB_IMMEDIATE:
                        currentInstruction.setRd(parts[1]);
                        currentInstruction.setRs(parts[2]);
                        currentInstruction.setImmediate(Integer.parseInt(parts[3]));
                        break;
                    case InstructionType.LOAD_WORD: // LW R1 100
                    case InstructionType.LOAD_DOUBLE_WORD:
                    case InstructionType.LOAD_DOUBLE_PRECISION:
                    case InstructionType.LOAD_SINGLE_PRECISION:
                        currentInstruction.setRd(parts[1]);
                        currentInstruction.setImmediate(Integer.parseInt(parts[2]));
                        break;
                    case InstructionType.STORE_WORD: // SW R1 100
                    case InstructionType.STORE_DOUBLE_WORD:
                    case InstructionType.STORE_DOUBLE_PRECISION:
                    case InstructionType.STORE_SINGLE_PRECISION:
                        currentInstruction.setRs(parts[1]);
                        currentInstruction.setImmediate(Integer.parseInt(parts[2]));

                        break;
                    case InstructionType.BRANCH_EQUAL: // BEQ R1 R2 100
                    case InstructionType.BRANCH_NOT_EQUAL:
                        currentInstruction.setRs(parts[1]);
                        currentInstruction.setRt(parts[2]);
                        currentInstruction.setImmediate(Integer.parseInt(parts[3]));
                }

                instructions.add(currentInstruction);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instructions;
    }
}
