package sprint3;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class E {

//    Тимофей решил купить несколько домов на знаменитом среди разработчиков Алгосском архипелаге.
//    Он нашёл n объявлений о продаже, где указана стоимость каждого дома в алгосских франках.
//    А у Тимофея есть k франков. Помогите ему определить, какое наибольшее количество домов
//    на Алгосах он сможет приобрести за эти деньги.

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            List<Integer> buyerInfo = readList(reader);
            int numberOfHouses = buyerInfo.get(0);
            int budget = buyerInfo.get(1);
            List<Integer> houses = readList(reader);
            int result = findNumberOfHouses(numberOfHouses, budget, houses);
            writer.write(String.valueOf(result));
        }
    }

    private static int findNumberOfHouses(int numberOfHouses, int budget, List<Integer> houses) {
        Collections.sort(houses);
        int result = 0;
        int cost = 0;
        int index = 0;
        while (result <= numberOfHouses && cost <= budget && index < houses.size()) {
            cost = cost + houses.get(index);
            if (cost <= budget) {
                result++;
                index++;
            }
        }
        return result;
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
