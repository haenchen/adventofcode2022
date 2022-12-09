package day9;

import java.util.HashSet;
import java.util.Set;

public class Rope {

    private Position[] knots;

    private Set<Position> tailVisited = new HashSet<>();

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public Rope(int knots) {
        if (knots < 2) {
            throw new RuntimeException("Illegal argument");
        }

        this.knots = new Position[knots];
        for (int i = 0; i < knots; ++i) {
            this.knots[i] = new Position(0, 0);
        }
        tailVisited.add(getTail());
    }

    private Position getTail() {
        return knots[knots.length - 1];
    }

    public void moveHead(Direction dir, int distance) {
        int inc = dir == Direction.UP || dir == Direction.RIGHT ? 1 : -1;
        for (int i = 0; i < distance; ++i) {
            Position head = knots[0];
            knots[0] = dir == Direction.UP || dir == Direction.DOWN ? head.moveY(inc) : head.moveX(inc);

            moveTail(1);
        }
    }

    private void moveTail(int knot) {
        if (knot < 1 || knot >= knots.length) {
            throw new RuntimeException("Invalid argument");
        }

        Position head = knots[knot - 1];
        Position tail = knots[knot];
        if (head.adjacent(tail)) {
            return;
        }

        boolean greaterY = head.y > tail.y;
        boolean greaterX = head.x > tail.x;
        if (tail.x == head.x) {
            tail = tail.moveY(greaterY ? 1 : -1);
        } else if (tail.y == head.y) {
            tail = tail.moveX(greaterX ? 1 : -1);
        } else {
            if (greaterX && greaterY) {
                tail = new Position(tail.x + 1, tail.y + 1);
            } else if (greaterX && !greaterY) {
                tail = new Position(tail.x + 1, tail.y - 1);
            } else if (!greaterX && greaterY) {
                tail = new Position(tail.x - 1, tail.y + 1);
            } else {
                tail = new Position(tail.x - 1, tail.y - 1);
            }
        }

        knots[knot] = tail;
        if (knot != knots.length - 1) {
            moveTail(knot + 1);
        } else {
            tailVisited.add(tail);
        }
    }

    public Set<Position> getTailVisited() {
        return tailVisited;
    }

    private static class Position {
        final int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean adjacent(Position comp) {
            if (equals(comp)) {
                return true;
            }

            return (Math.pow(x - comp.x, 2) + Math.pow(y - comp.y, 2)) <= 2;
        }

        public Position moveX(int i) {
            return new Position(x + i, y);
        }

        public Position moveY(int i) {
            return new Position(x, y + i);
        }

        @Override
        public boolean equals(Object o) {
            // auto generated
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position comp = (Position) o;

            return x == comp.x && y == comp.y;
        }

        @Override
        public int hashCode() {
            // auto generated
            return 31 * x + y;
        }
    }
}
