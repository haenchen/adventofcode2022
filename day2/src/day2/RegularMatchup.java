package day2;

public class RegularMatchup extends Matchup{

    public RegularMatchup(String left, String right) {
        this.left = lookupMove(left);
        this.right = lookupMove(right);
    }

}

