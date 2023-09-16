package sprint4;

import java.io.*;
import java.util.*;

public class H {
    /*
    Жители Алгосского архипелага придумали новый способ сравнения строк. Две строки считаются равными,
    если символы одной из них можно заменить на символы другой так, что первая строка станет точной копией второй строки.
    При этом необходимо соблюдение двух условий:

    Порядок вхождения символов должен быть сохранён.
    Одинаковым символам первой строки должны соответствовать одинаковые символы второй строки. Разным символам —– разные.
    Например, если строка s = «abacaba», то ей будет равна строка t = «xhxixhx», так как все вхождения «a» заменены на «x»,
    «b» –— на «h», а «c» –— на «i». Если же первая строка s=«abc», а вторая t=«aaa», то строки уже не будут равны, так как
    разные буквы первой строки соответствуют одинаковым буквам второй.
     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String stringA = reader.readLine();
            String stringB = reader.readLine();
            boolean result = isEquals(stringA, stringB);
            writer.write(result ? "YES" : "NO");
        }
    }

    private static boolean isEquals(String stringA, String stringB) {
        if (stringA.length() != stringB.length()) {
            return false;
        }

        Map<Character, Character> dictionary = new HashMap<>();
        char[] charArrayA = stringA.toCharArray();
        char[] charArrayB = stringB.toCharArray();
        for (int i = 0; i < charArrayA.length; i++) {
            char charA = charArrayA[i];
            char charB = charArrayB[i];
            boolean containsChar = dictionary.containsKey(charA);
            if (containsChar && !dictionary.get(charA).equals(charB)) {
                return false;
            } else if (!containsChar) {
                dictionary.put(charA, charB);
            }
        }
        HashSet<Character> characters = new HashSet<>(dictionary.values());
        return dictionary.size() == characters.size();
    }
}
