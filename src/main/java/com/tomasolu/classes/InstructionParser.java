package com.tomasolu.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InstructionParser {

    public static List<Instruction> parseInstructions(String filePath) throws IOException {
        List<Instruction> instructions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String operation = parts[0];
                // List<String> operands = new ArrayList<>();
                // for (int i = 1; i < parts.length; i++) {
                // operands.add(parts[i]);
                // }
                // instructions.add(new Instruction(operation, operands));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instructions;
    }
}