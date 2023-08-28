package sprint2.review;

import java.io.*;

public class A {

    private static void runCommand(MyCircleDequeue queue,
                                   String command,
                                   BufferedWriter writer) throws IOException {
        if (command.contains(" ")) {
            try {
                String[] s = command.split(" ");
                if ("push_back".equals(s[0])) {
                    queue.pushBack(s[1]);
                }
                if ("push_front".equals(s[0])) {
                    queue.pushFront(s[1]);
                }
            } catch (FullDequeException exception) {
                writer.write("error");
                writer.newLine();
            }
        }
        if ("pop_front".equals(command)) {
            writer.write(queue.popFront());
            writer.newLine();
        }
        if ("pop_back".equals(command)) {
            writer.write(queue.popBack());
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

        private String[] queue;
        private int head;
        private int tail;
        private int maxSize;
        private int size;

        public MyCircleDequeue(int queueSize) {
            this.queue = new String[queueSize];
            head = 1;
            tail = 0;
            maxSize = queueSize;
            size = 0;
        }

        public void pushBack(String value) throws FullDequeException {
            if (isFull()) {
                throw new FullDequeException();
            }
            queue[tail] = value;
            tail = (tail - 1 + maxSize) % maxSize;
            size++;
        }

        public void pushFront(String value) throws FullDequeException {
            if (isFull()) {
                throw new FullDequeException();
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

    public static class FullDequeException extends Exception {
    }
}
