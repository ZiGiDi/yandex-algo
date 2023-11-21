package sprint7.review;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class B {
    /*
        На Алгосах устроили турнир по настольному теннису. Гоша выиграл n партий,
        получив при этом некоторое количество очков за каждую из них.

        Гоше стало интересно, можно ли разбить все заработанные им во время турнира очки на две части так,
        чтобы сумма в них была одинаковой.
     */

        /*
        id: https://contest.yandex.ru/contest/25597/run-report/97890165/

        -- ПРИНЦИП РАБОТЫ --
            Для доказательства того, что можно разделить последовательность на 2 равные по сумме подпоследовательности,
         достаточно того, что мы можем найти группу элементов, сумма которых дает половину суммы всей подпоследовательности.
         В основании логики лежит динамический массив dp размерностью в половину суммы всех элементов основной последовательности,
         который хранит результаты вычислений(индекс элемента массива) при различной группировке элементов основного массива.
            Первый индекс массива 0 является базовым достижимым элементом. Далее к уже достигнутым индексам (значения в массиве true) мы прибавляем
         следующий элемент нашей последовательности, получаем новые значения возможных сумм. Например:

         Имеем последовательность из примера: 1 5 7 1. Средняя сумма которой 7
         Создаем динамический массив размерностью 7 +1 и отмечаем на нем базовый элемент, как достигнутый:

            1 2 3 4 5 6 7
            + - - - - - -

         Следующим шагам к индексу достигнутого элемента прибавляем элемент последовательности 1:

            1 2 3 4 5 6 7
            + + - - - - -

         Берем следующий элемент 5 и продолжаем до тех пор, пока не будет достигнут индекс 7 в динамическом массиве.
         Если мы достигли последний индекс, то последовательность можно разделить, если нет, то нельзя. Вносим только те значения,
         которые лежат в промежутке от 0 до половины суммы всех элементов массива, остальные значения откидываем.

            1 2 3 4 5 6 7
            + - - - - - -  базовый элемент
            + + - - - - -  прибавили 1
            + + - - + + -  прибавили 5
            + + - - + + +  прибавляем 7

         * Если есть хоть один элемент, равный половине суммы всех элементов, то последовательность можно разделить
         * Если есть хоть один элемент больший суммы половины последовательности, то последовательность нельзя разделить
         * Т.к. мы рассматриваем только целочисленные значения, то если сумма элементов последовательности нечетная,
         то последовательность нельзя разделить

         -- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
            В основании лежит динамический массив, в котором мы сохраняем все возможные комбинации сумм элементов.
         Нам достаточно определить, что найдется сумма, равная половине суммы всех элементов, что позволит нам разбить последовательность
         на 2 равные по суммам подпоследовательности.

        -- ВРЕМЕННАЯ СЛОЖНОСТЬ --
           В худшем варианте нам нужно пройтись по всем элементам последовательности N, и для каждого элемента пройтись
        по динамической матрице размерность половины суммы всех элементов SUM(N)/2

        Итого: O(N*SUM(N)) N - Количество элементов в массиве, SUM(N) - сумма всех элементов

        -- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
           Используем доп память для хранения 2 динамических массивов размерностью половины суммы всех элементов SUM(N)/2

         Итого: O(SUM(N))

     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            reader.readLine();
            List<Integer> numbers = readList(reader);

            boolean partitioningPossible = isPartitioningPossible(numbers);

            String answer = partitioningPossible ? "True" : "False";

            writer.write(answer);
        }
    }

    private static boolean isPartitioningPossible(List<Integer> numbers) {

        int sum = numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        if (sum % 2 != 0) {
            return false;
        }

        int halfSum = sum / 2;
        boolean[] pervioudDp = new boolean[halfSum + 1];
        pervioudDp[0] = true;

        for (int number : numbers) {

            if (number > halfSum) {
                return false;
            }
            if (number == halfSum) {
                return true;
            }

            boolean[] currentDp = Arrays.copyOf(pervioudDp, halfSum + 1);
            for (int i = 0; i < pervioudDp.length; i++) {
                if (pervioudDp[i]) {
                    int nextIndex = i + number;
                    if (nextIndex == halfSum) {
                        return true;
                    }
                    if (nextIndex < halfSum) {
                        currentDp[nextIndex] = true;
                    }
                }
            }
            pervioudDp = currentDp;
        }
        return pervioudDp[halfSum];
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
