package day5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        Stack<String>[] stacks9000 = new Stack[9];
        Stack<String>[] stacks9001 = new Stack[9];
        for (int i = 0; i < 9; ++i) {
            stacks9000[i] = new Stack<>();
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("day5/res/input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        String line;
        Pattern letterPattern = Pattern.compile("[A-Z]");
        Pattern numbers = Pattern.compile("\\d+");

        Stack<String> lines = new Stack<>();
        while ((line = reader.readLine()) != null) {
            Matcher numberMatcher = numbers.matcher(line);
            if (numberMatcher.find()) {
                // Skip empty line
                reader.readLine();
                break;
            }

            lines.push(line);
        }

        while (!lines.empty()) {
            line = lines.pop();
            for (int i = 0; i < line.length(); i += 4) {
                String part = line.substring(i, i + 3);
                if (part.isBlank()) {
                    continue;
                }

                Matcher matcher = letterPattern.matcher(part.trim());
                String letter;
                if (matcher.find()) {
                    letter = matcher.group();
                } else {
                    continue;
                }

                int index = Math.floorDiv(i, 4);
                Stack<String> stack = stacks9000[index];
                stack.push(letter);
            }
        }

        for (int i = 0; i < 9; ++i) {
            stacks9001[i] = (Stack<String>) stacks9000[i].clone();
        }

        while ((line = reader.readLine()) != null) {
            Matcher matches = numbers.matcher(line);
            List<Integer> moveArgs = new ArrayList<>();
            while (matches.find()) {
                if (moveArgs.size() >= 3) {
                    break;
                }
                moveArgs.add(Integer.parseInt(matches.group()));
            }

            if (moveArgs.size() < 3) {
                System.out.println("Arguments missing");
                return;
            }

            int count = moveArgs.get(0);
            int start = moveArgs.get(1);
            int destination = moveArgs.get(2);

            // CrateMover 9000
            Stack<String> from = stacks9000[start - 1];
            Stack<String> to = stacks9000[destination - 1];
            for (int i = 0; i < count; ++i) {
                String tmp = from.pop();
                to.push(tmp);
            }

            //CreateMover 9001
            from = stacks9001[start - 1];
            to = stacks9001[destination - 1];
            Stack<String> tmp = new Stack<>();
            for (int i = 0; i < count; ++i) {
                tmp.push(from.pop());
            }
            while (!tmp.empty()) {
                to.push(tmp.pop());
            }
        }

        StringBuilder builder = new StringBuilder();
        for (Stack<String> stack : stacks9000) {
            builder.append(stack.peek());
        }

        System.out.println(builder.toString());

        builder = new StringBuilder();
        for (Stack<String> stack : stacks9001) {
            builder.append(stack.peek());
        }

        System.out.println(builder.toString());
    }
}
