package sprint6;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class L {
    /*
    Неориентированный граф называется полным, если в нём каждая пара вершин соединена ребром.

    Вам дан неориентированный граф из n вершин и m рёбер. Выясните, является ли этот граф полным.
     */

    private static List<Set<Integer>> adjacencyList;

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            List<Integer> dataInfo = readList(reader);
            int numberOfVertexes = dataInfo.get(0);
            int numberOfEdges = dataInfo.get(1);

            adjacencyList = getAdjacencyList(numberOfVertexes, numberOfEdges, reader);

            boolean isCompleteGraph = checkCompleteGraph(numberOfVertexes);
            writeSolution(isCompleteGraph, writer);
        }
    }

    private static boolean checkCompleteGraph(int numberOfVertexes) {
        for (Set<Integer> vertexes : adjacencyList) {
            if (vertexes.size() != numberOfVertexes) {
                return false;
            }
        }

        return true;
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

    private static List<Set<Integer>> getAdjacencyList(int numberOfVertexes,
                                                       int numberOfEdges,
                                                       BufferedReader reader) throws IOException {
        ArrayList<Set<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            HashSet<Integer> neighbours = new HashSet<>();
            neighbours.add(i);
            adjacencyList.add(neighbours);
        }

        for (int i = 0; i < numberOfEdges; i++) {
            String[] split = reader.readLine().split(" ");
            int from = Integer.parseInt(split[0]);
            int to = Integer.parseInt(split[1]);
            adjacencyList.get(from - 1).add(to - 1);
            adjacencyList.get(to - 1).add(from - 1);
        }

        return adjacencyList;
    }

    private static void writeSolution(boolean isCompleteGraph,
                                      BufferedWriter writer) throws IOException {
        String result = isCompleteGraph ? "YES" : "NO";
        writer.write(result);
    }
}
