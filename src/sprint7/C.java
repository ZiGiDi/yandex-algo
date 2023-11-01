package sprint7;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class C {

    /*
    Гуляя по одному из островов Алгосского архипелага, Гоша набрёл на пещеру, в которой лежат кучи золотого песка.
    К счастью, у Гоши есть с собой рюкзак грузоподъёмностью до M килограмм, поэтому он может унести с собой какое-то
    ограниченное количество золота.

    Всего золотых куч n штук, и все они разные. В куче под номером i содержится mi килограммов золотого песка,
    а стоимость одного килограмма — ci алгосских франков.

    Помогите Гоше наполнить рюкзак так, чтобы общая стоимость золотого песка в пересчёте на алгосские франки
    была максимальной.
     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int liftingCapacity = readInt(reader);
            int bunchOfGoldNumbers = readInt(reader);

            ArrayList<List<Integer>> bunchesOfGold = getBunchesOfGold(bunchOfGoldNumbers, reader);

            long profit = getProfit(liftingCapacity, bunchesOfGold);

            writer.write(String.valueOf(profit));
        }
    }

    private static long getProfit(int liftingCapacity, ArrayList<List<Integer>> bunchesOfGold) {
        int weight = 0;
        long profit = 0;
        for (List<Integer> bunch : bunchesOfGold) {
            int costPerKilo = bunch.get(0);
            int kiloNumber = bunch.get(1);
            int kiloToLoad = Math.min(liftingCapacity - weight, kiloNumber);
            weight = weight + kiloToLoad;
            profit = profit + (long) kiloToLoad * costPerKilo;
            if (weight == liftingCapacity) {
                break;
            }
        }
        return profit;
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        String[] split = s.split(" ");
        return List.of(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }

    private static ArrayList<List<Integer>> getBunchesOfGold(int bunchOfGoldNumbers, BufferedReader reader) throws IOException {
        ArrayList<List<Integer>> bunchesOfGold = new ArrayList<>();
        for (int i = 0; i < bunchOfGoldNumbers; i++) {
            bunchesOfGold.add(readList(reader));
        }
        bunchesOfGold.sort(Comparator.comparing(element -> element.get(0), Comparator.reverseOrder()));
        return bunchesOfGold;
    }

}
