package sprint3.review;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class B {

//    Тимофей решил организовать соревнование по спортивному программированию, чтобы найти талантливых стажёров.
//    Задачи подобраны, участники зарегистрированы, тесты написаны. Осталось придумать, как в конце соревнования будет определяться победитель.
//
// Каждый участник имеет уникальный логин. Когда соревнование закончится, к нему будут привязаны два показателя: количество
// решённых задач Pi и размер штрафа Fi. Штраф начисляется за неудачные попытки и время, затраченное на задачу.
//
//Тимофей решил сортировать таблицу результатов следующим образом: при сравнении двух участников выше будет идти тот,
// у которого решено больше задач. При равенстве числа решённых задач первым идёт участник с меньшим штрафом.
// Если же и штрафы совпадают, то первым будет тот, у которого логин идёт раньше в алфавитном (лексикографическом) порядке.
//
//Тимофей заказал толстовки для победителей и накануне поехал за ними в магазин. В своё отсутствие он поручил вам реализовать
// алгоритм быстрой сортировки (англ. quick sort) для таблицы результатов. Так как Тимофей любит спортивное программирование
// и не любит зря расходовать оперативную память, то ваша реализация сортировки не может потреблять O(n) дополнительной памяти
// для промежуточных данных (такая модификация быстрой сортировки называется "in-place").


            /*
    id: https://contest.yandex.ru/contest/23815/run-report/90399919/

-- ПРИНЦИП РАБОТЫ --
Принцип работы прекрасно описан в условии самой задачи, и я поленюсь писать это от себя и просто скопирую:
Как и в случае обычной быстрой сортировки, которая использует дополнительную память,
необходимо выбрать опорный элемент (англ. pivot), а затем переупорядочить массив.
Сделаем так, чтобы сначала шли элементы, не превосходящие опорного, а затем —– большие опорного.
Затем сортировка вызывается рекурсивно для двух полученных частей.
Именно на этапе разделения элементов на группы в обычном алгоритме используется дополнительная память.
Единственное,

-- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
При каждой итерации мы выбираем свой опорный элемент и начинаем перемещать элемента больше опорного в правую часть,
А элементы меньше опорного в левую часть. Потом делим массив на 2 части, в каждой части выбираем свой опорный элемент и снова начнинаем
Процесс сортировки будет продолжаться пока массив не будет отсортирован

-- ВРЕМЕННАЯ СЛОЖНОСТЬ --
Т.к в идеальном случае разделения массива пополам на каждой итерации и в каждой итерации нам нужно пройти по всем элементам,
в Итоге имеем: O(n)* O(logn) = O(nlogn)
В худшем случае на каждой этерации разделение массива будет происходить на массив с 1 элементом и массив размерности n-1 элемент,
Тогда общее количество итераций будет O(n), и нам все такж епридется проходится витерациях по всем элементам.
Итого имеем:  O(n)* O(n)=  O(n^2)

П.С. все операции по массиву в плане нахождение элемента и его вставка происходят за O(n)

-- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
Основную память у нас занимает сам массив элементов размерностью n.
Из дополнительных параметров в памяти хранятся только индексы и значение pivot O(1)
Итого имеем, общее потребление память O(n) где n - элементов в массиве.
Доп памяти используется O(1) на хранение индексов, + с каждой итерацией мы увеличиваем стэк вызова
*/

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

            sortCompetitors(competitorsList);
            for (Competitor element : competitorsList) {
                writer.write(element.login);
                writer.newLine();
            }
        }
    }

    private static void sortCompetitors(List<Competitor> competitorsList) {
        sortCompetitors(competitorsList, 0, competitorsList.size() - 1);
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
