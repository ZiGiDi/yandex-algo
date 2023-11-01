package sprint6.review;

import java.io.*;
import java.util.*;

public class A {

    private static List<List<Edge>> adjacencyList;
    private static PriorityQueue<Edge> edgesForVisiting;
    private static final HashSet<Integer> visitedVertexNumbers = new HashSet<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            List<Integer> dataInfo = readList(reader);
            int numberOfVertexes = dataInfo.get(0);
            int numberOfEdges = dataInfo.get(1);

            adjacencyList = getAdjacencyListWithWeight(numberOfVertexes, numberOfEdges, reader);
            String maxWeight = findMaxWeight(numberOfVertexes);

            writer.write(maxWeight);
        }
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        String[] split = s.split(" ");
        return List.of(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }

    private static List<List<Edge>> getAdjacencyListWithWeight(int numberOfVertexes,
                                                               int numberOfEdges,
                                                               BufferedReader reader) throws IOException {
        ArrayList<List<Edge>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int i = 0; i < numberOfEdges; i++) {
            String[] split = reader.readLine().split(" ");
            int from = Integer.parseInt(split[0]);
            int to = Integer.parseInt(split[1]);
            int weight = Integer.parseInt(split[2]);
            if (from == to) {
                continue;
            }
            adjacencyList.get(from - 1).add(new Edge(to - 1, weight));
            adjacencyList.get(to - 1).add(new Edge(from - 1, weight));
        }

        return adjacencyList;
    }

    private static String findMaxWeight(int numberOfVertexes) {
        edgesForVisiting = new PriorityQueue<>((x, y) -> Integer.compare(y.getWeight(), x.getWeight()));
        return counterPrimsAlgo(numberOfVertexes);
    }

    static String counterPrimsAlgo(int numberOfVertexes) {
        long weightSum = 0;
        visitedVertexNumbers.add(0);
        updateEdgesForVisitingList(0);

        while (!edgesForVisiting.isEmpty()) {
            Edge maxWeightEdge = getMaxWeightEdge();
            if (maxWeightEdge == null) break;

            int newVisitedVertex = maxWeightEdge.getTo();
            weightSum = weightSum + maxWeightEdge.getWeight();

            visitedVertexNumbers.add(newVisitedVertex);
            updateEdgesForVisitingList(newVisitedVertex);
        }

        if (numberOfVertexes != visitedVertexNumbers.size()) {
            return "Oops! I did it again";
        }
        return String.valueOf(weightSum);
    }

    private static Edge getMaxWeightEdge() {
        Edge vertexWeightEntry = edgesForVisiting.poll();
        while (vertexWeightEntry != null && visitedVertexNumbers.contains(vertexWeightEntry.getTo())) {
            vertexWeightEntry = edgesForVisiting.poll();
        }
        return vertexWeightEntry;
    }

    private static void updateEdgesForVisitingList(int vertexNumber) {
        for (Edge edge : adjacencyList.get(vertexNumber)) {
            if (!visitedVertexNumbers.contains(edge.getTo())) {
                edgesForVisiting.add(edge);
            }
        }
    }

    private static class Edge {
        private final int to;
        private final int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        public int getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }
    }
}
