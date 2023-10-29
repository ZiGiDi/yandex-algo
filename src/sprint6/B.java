package sprint6;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class B {

    //    Алла успешно справилась с предыдущим заданием, и теперь ей дали новое. На этот раз список рёбер ориентированного
//    графа надо переводить в матрицу смежности. Конечно же, Алла попросила вас помочь написать программу для этого.
    private static List<List<Integer>> getAdjacencyMatrix(int numberOfVertexes, List<List<Integer>> edges) {
        ArrayList<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            adjacencyList.add(new ArrayList<>(Collections.nCopies(numberOfVertexes, 0)));
        }

        for (List<Integer> edge : edges) {
            int from = edge.get(0);
            int to = edge.get(1);
            adjacencyList.get(from - 1).set(to - 1, 1);
        }

        return adjacencyList;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            List<Integer> dataInfo = readList(reader);
            int numberOfVertexes = dataInfo.get(0);
            int numberOfEdges = dataInfo.get(1);

            List<List<Integer>> edges = new ArrayList<>(numberOfEdges);
            while (reader.ready()) {
                edges.add(readList(reader));
            }

            List<List<Integer>> adjacencyList = getAdjacencyMatrix(numberOfVertexes, edges);
            writeSolution(adjacencyList, writer);
        }
    }

    private static void writeSolution(List<List<Integer>> adjacencyList, BufferedWriter writer) throws IOException {
        for (List<Integer> vertexes : adjacencyList) {
            String vertexesList = vertexes.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(" "));
            writer.write(vertexesList);
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
}
