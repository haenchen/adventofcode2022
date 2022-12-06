package day6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("day6/res/input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        int current, counter = 0;
        StringBuilder characters = new StringBuilder();
        while ((current = reader.read()) != -1) {
            counter += 1;
            String character = String.valueOf((char) current);
            handleCharacter(characters, character);

            if (characters.length() == 4) {
                break;
            }
        }
        System.out.println(characters + ": " + counter);

        characters = new StringBuilder();
        while ((current = reader.read()) != -1) {
            counter += 1;
            String character = String.valueOf((char) current);
            handleCharacter(characters, character);

            if (characters.length() == 14) {
                break;
            }
        }
        System.out.println(characters + ": " + counter);
    }

    private static void handleCharacter(StringBuilder string, String character) {
        if (string.toString().contains(character)) {
            int index = string.indexOf(character);
            string.delete(0, index + 1);
        }
        string.append(character);
    }
}
