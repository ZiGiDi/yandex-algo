package sprint7;

import java.io.*;
import java.util.List;

public class H {
    /*
    Черепаха Кондратина путешествует по клетчатому полю из n строк и m столбцов. В каждой клетке либо растёт цветочек,
    либо не растёт. Кондратине надо добраться из левого нижнего в правый верхний угол и собрать как можно больше цветочков.

    Помогите ей с этой сложной задачей и определите, какое наибольшее число цветочков она сможет собрать при условии,
    что Кондратина умеет передвигаться только на одну клетку вверх или на одну клетку вправо за ход.
     */

    private static int[][] field;

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            List<Integer> incomingData = readList(reader);
            int numberOfRows = incomingData.get(0);
            int numberOfColumns = incomingData.get(1);
            field = initializeField(reader, numberOfRows, numberOfColumns);
            int result = findMaximumFlowers(numberOfRows, numberOfColumns);
            writer.write(String.valueOf(result));
        }
    }

    private static int findMaximumFlowers(int numberOfRows, int numberOfColumns) {
        int[][] dp = new int[numberOfRows + 1][numberOfColumns + 1];
        for (int i = 0; i < numberOfRows + 1; i++) {
            dp[i][0] = Integer.MIN_VALUE;
        }
        for (int i = 0; i < numberOfColumns + 1; i++) {
            dp[0][i] = Integer.MIN_VALUE;
        }
        for (int i = 1; i < numberOfRows + 1; i++) {
            for (int j = 1; j < numberOfColumns + 1; j++) {
                if (i == 1 && j == 1) {
                    dp[i][j] = field[i - 1][j - 1];
                }
                else {
                    dp[i][j] = Integer.max(dp[i - 1][j], dp[i][j - 1]) + field[i - 1][j - 1];
                }
            }
        }
        return dp[numberOfRows][numberOfColumns];
    }

    private static int[][] initializeField(BufferedReader reader, int numberOfRows, int numberOfColumns) throws IOException {
        int[][] field = new int[numberOfRows][numberOfColumns];
        for (int i = numberOfRows -1; i >= 0; i--) {
            String string = reader.readLine();
            char[] charArray = string.toCharArray();
            for (int j = 0; j < numberOfColumns; j++) {
                field[i][j] = Integer.parseInt(String.valueOf(charArray[j]));
            }
        }
        return field;
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        String[] split = s.split(" ");
        return List.of(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }
}
