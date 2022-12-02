package day2;

public class RiggedMatchup extends Matchup{

    public RiggedMatchup(String left, String right) {
        this.left = lookupMove(left);
        this.right = lookUpRiggedMove(right);
    }

    private Move lookUpRiggedMove(String input) {
        if (left == null) {
            throw new RuntimeException("Logic error");
        }
        return switch (input.toLowerCase()) {
            case "x" -> getWin(false);
            case "y" -> left;
            case "z" -> getWin(true);
            default -> throw new RuntimeException("Unexpected value");
        };
    }

    private Move getWin(boolean winRight) {
        return switch (left) {
            case ROCK -> winRight ? Move.PAPER : Move.SCISSOR;
            case PAPER -> winRight ? Move.SCISSOR : Move.ROCK;
            case SCISSOR -> winRight ? Move.ROCK : Move.PAPER;
        };
    }
}
