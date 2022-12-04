package day4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("day4/res/input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        String line;
        int containing = 0, overlapping = 0;
        while ((line = reader.readLine()) != null) {
            String[] sides = line.split(",");
            String[] start = sides[0].split("-");
            Range left = new Range(Integer.parseInt(start[0]), Integer.parseInt(start[1]));
            String[] end = sides[1].split("-");
            Range right = new Range(Integer.parseInt(end[0]), Integer.parseInt(end[1]));
            if (right.contains(left) || left.contains(right)) {
                containing += 1;
            }

            if (right.overlaps(left)) {
                overlapping += 1;
            }
        }
        System.out.println(containing);
        System.out.println(overlapping);
    }
}
