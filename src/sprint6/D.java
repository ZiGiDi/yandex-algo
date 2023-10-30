package sprint6;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class D {

//    Задан неориентированный граф. Обойдите поиском в ширину все вершины, достижимые из заданной вершины s,
//    и выведите их в порядке обхода, если начинать обход из s.

    private static List<Integer> colors;
    private static List<List<Integer>> adjacencyList;

    //    BFS Поиск в ширину
    private static List<Integer> distance;
    private static List<Integer> previous;

    private static final List<Integer> order = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            List<Integer> dataInfo = readList(reader);
            int numberOfVertexes = dataInfo.get(0);
            int numberOfEdges = dataInfo.get(1);

            adjacencyList = getAdjacencyList(numberOfVertexes, numberOfEdges, reader);
            int startingVertexNumber = readInt(reader);

            runBFS(numberOfVertexes, startingVertexNumber);
            writeSolution(writer);
        }
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

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static List<List<Integer>> getAdjacencyList(int numberOfVertexes,
                                                        int numberOfEdges,
                                                        BufferedReader reader) throws IOException {
        ArrayList<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int i = 0; i < numberOfEdges; i++) {
            String[] split = reader.readLine().split(" ");
            int from = Integer.parseInt(split[0]);
            int to = Integer.parseInt(split[1]);
            adjacencyList.get(from - 1).add(to);
            adjacencyList.get(to - 1).add(from);
        }

        for (int i = 0; i < numberOfVertexes; i++) {
            Collections.sort(adjacencyList.get(i));
        }

        return adjacencyList;
    }

    private static void runBFS(int numberOfVertexes, int startingVertexNumber) {
        colors = initializeListWithDefaultValue(numberOfVertexes, 0);
        distance = initializeListWithDefaultValue(numberOfVertexes, -1);
        previous = initializeListWithDefaultValue(numberOfVertexes, -1);

        BFS(startingVertexNumber);
    }

    private static List<Integer> initializeListWithDefaultValue(int numberOfVertexes, int defaultValue) {
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            colors.add(defaultValue);
        }
        return colors;
    }

    private static void BFS(int s) {
        // Создадим очередь вершин и положим туда стартовую вершину.
        Queue<Integer> planned = new LinkedList<>();
        planned.add(s);
        colors.set(s - 1, 1);
        distance.set(s - 1, 0);
        while (!planned.isEmpty()) {
            int u = planned.poll();// Возьмём вершину из очереди.
            order.add(u);
            for (int v : adjacencyList.get(u - 1)) { // adjList - список смежности графа.
                if (colors.get(v - 1) == 0) { // Серые и чёрные вершины уже
                    // либо в очереди, либо обработаны.
                    distance.set(s - 1, distance.get(u - 1) + 1);
                    previous.set(v - 1, u);
                    colors.set(v - 1, 1);
                    planned.add(v); // Запланируем посещение вершины.
                }
            }
            colors.set(u - 1, 2); // Теперь вершина считается обработанной.
        }
    }

    private static void writeSolution(BufferedWriter writer) throws IOException {
        String vertexesList = order.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
        writer.write(vertexesList);
    }
}
