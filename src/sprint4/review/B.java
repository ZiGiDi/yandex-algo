package sprint4.review;

import java.io.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class B {
    private static final String LINE_SEPARATOR = " ";
    private static MyHashTable hashTable;

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