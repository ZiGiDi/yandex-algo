package sprint7.review;

import java.io.*;
import java.util.Arrays;

public class A {
    /*
        Расстоянием Левенштейна между двумя строками s и t называется количество атомарных изменений,
        с помощью которых можно одну строку превратить в другую. Под атомарными изменениями подразумеваются:
        удаление одного символа, вставка одного символа, замена одного символа на другой.

        Найдите расстояние Левенштейна для предложенной пары строк.
        Выведите единственное число — расстояние между строками.

     */

        /*
        id: https://contest.yandex.ru/contest/25597/run-report/98194210/

        -- ПРИНЦИП РАБОТЫ --
         Для определения расстояния Левенштейна используется формула Вагнера-Фишера. Логика отрабатывает по рекуретной формуле,
         описание которой лежит по ссылке ниже. Все сводится к заполнении матрицы размерностью m * n, гле n и m длины строк.
         По формуле вычисляется  минимальная стоимость действия на вставку, удаление или замену слова.


         https://ru.wikipedia.org/wiki/%D0%A0%D0%B0%D1%81%D1%81%D1%82%D0%BE%D1%8F%D0%BD%D0%B8%D0%B5_%D0%9B%D0%B5%D0%B2%D0%B5%D0%BD%D1%88%D1%82%D0%B5%D0%B9%D0%BD%D0%B0


         -- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
            Сами шаги рекуретной формулы представляют собой простые логические операции, основанные на предыдущих результатах вычислений,
         которые мы храним в матрице. Более детально с доказательством можно ознакомиться по ссылке из принципа работы.

        -- ВРЕМЕННАЯ СЛОЖНОСТЬ --
            Алгоритм должен пройтись один раз по матрице размерностью (M+1)*(N+1), где N - количество символов в первой строке,
        а M во второй.

        Итого: O(N*M)

        -- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
           Используется 2 динамические матрицы размерностью (M+1), Где M - Длина второй строки. Т.к на каждом шаге итерации
        нам нужны результаты только текущей итерации и предыдущей, то можно освободить память, и не хранить результаты вычислений
        для все матрицы размерностью (N+1)*(M+1), а только текущей и предыдущий. Отсюда и 2 матрицы размерностью M+1

          Итого: O(M)
     */

    private static int[] currentDp;
    private static int[] previousDp;

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();

            int distance = levenshteinDistance(line1, line2);

            writer.write(String.valueOf(distance));
        }
    }

    private static int levenshteinDistance(String line1, String line2) {
        currentDp = new int[line2.length() + 1];
        for (int i = 0; i < line1.length()+1; i++) {
            previousDp = Arrays.copyOf(currentDp, currentDp.length);
            for (int j = 0; j < line2.length()+1; j++) {
                currentDp[j] = levDiv(i, j, line1, line2);
            }
        }
        return currentDp[line2.length()];
    }

    private static int levDiv(int i, int j, String line1, String line2) {
        if (i == 0 && j == 0) {
            return 0;
        }
        if (i > 0 && j == 0) {
            return i;
        }
        if (j > 0 && i == 0) {
            return j;
        }

        int m = (line1.charAt(i - 1) == line2.charAt(j - 1)) ? 0 : 1;
        return Math.min(previousDp[j - 1] + m,
                Math.min(previousDp[j] + 1, currentDp[j - 1] + 1));
    }
}