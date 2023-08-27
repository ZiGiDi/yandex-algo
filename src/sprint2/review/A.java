package sprint2.review;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class A {

    private static void runCommands(int maxQueueSize, List<String> commands) {
        MyCircleQueue queue = new MyCircleQueue(maxQueueSize);
        for (String command : commands) {
            if (command.contains(" ")) {
                String[] s = command.split(" ");
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
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int commandsNumber = readInt(reader);
            int maxQueueSize = readInt(reader);
            List<String> commands = new ArrayList<>(commandsNumber);
            while (reader.ready()) {
                commands.add(reader.readLine());
            }
            runCommands(maxQueueSize, commands);
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
            if (size != maxSize) {
                queue[tail] = value;
                tail = (tail - 1 + maxSize) % maxSize;
                size++;
            } else System.out.println("error");
        }

        public void pushFront(String value) {
            if (size != maxSize) {
                queue[head] = value;
                head = (head + 1) % maxSize;
                size++;
            } else System.out.println("error");
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
    }
}
