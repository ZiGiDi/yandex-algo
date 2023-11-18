package sprint7.review;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class B {

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            reader.readLine();
            List<Integer> numbers = readList(reader);

            boolean partitioningPossible = isPartitioningPossible(numbers);

            String answer = partitioningPossible ? "True" : "False";

            writer.write(answer);
        }
    }

    private static boolean isPartitioningPossible(List<Integer> numbers) {

        int sum = numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        if (sum % 2 != 0) {
            return false;
        }

        int halfSum = sum / 2;
        boolean[] pervioudDp = new boolean[halfSum + 1];
        pervioudDp[0] = true;

        for (int number : numbers) {

            if (number > halfSum) {
                return false;
            }
            if (number == halfSum) {
                return true;
            }

            boolean[] currentDp = Arrays.copyOf(pervioudDp, halfSum + 1);
            for (int i = 0; i < pervioudDp.length; i++) {
                if (pervioudDp[i]) {
                    int nextIndex = i + number;
                    if (nextIndex == halfSum) {
                        return true;
                    }
                    if (nextIndex < halfSum) {
                        currentDp[nextIndex] = true;
                    }
                }
            }
            pervioudDp = currentDp;
        }
        return pervioudDp[halfSum];
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        if (s.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(s.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
