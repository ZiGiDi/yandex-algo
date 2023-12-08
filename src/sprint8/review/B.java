package sprint8.review;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class B {

    /*
    Вася готовится к экзамену по алгоритмам и на всякий случай пишет шпаргалки.

    Чтобы уместить на них как можно больше информации, он не разделяет слова пробелами.
    В итоге получается одна очень длинная строка. Чтобы на самом экзамене из-за нервов не запутаться в прочитанном,
    он просит вас написать программу, которая по этой длинной строке и набору допустимых слов определит,
    можно ли разбить текст на отдельные слова из набора.

     Более формально: дан текст T и набор строк s1, ... ,sn. Надо определить, представим ли T как sk1sk2...skr, где
     ki — индексы строк. Индексы могут повторяться. Строка si может встречаться в разбиении текста T произвольное число раз.
     Можно использовать не все строки для разбиения. Строки могут идти в любом порядке.
     */

    /*
        https://contest.yandex.ru/contest/26133/run-report/102017563/

        -- ПРИНЦИП РАБОТЫ --
            Пошел по простому пути. Строим префиксное дерево и отмечаем терминальные ноды.
         Далее берем наш тект, который нам нужно проверить и перебирая буквы находим все возможные варианты комбинация слов,
         из префиксного дерева, отмечая успешо посещенные буквы текста в динамическом массиве. Как только хоть одна из комбинаций
         дошла до последней буквы текста, то текст можно составить из исходных слов в префиксном дереве

         -- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
            В основании лежит динамический массив, в котором мы сохраняем все возможные комбинации элементов.
        Префиксное дерево хранит все шаблоны. По тексту мы проходим по всем буквам и отмечаем все варианты в динамическом массиве

        -- ВРЕМЕННАЯ СЛОЖНОСТЬ --
          1. Нужно заполнить префиксное дерево пройдясь по всем буквам шаблонов O(Σ(W))
          2. Далее проходим по каждой букве текста длиной L и итерируемся по самому длинному слову из шаблона W
          O(L*W)

          итого: O(Σ(W)) + O(L*W)

        -- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
           Используем доп память для хранения динамических массива длины исходного текста L O(L),
         плюс для хранения префиксного дерева O(Σ(W))

         Итого: O(L + Σ(W))

     */

    private static final Node root = new Node();

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String text = reader.readLine();

            int wordsNumber = readInt(reader);
            for (int i = 0; i < wordsNumber; i++) {
                fillTrie(reader.readLine());
            }
            boolean containsWords = checkText(text);
            String result = containsWords ? "YES" : "NO";

            writer.write(result);

        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static void fillTrie(String word) {
        Node rootNode = root;
        char[] chars = word.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            Node node = rootNode.getChildren()
                    .computeIfAbsent(chars[i], e -> new Node());
            if (i == chars.length - 1) {
                node.setTerminal(true);
            }
            rootNode = node;
        }
    }

    private static boolean checkText(String text) {
        int textLength = text.length();
        boolean[] dp = new boolean[textLength + 1];
        dp[0] = true;
        for (int i = 0; i < textLength; i++) {
            Node node = root;
            if (dp[textLength]) {
                return true;
            }
            if (dp[i]) {
                for (int j = i; j < textLength + 1; j++) {
                    if (node.isTerminal()) {
                        dp[j] = true;
                    }
                    if (j == textLength || !node.getChildren().containsKey(text.charAt(j))) {
                        break;
                    }
                    node = node.getChildren().get(text.charAt(j));
                }
            }
        }
        return dp[textLength];
    }


    private static class Node {

        private final Map<Character, Node> children;
        private boolean terminal = false;

        public Node() {
            children = new HashMap<>();
        }

        public Map<Character, Node> getChildren() {
            return children;
        }

        public boolean isTerminal() {
            return terminal;
        }

        public void setTerminal(boolean terminal) {
            this.terminal = terminal;
        }
    }
}

