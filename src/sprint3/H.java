package sprint3;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class H {

//    Вечером ребята решили поиграть в игру «Большое число».
//Даны числа. Нужно определить, какое самое большое число можно из них составить.

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int count = readInt(reader);
            List<Integer> unsortedList = readList(reader);
            findBiggestNumber(unsortedList, writer);
        }
    }

    private static void findBiggestNumber(List<Integer> unsortedList, BufferedWriter writer) throws IOException {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = unsortedList.size() - 1; i > 0; i--) {
                if (compare(unsortedList.get(i), unsortedList.get(i - 1))) {
                    int temp = unsortedList.get(i);
                    unsortedList.set(i, unsortedList.get(i - 1));
                    unsortedList.set(i - 1, temp);
                    isSorted = false;
                }
            }
        }
        for (Integer element : unsortedList) {
            writer.write(element.toString());
        }
    }

    private static boolean compare(Integer a, Integer b) {
        String aString = a.toString();
        String bString = b.toString();
        if (aString.length() == bString.length()) {
            return a > b;
        }
        return Integer.parseInt(aString + bString) > Integer.parseInt(bString + aString);
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
