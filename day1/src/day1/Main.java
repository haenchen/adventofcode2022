package day1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<Elf> elves;
    public static void main(String[] args) {
        if (!setUp()) {
            return;
        }

        // Solve task 1
        task1();

        // Solve task 2
        task2();
    }

    protected static boolean setUp() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("day1/res/input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return false;
        }

        List<String> lines = reader.lines().toList();
        int size = lines.size();

        Elf elf = addElf();
        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            if (line.isBlank() && i < size - 1) {
                elf = addElf();
            } else {
                elf.addFoodItem(Integer.parseInt(line.trim()));
            }
        }

        return true;
    }

    protected static Elf addElf() {
        Elf elf = new Elf();
        if (elves == null) {
            elves = new ArrayList<>();
        }
        elves.add(elf);
        return elf;
    }

    protected static void task1() {
        if (elves.size() < 1) {
            System.out.println("There are no elves :^(");
            return;
        }

        Integer highest = 0;
        Elf coolestElf = elves.get(0);
        for (Elf elf : elves) {
            Integer item = elf.getTotalCalories();
            if (item > highest) {
                highest = item;
                coolestElf = elf;
            }
        }

        System.out.println(coolestElf.label() + " carries the most calories: " + highest);
    };

    protected static void task2() {
        elves.sort((left, right) -> right.getTotalCalories() - left.getTotalCalories());
        StringBuilder builder = new StringBuilder("Elves");
        Integer sum = 0;
        for (int i = 0; i < 3; i++) {
            Elf elf = elves.get(i);
            sum += elf.getTotalCalories();
            builder.append(" #").append(elf.id());
            if (i < 2) {
                builder.append(",");
            }

            if (i == 1) {
                builder.append(" and");
            }

        }
        builder.append(" carry the most calories with a combined total of ").append(sum);
        System.out.println(builder.toString());
    }
}