package sprint8;

import java.io.*;
import java.util.*;

public class J {

    /*
    В некоторых IDE поддерживается навигация по файлам через их сокращённые названия.
    Если в языке принято называть классы CamelCase'ом (как в Java, например), то по заглавным буквам названия можно
    быстро найти нужный класс. Например, если название класса «MyFavouriteConfigurableScannerFactory»,
    то его можно найти по строке «MFCSF». Но если в проекте есть класс «theMultiFunctionalCommaSeparatedFile»,
    то он тоже будет подходить под этот паттерн, и при поиске надо будет выбрать между этими двумя вариантами.

    Вам дан набор строк в CamelCase. Далее будут поступать запросы в виде строк-паттернов из прописных букв английского алфавита.
    Вам надо находить такие строки среди исходных, которые удовлетворяют заданному шаблону, и выводить их в лексикографическом порядке.

    Также в паттерне может быть только несколько первых заглавных букв. Например, если бы в указанном выше примере был бы паттерн «MFCS»,
    то существующие две строки походили бы под него, а также подходил бы, например, «MamaFicusCodingSouthWestNorth».
    А вот «MamaCodingSouthWestNorth» –— уже нет.
     */

    private static final Node root = new Node();

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int classNamesNumbers = readInt(reader);
            for (int i = 0; i < classNamesNumbers; i++) {
                fillTrie(reader.readLine());
            }

            int searchValuesNumbers = readInt(reader);
            for (int i = 0; i < searchValuesNumbers; i++) {
                List<String> result = searchClassNames(reader.readLine());
                writeResult(result, writer);
            }
        }
    }

    private static void fillTrie(String className) {
        List<Character> chars = new ArrayList<>();
        for (Character ch : className.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                chars.add(ch);
            }
        }

        Node rootNode = root;

        if (chars.isEmpty()) {
            rootNode.getWords().add(className);
        }

        for (int i = 0; i < chars.size(); i++) {
            Node node = rootNode.getChildren()
                    .computeIfAbsent(chars.get(i), e -> new Node());
            if (i == chars.size() - 1) {
                node.getWords().add(className);
            }
            rootNode = node;
        }
    }

    private static List<String> searchClassNames(String searchValue) {
        List<String> result = new ArrayList<>();
        char[] charArray = searchValue.toCharArray();
        Node rootNode = root;
        for (int i = 0; i < charArray.length; i++) {
            if (!rootNode.getChildren().containsKey(charArray[i])) {
                return List.of();
            }
            Node node = rootNode.getChildren().get(charArray[i]);
            if (i == searchValue.length() - 1) {
                result.addAll(node.words);
            }
            rootNode = node;
        }

        if (searchValue.isEmpty()) {
            result.addAll(rootNode.words);
        }

        List<String> words = findAllWords(rootNode);
        result.addAll(words);
        return result;
    }

    private static List<String> findAllWords(Node rootNode) {
        List<String> words = new ArrayList<>();
        if (rootNode.getChildren().isEmpty()) {
            return List.of();
        }
        Stack<Node> stack = new Stack<>();
        stack.addAll(rootNode.getChildren().values());
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            stack.addAll(pop.getChildren().values());
            words.addAll(pop.getWords());
        }
        return words;
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static void writeResult(List<String> result, BufferedWriter writer) throws IOException {
        if (!result.isEmpty()) {
            Collections.sort(result);
            for (String st : result) {
                writer.write(st);
                writer.newLine();
            }
        } else {
            writer.write("");
            writer.newLine();
        }
    }

    private static class Node {

        private Map<Character, Node> children;
        private List<String> words;

        public Node() {
            children = new HashMap<>();
            words = new ArrayList<>();
        }

        public Map<Character, Node> getChildren() {
            return children;
        }

        public List<String> getWords() {
            return words;
        }
    }
}
