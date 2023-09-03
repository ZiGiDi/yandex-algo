package sprint2.review;

import java.io.*;

public class A {



//    Гоша реализовал структуру данных Дек, максимальный размер которого определяется заданным числом. Методы push_back(x), push_front(x), pop_back(), pop_front() работали корректно. Но, если в деке было много элементов, программа работала очень долго. Дело в том, что не все операции выполнялись за O(1). Помогите Гоше! Напишите эффективную реализацию.
//
//Внимание: при реализации используйте кольцевой буфер.
//
//Формат ввода
//В первой строке записано количество команд n — целое число, не превосходящее 100000. Во второй строке записано число m — максимальный размер дека. Он не превосходит 50000. В следующих n строках записана одна из команд:
//
//push_back(value) – добавить элемент в конец дека. Если в деке уже находится максимальное число элементов, вывести «error».
//push_front(value) – добавить элемент в начало дека. Если в деке уже находится максимальное число элементов, вывести «error».
//pop_front() – вывести первый элемент дека и удалить его. Если дек был пуст, то вывести «error».
//pop_back() – вывести последний элемент дека и удалить его. Если дек был пуст, то вывести «error».
//Value — целое число, по модулю не превосходящее 1000.
//Формат вывода
//Выведите результат выполнения каждой команды на отдельной строке. Для успешных запросов push_back(x) и push_front(x) ничего выводить не надо.

    /*
    id: https://contest.yandex.ru/contest/22781/run-report/90043266/

-- ПРИНЦИП РАБОТЫ --
Как и требовалось в задании, все реализованно на кольцевом буфере.
Есть некий фиксированный массив в 2 указетлями на начало и конец.
Указатель на начало при добавлении элемента движется в направление уменьшения по индексу, а конца в повышение при добавлении элемента
Так же используется параметр количества текущих элементов в деке, чтобы не проходить весь массив в подсчете количества элементов или какой-либо другой оперрацией.
При добавлении в начало или конец проверяется есть ли свободное место, в случае наличие оного,
элемент добавляется и указатель сдвигается на 1 позицию. Если в начало, то вправо, если в конец то влево.
В случаю удаление элемента указатель сдвигается в противпоположном направлении.
т.к. у нас кольцевой буфер, то в граничных условиях указатели переходят с нулевого элемента массива в самый последний,
или с последнего на первый и продолжают дижение на увеличение или уменьшение индекса

-- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
Т.к. у нас фиксированный массив и указатели элементов могут сдвигаться только на 1 элемент
(в границах сдвиг переходит с одной границы на другую),
указатели начала и конца движутся в противополжных направлениях из начальной точки,
массив будет заполнен без пробелов, при добавлении или удалении элемента счетчик всегда реагирует и переполнение буффера невозможно.
В любой момент времени мы точно знаем где находится верхний и нижний элемент.

-- ВРЕМЕННАЯ СЛОЖНОСТЬ --
Добавление в очередь стоит O(1), потому что добавление в фиксированный массив в определенную позицию стоит O(1).
Извлечение из очереди стоитстоит O(1), потому что выгрузка элемента вфиксированный массив из определенной позицию стоит O(1).
Т.к каждая опреация стоит O(1) и по всем командам мы проходим только за 1 раз,
Итого имеем временную сложность O(n), где n - число команд

-- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
По условию мы задаем начальный размер буффера k.
Массив, содержащий k элементов, занимает O(k) памяти.
*/

    private static void runCommand(MyCircleDequeue queue,
                                   String command,
                                   BufferedWriter writer) throws IOException {
        try {
            if (command.contains(" ")) {
                String[] s = command.split(" ");
                if ("push_back".equals(s[0])) {
                    queue.pushBack(Integer.parseInt(s[1]));
                }
                if ("push_front".equals(s[0])) {
                    queue.pushFront(Integer.parseInt(s[1]));
                }
            }
            if ("pop_front".equals(command)) {
                writer.write(queue.popFront().toString());
                writer.newLine();
            }
            if ("pop_back".equals(command)) {
                writer.write(queue.popBack().toString());
                writer.newLine();
            }
        } catch (FullDequeException | EmptyDequeException exception) {
            writer.write("error");
            writer.newLine();
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int commandsNumber = readInt(reader);
            int maxQueueSize = readInt(reader);
            MyCircleDequeue queue = new MyCircleDequeue(maxQueueSize);
            while (reader.ready()) {
                runCommand(queue, reader.readLine(), writer);
            }
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static class MyCircleDequeue {

        private Integer[] queue;
        private int head;
        private int tail;
        private int maxSize;
        private int size;

        public MyCircleDequeue(int queueSize) {
            this.queue = new Integer[queueSize];
            head = 1;
            tail = 0;
            maxSize = queueSize;
            size = 0;
        }

        public void pushBack(Integer value) throws FullDequeException {
            if (isFull()) {
                throw new FullDequeException();
            }
            queue[tail] = value;
            tail = (tail - 1 + maxSize) % maxSize;
            size++;
        }

        public void pushFront(Integer value) throws FullDequeException {
            if (isFull()) {
                throw new FullDequeException();
            }
            queue[head] = value;
            head = (head + 1) % maxSize;
            size++;
        }

        public Integer popBack() throws EmptyDequeException {
            if (isEmpty()) {
                throw new EmptyDequeException();
            }
            tail = (tail + 1) % maxSize;
            Integer value = queue[tail];
            queue[tail] = null;
            size--;
            return value;
        }

        public Integer popFront() throws EmptyDequeException {
            if (isEmpty()) {
                throw new EmptyDequeException();
            }
            head = (head - 1 + maxSize) % maxSize;
            Integer value = queue[head];
            queue[head] = null;
            size--;
            return value;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == maxSize;
        }
    }

    public static class FullDequeException extends Exception {
    }

    public static class EmptyDequeException extends Exception {
    }
}
