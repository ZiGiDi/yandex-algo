package sprint6;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class K {

    /*
    Вы приехали на архипелаг Алгосы (наконец-то!). Здесь есть n достопримечательностей.
    Ваша лодка может высадить вас у одной из них, забрать у какой-то другой, возможно,
    той же самой достопримечательности и увезти на материк.

    Чтобы более тщательно спланировать свой маршрут, вы хотите узнать расстояния между
    каждой парой достопримечательностей Алгосов. Некоторые из них соединены мостами,
    по которым вы можете передвигаться в любую сторону. Всего мостов m.

    Есть вероятность, что карта архипелага устроена так, что нельзя добраться от какой-то одной
    достопримечательности до другой без использования лодки.

    Найдите кратчайшие расстояния между всеми парами достопримечательностей.
     */

    private static List<Map<Integer, Integer>> adjacencyList;

    //  Алгоритм Дейкстры
    private static List<Integer> distance;
    private static List<Integer> previous;
    private static List<Boolean> visited;

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            List<Integer> dataInfo = readList(reader);
            int numberOfVertexes = dataInfo.get(0);
            int numberOfEdges = dataInfo.get(1);

            adjacencyList = getAdjacencyListWithWeight(numberOfVertexes, numberOfEdges, reader);

            List<List<Integer>> shortcuts = findShortcuts(numberOfVertexes);
            writeSolution(shortcuts, writer);
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

    private static List<Map<Integer, Integer>> getAdjacencyListWithWeight(int numberOfVertexes,
                                                                          int numberOfEdges,
                                                                          BufferedReader reader) throws IOException {
        ArrayList<Map<Integer, Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            adjacencyList.add(new HashMap<>());
        }

        for (int i = 0; i < numberOfEdges; i++) {
            String[] split = reader.readLine().split(" ");
            int from = Integer.parseInt(split[0]);
            int to = Integer.parseInt(split[1]);
            int weight = Integer.parseInt(split[2]);
            adjacencyList.get(from - 1)
                    .compute(to - 1, (key, value) -> value != null ? value > weight ? weight : value : weight);
            adjacencyList.get(to - 1)
                    .compute(from - 1, (key, value) -> value != null ? value > weight ? weight : value : weight);
        }

        return adjacencyList;
    }

    private static List<List<Integer>> findShortcuts(int numberOfVertexes) {

        List<List<Integer>> result = new ArrayList<>(numberOfVertexes);

        for (int i = 0; i < numberOfVertexes; i++) {
            distance = initializeListWithDefaultValue(numberOfVertexes, Integer.MAX_VALUE);
            previous = initializeListWithDefaultValue(numberOfVertexes, -1);
            visited = initializeVisitedList(numberOfVertexes);

            dijkstra(i, numberOfVertexes);
            result.add(getDistances());
        }

        return result;
    }

    private static List<Integer> getDistances() {
        return distance.stream().map(value -> {

                    if (value == Integer.MAX_VALUE) {
                        return -1;
                    }
                    return value;
                })
                .collect(Collectors.toList());
    }

    private static List<Integer> initializeListWithDefaultValue(int numberOfVertexes, int defaultValue) {
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            colors.add(defaultValue);
        }
        return colors;
    }

    private static List<Boolean> initializeVisitedList(int numberOfVertexes) {
        List<Boolean> list = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            list.add(false);
        }
        return list;
    }

    static void dijkstra(int startingVertex, int numberOfVertexes) {

        distance.set(startingVertex, 0);

        while (true) {
            Integer u = getMinDistNotVisitedVertex(numberOfVertexes);

            if (u == null || distance.get(u) == Integer.MAX_VALUE) {
                break;
            }
            visited.set(u, true);

            Map<Integer, Integer> neighbours = adjacencyList.get(u);

            for (Map.Entry<Integer, Integer> neighbour : neighbours.entrySet()) {
                relax(u, neighbour);
            }
        }
    }

    static void relax(int u, Map.Entry<Integer, Integer> neighbour) {
        Integer distanceBetweenVertexes = distance.get(u) + neighbour.getValue();
        if (distance.get(neighbour.getKey()) > distanceBetweenVertexes) {
            distance.set(neighbour.getKey(), distanceBetweenVertexes);
            previous.set(neighbour.getKey(), u);
        }
    }

    static Integer getMinDistNotVisitedVertex(int numberOfVertexes) {
        Integer currentMinimum = Integer.MAX_VALUE;
        Integer currentMinimumVertex = null;

        for (int i = 0; i < numberOfVertexes; i++) {
            if (!visited.get(i) && distance.get(i) < currentMinimum) {
                currentMinimum = distance.get(i);
                currentMinimumVertex = i;
            }
        }
        return currentMinimumVertex;
    }

    private static void writeSolution(List<List<Integer>> shortcuts, BufferedWriter writer) throws IOException {
        for (List<Integer> list : shortcuts) {
            String string = list.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(" "));
            writer.write(string);
            writer.newLine();
        }
    }
}
