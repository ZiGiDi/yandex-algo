package sprint7;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class K {

    /*
    В мире последовательностей нет гороскопов. Поэтому когда две последовательности хотят понять,
    могут ли они счастливо жить вместе, они оценивают свою совместимость как длину их наибольшей общей подпоследовательности.
    Подпоследовательность получается из последовательности удалением некоторого (возможно, нулевого) числа элементов.
    То есть элементы сохраняют свой относительный порядок, но не обязаны изначально идти подряд.
    Найдите наибольшую общую подпоследовательность двух одиноких последовательностей и выведите её!
     */

    private static int[][] dp;

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            reader.readLine();
            List<Integer> firstSubsequence = readList(reader);
            reader.readLine();
            List<Integer> secondSubsequence = readList(reader);
            dp = initializeDp(firstSubsequence, secondSubsequence);

            List<String> indexes = findIndexes(firstSubsequence, secondSubsequence);

            writer.write(String.valueOf(dp[firstSubsequence.size()][secondSubsequence.size()]));
            writer.newLine();
            writer.write(indexes.get(0));
            writer.newLine();
            writer.write(indexes.get(1));

        }
    }

    private static int[][] initializeDp(List<Integer> firstSubsequence, List<Integer> secondSubsequence) {
        int[][] dp = new int[firstSubsequence.size() + 1][secondSubsequence.size() + 1];

        for (int i = 1; i < firstSubsequence.size() + 1; i++) {
            int subsequence1Value = firstSubsequence.get(i - 1);
            for (int j = 1; j < secondSubsequence.size() + 1; j++) {
                int subsequence2Value = secondSubsequence.get(j - 1);
                if (subsequence1Value == subsequence2Value) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Integer.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp;
    }

    private static List<String> findIndexes(List<Integer> firstSubsequence, List<Integer> secondSubsequence) {
        List<Integer> firstIndices = new ArrayList<>();
        List<Integer> secondIndices = new ArrayList<>();

        int i = firstSubsequence.size();
        int j = secondSubsequence.size();

        while (i >= 1 && j >= 1) {
            if (firstSubsequence.get(i - 1).equals(secondSubsequence.get(j - 1))) {
                firstIndices.add(i);
                secondIndices.add(j);
                i--;
                j--;
            } else if (i - 1 >= 1 && dp[i - 1][j] == dp[i][j]) {
                i--;
            } else if (j - 1 >= 1 && dp[i][j - 1] == dp[i][j]) {
                j--;
            } else {
                break;
            }
        }

        Collections.reverse(firstIndices);
        String firstAnswer = firstIndices.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));

        Collections.reverse(secondIndices);
        String secondAnswer = secondIndices.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));

        return List.of(firstAnswer, secondAnswer);
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
