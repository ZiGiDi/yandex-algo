package sprint7;

import java.io.*;

public class D {

    /*
    D. Числа Фибоначчи для взрослых
    Гоша практикуется в динамическом программировании — он хочет быстро считать числа Фибоначчи. Напомним,
    что числа Фибоначчи определены как последовательность . F0 = F1 = 1, Fn = Fn -1 + Fn-2, n ≥ 2.
    Помогите Гоше решить эту задачу.
     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int number = readInt(reader);
            long fibonacciValue = findFibonacciValue(number);
            writer.write(String.valueOf(fibonacciValue));
        }
    }

    private static long findFibonacciValue(int number) {
        long[] array = new long[number + 1];
        array[0] = 1;
        array[1] = 1;
        for (int i = 2; i <= number; i++) {
            long fib = array[i - 1] + array[i - 2];
            array[i] = fib % ((long) Math.pow(10, 9) + 7);
        }
        return array[number];
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}
