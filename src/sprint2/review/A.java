package sprint2.review;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class A {

    private static void runCommand(MyCircleQueue queue, String command) {
        String[] s = command.split(" ");
        if (command.contains(" ")) {
            if ("push_back".equals(s[0])) {
                queue.pushBack(s[1]);
            }
            if ("push_front".equals(s[0])) {
                queue.pushFront(s[1]);
            }
        }
        if ("pop_front".equals(command)) {
            System.out.println(queue.popFront());
        }
        if ("pop_back".equals(command)) {
            System.out.println(queue.popBack());
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int commandsNumber = readInt(reader);
            int maxQueueSize = readInt(reader);
            MyCircleQueue queue = new MyCircleQueue(maxQueueSize);
            while (reader.ready()) {
                runCommand(queue, reader.readLine());
            }
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static class MyCircleQueue {

        private String[] queue;
        private int head;
        private int tail;
        private int maxSize;
        private int size;

        public MyCircleQueue(int queueSize) {
            this.queue = new String[queueSize];
            head = 1;
            tail = 0;
            maxSize = queueSize;
            size = 0;
        }

        public void pushBack(String value) {
            if (isFull()) {
                System.out.println("error");
                return;
            }
            queue[tail] = value;
            tail = (tail - 1 + maxSize) % maxSize;
            size++;
        }

        public void pushFront(String value) {
            if (isFull()) {
                System.out.println("error");
                return;
            }
            queue[head] = value;
            head = (head + 1) % maxSize;
            size++;
        }

        public String popBack() {
            if (isEmpty()) {
                return "error";
            }
            tail = (tail + 1) % maxSize;
            String value = queue[tail];
            queue[tail] = null;
            size--;
            return value;
        }

        public String popFront() {
            if (isEmpty()) {
                return "error";
            }
            head = (head - 1 + maxSize) % maxSize;
            String value = queue[head];
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
}
