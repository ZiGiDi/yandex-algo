package sprint6.review;

import java.io.*;
import java.util.*;

public class A {

        /*
    id: https://contest.yandex.ru/contest/25070/run-report/95510243/

-- ПРИНЦИП РАБОТЫ --
    Сам граф храним в виде списка смежных вершин, А для поиска наиболее дорога остова используем аналог алгоритма Прима,
только вместо самых дешевых вершин берем самые дорогие. Все посещенные ребра храним в отдельном списке. И в случае, если
у графа несколько компонент связанности, а строить остов мы начинаем с любой вершины и можем перемещаться только по ребрам,
то количество посещенных вершин и количество вершин графа будут разными.

https://neerc.ifmo.ru/wiki/index.php?title=%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%9F%D1%80%D0%B8%D0%BC%D0%B0

-- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
    Т.к. при обходе вершин мы все ребра складываем в одну приоритетную очередь, где приоритет определяется по весу ребро,
то мы точно в любой момент времени получим самую весомое ребро.
    Т.к. для посещенных вершин мы храним отдельный список, то мы гарантируем, что не посетим одну и ту же вершину дважды.

-- ВРЕМЕННАЯ СЛОЖНОСТЬ --
    O(E * log(V)), V - количество вершин, E - количество ребер, т.к. мы используем приоритетную очередь для поиска самой тяжелого ребра.

-- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
    Память для списка смежности: O(2E+V), где E - количество ребер, V - количество вершин.
 2E - ребра храним дважды ждя каждой вершины, т.к. граф неориентирован.
 Отдельно тратим память для очереди ребер O(E), и так же список посещенных вершин O(V)

    Итого, при удалении констант: O(E+V)
     */

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
