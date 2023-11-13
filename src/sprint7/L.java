package sprint7;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class L {

    /*
    Лепреконы в данной задаче появились по соображениям общей морали, так как грабить банки — нехорошо.
    Вам удалось заключить неплохую сделку с лепреконами, поэтому они пустили вас в своё хранилище золотых слитков.
    Все слитки имеют единую пробу, то есть стоимость 1 грамма золота в двух разных слитках одинакова.
    В хранилище есть n слитков, вес i-го слитка равен wi кг. У вас есть рюкзак, вместимость которого M килограмм.
    Выясните максимальную суммарную массу золотых слитков, которую вы сможете унести.
     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            List<Integer> incomingData = readList(reader);
            Integer numberOfBars = incomingData.get(0);
            Integer maxWeight = incomingData.get(1);
            List<Integer> bars = readList(reader);

            int weight = findMaximumWeight(maxWeight, bars);
            writer.write(String.valueOf(weight));
        }
    }

    private static int findMaximumWeight(Integer maxWeight, List<Integer> bars) {
        int[] previousDp = new int[maxWeight + 1];
        int[] currentDb = new int[maxWeight + 1];
        for (int i = 0; i < bars.size(); i++) {
            for (int j = 0; j < maxWeight + 1; j++) {
                int weight = j >= bars.get(i) ? bars.get(i) : 0;
                if (i == 0) {
                    currentDb[j] = weight;
                } else {
                    currentDb[j] = Integer.max(previousDp[j], weight + previousDp[j - weight]);
                }
            }
            previousDp = Arrays.copyOf(currentDb, currentDb.length);
            Arrays.fill(currentDb, 0);
        }
        return previousDp[maxWeight];
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
