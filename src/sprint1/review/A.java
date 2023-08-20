import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A {

    //https://contest.yandex.ru/contest/22450/run-report/89659323/
    private static List<Integer> getDistance(List<Integer> street) {
        int streetSize = street.size();
        List<Integer> result = new ArrayList<>(streetSize);
        for (int i = 0; i < streetSize; i++) {
            result.add(0);
        }

        List<Integer> zeroIndexList = new ArrayList<>();
        for (int i = 0; i < streetSize; i++) {
            if (street.get(i) == 0) {
                zeroIndexList.add(i);
            }
        }

        int zeroIndexSize = zeroIndexList.size();
        Integer firstZeroIndex = zeroIndexList.get(0);
        Integer lastZeroIndex = zeroIndexList.get(zeroIndexSize - 1);
        if (!firstZeroIndex.equals(lastZeroIndex)) {
            for (int i = 0; i < zeroIndexSize - 1; i++) {
                int left = zeroIndexList.get(i);
                int right = zeroIndexList.get(i + 1);
                for (int j = left + 1; j < right; j++) {
                    result.set(j, Math.min(j - left, right - j));
                }
                //в начале как раз сделал в 2 прохода, но задача не проходила по времени, решил пройтись за 1.
                // тут пример решения в 2 прохода: https://contest.yandex.ru/contest/22450/run-report/89658106/
            }
        }

        for (int i = 0; i < firstZeroIndex; i++) {
            result.set(i, firstZeroIndex - i);
        }

        for (int i = lastZeroIndex + 1; i < streetSize; i++) {
            result.set(i, i - lastZeroIndex);
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int numberOfDays = readInt(reader);
            List<Integer> street = readList(reader);
            List<Integer> result = getDistance(street);
            for (int elem : result) {
                writer.write(elem + " ");
            }
        }
    }


    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        return Arrays.asList(reader.readLine().split(" "))
                .stream()
                .map(elem -> Integer.parseInt(elem))
                .collect(Collectors.toList());
    }
}
