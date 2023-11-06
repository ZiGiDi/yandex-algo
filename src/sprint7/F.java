package sprint7;

import java.io.*;
import java.util.List;

public class F {

    /*
    Алла хочет доказать, что она умеет прыгать вверх по лестнице быстрее всех. На этот раз соревнования будут проходить
    на специальной прыгательной лестнице. С каждой её ступеньки можно прыгнуть вверх на любое расстояние от 1 до k.
    Число k придумывает Алла.

    Гоша не хочет проиграть, поэтому просит вас посчитать количество способов допрыгать от первой ступеньки до n-й.
    Изначально все стоят на первой ступеньке.
     */

    private static final int MOD = (int) (1e9 + 7);

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            List<Integer> incomingData = readList(reader);
            int stairs = incomingData.get(0);
            int step = incomingData.get(1);
            long result = findNumberOfVariants(stairs, step);
            writer.write(String.valueOf(result));
        }
    }

    private static long findNumberOfVariants(int n, int k) {
        int[] steps = new int[n + 1];
        steps[1] = 1;
        for (int stair = 2; stair <= n; stair++) {
            for (int step = 1; step <= k; step++) {
                if (step <= stair) {
                    steps[stair] += steps[stair - step];
                    steps[stair] %= MOD;
                }
            }
        }
        return steps[n];
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        String[] split = s.split(" ");
        return List.of(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }
}
