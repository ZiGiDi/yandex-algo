package sprint6;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class H {

//    Вам дан ориентированный граф. Известно, что все его вершины достижимы из вершины s=1.
//    Найдите время входа и выхода при обходе в глубину, производя первый запуск из вершины s.
//    Считайте, что время входа в стартовую вершину равно 0. Соседей каждой вершины обходите
//    в порядке увеличения номеров.
    private static List<String> colors;
    private static List<List<Integer>> adjacencyList;

    //    Поиск в глубину с метками времени
    private static List<Integer> entry;
    private static List<Integer> leave;
    private static int time = -1;

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            List<Integer> dataInfo = readList(reader);
            int numberOfVertexes = dataInfo.get(0);
            int numberOfEdges = dataInfo.get(1);

            List<List<Integer>> edges = new ArrayList<>(numberOfEdges);
            for (int i = 0; i < numberOfEdges; i++) {
                edges.add(readList(reader));
            }
            runDFS(numberOfVertexes, edges);
            writeSolution(numberOfVertexes, writer);
        }
    }

    private static void runDFS(int numberOfVertexes,
                               List<List<Integer>> edges) {
        adjacencyList = getAdjacencyList(numberOfVertexes, edges);
        colors = initializeColor(numberOfVertexes);
        entry = initializeEntry(numberOfVertexes);
        leave = initializeEntry(numberOfVertexes);
        DFS(1);
    }

    private static List<Integer> initializeEntry(int numberOfVertexes) {
        return new ArrayList<>(Collections.nCopies(numberOfVertexes, 0));
    }

    private static void writeSolution(int numberOfVertexes, BufferedWriter writer) throws IOException {
        for (int i = 0; i < numberOfVertexes; i++) {
            writer.write(String.valueOf(entry.get(i)));
            writer.write(" ");
            writer.write(String.valueOf(leave.get(i)));
            writer.newLine();
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

    private static List<List<Integer>> getAdjacencyList(int numberOfVertexes, List<List<Integer>> edges) {
        ArrayList<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (List<Integer> edge : edges) {
            int from = edge.get(0);
            int to = edge.get(1);
            List<Integer> integers = adjacencyList.get(from - 1);
            integers.add(to);
        }

        for (int i = 0; i < numberOfVertexes; i++) {
            Collections.sort(adjacencyList.get(i));
        }

        return adjacencyList;
    }

    private static List<String> initializeColor(int numberOfVertexes) {
        List<String> colors = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            colors.add("white");
        }
        return colors;
    }

    private static void DFS(int v) {
        time += 1;
        entry.set(v - 1, time);
        colors.set(v - 1, "gray"); // Вершина посещена, но ещё не обработана.
        List<Integer> outgoingEdges = adjacencyList.get(v - 1);
        for (int w : outgoingEdges) {
            if (colors.get(w - 1).equals("white")) { // Если вершина не посещена, то
                DFS(w); // запустим обход от найденной смежной вершины.
            }
        }
        time += 1;
        leave.set(v - 1, time);
        colors.set(v - 1, "black"); // Теперь вершина обработана.
    }
}
