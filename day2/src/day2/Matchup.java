package day2;

public abstract class Matchup {
    Move left, right;

    enum Move {
        ROCK,
        PAPER,
        SCISSOR
    }

    enum Result {
        LEFT_WIN,
        DRAW,
        RIGHT_WIN
    }

    public int resolveForLeft() {
        int score = getMoveScore(left);
        score += switch (resolveMatchup()) {
            case LEFT_WIN -> 6;
            case DRAW -> 3;
            case RIGHT_WIN -> 0;
        };
        return score;
    }

    public int resolveForRight() {
        int score = getMoveScore(right);
        score += switch (resolveMatchup()) {
            case LEFT_WIN -> 0;
            case DRAW -> 3;
            case RIGHT_WIN -> 6;
        };
        return score;
    }

    protected final Move lookupMove(String input) throws RuntimeException {
        if (input.length() > 1) {
            throw new RuntimeException("Unexpected value");
        }
        return switch (input.toLowerCase()) {
            case "a", "x" -> Move.ROCK;
            case "b", "y" -> Move.PAPER;
            case "c", "z" -> Move.SCISSOR;
            default -> throw new RuntimeException("Unexpected value");
        };
    }

    protected final int getMoveScore(Move input) {
        return switch (input) {
            case ROCK -> 1;
            case PAPER -> 2;
            case SCISSOR -> 3;
        };
    }

    /**
     * @return -1 if left won, 0 for draw, 1 if right won
     */
    protected final Result resolveMatchup() {
        return switch (left) {
            case ROCK -> switch (right) {
                case ROCK -> Result.DRAW;
                case PAPER -> Result.RIGHT_WIN;
                case SCISSOR -> Result.LEFT_WIN;
            };
            case PAPER -> switch (right) {
                case ROCK -> Result.LEFT_WIN;
                case PAPER -> Result.DRAW;
                case SCISSOR -> Result.RIGHT_WIN;
            };
            case SCISSOR -> switch (right) {
                case ROCK -> Result.RIGHT_WIN;
                case PAPER -> Result.LEFT_WIN;
                case SCISSOR -> Result.DRAW;
            };
        };
    }
}
