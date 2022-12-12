package day12;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.function.Predicate;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("day12/res/input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        List<String> lines = reader.lines().toList();
        Node start = null, end = null;

        char[][] grid = new char[lines.get(0).length()][lines.size()];
        //determine start and end
        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); ++j) {
                grid[j][i] = line.charAt(j);
                if (line.charAt(j) == 'E') {
                    end = new Node('z', j, i);
                } else if (line.charAt(j) == 'S') {
                    start = new Node('a', j, i);
                }
            }
        }

        if (end == null || start == null) {
            throw new RuntimeException("No start and end found");
        }


        Queue<Node> open = new PriorityQueue<>();
        open.add(start);
        Set<Node> closed = new HashSet<>();

        Node current = null;
        while (!open.isEmpty()) {
            current = open.poll();
            int x, y;
            List<Node> neighbors = new ArrayList<>();
            if (current.x >= 1) {
                x = current.x - 1;
                y = current.y;
                char value = convertHeight(grid[x][y]);
                if (value - 1 <= current.height) {
                    double h = Math.sqrt(Math.pow(end.x - x, 2) + Math.pow(Math.abs(end.y - y), 2));
                    if (h == 0) {
                        break;
                    }
                    Node neighbor = new Node(value, x, y, h);
                    neighbor.g = current.g + 1;
                    neighbor.parent = current;
                    neighbors.add(neighbor);
                }
            }
            if (current.y >= 1) {
                x = current.x;
                y = current.y - 1;
                char value = convertHeight(grid[x][y]);
                if (value - 1 <= current.height) {
                    double h = Math.sqrt(Math.pow(end.x - x, 2) + Math.pow(Math.abs(end.y - y), 2));
                    if (h == 0) {
                        break;
                    }
                    Node neighbor = new Node(value, x, y, h);
                    neighbor.g = current.g + 1;
                    neighbor.parent = current;
                    neighbors.add(neighbor);
                }
            }
            if (current.x < grid.length - 1) {
                x = current.x + 1;
                y = current.y;
                char value = convertHeight(grid[x][y]);
                if (value - 1 <= current.height) {
                    double h = Math.sqrt(Math.pow(end.x - x, 2) + Math.pow(Math.abs(end.y - y), 2));
                    if (h == 0) {
                        break;
                    }
                    Node neighbor = new Node(value, x, y, h);
                    neighbor.g = current.g + 1;
                    neighbor.parent = current;
                    neighbors.add(neighbor);
                }
            }
            if (current.y < grid[0].length - 1) {
                x = current.x;
                y = current.y + 1;
                char value = convertHeight(grid[x][y]);
                if (value - 1 <= current.height) {
                    double h = Math.sqrt(Math.pow(end.x - x, 2) + Math.pow(Math.abs(end.y - y), 2));
                    if (h == 0) {
                        break;
                    }
                    Node neighbor = new Node(value, x, y, h);
                    neighbor.g = current.g + 1;
                    neighbor.parent = current;
                    neighbors.add(neighbor);
                }
            }

            for (Node neighbor : neighbors) {
                Predicate<Node> filter = makePredicate(neighbor.x, neighbor.y);
                Optional<Node> node = open.stream().filter(filter).findFirst();
                if (node.isPresent()) {
                    Node presentNode = node.get();
                    if (presentNode.f() < neighbor.f()) {
                        continue;
                    } else {
                        open.remove(presentNode);
                    }
                }

                Optional<Node> closedNode = closed.stream().filter(filter).findFirst();
                if (closedNode.isPresent() && closedNode.get().f() < neighbor.f()) {
                    continue;
                }
                open.add(neighbor);
            }

            closed.add(current);
        }


        if (current == null) {
            throw new RuntimeException("Something's wrong, I can feel it.");
        }

        System.out.println(current.g + 1);



        // Task 2

        open = new PriorityQueue<>();
        open.add(end);
        closed = new HashSet<>();

        current = null;
        while (!open.isEmpty()) {
            current = open.poll();
            int x, y;
            List<Node> neighbors = new ArrayList<>();
            if (current.x >= 1) {
                x = current.x - 1;
                y = current.y;
                char value = convertHeight(grid[x][y]);
                if (value + 1 >= current.height) {
                    double h = Math.sqrt(Math.pow(end.x - x, 2) + Math.pow(Math.abs(end.y - y), 2));
                    if (value == 'a') {
                        break;
                    }
                    Node neighbor = new Node(value, x, y, h);
                    neighbor.g = current.g + 1;
                    neighbor.parent = current;
                    neighbors.add(neighbor);
                }
            }
            if (current.y >= 1) {
                x = current.x;
                y = current.y - 1;
                char value = convertHeight(grid[x][y]);
                if (value + 1 >= current.height) {
                    double h = Math.sqrt(Math.pow(end.x - x, 2) + Math.pow(Math.abs(end.y - y), 2));
                    if (value == 'a') {
                        break;
                    }
                    Node neighbor = new Node(value, x, y, h);
                    neighbor.g = current.g + 1;
                    neighbor.parent = current;
                    neighbors.add(neighbor);
                }
            }
            if (current.x < grid.length - 1) {
                x = current.x + 1;
                y = current.y;
                char value = convertHeight(grid[x][y]);
                if (value + 1 >= current.height) {
                    double h = Math.sqrt(Math.pow(end.x - x, 2) + Math.pow(Math.abs(end.y - y), 2));
                    if (value == 'a') {
                        break;
                    }
                    Node neighbor = new Node(value, x, y, h);
                    neighbor.g = current.g + 1;
                    neighbor.parent = current;
                    neighbors.add(neighbor);
                }
            }
            if (current.y < grid[0].length - 1) {
                x = current.x;
                y = current.y + 1;
                char value = convertHeight(grid[x][y]);
                if (value + 1 >= current.height) {
                    double h = Math.sqrt(Math.pow(end.x - x, 2) + Math.pow(Math.abs(end.y - y), 2));
                    if (value == 'a') {
                        break;
                    }
                    Node neighbor = new Node(value, x, y, h);
                    neighbor.g = current.g + 1;
                    neighbor.parent = current;
                    neighbors.add(neighbor);
                }
            }

            for (Node neighbor : neighbors) {
                Predicate<Node> filter = makePredicate(neighbor.x, neighbor.y);
                Optional<Node> node = open.stream().filter(filter).findFirst();
                if (node.isPresent()) {
                    Node presentNode = node.get();
                    if (presentNode.f() < neighbor.f()) {
                        continue;
                    } else {
                        open.remove(presentNode);
                    }
                }

                Optional<Node> closedNode = closed.stream().filter(filter).findFirst();
                if (closedNode.isPresent() && closedNode.get().f() < neighbor.f()) {
                    continue;
                }
                open.add(neighbor);
            }

            closed.add(current);
        }


        if (current == null) {
            throw new RuntimeException("Something's wrong, I can feel it.");
        }

        System.out.println(current.g + 1);

    }

    private static char convertHeight(char c) {
        return switch (c) {
            case 'E' -> 'z';
            case 'S' -> 'a';
            default -> c;
        };
    }

    private static Predicate<Node> makePredicate(int x, int y) {
        return node -> node.x == x && node.y == y;
    }

    private static class Node implements Comparable<Node> {

        Node parent;
        private char height;

        private int x, y;
        private int g = 0;
        private double h = -1;

        public Node(char height, int x, int y, double h) {
            this(height, x, y);
            this.h = h;
        }

        public Node(char height, int x, int y) {
            this.height = convertHeight(height);
            this.x = x;
            this.y = y;
        }

        public double f() {
            return g + h;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public int compareTo(Node right) {
            if (f() == right.f()) {
                return 0;
            }

            return f() > right.f() ? 1 : -1;
        }
    }
}
