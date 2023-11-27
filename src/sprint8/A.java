package sprint8;

import java.io.*;

public class A {

    /*
    В некоторых языках предложения пишутся и читаются не слева направо, а справа налево.
    Вам под руку попался странный текст –— в нём обычный (слева направо) порядок букв в словах.
    А вот сами слова идут в противоположном направлении. Вам надо преобразовать текст так, чтобы слова в нём были написаны слева направо.
     */

    private static final String WORD_SEPARATOR = " ";

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String string = reader.readLine();

            String result = revertWordsOrder(string);

            writer.write(result);
        }
    }

    private static String revertWordsOrder(String string) {
        StringBuilder sb = new StringBuilder();
        String[] split = string.split(WORD_SEPARATOR);
        for (int i = split.length - 1; i >= 0; i--) {
            sb.append(split[i]);
            sb.append(WORD_SEPARATOR);
        }
        return sb.toString();
    }
}
