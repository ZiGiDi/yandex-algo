package sprint7;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A {
    /*
    Рита хочет попробовать поиграть на бирже. Но для начала она решила потренироваться на исторических данных.

    Даны стоимости акций в каждый из n дней. В течение дня цена акции не меняется. Акции можно покупать и продавать,
    но только по одной штуке в день. В один день нельзя совершать более одной операции (покупки или продажи).
    Также на руках не может быть более одной акции в каждый момент времени.

    Помогите Рите выяснить, какую максимальную прибыль она могла бы получить.
     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int daysNumber = readInt(reader);
            List<Integer> shares = readList(reader);
            int profit = findMaximumProfit(shares);
            writer.write(String.valueOf(profit));
        }
    }

    private static int findMaximumProfit(List<Integer> shares) {
        int profit = 0;

        boolean hasShare = false;
        int previousDaySharePrice = shares.get(0);
        for (int i = 1; i < shares.size(); i++) {
            if (previousDaySharePrice < shares.get(i) && !hasShare) {
                profit = profit - previousDaySharePrice;
                hasShare = true;
            } else if (previousDaySharePrice > shares.get(i) && hasShare) {
                profit = profit + previousDaySharePrice;
                hasShare = false;
            }
            if (i == shares.size() - 1 && hasShare) {
                profit = profit + shares.get(i);
            }
            previousDaySharePrice = shares.get(i);
        }
        return profit;
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
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
