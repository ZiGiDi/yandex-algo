package sprint1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class K {

//    Вася просил Аллу помочь решить задачу. На этот раз по информатике.
//
//    Для неотрицательного целого числа X списочная форма –— это массив его цифр слева направо. К примеру, для 1231 списочная форма будет [1,2,3,1]. На вход подается количество цифр числа Х, списочная форма неотрицательного числа Х и неотрицательное число K. Число К не превосходят 10000. Длина числа Х не превосходит 1000.
//
//    Нужно вернуть списочную форму числа X + K.
//
//    Не используйте встроенные средства языка для сложения длинных чисел.
//
//            Формат ввода
//    В первой строке — длина списочной формы числа X. На следующей строке — сама списочная форма с цифрами записанными через пробел.
//
//    В последней строке записано число K, 0 ≤ K ≤ 10000.
//
//    Формат вывода
//    Выведите списочную форму числа X+K.

    private static List<Integer> getSum(List<Integer> numberList, int k) {
        List<Integer> result = new ArrayList<>();
        Collections.reverse(numberList);
        char[] charArray = new StringBuilder(String.valueOf(k)).reverse().toString().toCharArray();

        int remainder = 0;
        for (int i = 0; i < Math.max(numberList.size(), charArray.length); i++) {
            int fromCharArray = getFromCharArray(charArray, i);
            int fromList = getFromList(numberList, i);
            int sum = fromCharArray + fromList + remainder;
            remainder = 0;

            if (sum >= 10) {
                remainder = 1;
                sum = sum - 10;
            }
            result.add(sum);
        }
        if(remainder > 0){
            result.add(remainder);
        }
        Collections.reverse(result);
        return result;
    }

    private static int getFromList(List<Integer> list, int index) {
        if (index < list.size()) {
            return list.get(index);
        }
        return 0;
    }

    private static int getFromCharArray(char[] chars, int index) {
        if (index < chars.length) {
            return Integer.valueOf(String.valueOf(chars[index]));
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int numberLength = readInt(reader);
            List<Integer> numberList = readList(reader);
            int k = readInt(reader);
            List<Integer> sum = getSum(numberList, k);
            for (int elem : sum) {
                writer.write(elem + " ");
            }
        }
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        return Arrays.asList(reader.readLine().split(" "))
                .stream()
                .map(elem -> Integer.parseInt(elem))
                .collect(Collectors.toList());
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}