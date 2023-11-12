package sprint6.review;

import java.io.*;
import java.util.*;

public class B {

            /*
    id: https://contest.yandex.ru/contest/25070/run-report/96906967/

-- ПРИНЦИП РАБОТЫ --
    Сам граф храним в виде списка смежных вершин. Ребра R разворачиваем.
    А для поиска циклов будем использовать обход в глубину.То есть из каждой вершины, в которую мы ещё ни разу не приходили,
запустим поиск в глубину, который при входе в вершину будет красить её в серый цвет, а при выходе из нее — в чёрный.
И, если алгоритм пытается пойти в серую вершину, то это означает, что цикл найден.

https://neerc.ifmo.ru/wiki/index.php?title=%D0%98%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5_%D0%BE%D0%B1%D1%85%D0%BE%D0%B4%D0%B0_%D0%B2_%D0%B3%D0%BB%D1%83%D0%B1%D0%B8%D0%BD%D1%83_%D0%B4%D0%BB%D1%8F_%D0%BF%D0%BE%D0%B8%D1%81%D0%BA%D0%B0_%D1%86%D0%B8%D0%BA%D0%BB%D0%B0

-- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
    Т.к. в основании обхода мы используем обход в глубину DFS, то при обходе, если мы натыкаемся на вершину, которую уже окрасили в серый цвет,
т.е. уровнем выше, то граф циккличен.

-- ВРЕМЕННАЯ СЛОЖНОСТЬ --
   O(V + E), где V - количество вершин, E - количество ребер. Обусловлено логикой алгоритма DFS

-- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
    Память для списка смежности: O(E+V), где E - количество ребер, V - количество вершин.
    Отдельно тратим память для хранения цветов вершин O(V).
    Так же для DFS используется не рекурсивный вызов, а стэк в который кладутся и достаются вершины - O(V)

    Итого, при удалении констант: O(E+V)
     */

    private static Color[] colors;
    private static List<List<Integer>> adjacencyList;

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int citiesNumber = readInt(reader);

            adjacencyList = getAdjacencyList(citiesNumber, reader);
            boolean isCyclic = checkCyclicGraph(citiesNumber);

            String result = isCyclic ? "NO" : "YES";

            writer.write(result);
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static List<List<Integer>> getAdjacencyList(int citiesNumber,
                                                        BufferedReader reader) throws IOException {
        ArrayList<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < citiesNumber; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int i = 0; i < citiesNumber - 1; i++) {
            String edges = reader.readLine();
            if (edges.isEmpty()) {
                continue;
            }
            char[] charArray = edges.toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                int from = i;
                int to = j + i + 1;
                if ('B' == charArray[j]) {
                    adjacencyList.get(from).add(to);
                } else {
                    adjacencyList.get(to).add(from);
                }
            }
        }
        return adjacencyList;
    }

    private static boolean checkCyclicGraph(int numberOfVertexes) {
        colors = initializeColors(numberOfVertexes);

        for (int i = 0; i < numberOfVertexes; i++) {
            if (colors[i] != Color.WHITE) {
                continue;
            }
            if (DFS(i)) {
                return true;
            }
        }
        return false;
    }

    private static Color[] initializeColors(int numberOfVertexes) {
        Color[] colors = new Color[numberOfVertexes];
        Arrays.fill(colors, Color.WHITE);
        return colors;
    }

    private static boolean DFS(int s) {
        // Создадим очередь вершин и положим туда стартовую вершину.
        Stack<Integer> planned = new Stack<>();
        planned.add(s);
        while (!planned.isEmpty()) {
            int u = planned.pop();// Возьмём вершину из очереди.
            if (colors[u] == Color.WHITE) {
                colors[u] = Color.GREY;
                planned.add(u);

                for (int v : adjacencyList.get(u)) { // adjList - список смежности графа.
                    if (colors[v] == Color.WHITE) { // Серые и чёрные вершины уже
                        // либо в очереди, либо обработаны.
                        planned.add(v); // Запланируем посещение вершины.
                    }
                    if (colors[v] == Color.GREY) {
                        return true; //Если наталкиваемся уже на серую вершину, значит граф цикличный
                    }
                }
                continue;
            }
            if (colors[u] == Color.GREY) {
                colors[u] = Color.BLACK; // В конце красим обработанные вершины в черный цвет
            }
        }
        return false;
    }

    public enum Color {
        WHITE,
        GREY,
        BLACK
    }
}
