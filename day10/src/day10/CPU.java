package day10;

import java.util.List;

public class CPU {

    // register
    private int x = 1, instructionPointer = 0;

    private String preparedInstruction = "";

    private List<String> instructions;

    public CPU (List<String> instructions) {
        this.instructions = instructions;
    }

    public void loadProgram(List<String> instructions) {
        this.instructions = instructions;
        instructionPointer = 0;
        x = 1;
    }

    /**
     * Basically a clock cycle
     *
     * @return value during cycle
     */
    public int advanceProgram() throws EndProgramException {
        if (instructionPointer >= instructions.size()) {
            throw new EndProgramException("Program ended");
        }

        int tmp = x;

        if (preparedInstruction.equals("")) {
            String instruction = instructions.get(instructionPointer++);
            if (instruction.isBlank()) {
                throw new RuntimeException("Illegal instruction");
            }
            if (!instruction.contains("noop")) {
                preparedInstruction = instruction;
            }
        } else {
            String[] parts = preparedInstruction.split(" ");
            x +=  Integer.parseInt(parts[1]);
            preparedInstruction = "";
        }

        return tmp;
    }

    static class EndProgramException extends Exception {

        public EndProgramException(String message) {
            super(message);
        }
    }
}
