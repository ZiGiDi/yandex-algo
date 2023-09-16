package sprint4;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class G {

    /*
    Жители Алгосов любят устраивать турниры по спортивному программированию. Все участники разбиваются на пары
    и соревнуются друг с другом. А потом два самых сильных программиста встречаются в финальной схватке,
    которая состоит из нескольких раундов. Если в очередном раунде выигрывает первый участник, в таблицу с
    результатами записывается 0, если второй, то 1. Ничьей в раунде быть не может.

    Нужно определить наибольший по длине непрерывный отрезок раундов, по результатам которого суммарно
    получается ничья. Например, если дана последовательность 0 0 1 0 1 1 1 0 0 0, то раунды с 2-го по 9-й
    (нумерация начинается с единицы) дают ничью.
     */

    private static int findMaxDrawLength(List<Integer> roundResults) {
        int points = 0;
        int maxLength = 0;
        Map<Integer, Integer> pointIndexMap = new HashMap<>(roundResults.size() + 1);
        pointIndexMap.put(0, -1);
        for (int i = 0; i < roundResults.size(); i++) {
            if (roundResults.get(i) == 1) {
                points = points + 1;
            } else {
                points = points - 1;
            }
            if (!pointIndexMap.containsKey(points)) {
                pointIndexMap.put(points, i);
            } else {
                Integer previousIndex = pointIndexMap.get(points);
                int length = i - previousIndex;
                if (length > maxLength) {
                    maxLength = length;
                }
            }
        }
        return maxLength;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int numberOfRounds = readInt(reader);
            List<Integer> roundResults = readList(reader);
            int result = findMaxDrawLength(roundResults);
            writer.write(String.valueOf(result));
        }
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
