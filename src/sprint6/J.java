package sprint6;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class J {
//    Дан ациклический ориентированный граф (так называемый DAG, directed acyclic graph).
//    Найдите его топологическую сортировку, то есть выведите его вершины в таком порядке,
//    что все рёбра графа идут слева направо. У графа может быть несколько подходящих перестановок вершин.
//    Вам надо найти любую топологическую сортировку.

    private static List<Integer> colors;
    private static List<List<Integer>> adjacencyList;

    //    Топологическая сортировка
    private static final Stack<Integer> order = new Stack<>();


    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            List<Integer> dataInfo = readList(reader);
            int numberOfVertexes = dataInfo.get(0);
            int numberOfEdges = dataInfo.get(1);

            adjacencyList = getAdjacencyList(numberOfVertexes, numberOfEdges, reader);

            runDFS(numberOfVertexes);
            writeSolution(writer);
        }
    }

    private static void runDFS(int numberOfVertexes) {
        colors = initializeColor(numberOfVertexes);
        DFS(1);
        for (int i = 0; i < colors.size(); i++) {
            // Перебираем варианты стартовых вершин, пока они существуют.
            if (colors.get(i) == 0) {
                DFS(i + 1); // Запускаем обход, стартуя с i-й вершины.
            }
        }
    }

    private static void writeSolution(BufferedWriter writer) throws IOException {
        while (!order.isEmpty()) {
            writer.write(String.valueOf(order.pop()));
            writer.write(" ");
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
            List<Integer> integers = adjacencyList.get(from - 1);
            integers.add(to);
        }

        return adjacencyList;
    }

    private static List<Integer> initializeColor(int numberOfVertexes) {
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            colors.add(0);
        }
        return colors;
    }

    private static void DFS(int v) {
        colors.set(v - 1, 1); // Вершина посещена, но ещё не обработана.
        List<Integer> outgoingEdges = adjacencyList.get(v - 1);
        for (int w : outgoingEdges) {
            if (colors.get(w - 1) == 0) { // Если вершина не посещена, то
                DFS(w); // запустим обход от найденной смежной вершины.
            }
        }
        colors.set(v - 1, 2); // Теперь вершина обработана.
        order.push(v);
    }
}
