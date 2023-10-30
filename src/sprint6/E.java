package sprint6;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class E {
//    Вам дан неориентированный граф. Найдите его компоненты связности.

    private static List<Integer> colors;
    private static List<List<Integer>> adjacencyList;

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
            adjacencyList.get(from - 1).add(to);
            adjacencyList.get(to - 1).add(from);
        }

        return adjacencyList;
    }

    private static void runDFS(int numberOfVertexes) {
        colors = initializeColor(numberOfVertexes);
        int colorNumber = 0;
        for (int i = 0; i < colors.size(); i++) {
            // Перебираем варианты стартовых вершин, пока они существуют.
            if (colors.get(i) == 0) {
                colorNumber = colorNumber + 1;
                DFS(i + 1, colorNumber); // Запускаем обход, стартуя с i-й вершины.
            }
        }
    }

    private static List<Integer> initializeColor(int numberOfVertexes) {
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            colors.add(0);
        }
        return colors;
    }

    private static void DFS(int v, int color) {
        colors.set(v - 1, -1); // Вершина посещена, но ещё не обработана.
        List<Integer> outgoingEdges = adjacencyList.get(v - 1);
        for (int w : outgoingEdges) {
            if (colors.get(w - 1) == 0) { // Если вершина не посещена, то
                DFS(w, color); // запустим обход от найденной смежной вершины.
            }
        }
        colors.set(v - 1, color); // Теперь вершина обработана.
    }

    private static void writeSolution(BufferedWriter writer) throws IOException {
        Map<Integer, List<Integer>> colorVertexListMap = new HashMap<>();
        for (int i = 0; i < colors.size(); i++) {
            colorVertexListMap.computeIfAbsent(colors.get(i), e -> new ArrayList<>())
                    .add(i + 1);
        }
        writer.write(String.valueOf(colorVertexListMap.size()));
        writer.newLine();
        for(List<Integer> vertexes: colorVertexListMap.values()){
            String vertexesList = vertexes.stream()
                    .sorted()
                    .map(Object::toString)
                    .collect(Collectors.joining(" "));
            writer.write(vertexesList);
            writer.newLine();
        }
    }
}
