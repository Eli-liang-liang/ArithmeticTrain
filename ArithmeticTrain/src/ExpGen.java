import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * define enum type: +, -, *, and /
 */
enum ops {
    add, sub, mul, div;
}

/**
 * This class generate question list with question and answer
 */
public class ExpGen {
    private final ops op;
    private final int range;
    private final int number;
    private final boolean sign;

    /**
     * construct expression generator with operator, range, total number of question, sign
     */
    public ExpGen(ops op, int range, int number, boolean sign) {
        this.op = op;
        this.range = range;
        this.number = number;
        this.sign = sign;
    }

    /**
     * Generate question list
     * @return a map with key as the expression and value as the answer
     */
    public Map<String, Integer> getQuestions() {
        Map<String, Integer> questionMap = new HashMap<String, Integer>(); // question list
        for (int i = 0; i < number; i++) { // generate n questions using for loop
            String question = ""; // the expression, such as "2 + 3"
            int result = 0; // the answer
            Random rand = new Random();
            int operand1 = rand.nextInt(range); // operand 1
            int operand2 = rand.nextInt(range); // operand 2
            if (sign) {
                if (rand.nextBoolean()) {
                    operand1 *= -1;
                }
                if (rand.nextBoolean()) {
                    operand2 *= -1;
                }
            }
            /*
              if user want to include sign, randomly pick sign for both operand
             */
            String operand2String = Integer.toString(operand2);
            if (operand2 < 0) {
                operand2String = "(" + operand2String + ")";
            }
            /*
              if operand2 is negative, add parentheses
             */
            if (op == ops.add) {
                result = operand1 + operand2;
                question = Integer.toString(operand1) + " + " + operand2String;
            } else if (op == ops.sub) {
                result = operand1 - operand2;
                question = Integer.toString(operand1) + " - " + operand2String;
            } else if (op == ops.mul) {
                result = operand1 * operand2;
                question = Integer.toString(operand1) + " * " + operand2String;
            } else if (op == ops.div) {
                result = operand1 / operand2;
                question = Integer.toString(operand1) + " / " + operand2String;
            }
            /*
              generate question as string and result as int
             */
            questionMap.put(question, result); // put question and answer on the map
        }
        return questionMap;
    }

    /**
     * Quick test to see if we generate question and answer correctly
     */
    public static void main(String[] args) {
        ExpGen expGen = new ExpGen(ops.add, 100, 5, true);
        Map<String, Integer> mp = expGen.getQuestions();
        for (Map.Entry<String, Integer> entry : mp.entrySet()) {
            System.out.println(entry.getKey() + "   " + entry.getValue());
        }
    }
}
