package sprint5.review;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class A {

    private static final String WORD_SEPARATOR = " ";

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int numberOfCompetitors = readInt(reader);
            MyHeap heap = new MyHeap(numberOfCompetitors);
            for (int i = 0; i < numberOfCompetitors; i++) {
                String[] split = reader.readLine().split(WORD_SEPARATOR);
                heap.heapAdd(new Competitor(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2])));
            }
            for (int i = 0; i < numberOfCompetitors; i++) {
                String login = heap.popMax().getLogin();
                writer.write(login);
                writer.newLine();
            }
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static class MyHeap {

        private static List<Competitor> heap;

        public MyHeap(int heapSize) {
            heap = new ArrayList<>(heapSize);
        }

        public static void heapAdd(Competitor competitor) {
            int index = heap.size() + 1;
            heap.add(index - 1, competitor);
            siftUp(index);
        }

        public static Competitor popMax() {
            Competitor result = heap.get(0);
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            siftDown(0);
            return result;
        }

        private static void siftUp(int index) {
            if (index == 1) return;

            int parentIndex = index / 2;
            if (heap.get(parentIndex - 1).compareTo(heap.get(index - 1)) < 0) {
                Competitor temp = heap.get(parentIndex - 1);
                heap.set(parentIndex - 1, heap.get(index - 1));
                heap.set(index - 1, temp);
                siftUp(parentIndex);
            }
        }

        public static void siftDown(int index) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;

            if (left >= heap.size()) {
                return;
            }

            int indexLargest = left;
            if (right < heap.size() && heap.get(left).compareTo(heap.get(right)) < 0) {
                indexLargest = right;
            }

            if (heap.get(index).compareTo(heap.get(indexLargest)) < 0) {
                Competitor temp = heap.get(index);
                heap.set(index, heap.get(indexLargest));
                heap.set(indexLargest, temp);
                siftDown(indexLargest);
            }
        }
    }

    private static class Competitor implements Comparable<Competitor> {

        private static final Comparator<Competitor> COMPARATOR = Comparator
                .comparing(Competitor::getScore)
                .thenComparing(Competitor::getPenalty, Comparator.reverseOrder())
                .thenComparing(Competitor::getLogin, Comparator.reverseOrder());

        private String login;
        private int score;
        private int penalty;

        public Competitor(String login, int score, int penalty) {
            this.login = login;
            this.score = score;
            this.penalty = penalty;
        }

        public String getLogin() {
            return login;
        }

        public int getScore() {
            return score;
        }

        public int getPenalty() {
            return penalty;
        }

        @Override
        public int compareTo(Competitor o) {
            return COMPARATOR.compare(this, o);
        }
    }
}
