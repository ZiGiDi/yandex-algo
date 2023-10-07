package sprint4.review;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class A {
    private static final Map<String, Map<Integer, Integer>> wordsByTextMap = new HashMap<>();
    private static final String WORD_SEPARATOR = " ";
    private static final int RESULT_LIMIT = 5;

    /*
    id: https://contest.yandex.ru/contest/24414/run-report/90887058/

-- ПРИНЦИП РАБОТЫ --
1) Из документов, в которых будет осуществляться поиск заполняем мапу словаря: Map<слово, Map<Номер_документа, Количество_вхождений_слова>>
2) Из запроса выделяем уникальные слова и проводим поиск по словарю, высчитывая релевантность документа по условию и заполняя
мапу Map<Номер_документа, Релевантность_документа>
3) Сортируем согласно условию задачи и выводим результат

-- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
Т.к. используется стандартная реализация мап, то будем считать гарантированным,
что на запрашиваемые данные из мап мы получим конкретный элемент, который мы туда и положили

-- ВРЕМЕННАЯ СЛОЖНОСТЬ --
На заполнения словаря:
O(d*w), где d - количество документов, по которым нужно осуществить поиск, w - количество слов в документе.
Т.к. нам нужно пройтись по всем словам из документов
Сбор результата:
O(R*W*D), где R - количество запросов, W - уникальные слова в запросе, D количество документов, в которых эти слова присутствуют
Т.к. нужно пройтись по все запросам, выделив из них уникальные слова и для каждого слова из словаря мы получаем список документов,
в котором это слово пресутсвуют
Сортировка результата:
Используется стандартная реализация сортировки, сложность которой O(nlog(n)), где n - это число всех найденных документов.
Но т.к. число найденных документов существенно меньше произведения R*W*D, то им можно пренебречь
Итого затраты будут складываться из заполнения словаря и сбора результата

П.С. Валерий, спасибо за ваши замечания! Очень жаль, что в полях ревью никак на них нельзя отреагировать

-- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
На словарь:
O(d*w), где d - количество документов, по которых нужно осуществить поиск, w - количество слов в документе.
На формирование результата поиска Map<Номер_документа, Релевантность_документа>:
O(D), где D количество документов, в которых есть искомые слова
     */

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
