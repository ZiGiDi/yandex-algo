package sprint5.review;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class A {
    /*
    В данной задаче необходимо реализовать сортировку кучей. При этом кучу необходимо реализовать самостоятельно,
    использовать имеющиеся в языке реализации нельзя. Сначала рекомендуется решить задачи про просеивание вниз и вверх.

    Тимофей решил организовать соревнование по спортивному программированию, чтобы найти талантливых стажёров.
    Задачи подобраны, участники зарегистрированы, тесты написаны. Осталось придумать, как в конце соревнования
    будет определяться победитель.

    Каждый участник имеет уникальный логин. Когда соревнование закончится, к нему будут привязаны два показателя:
    количество решённых задач Pi и размер штрафа Fi. Штраф начисляется за неудачные попытки и время, затраченное на задачу.

    Тимофей решил сортировать таблицу результатов следующим образом: при сравнении двух участников выше будет идти тот,
    у которого решено больше задач. При равенстве числа решённых задач первым идёт участник с меньшим штрафом.
    Если же и штрафы совпадают, то первым будет тот, у которого логин идёт раньше в алфавитном (лексикографическом) порядке.

    Тимофей заказал толстовки для победителей и накануне поехал за ними в магазин. В своё отсутствие он поручил
    вам реализовать алгоритм сортировки кучей (англ. Heapsort) для таблицы результатов.
     */

    /*
    id: https://contest.yandex.ru/contest/24810/run-report/95051956/

-- ПРИНЦИП РАБОТЫ --
    По факту это просто реализация кучи, которая полностью описана в самом курсе: https://practicum.yandex.ru/learn/algorithms/courses/7f101a83-9539-4599-b6e8-8645c3f31fad/sprints/134510/topics/e7dbf42a-fd5a-434b-990d-9cfe0e3a10c8/lessons/c29642e4-76ff-47df-82d2-87848ddc7f77/
Единственное, что пришлось добавить, это свой компаратор, соответствующий условию задачи
    В корне дерева у нас всегда самый большой по приоритету элемент. При добавлении нового (добавляем в конец) - просеиваем вверх,
при удалении - просеиваем вниз

-- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
    Т.к. при добавлении элментта мы запускаем логику просеивания вниз, то наша куча остается отсортированной
и в корне лежит самый большой элемент, аналогично при удалении элемента наша куча остается отсортированной и во главе
лежит самый больщой элемент, т.к. происходит просеивание вверх

-- ВРЕМЕННАЯ СЛОЖНОСТЬ --
Просеивание одного элемента занимает O(log(n)), При добавлении элемента нам нужно пройтись по n элементам, итого
имеем O(nlog(n)), Но т.к. у нас 2 просеивания вверх и вниз имеет O(2*n*log(n))

Итого: O(2*n*log(n)) -> O(n*log(n))

-- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
 Только для хранения элементов в кучи: O(n)
     */


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
                swapByIndexes(parentIndex - 1, index - 1);
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
                swapByIndexes(index, indexLargest);
                siftDown(indexLargest);
            }
        }

        private static void swapByIndexes(int a, int b) {
            Competitor temp = heap.get(a);
            heap.set(a, heap.get(b));
            heap.set(b, temp);
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
