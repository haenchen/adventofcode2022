package day2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<Matchup> matches;
    public static void main(String[] args) throws IOException {
        if (!setUp(false)) {
            return;
        }
        solve();

        if (!setUp(true)) {
            return;
        }
        solve();
    }

    private static boolean setUp(boolean task2) throws IOException {
        matches = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("day2/res/input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return false;
        }

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            if (parts.length < 2) {
                continue;
            }
            if (!task2) {
                matches.add(new RegularMatchup(parts[0], parts[1]));
            } else {
                matches.add(new RiggedMatchup(parts[0], parts[1]));
            }
        }

        return true;
    }

    private static void solve() {
        int score = 0;
        for (Matchup match : matches) {
            score += match.resolveForRight();
        }
        System.out.println("You would have " + score + " points.");
    }
}