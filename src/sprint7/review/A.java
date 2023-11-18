package sprint7.review;

import java.io.*;

public class A {

    private static int[][] matrix;

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();

            int distance = levenshteinDistance(line1, line2);

            writer.write(String.valueOf(distance));
        }
    }

    private static int levenshteinDistance(String line1, String line2) {
        matrix = new int[line1.length() + 1][line2.length() + 1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = levDiv(i, j, line1, line2);
            }
        }
        return matrix[line1.length()][line2.length()];
    }

    private static int levDiv(int i, int j, String line1, String line2) {
        if (i == 0 && j == 0) {
            return 0;
        }
        if (i > 0 && j == 0) {
            return i;
        }
        if (j > 0 && i == 0) {
            return j;
        }

        int m = (line1.charAt(i - 1) == line2.charAt(j - 1)) ? 0 : 1;
        return Math.min(matrix[i - 1][j - 1] + m,
                Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1));
    }
}