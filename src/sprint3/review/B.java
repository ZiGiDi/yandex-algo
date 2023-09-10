package sprint3.review;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class B {

    private static void sortCompetitors(List<Competitor> competitorsList, int leftBound, int rightBound) {
        int leftIndex = leftBound;
        int rightIndex = rightBound;
        if (leftBound >= rightBound) {
            return;
        }

        Competitor pivot = competitorsList.get(rightBound);
        while (leftIndex <= rightIndex) {
            while (pivot.compareTo(competitorsList.get(leftIndex)) > 0) {
                leftIndex++;
            }
            while (pivot.compareTo(competitorsList.get(rightIndex)) < 0) {
                rightIndex--;
            }

            if (leftIndex <= rightIndex) {
                Collections.swap(competitorsList, leftIndex, rightIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        sortCompetitors(competitorsList, leftBound, rightIndex);
        sortCompetitors(competitorsList, leftIndex, rightBound);
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int numberOfCompetitors = readInt(reader);
            List<Competitor> competitorsList = getCompetitors(numberOfCompetitors, reader);

            sortCompetitors(competitorsList, 0, numberOfCompetitors - 1);
            for (Competitor element : competitorsList) {
                writer.write(element.login);
                writer.newLine();
            }
        }
    }

    private static List<Competitor> getCompetitors(int numberOfCompetitors, BufferedReader reader) throws IOException {
        int i = numberOfCompetitors;
        List<String> lines = new ArrayList<>(numberOfCompetitors);
        while (i > 0) {
            lines.add(reader.readLine());
            i--;
        }
        return lines.stream()
                .map(line -> {
                    String[] s = line.split(" ");
                    return new Competitor(s[0], Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                }).collect(Collectors.toList());
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static class Competitor implements Comparable<Competitor> {
        private final String login;
        private final int solvedTasks;
        private final int fine;

        public Competitor(String login, int solvedTasks, int fine) {
            this.login = login;
            this.solvedTasks = solvedTasks;
            this.fine = fine;
        }

        @Override
        public int compareTo(Competitor o) {
            int compareTasks = Integer.compare(solvedTasks, o.solvedTasks);
            if (compareTasks != 0) {
                return -1 * compareTasks;
            }
            int compareFines = Integer.compare(fine, o.fine);
            if (compareFines != 0) {
                return compareFines;
            }
            return login.compareTo(o.login);
        }
    }
}
