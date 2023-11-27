package sprint8;

import java.io.*;

public class E {

    private static int[] letterIndexes;

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String string = reader.readLine();

            initializeLetterIndexes(string);

            int addingNumber = readInt(reader);
            for (int i = 0; i < addingNumber; i++) {
                string = addSubstring(reader, string);
            }

            writer.write(string);
        }
    }

    private static void initializeLetterIndexes(String string) {
        letterIndexes = new int[string.length()+1];
        for (int i = 0; i < string.length()+1; i++) {
            letterIndexes[i] = i;
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static String addSubstring(BufferedReader reader, String string) throws IOException {
        String line = reader.readLine();
        String[] split = line.split(" ");
        String substring = split[0];
        int index = Integer.parseInt(split[1]);

        int wordIndex = letterIndexes[index];
        string = insert(string, wordIndex, substring);
        updateWordIndexes(wordIndex, substring);
        return string;
    }

    private static void updateWordIndexes(int wordIndex, String substring) {
        for (int j = wordIndex; j < letterIndexes.length; j++) {
            letterIndexes[j] = letterIndexes[j] + substring.length();
        }
    }

    public static String insert(String string, int index, String substring) {
        int length = string.length();
        int shift = substring.length();
        if (index > length) {
            // index == length - край строки
            throw new IllegalArgumentException("Нет такой позиции");
        }
        string = String.format("%-" + (length + shift) + "s", string);
        if (length > 0) {
            // Если length == 0, делать сдвиг нет смысла.
            // Кроме того, не следует в вычислениях писать (length - 1),
            // не проверив, что индекс не ноль.
            // В некоторых языках длина представляется беззнаковым целым числом,
            // в таком случае (length - 1) будет равен не -1, а числу MAX_INT,
            // и цикл станет некорректным. Мы этого избегаем.
            for (int i = length - 1; i >= index; i--) {
                string = string.substring(0, i + shift) + string.charAt(i) + string.substring(i + shift + 1);
            }
        }
        for (int i = 0; i < shift; i++) {
            string = string.substring(0, index + i) + substring.charAt(i) + string.substring(index + i + 1);
        }
        return string;
    }
}
