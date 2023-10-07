package sprint4.review;

import java.io.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class B {
    private static final String LINE_SEPARATOR = " ";
    private static MyHashTable hashTable;

    /*
    id: https://contest.yandex.ru/contest/24414/run-report/91133004/

    -- ПРИНЦИП РАБОТЫ --
В основе хэш таблицы лежит массив бакетов, где позиция элемента в какой индекс бакета он попадет, определяется по соотношению хэш кода
ключа на количество бакетов. В случае коллизии бакеты представляют собой связанный список.

При добавлении элемента:
1) находим индекс бакета
2) проверяем наличие в бакете элементов, если их нет, то добавляем новый
3) Если есть элементы проходим по списку до тех пор, пока не совпадет ключ. Переписываем значение.
4) Если элемент не найден, то создаем новый и добавляем в начало списка

При поиске:
1) находим индекс бакета
2) проверяем наличие в бакете элементов, если их нет, то возвращаем None
3) Если есть элементы проходим по списку до тех пор, пока не совпадет ключ. Возвращаем значение.
4) Если элемент не найден, то возвращаем None

При удалении:
1) находим индекс бакета
2) проверяем наличие в бакете элементов, если их нет, то возвращаем None
3) Если есть элементы проходим по списку до тех пор, пока не совпадет ключ. Возвращаем значение и удаляем его:
    3.1) Если в бакете только один элемент, то бакет оставляем пустым
    3.2) Если в бакете несколько элементов:
        3.2.1) Если искомы элемент первый, то связанный список будет начинаться с последующего элемента после искомого
        3.2.2) Если нет, то переделываем указатель на следующий элемент предыдущего от искомого, на следующий за искомым
4) Если элемент не найден, то возвращаем None

-- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
Т.к Размер массива бакетов у нас неизменяем, Для одного и того же ключа хэш у нас возвращается один и тот же,
то для одного и того же ключа индекс бакета будет всегда один и тот же. Для обхода коллизии используется связанный список,
который сравнивает элементы не по хеше, а по ключу.

-- ВРЕМЕННАЯ СЛОЖНОСТЬ --
O(1) – в лучшем случае, т.к. вставка и нахождение по индексу из массива, и имеем один элемент в цепочке
В худшем случае O(n), если все элементы попадут в один бакет и нам придется по всем ним итерироваться.
Т.к. у нас метод цепочек, то в среднем сложность операций в хеш-таблице равняется O(1+α), где
α = N/M N — количество элементов, добавленных в таблицу, M — количество корзин в таблице
https://ru.wikipedia.org/wiki/%D0%A5%D0%B5%D1%88-%D1%82%D0%B0%D0%B1%D0%BB%D0%B8%D1%86%D0%B0

-- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
O(N') - где N' - количество хранимых элементов. Т.к. расход памяти линейно зависит от их количество,
Несмотря на то, что у каждого элемента есть доп информация O(1).
Т.к. у нас массив нерасширяемый, то в начале программы имеем выделенную память под начальную емкость хэш таблицы
O(M), где M-массив бакетов.
Итого имеем выделенную память под сами элементы и начальную емкость хэш таблицы
     */

    private static void runCommand(String command, BufferedWriter writer) throws IOException {
        String[] split = command.split(LINE_SEPARATOR);

        if ("put".equals(split[0])) {
            hashTable.put(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
        if ("get".equals(split[0])) {
            Integer result = hashTable.get(Integer.parseInt(split[1]));
            writer.write(String.valueOf(result));
            writer.newLine();
        }
        if ("delete".equals(split[0])) {
            Integer result = hashTable.delete(Integer.parseInt(split[1]));
            writer.write(String.valueOf(result));
            writer.newLine();
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int numberOfCommands = readInt(reader);
            int size = numberOfCommands > 10 ? numberOfCommands / 10 : 1;
            hashTable = new MyHashTable(size);
            for (int i = 0; i < numberOfCommands; i++) {
                try {
                    runCommand(reader.readLine(), writer);
                } catch (NoSuchElementException e) {
                    writer.write("None");
                    writer.newLine();
                }
            }

        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}

class MyHashTable {
    //При любой последовательности команд, количество ключей в хеш-таблице не может превышать 10^5
    private static final int DEFAULT_HASH_TABLE_SIZE = 100_000;

    int size = 0;
    Node[] buckets;

    public MyHashTable() {
        buckets = new Node[DEFAULT_HASH_TABLE_SIZE];
        Arrays.fill(buckets, null);
    }

    public MyHashTable(int size) {
        buckets = new Node[size];
        Arrays.fill(buckets, null);
    }

    public void put(Integer key, Integer value) {
        int bucketNumber = getBucketNumber(key);
        Node head = buckets[bucketNumber];
        if (head == null) {
            buckets[bucketNumber] = new Node(key, value);
            size++;
            return;
        }

        Node node = findNode(key, head);
        if (node != null) {
            node.value = value;
            return;
        }

        Node newHead = new Node(key, value);
        newHead.nextNode = head;
        buckets[bucketNumber] = newHead;
        size++;
    }

    public Integer get(Integer key) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int bucketNumber = getBucketNumber(key);
        Node head = buckets[bucketNumber];
        Node node = findNode(key, head);
        if (node != null) {
            return node.value;
        }
        throw new NoSuchElementException();
    }

    public Integer delete(Integer key) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int bucketNumber = getBucketNumber(key);
        Node head = buckets[bucketNumber];
        if (head == null) {
            throw new NoSuchElementException();
        }

        if (head.key.equals(key)) {
            if (head.nextNode == null) {
                buckets[bucketNumber] = null;
                size--;
                return head.value;
            }
            buckets[bucketNumber] = head.nextNode;
            size--;
            return head.value;
        }

        Node node = head;
        while (node.nextNode != null) {
            Node nextNode = node.nextNode;
            if (nextNode.key.equals(key)) {
                node.nextNode = nextNode.nextNode;
                size--;
                return nextNode.value;
            }
            node = nextNode;
        }

        throw new NoSuchElementException();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int getBucketNumber(Integer key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    private Node findNode(Integer key, Node head) {
        if (head == null) {
            throw new NoSuchElementException();
        }

        if (head.key.equals(key)) {
            return head;
        }

        Node node = head;
        while (node.nextNode != null) {
            Node nextNode = node.nextNode;
            if (nextNode.key.equals(key)) {
                return nextNode;
            }
            node = nextNode;
        }
        return null;
    }

    static class Node {
        final Integer key;
        Integer value;
        Node nextNode;

        public Node(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }
}