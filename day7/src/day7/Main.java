package day7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Directory current = FSItem.getRoot();
    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("day7/res/input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        String line;
        // skip first two lines
        reader.readLine();
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            if (parts[0].trim().equals("dir")) {
                current.addChild(new Directory(parts[1].trim()));
            } else if (isNumeric(parts[0])) {
                int size = Integer.parseInt(parts[0]);
                current.addChild(new File(current, parts[1], size));
            } else if (parts[1].trim().equals("cd")) {
                handleCd(parts[2]);
            }
        }


        List<Directory> dirsUnder100k = new ArrayList<>();
        findDirsUnder(FSItem.getRoot(), dirsUnder100k);
        int sum = 0;
        for (Directory dir : dirsUnder100k) {
            sum += dir.getSize();
        }

        System.out.println(sum);

        final int UPDATE_SIZE = 30000000;
        final int FS_SIZE = 70000000;
        int unused = FS_SIZE - FSItem.root.getSize();
        if (unused >= UPDATE_SIZE) {
            System.out.println("No problemo.");
            return;
        }

        List<Directory> dirs = new ArrayList<>();
        int needed = UPDATE_SIZE - unused;
        findDirsOver(FSItem.getRoot(), dirs, needed);
        Directory smallest = dirs.get(0);
        for (Directory dir : dirs) {
            if (dir.getSize() < smallest.getSize()) {
                smallest = dir;
            }
        }

        System.out.println(smallest.getSize());

    }

    private static void handleCd(String dir) {
        switch (dir.trim()) {
            case "/" -> current = FSItem.getRoot();
            case ".." -> current = current.getParent();
            default -> current = (Directory) current.addChild(new Directory(current, dir));
        }
    }

    private static void findDirsUnder(Directory dir, List<Directory> results) {
        for (FSItem child : dir.children()) {
            if (!child.isDir()) {
                continue;
            }

            if (child.getSize() < 100000) {
                results.add((Directory) child);
            }

            findDirsUnder((Directory) child, results);
        }
    }

    private static boolean findDirsOver(Directory dir, List<Directory> results, int target) {
        boolean found = false;
        for (FSItem child : dir.children()) {
            if (!child.isDir()) {
                continue;
            }

            if (child.getSize() >= target) {
                // try to avoid adding unnecessary dirs
                if (!findDirsOver((Directory) child, results, target)) {
                    results.add((Directory) child);
                }
                found = true;
            }
        }

        return found;
    }

    private static boolean isNumeric(String subject) {
        if (subject == null) {
            return false;
        }

        try {
            double test = Double.parseDouble(subject);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
