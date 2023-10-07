package sprint4;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class C {

    private static Map<Integer, Integer> aPowerOfNMap = new HashMap<>();
    private static Map<Integer, Integer> indexWithHashMap = new HashMap<>();
    private static String word;

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int a = readInt(reader);
            int m = readInt(reader);
            word = reader.readLine();
            int numberOfBoundaries = readInt(reader);

            List<List<Integer>> boundaries = new ArrayList<>(numberOfBoundaries);
            for (int i = 0; i < numberOfBoundaries; i++) {
                boundaries.add(readList(reader));
            }
            getHash(a, m, word);

            for (List<Integer> boundary : boundaries) {
                int hash = getHash(boundary, m);
                writer.write(String.valueOf(hash));
                writer.newLine();
            }
        }
    }

    private static int getHash(List<Integer> boundary, int m) {
        Integer firstIndex = boundary.get(0);
        Integer lastIndex = boundary.get(1);

        Integer first = indexWithHashMap.get(firstIndex);
        Integer aPow = aPowerOfNMap.get(word.length() - lastIndex);
        Integer last = indexWithHashMap.get(lastIndex + 1);
        return (Math.floorDiv(first, aPow) - last + m) % m;
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
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

    private static int getHash(int a, int m, String string) {
        if (string.isEmpty()) {
            return 0;
        }
        char[] charArray = string.toCharArray();
        long aPowOfN = a;
        aPowerOfNMap.put(0, 1);
        aPowerOfNMap.put(1, a);
        int length = charArray.length;
        int sum = (charArray[length - 1]) % m;
        indexWithHashMap.put(length, sum);
        indexWithHashMap.put(length + 1, 0);
        for (int i = length - 2; i >= 0; i--) {
            int charValue = charArray[i];
            sum = (int) ((sum + charValue * aPowOfN) % m);
            aPowOfN = (aPowOfN * a) % m;
            aPowerOfNMap.put(length - i, (int) aPowOfN);
            indexWithHashMap.put(i + 1, sum);
        }
        return sum;
    }
}
