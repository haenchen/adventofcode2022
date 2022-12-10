package day10;

import java.io.*;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("day10/res/input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        List<String> lines = reader.lines().toList();
        CPU cpu = new CPU(lines);

        int cycle = 1;
        Set<Integer> relevantCycles = Set.of(new Integer[] {20, 60, 100, 140, 180, 220});
        int sum = 0;
        PrintStream printer = System.out;
        int printCol = 0;
        while (true) {
            try {
                int x = cpu.advanceProgram();;
                if (relevantCycles.contains(cycle)) {
                   sum += x * cycle;
                }
                cycle += 1;

                // CRT - Bogus
                boolean draw = (x - 1) <= printCol && printCol <= (x + 1);
                printer.print(draw ? "#" : ".");
                printCol += 1;
                if (printCol > 39) {
                    printCol = 0;
                    printer.println();
                }
            } catch (CPU.EndProgramException e) {
                break;
            }
        }

        System.out.println(sum);
    }
}