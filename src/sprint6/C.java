package sprint6;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class C {

//    Задан неориентированный граф. Обойдите с помощью DFS все вершины, достижимые из заданной вершины s,
//    и выведите их в порядке обхода, если начинать обход из s.

    private static List<String> colors;
    private static List<List<Integer>> adjacencyList;


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

            int startingVertexNumber = readInt(reader);

            List<Integer> traversalOrder = getTraversalOrder(numberOfVertexes, edges, startingVertexNumber);
            writeSolution(traversalOrder, writer);
        }
    }

    private static List<Integer> getTraversalOrder(int numberOfVertexes,
                                                   List<List<Integer>> edges,
                                                   int startingVertexNumber) {
        adjacencyList = getAdjacencyList(numberOfVertexes, edges);
        colors = initializeColor(numberOfVertexes);
        List<Integer> transferalOrder = new ArrayList<>(numberOfVertexes);
        DFS(startingVertexNumber, transferalOrder);
//        for (int i = 0; i < colors.size(); i++) {
//            // Перебираем варианты стартовых вершин, пока они существуют.
//            if (colors.get(i).equals("white")) {
//                DFS(i + 1, transferalOrder); // Запускаем обход, стартуя с i-й вершины.
//            }
//        }
        return transferalOrder;
    }

    private static void writeSolution(List<Integer> transferalOrder, BufferedWriter writer) throws IOException {
        String vertexesList = transferalOrder.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
        writer.write(vertexesList);
        writer.newLine();
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
            List<Integer> integers1 = adjacencyList.get(to - 1);
            integers1.add(from);
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

    private static void DFS(int v, List<Integer> transferalOrder) {
        transferalOrder.add(v);
        colors.set(v - 1, "gray"); // Вершина посещена, но ещё не обработана.
        List<Integer> outgoingEdges = adjacencyList.get(v - 1);
        for (int w : outgoingEdges) {
            if (colors.get(w - 1).equals("white")) { // Если вершина не посещена, то
                DFS(w, transferalOrder); // запустим обход от найденной смежной вершины.
            }
        }
        colors.set(v - 1, "black"); // Теперь вершина обработана.
    }
}
