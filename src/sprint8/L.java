package sprint8;

import java.io.*;

public class L {
    /*
    В этой задаче вам необходимо посчитать префикс-функцию для заданной строки.
     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String text = reader.readLine();

            int[] integers = prefixFunction(text);
            for (int integer : integers) {
                writer.write(String.valueOf(integer));
                writer.write(" ");
            }
        }
    }

    public static int[] prefixFunction(String s) {
        int N = s.length();
        int[] π = new int[N];
        for (int i = 1; i < N; i++) {
            int k = π[i-1];
            while (k > 0 && s.charAt(k) != s.charAt(i)) {
                k = π[k-1];
            }
            if (s.charAt(k) == s.charAt(i)) {
                k++;
            }
            π[i] = k;
        }
        return π;
    }
}
