package sprint8;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class H {

    /*
    Напишите программу, которая будет заменять в тексте все вхождения строки s на строку t.
    Гарантируется, что никакие два вхождения шаблона s не пересекаются друг с другом.
     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String text = reader.readLine();
            String pattern = reader.readLine();
            String replacement = reader.readLine();

            String result = replace(pattern, text, replacement);

            writer.write(result);
        }
    }

    private static String replace(String pattern, String text, String replacement) {
        List<Integer> search = searchPosition(pattern, text);

        int index = 0;
        StringBuilder sb = new StringBuilder();
        for (int pos : search) {
            sb.append(text, index, pos);
            sb.append(replacement);
            index = pos + pattern.length();
        }
        sb.append(text, index, text.length());
        return sb.toString();
    }

    public static List<Integer> searchPosition(String p, String text) {
        // Функция возвращает все позиции вхождения шаблона в тексте.
        List<Integer> result = new ArrayList<>();
        String s = p + "#" + text;
        int[] π = new int[p.length()];  // Массив длины |p|.
        Arrays.fill(π, 0);
        int π_prev = 0;
        for (int i = 1; i < s.length(); i++) {
            int k = π_prev;
            while (k > 0 && s.charAt(k) != s.charAt(i)) {
                k = π[k - 1];
            }
            if (s.charAt(k) == s.charAt(i)) {
                k++;
            }
            // Запоминаем только первые |p| значений π-функции.
            if (i < p.length()) {
                π[i] = k;
            }
            // Запоминаем последнее значение π-функции.
            π_prev = k;
            // Если значение π-функции равно длине шаблона, то вхождение найдено.
            if (k == p.length()) {
                // i - это позиция конца вхождения шаблона.
                // Дважды отнимаем от него длину шаблона, чтобы получить позицию начала:
                //  - чтобы «переместиться» на начало найденного шаблона,
                //  - чтобы не учитывать добавленное "pattern#".
                result.add(i - 2 * p.length());
            }
        }
        return result;
    }
}
