package sprint3;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class D {

//    К Васе в гости пришли одноклассники. Его мама решила угостить ребят печеньем.
//
//Но не всё так просто. Печенья могут быть разного размера. А у каждого ребёнка есть фактор жадности —– минимальный размер печенья, которое он возьмёт. Нужно выяснить, сколько ребят останутся довольными в лучшем случае, когда они действуют оптимально.
//
//Каждый ребёнок может взять не больше одного печенья.

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int numberOfKids = readInt(reader);
            List<Integer> kids = readList(reader);
            int numberOfCookies = readInt(reader);
            List<Integer> cookies = readList(reader);
            int result = countHappyKids(kids, cookies);
            writer.write(String.valueOf(result));
        }
    }

    private static int countHappyKids(List<Integer> kids, List<Integer> cookies) {
        int[] levelOfGreedArray = new int[1001];
        Arrays.fill(levelOfGreedArray, 0);
        for (Integer kid : kids) {
            levelOfGreedArray[kid] = levelOfGreedArray[kid] + 1;
        }

        int[] levelOfFullnessArray = new int[10001];
        Arrays.fill(levelOfFullnessArray, 0);
        for (Integer cookie : cookies) {
            levelOfFullnessArray[cookie] = levelOfFullnessArray[cookie] + 1;
        }

        int countOfHappyKids = 0;
        for (int greedLevelIndex = 0; greedLevelIndex < levelOfGreedArray.length; greedLevelIndex++) {
            int fullnessIndex = greedLevelIndex;
            while (levelOfGreedArray[greedLevelIndex] != 0 && fullnessIndex < levelOfGreedArray.length) {
                if (levelOfFullnessArray[fullnessIndex] == 0) {
                    fullnessIndex++;
                } else {
                    levelOfFullnessArray[fullnessIndex] = levelOfFullnessArray[fullnessIndex] - 1;
                    levelOfGreedArray[greedLevelIndex] = levelOfGreedArray[greedLevelIndex] - 1;
                    countOfHappyKids++;
                }
            }
        }
        return countOfHappyKids;
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
