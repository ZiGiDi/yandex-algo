package sprint6;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class M {

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

            boolean result = checkGraph(numberOfVertexes);
            writeSolution(result, writer);
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

    private static boolean checkGraph(int numberOfVertexes) {
        colors = initializeColor(numberOfVertexes);

        for (int i = 0; i < colors.size(); i++) {
            // Перебираем варианты стартовых вершин, пока они существуют.
            if (colors.get(i) == -1) {
                if (!checkBipartiteness(i + 1, 0)) { // Запускаем обход, стартуя с i-й вершины.
                    return false;
                }
            }
        }
        return true;
    }

    private static List<Integer> initializeColor(int numberOfVertexes) {
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < numberOfVertexes; i++) {
            colors.add(-1);
        }
        return colors;
    }

    private static boolean checkBipartiteness(int v, int color) {
        color = color % 2;
        int parentColor = color;
        colors.set(v - 1, color); // Вершина посещена, но ещё не обработана.
        color++;
        List<Integer> outgoingEdges = adjacencyList.get(v - 1);
        for (int w : outgoingEdges) {
            if (colors.get(w - 1) == parentColor) {
                return false;
            }
            if (colors.get(w - 1) == -1) { // Если вершина не посещена, то
                checkBipartiteness(w, color); // запустим обход от найденной смежной вершины.
            }
        }
        colors.set(v - 1, parentColor); // Теперь вершина обработана.
        return true;
    }

    private static void writeSolution(boolean isCompleteGraph,
                                      BufferedWriter writer) throws IOException {
        String result = isCompleteGraph ? "YES" : "NO";
        writer.write(result);
    }
}
