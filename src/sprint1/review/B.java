package sprint1.review;

import java.io.*;
import java.util.*;

public class B {

    private static int getMaxPoint(int k, List<String> matrix) {
        List<Integer> countOfNambers = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            countOfNambers.add(0);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String line : matrix) {
            stringBuilder.append(line);
        }

        String numbers = stringBuilder.toString().replace(".", "");
        if (numbers.isEmpty()) {
            return 0;
        }

        for (String elem : numbers.split("")) {
            int index = Integer.parseInt(elem) - 1;
            countOfNambers.set(index, countOfNambers.get(index) + 1);
        }

        int maxNumbers = k * 2;
        return (int) countOfNambers.stream()
                .filter(count -> count <= maxNumbers && count != 0)
                .count();
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            List<String> matrix = new ArrayList<>(4);
            int k = readInt(reader);
            while (reader.ready()) {
                matrix.add(reader.readLine());
            }

            int result = getMaxPoint(k, matrix);
            System.out.println(result);
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}
