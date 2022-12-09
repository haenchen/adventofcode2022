package day8;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("day8/res/input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        List<String> lines = reader.lines().toList();
        final int forestDepth = lines.size();
        final int forestWidth = lines.get(0).length();
        char[][] forest = new char[forestWidth][forestDepth];

        for (int i = 0; i < forestDepth; ++i) {
            String line = lines.get(i);
            for (int j = 0; j < forestWidth; ++j) {
                forest[j][i] = line.charAt(j);
            }
        }

        Set<Tree> visibleTrees = new HashSet<>();
        for (int i = 0; i < forestWidth; ++i) {
            char highest = 0;
            for (int j = 0; j < forestDepth; ++j) {
                char current = forest[i][j];
                if (i == 0 || current > highest) {
                    visibleTrees.add(new Tree(i, j, current));
                    highest = current;
                }

                if (current == 9) {
                    break;
                }
            }

            highest = 0;
            for (int j = forestDepth - 1; j >= 0; --j) {
                char current = forest[i][j];
                if (i == forestDepth - 1 || current > highest) {
                    visibleTrees.add(new Tree(i, j, current));
                    highest = current;
                }

                if (current == 9) {
                    break;
                }
            }
        }

        for (int i = 0; i < forestDepth; ++i) {
            char highest = 0;
            for (int j = 0; j < forestWidth; ++j) {
                char current = forest[j][i];
                if (i == 0 || current > highest) {
                    visibleTrees.add(new Tree(j, i, current));
                    highest = current;
                }

                if (current == 9) {
                    break;
                }
            }

            highest = 0;
            for (int j = forestWidth - 1; j >= 0; --j) {
                char current = forest[j][i];
                if (i == forestWidth - 1 || current > highest) {
                    visibleTrees.add(new Tree(j, i, current));
                    highest = current;
                }

                if (current == 9) {
                    break;
                }
            }
        }

        System.out.println(visibleTrees.size());
        
        Tree coolestTree = new Tree(0, 0, forest[0][0]);
        coolestTree.calculateScenicScore(forest);
        for (int i = 0; i < forestWidth; ++i) {
            for (int j = 0; j < forestDepth; ++j) {
                Tree current = new Tree(i, j, forest[i][j]);
                if (current.calculateScenicScore(forest) > coolestTree.scenicScore) {
                    coolestTree = current;
                }
            }
        }

        System.out.println(coolestTree.scenicScore);
    }

    private static class Tree {
        private final int x,y, height;
        private int scenicScore;

        public Tree(int x, int y, int height) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.scenicScore = 0;
        }

        @Override
        public boolean equals(Object o) {
            // auto generated
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Tree tuple = (Tree) o;
            return x == tuple.x && y == tuple.y;
        }

        @Override
        public int hashCode() {
            // auto generated
            int result = x;
            result = 31 * result + y;
            return result;
        }

        public int calculateScenicScore(char[][] forest) {
            int i = x + 1, positiveX = 0, negativeX = 0, positiveY = 0, negativeY = 0;
            while (i < forest[0].length) {
                char current = forest[i++][y];
                positiveX++;
                if (current >= height) {
                    break;
                }
            }
            
            i = x - 1;
            while (i >= 0) {
                char current = forest[i--][y];
                negativeX++;
                if (current >= height) {
                    break;
                }
            }
            
            i = y + 1;
            while (i < forest.length) {
                char current = forest[x][i++];
                positiveY++;
                if (current >= height) {
                    break;
                }
            }

            i = y - 1;
            while (i >= 0) {
                char current = forest[x][i--];
                negativeY++;
                if (current >= height) {
                    break;
                }
            }

            scenicScore = positiveX * positiveY * negativeX * negativeY;
            return scenicScore;
        }
    }
}
