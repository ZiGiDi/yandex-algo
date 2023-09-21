package sprint4.review;

import java.io.*;
import java.util.Arrays;

public class B {
    private static final String WORD_SEPARATOR = " ";
    private static final MyHashTable hashTable = new MyHashTable();

    private static void runCommand(String command, BufferedWriter writer) throws IOException {
        String[] split = command.split(WORD_SEPARATOR);

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
            for (int i = 0; i < numberOfCommands; i++) {
                try {
                    runCommand(reader.readLine(), writer);
                } catch (RuntimeException e) {
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

    private static final int HASH_TABLE_SIZE = 100_000;

    int size = 0;
    Node[] buckets;

    public MyHashTable() {
        buckets = new Node[HASH_TABLE_SIZE];
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
        if (head.key.equals(key)) {
            head.value = value;
        }

        Node bucket = head;
        while (bucket.nextNode != null) {
            Node nextNode = bucket.nextNode;
            if (nextNode.key.equals(key)) {
                nextNode.value = value;
                return;
            }
            bucket = nextNode;
        }

        Node newHead = new Node(key, value);
        newHead.nextNode = head;
        buckets[bucketNumber] = newHead;
        size++;
    }

    private static int getBucketNumber(Integer key) {
        return Math.abs(key.hashCode() % HASH_TABLE_SIZE);
    }

    public Integer get(Integer key) {
        if (isEmpty()) {
            throw new RuntimeException();
        }

        int bucketNumber = getBucketNumber(key);
        Node bucket = buckets[bucketNumber];
        if (bucket == null) {
            throw new RuntimeException();
        }

        if (bucket.key.equals(key)) {
            return bucket.value;
        }

        while (bucket.nextNode != null) {
            Node nextNode = bucket.nextNode;
            if (nextNode.key.equals(key)) {
                return nextNode.value;
            }
            bucket = nextNode;
        }
        throw new RuntimeException();
    }

    public Integer delete(Integer key) {
        if (isEmpty()) {
            throw new RuntimeException();
        }

        int bucketNumber = getBucketNumber(key);
        Node bucket = buckets[bucketNumber];
        if (bucket == null) {
            throw new RuntimeException();
        }

        if (bucket.key.equals(key)) {
            if (bucket.nextNode == null) {
                buckets[bucketNumber] = null;
                size--;
                return bucket.value;
            }
            buckets[bucketNumber] = bucket.nextNode;
            size--;
            return bucket.value;
        }

        while (bucket.nextNode != null) {
            Node nextNode = bucket.nextNode;
            if (nextNode.key.equals(key)) {
                bucket.nextNode = nextNode.nextNode;
                size--;
                return nextNode.value;
            }
            bucket = nextNode;
        }

        throw new RuntimeException();

    }

    public boolean isEmpty() {
        return size == 0;
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
