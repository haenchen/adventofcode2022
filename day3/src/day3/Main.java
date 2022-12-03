package day3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("day3/res/input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        List<String> lines = reader.lines().toList();
        String previousLine = "";
        List<String> sharedItems = new ArrayList<>();
        int sumDoubledItems = 0, sumBadgeItems = 0;
        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            int offset = Math.floorDiv(line.length(), 2);
            String firstCompartment = line.substring(0, offset);
            String secondCompartment = line.substring(offset);

            for (int j = 0; j < secondCompartment.length(); ++j) {
                char current = secondCompartment.charAt(j);
                if (firstCompartment.indexOf(current) != -1) {
                    sumDoubledItems += getItemValue(current);
                    break;
                }
            }

            if (!previousLine.equals("")) {
                if (sharedItems.size() == 0) {
                    for (int j = 0; j < line.length(); ++j) {
                        char current = line.charAt(j);
                        if (previousLine.indexOf(current) != -1) {
                            sharedItems.add(String.valueOf(current));
                        }
                    }
                } else {
                    for (String character : sharedItems) {
                        if (line.contains(character)) {
                            sumBadgeItems += getItemValue(character.charAt(0));
                            break;
                        }
                    }
                }
            }

            if (i % 3 != 2) {
                previousLine = line;
            }
            else {
                previousLine = "";
                sharedItems = new ArrayList<>();
            }
        }

        System.out.println(sumDoubledItems);
        System.out.println(sumBadgeItems);
    }

    private static int getItemValue(char item) {
        return item - (item >= 97 ? 96 : 38);
    }
}
