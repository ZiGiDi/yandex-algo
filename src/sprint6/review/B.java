package sprint6.review;

import java.io.*;
import java.util.*;

public class B {

    private static final int WHITE = 0;
    private static final int GREY = 1;
    private static final int BLACK = 2;

    private static List<Integer> colors;
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
        colors = initializeListWithDefaultValue(numberOfVertexes, WHITE);

        for (int i = 0; i < numberOfVertexes; i++) {
            if (colors.get(i) != WHITE) {
                continue;
            }
            if (BFS(i)) {
                return true;
            }
        }
        return false;
    }

    private static List<Integer> initializeListWithDefaultValue(int numberOfVertexes, int defaultValue) {
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            colors.add(defaultValue);
        }
        return colors;
    }

    private static boolean BFS(int s) {
        // Создадим очередь вершин и положим туда стартовую вершину.
        Stack<Integer> planned = new Stack<>();
        planned.add(s);
        while (!planned.isEmpty()) {
            int u = planned.pop();// Возьмём вершину из очереди.
            if (colors.get(u) == WHITE) {
                colors.set(u, 1);
                planned.add(u);

                for (int v : adjacencyList.get(u)) { // adjList - список смежности графа.
                    if (colors.get(v) == WHITE) { // Серые и чёрные вершины уже
                        // либо в очереди, либо обработаны.
                        planned.add(v); // Запланируем посещение вершины.
                    }
                    if (colors.get(v) == GREY) {
                        return true; //Если наталкиваемся уже на серую вершину, значит граф цикличный
                    }
                }
                continue;
            }
            if (colors.get(u) == GREY) {
                colors.set(u, BLACK); // В конце красим обработанные вершины в черный цвет
            }
        }
        return false;
    }
}
