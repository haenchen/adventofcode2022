package day9;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("day9/res/input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        String line;
        Rope shortRope = new Rope(2);
        Rope longRope = new Rope(10);
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            Rope.Direction dir = switch (parts[0]) {
                case "U" -> Rope.Direction.UP;
                case "D" -> Rope.Direction.DOWN;
                case "R" -> Rope.Direction.RIGHT;
                default -> Rope.Direction.LEFT;
            };

            int steps = Integer.parseInt(parts[1]);
            shortRope.moveHead(dir, steps);
            longRope.moveHead(dir, steps);
        }

        System.out.println(shortRope.getTailVisited().size());
        System.out.println(longRope.getTailVisited().size());
    }
}

