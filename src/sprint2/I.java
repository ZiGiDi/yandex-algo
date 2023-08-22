package sprint2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class I {

//    Астрологи объявили день очередей ограниченного размера. Тимофею нужно написать класс MyQueueSized, который принимает параметр max_size, означающий максимально допустимое количество элементов в очереди.
//
//Помогите ему —– реализуйте программу, которая будет эмулировать работу такой очереди. Функции, которые надо поддержать, описаны в формате ввода.
//
//Формат ввода
//В первой строке записано одно число — количество команд, оно не превосходит 5000.
//Во второй строке задан максимально допустимый размер очереди, он не превосходит 5000.
//Далее идут команды по одной на строке. Команды могут быть следующих видов:
//
//push(x) — добавить число x в очередь;
//pop() — удалить число из очереди и вывести на печать;
//peek() — напечатать первое число в очереди;
//size() — вернуть размер очереди;
//При превышении допустимого размера очереди нужно вывести «error». При вызове операций pop() или peek() для пустой очереди нужно вывести «None».
//Формат вывода
//Напечатайте результаты выполнения нужных команд, по одному на строке.

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

    private static void runCommands(int maxQueueSize, List<String> commands) {
        MyQueueSized queue = new MyQueueSized(maxQueueSize);
        for (String command : commands) {
            if (command.contains(" ")) {
                String[] s = command.split(" ");
                if ("push".equals(s[0])) {
                    queue.push(s[1]);
                }
            }
            if ("pop".equals(command)) {
                System.out.println(queue.pop());
            }
            if ("peek".equals(command)) {
                System.out.println(queue.peek());
            }
            if ("size".equals(command)) {
                System.out.println(queue.size());
            }
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static class MyQueueSized {
        private ArrayList<String> queue;
        private int head;
        private int tail;
        private int maxSize;
        private int size;

        public MyQueueSized(int queueSize) {
            this.queue = new ArrayList<>(queueSize);
            head = 0;
            tail = 0;
            maxSize = queueSize;
            size = 0;
            for (int i = 0; i < queueSize; i++) {
                queue.add(null);
            }
        }

        public void push(String value) {
            if (size != maxSize) {
                queue.set(tail, value);
                tail = (tail + 1) % maxSize;
                size++;
            } else System.out.println("error");
        }

        public String pop() {
            if (isEmpty()) {
                return "None";
            }
            String value = queue.get(head);
            queue.set(head, null);
            head = (head + 1) % maxSize;
            size--;
            return value;
        }

        public String peek() {
            if (isEmpty()) {
                return "None";
            }
            return queue.get(head);
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }
    }
}
