package sprint2.review;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class B {

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line = reader.readLine();
            int chaosNumber = calculate(line);
            System.out.println(chaosNumber);
        }
    }

    private static int calculate(String line) {
        Stack<Integer> stackForNumbers = new Stack<>();
        String[] symbols = line.split(" ");
        for (String symbol : symbols) {
            if (symbol.matches("[-+]?\\d+")) {
                stackForNumbers.push(Integer.parseInt(symbol));
            } else if ("+".equals(symbol)) {
                int secondElem = stackForNumbers.pop();
                int firstElem = stackForNumbers.pop();
                stackForNumbers.push(firstElem + secondElem);
            } else if ("-".equals(symbol)) {
                int secondElem = stackForNumbers.pop();
                int firstElem = stackForNumbers.pop();
                stackForNumbers.push(firstElem - secondElem);
            } else if ("*".equals(symbol)) {
                int secondElem = stackForNumbers.pop();
                int firstElem = stackForNumbers.pop();
                stackForNumbers.push(firstElem * secondElem);
            } else if ("/".equals(symbol)) {
                int secondElem = stackForNumbers.pop();
                int firstElem = stackForNumbers.pop();
                stackForNumbers.push(Math.floorDiv(firstElem, secondElem));
            }
        }
        return stackForNumbers.pop();
    }
}
