package day11;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {

    private static Monkey[] monkeys = new Monkey[8];

    private static int mod = 1;

    public static void main(String[] args) throws IOException {
        boolean task2 = true;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("day11/res/input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        for (int i = 0; i < monkeys.length; ++i) {
            monkeys[i] = new Monkey();
        }

        Pattern monkeyPattern = Pattern.compile("^Monkey (\\d):$");
        Monkey current = null;
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isBlank()) {
                continue;
            }
            Matcher monkeyMatcher = monkeyPattern.matcher(line);
            if (monkeyMatcher.matches()) {
                int number = Integer.parseInt(monkeyMatcher.group(1));
                current = monkeys[number];
            } else if (current != null) {
                String[] parts = line.split(": ");
                prepareMonkey(current, parts);
            }
        }

        long[] inspections = new long[] {
            0, 0, 0, 0, 0, 0, 0, 0
        } ;

        int rounds = task2 ? 10000 : 20;
        for (int i = 1; i <= rounds; ++i) {
            for (int j = 0; j < monkeys.length; ++j) {
                System.out.println("Monkey " + j);
                if (task2) {
                    inspections[j] += monkeys[j].performTurn(mod);
                } else {
                    inspections[j] += monkeys[j].performTurn();
                }
            }
        }

        long max = 0, second = 0;
        for (long value : inspections) {
            if (value > max) {
                second = max;
                max = value;
            } else if (value > second) {
                second = value;
            }
        }

        long result = max * second;
        System.out.println("Monkey business level: " + result);
    }

    private static void prepareMonkey(Monkey monkey, String[] parts) {
        String check = parts[0].trim();
        if (check.equals("Operation")) {
            monkey.setOperation(parts[1]);
            return;
        }

        Pattern numberPattern = Pattern.compile("\\d+");
        Matcher matcher = numberPattern.matcher(parts[1]);
        if (!matcher.find()) {
            throw new RuntimeException();
        }
        int first = Integer.parseInt(matcher.group());

        switch (check) {
            case "Starting items": {
                Queue<Long> queue = new LinkedList<>();
                queue.add((long) first);
                while (matcher.find()) {
                    queue.add(Long.parseLong(matcher.group()));
                }
                monkey.setItems(queue);
                break;
            }
            case "Test": {
                mod *= first;
                monkey.setTest(first);
                break;
            }
            case "If true": {
                monkey.setTrueTarget(monkeys[first]);
                break;
            }
            case "If false": {
                monkey.setFalseTarget(monkeys[first]);
                break;
            }
            default: {
                System.out.println(check);
                throw new RuntimeException();
            }
        }
    }
}