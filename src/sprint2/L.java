package sprint2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class L {

    //    У Тимофея было n(0≤n≤32) стажёров. Каждый стажёр хотел быть лучше своих предшественников,
//    поэтому i-й стажёр делал столько коммитов, сколько делали два предыдущих стажёра в сумме.
//    Два первых стажёра были менее инициативными —– они сделали по одному коммиту.
//    Определите, сколько кода напишет следующий стажёр –— найдите последние k цифр числа Fn.

    private static long getFibonachi(int n, int k) {
        int num1 = 1;
        int num2 = 1;
        if (n <= 1) {
            return 1;
        } else {
            for (int i = 0; i < n - 1; i++) {
                int temp = num1;
                num1 = num2;
                num2 = (num2 + temp) % (int) Math.pow(10, k);
            }
            return num2;
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            List<Integer> integers = readList(reader);
            int n = integers.get(0);
            int k = integers.get(1);
            System.out.println(getFibonachi(n, k));
        }
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
