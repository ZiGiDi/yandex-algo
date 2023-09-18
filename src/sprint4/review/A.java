package sprint4.review;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class A {
    private static final Map<String, Map<Integer, Integer>> wordsByTextMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int numberOfDocuments = readInt(reader);
            for (int i = 0; i < numberOfDocuments; i++) {
                fillHashMAp(reader.readLine(), i + 1);
            }

            int numberOfSearches = readInt(reader);
            for (int i = 0; i < numberOfSearches; i++) {
                List<Integer> result = found(reader.readLine());
                for (Integer textNumber : result) {
                    writer.write(textNumber + " ");
                }
                writer.newLine();
            }
        }
    }

    private static List<Integer> found(String searchText) {
        Map<Integer, Integer> resultMap = new HashMap<>();
        HashSet<String> words = new HashSet<>(Arrays.asList(searchText.split(" ")));
        for (String word : words) {
            if (wordsByTextMap.containsKey(word)) {
                Map<Integer, Integer> textCountMap = wordsByTextMap.get(word);
                for (Map.Entry<Integer, Integer> entry : textCountMap.entrySet()) {
                    resultMap.computeIfPresent(entry.getKey(), (key, val) -> val + entry.getValue());
                    resultMap.computeIfAbsent(entry.getKey(), a -> entry.getValue());
                }
            }
        }
        return resultMap.entrySet().stream().sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static void fillHashMAp(String document, int documentNumber) {
        for (String word : document.split(" ")) {
            Map<Integer, Integer> textCountMap = wordsByTextMap
                    .computeIfAbsent(word, e -> new HashMap<>());
            if (textCountMap.containsKey(documentNumber)) {
                textCountMap.put(documentNumber, textCountMap.get(documentNumber) + 1);
            } else {
                textCountMap.put(documentNumber, 1);
            }
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}
