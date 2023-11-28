package sprint8;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class G {

    /*
    Гоша измерял температуру воздуха n дней подряд. В результате у него получился некоторый временной ряд.
    Теперь он хочет посмотреть, как часто встречается некоторый шаблон в получившейся последовательности.
    Однако температура — вещь относительная, поэтому Гоша решил, что при поиске шаблона длины m (a1, a2, ..., am)
    стоит также рассматривать сдвинутые на константу вхождения. Это значит, что если для некоторого числа c в
    исходной последовательности нашёлся участок вида (a1 + c, a2 + c, ... , am + c),
    то он тоже считается вхождением шаблона (a1, a2, ..., am).

    По заданной последовательности измерений X и шаблону A=(a1, a2, ..., am) определите все вхождения A в X,
    допускающие сдвиг на константу.

    Подсказка: если вы пишете на питоне и сталкиваетесь с TL, то попробуйте заменить какие-то из циклов операциями со срезами.
     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String textSize = reader.readLine();
            List<Integer> text = readList(reader);

            String patternSize = reader.readLine();
            List<Integer> pattern = readList(reader);

            List<Integer> result = findAll(text, pattern);
            String answer = result.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(" "));

            writer.write(answer);
        }
    }


    private static List<Integer> readList(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public static List<Integer> findAll(List<Integer> text, List<Integer> pattern) {
        List<Integer> occurrences = new ArrayList<>();
        int start = 0; // Начнём поиск с начала строки.
        // Найдём первое вхождение, если оно есть.
        while (true) {
            int pos = find(text, pattern, start);
            if (pos == -1) {
                break;
            }
            occurrences.add(pos + 1); // Сохраним вхождение в список.
            start = pos + 1;       // И продолжим поиск, начиная с позиции,
            // следующей за только что найденной.
        }
        return occurrences;
    }

    // Найти первое вхождение подстроки pattern в строке text,
//   находящееся на позиции не раньше start.
    public static int find(List<Integer> text, List<Integer> pattern, int start) {
        if (text.size() < pattern.size()) {
            return -1;  // Длинный шаблон не может содержаться в короткой строке.
        }
        for (int pos = start; pos <= text.size() - pattern.size(); pos++) {
            // Проверяем, не совпадёт ли шаблон, сдвинутый на позицию pos,
            //   с соответствующим участком строки.
            boolean match = true;
            int shift = text.get(pos) - pattern.get(0);
            for (int offset = 0; offset < pattern.size(); offset++) {
                if (text.get(pos + offset) != pattern.get(offset) + shift) {
                    // Одного несовпадения достаточно, чтобы не проверять
                    //   дальше текущее расположение шаблона.
                    match = false;
                    break;
                }
            }
            // Как только нашлось совпадение шаблона, возвращаем его.
            // Это первое вхождение шаблона в строку.
            if (match) {
                return pos;
            }
            // Если совпадение не нашлось, цикл перейдёт к проверке следующей позиции.
        }
        // Числом -1 часто маркируют, что подстрока не была найдена,
        //   поскольку в строке нет позиции -1.
        // В качестве альтернативы можно возвращать null.
        return -1;
    }
}
