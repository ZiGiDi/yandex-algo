package sprint4.review;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class A {
    private static final Map<String, Map<Integer, Integer>> wordsByTextMap = new HashMap<>();
    private static final String WORD_SEPARATOR = " ";
    private static final int RESULT_LIMIT = 5;

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int numberOfDocuments = readInt(reader);
            for (int i = 0; i < numberOfDocuments; i++) {
                fillWordsHashMap(reader.readLine(), i + 1);
            }

            int numberOfSearches = readInt(reader);
            for (int i = 0; i < numberOfSearches; i++) {
                writer.write(found(reader.readLine()));
                writer.newLine();
            }
        }
    }

    private static void fillWordsHashMap(String document, int documentNumber) {
        for (String word : document.split(WORD_SEPARATOR)) {
            Map<Integer, Integer> textCountMap = wordsByTextMap.computeIfAbsent(word, e -> new HashMap<>());
            textCountMap.put(documentNumber, textCountMap.getOrDefault(documentNumber, 0) + 1);
        }
    }

    private static String found(String searchText) {
        Map<Integer, Integer> resultMap = new HashMap<>();
        HashSet<String> words = new HashSet<>(Arrays.asList(searchText.split(" ")));
        for (String word : words) {
            if (wordsByTextMap.containsKey(word)) {
                Map<Integer, Integer> textCountMap = wordsByTextMap.get(word);
                for (Map.Entry<Integer, Integer> entry : textCountMap.entrySet()) {
                    resultMap.put(entry.getKey(), resultMap.getOrDefault(entry.getKey(), 0) + entry.getValue());
                }
            }
        }
        return resultMap.entrySet().stream()
                .sorted(getEntryComparator())
                .limit(RESULT_LIMIT)
                .map(Map.Entry::getKey)
                .map(Object::toString)
                .collect(Collectors.joining(WORD_SEPARATOR));
    }

    private static Comparator<Map.Entry<Integer, Integer>> getEntryComparator() {
        return (a, b) -> {
            int compare = Integer.compare(b.getValue(), a.getValue());
            if (compare == 0) {
                return Integer.compare(a.getKey(), b.getKey());
            }
            return compare;
        };
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}
