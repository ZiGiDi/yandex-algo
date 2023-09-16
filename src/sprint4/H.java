package sprint4;

import java.io.*;
import java.util.*;

public class H {

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

        Map<Character, Integer> mapA = new LinkedHashMap<>();
        Map<Character, Integer> mapB = new LinkedHashMap<>();
        char[] charArrayA = stringA.toCharArray();
        char[] charArrayB = stringB.toCharArray();
        for (int i = 0; i < charArrayA.length; i++) {
            char charA = charArrayA[i];
            char charB = charArrayB[i];
            if (mapA.containsKey(charA) && mapB.containsKey(charB)) {
                if (!mapA.get(charA).equals(mapB.get(charB))) {
                    return false;
                } else {
                    mapA.put(charA, i);
                    mapB.put(charB, i);
                }
            } else if (!mapA.containsKey(charA) && !mapB.containsKey(charB)) {
                mapA.put(charA, i);
                mapB.put(charB, i);
            } else {
                return false;
            }
        }
        return true;
    }
}
