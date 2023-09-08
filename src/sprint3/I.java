package sprint3;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class I {

//    На IT-конференции присутствовали студенты из разных вузов со всей страны.
//    Для каждого студента известен ID университета, в котором он учится.
//
//  Тимофей предложил Рите выяснить, из каких k вузов на конференцию пришло больше всего учащихся.

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int numberOfStudents = readInt(reader);
            List<Integer> ids = readList(reader);
            int numberOfResults = readInt(reader);
            List<Integer> result = findBiggestUnis(ids);
            for (int i = 0; i < numberOfResults && i < result.size(); i++) {
                writer.write(result.get(i) + " ");
            }
        }
    }

    private static List<Integer> findBiggestUnis(List<Integer> ids) {
        int[] numberOfStudentsArray = new int[10000];
        Arrays.fill(numberOfStudentsArray, 0);
        for (Integer id : ids) {
            numberOfStudentsArray[id] = numberOfStudentsArray[id] + 1;
        }

        ArrayList<Uni> unis = new ArrayList<>();
        for (int i = 0; i < numberOfStudentsArray.length; i++) {
            if (numberOfStudentsArray[i] != 0) {
                unis.add(new Uni(numberOfStudentsArray[i], i));
            }
        }
        unis.sort((o1, o2) -> {
            if (o1.count < o2.count) {
                return 1;
            } else if (o1.count > o2.count) {
                return -1;
            } else if (o1.id < o2.id) {
                return -1;
            } else if (o1.id > o2.id) {
                return 1;
            }
            return 0;
        });

        return unis.stream().map(o -> o.id).collect(Collectors.toList());
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
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


    private static class Uni {
        private int count;
        private int id;

        public Uni(int count, int id) {
            this.count = count;
            this.id = id;
        }
    }
}
