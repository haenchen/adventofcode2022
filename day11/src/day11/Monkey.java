package day11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monkey {

    private Queue<Long> items = new LinkedList<>();

    private Operation operation;

    private Integer test;

    Monkey trueTarget, falseTarget;

    public int performTurn() {
        return performTurn(0);
    }

    public int performTurn(int worryReduction) {
        int tmp = items.size();
        while (items.size() > 0) {
            long item = items.poll();
            item = operation.perform(item);
            if (worryReduction > 0) {
                item %= worryReduction;
            } else {
                item = Math.floorDiv(item, 3);
            }

            if (item % test == 0) {
                trueTarget.items.add(item);
            } else {
                falseTarget.items.add(item);
            }
        }

        return tmp;
    }

    public void setTest(Integer test) {
        this.test = test;
    }

    public void setOperation(String operation) {
        Pattern numberPattern = Pattern.compile("^new = old [+*] (\\d+)?|(old)$");
        Matcher numberMatcher = numberPattern.matcher(operation);
        if (!numberMatcher.find()) {
            System.out.println(operation);
            throw new RuntimeException("No valid operation");
        }

        int value;
        try {
            value = Integer.parseInt(numberMatcher.group(1));
        } catch (NumberFormatException e) {
            value = -1;
        }

        Pattern operatorPattern = Pattern.compile("[+*]");
        Matcher operatorMatcher = operatorPattern.matcher(operation);
        if (!operatorMatcher.find()) {
            throw new RuntimeException("No valid operation");
        }
        String operator = operatorMatcher.group();

        this.operation = new Operation(operator, value);
    }

    public void setItems(Queue<Long> items) {
        this.items = items;
    }

    public void setTrueTarget(Monkey trueTarget) {
        this.trueTarget = trueTarget;
    }

    public void setFalseTarget(Monkey falseTarget) {
        this.falseTarget = falseTarget;
    }

    private static class Operation {

        /**
         * Can only be * or +
         */
        String operator;

        int value;

        public Operation(String operator, int value) {
            this.operator = operator;
            this.value = value;
        }

        public long perform(long input) {
            long operationValue = value;
            if (value == -1) {
                operationValue = input;
            }

            if (operator.equals("*")) {
                return input * operationValue;
            }

            return input + operationValue;
        }
    }
}
