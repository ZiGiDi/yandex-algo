package sprint8.review;

import java.io.*;
import java.util.*;

public class A {

        /*
    Вам даны строки в запакованном виде. Определим запакованную строку (ЗС) рекурсивно.
    Строка, состоящая только из строчных букв английского алфавита является ЗС.
    Если A и B —– корректные ЗС, то и AB является ЗС. Если A —– ЗС, а n — однозначное натуральное число, то n[A] тоже ЗС.
    При этом запись n[A] означает, что при распаковке строка A записывается подряд n раз.
    Найдите наибольший общий префикс распакованных строк и выведите его (в распакованном виде).
     */

        /*
        https://contest.yandex.ru/contest/26133/run-report/102207899/

        -- ПРИНЦИП РАБОТЫ --
            Первоначально распаковываем слово

           1. Идем по символам слова, каждый элемент которого добавляем в стэк
           2. Как только доходим до символа ']', начинаем вытаскивать элементы из стэка до символа '['
           3. Из прошлой операции мы имеем запакованный шаблон, далее из стэка вытаскиваем коэффициент умножения
           4. Распаковываем шаблон, дублируя его по размеру коэффициента
           5. Распакованную часть текста помещаем назад в стэк

            Далее для каждого слова после распаковки мы ищем общий префикс, первое слова становится префиксом,
          И далее сравнивая его с другими распакованными словами, мы его уменьшаем до общего префикса для всех слов

        -- ВРЕМЕННАЯ СЛОЖНОСТЬ --
          1. распаковка n слов длинною L O(n*L)
          2. И далее в худшем случае, если у всех слов префикс одинаковый, итерируемся по всем элементам слов O(n*L)

          итого: O(n*L)

        -- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
           Используем стэк для хранения элементов, в худшем случае он будет заполнен на размер максимального слова
           O(L)

         Итого: O(L)

     */


    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int linesNumber = readInt(reader);
            String prefix = null;
            for (int i = 0; i < linesNumber; i++) {
                String line = unpackLine(reader.readLine());
                prefix = findCommonPrefix(prefix, line);
            }

            if (prefix == null) {
                prefix = "";
            }
            writer.write(prefix);

        }
    }

    private static String findCommonPrefix(String prefix, String line) {
        if (prefix == null) {
            return line;
        }

        if (line.isEmpty()) {
            return "";
        }

        int prefixLength = Math.min(prefix.length(), line.length());
        char[] lineCharArray = line.toCharArray();
        char[] prefixCharArray = prefix.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prefixLength; i++) {
            if (lineCharArray[i] != prefixCharArray[i]) {
                break;
            }
            sb.append(prefix.charAt(i));
        }

        return sb.toString();
    }


    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static String unpackLine(String line) {
        Stack<String> stack = new Stack<>();
        for (char c : line.toCharArray()) {
            if (c == ']') {
                List<String> packedWord = new ArrayList<>();
                String stackChar = stack.pop();
                while (!stackChar.equals("[")) {
                    packedWord.add(stackChar);
                    stackChar = stack.pop();
                }
                Collections.reverse(packedWord);
                String word = String.join("", packedWord);
                int count = Integer.parseInt(stack.pop());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < count; i++) {
                    sb.append(word);
                }
                stack.push(sb.toString());
            } else {
                stack.push(Character.toString(c));
            }
        }
        StringBuilder result = new StringBuilder();
        for (String str : stack) {
            result.append(str);
        }
        return result.toString();
    }
}
