package sprint8.review;

import java.io.*;
import java.util.*;

public class A {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int linesNumber = readInt(reader);
            String prefix = null;
            for (int i = 0; i < linesNumber; i++) {
                String line = unpackLine(reader.readLine());
                prefix = findCommonPrefix(prefix, line);
            }

            if (prefix == null) {
                prefix = "";
            }
            writer.write(prefix);

        }
    }

    private static String findCommonPrefix(String prefix, String line) {
        if (prefix == null) {
            prefix = line;
        }

        if (line.isEmpty()) {
            return "";
        }

        while (line.indexOf(prefix) != 0) {
            prefix = prefix.substring(0, prefix.length() - 1);
            if (prefix.isEmpty()) {
                return "";
            }
        }

        return prefix;
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static String unpackLine(String line) {
        Stack<String> stack = new Stack<>();
        for (char c : line.toCharArray()) {
            if (c == ']') {
                List<String> packedWord = new ArrayList<>();
                String stackChar = stack.pop();
                while (!stackChar.equals("[")) {
                    packedWord.add(stackChar);
                    stackChar = stack.pop();
                }
                Collections.reverse(packedWord);
                String word = String.join("", packedWord);
                int count = Integer.parseInt(stack.pop());
                stack.push(word.repeat(count));
            } else {
                stack.push(Character.toString(c));
            }
        }
        StringBuilder result = new StringBuilder();
        for (String str : stack) {
            result.append(str);
        }
        return result.toString();
    }
}
