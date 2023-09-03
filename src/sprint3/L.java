package sprint3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class L {

//    Вася решил накопить денег на два одинаковых велосипеда — себе и сестре. У Васи есть копилка, в которую каждый день он может добавлять деньги (если, конечно, у него есть такая финансовая возможность). В процессе накопления Вася не вынимает деньги из копилки.
//
//У вас есть информация о росте Васиных накоплений — сколько у Васи в копилке было денег в каждый из дней.
//
//Ваша задача — по заданной стоимости велосипеда определить
//
//первый день, в которой Вася смог бы купить один велосипед,
//и первый день, в который Вася смог бы купить два велосипеда.
//Подсказка: решение должно работать за O(log n).

    private static List<Integer> findBuyingDays(List<Integer> moneyBoxList, int bicyclePrice) {
        int firstBicycleDayIndex = binarySearch(moneyBoxList, bicyclePrice, 0, moneyBoxList.size());
        if (firstBicycleDayIndex == -1) {
            return List.of(-1, -1);
        }
        int secondBicycleDayIndex = binarySearch(moneyBoxList, bicyclePrice * 2, firstBicycleDayIndex, moneyBoxList.size());
        if (secondBicycleDayIndex == -1) {
            return List.of(firstBicycleDayIndex + 1, -1);
        }
        return List.of(firstBicycleDayIndex + 1,
                secondBicycleDayIndex + 1);
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int daysNumber = readInt(reader);
            List<Integer> moneyBoxList = readList(reader);
            int bicyclePrice = readInt(reader);
            List<Integer> days = findBuyingDays(moneyBoxList, bicyclePrice);
            for (int element : days) {
                System.out.print(element + " ");
            }
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public static int binarySearch(List<Integer> list, int x, int left, int right) {
        if (right <= left) {
            return -1;
        }

        int mid = (left + right) / 2;
        if (x <= list.get(mid)) {
            int result = binarySearch(list, x, left, mid);
            if (result == -1) {
                return mid;
            }
            return result;
        } else {
            return binarySearch(list, x, mid + 1, right);
        }
    }
}
