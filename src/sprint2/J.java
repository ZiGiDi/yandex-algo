package sprint2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class J {

//    Любимый вариант очереди Тимофея — очередь, написанная с использованием связного списка. Помогите ему с реализацией. Очередь должна поддерживать выполнение трёх команд:
//
//get() — вывести элемент, находящийся в голове очереди, и удалить его. Если очередь пуста, то вывести «error».
//put(x) — добавить число x в очередь
//size() — вывести текущий размер очереди
//Формат ввода
//В первой строке записано количество команд n — целое число, не превосходящее 1000. В каждой из следующих n строк записаны команды по одной строке.
//
//Формат вывода
//Выведите ответ на каждый запрос по одному в строке.

    private static void runCommands(List<String> commands) {
        MyLinkedQueue queue = new MyLinkedQueue();
        for (String command : commands) {
            if (command.contains(" ")) {
                String[] s = command.split(" ");
                if ("put".equals(s[0])) {
                    queue.put(s[1]);
                }
            }
            if ("get".equals(command)) {
                System.out.println(queue.get());
            }
            if ("size".equals(command)) {
                System.out.println(queue.size());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int commandsNumber = readInt(reader);
            List<String> commands = new ArrayList<>(commandsNumber);
            while (reader.ready()) {
                commands.add(reader.readLine());
            }
            runCommands(commands);
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static class MyLinkedQueue {
        LinkedList<String> list = new LinkedList<>();
        private int size;

        public MyLinkedQueue() {
            this.size = 0;
        }

        public String get() {
            if (size == 0) {
                return "error";
            }
            String value = list.getFirst();
            list.removeFirst();
            size--;
            return value;
        }

        public void put(String value) {
            list.addLast(value);
            size++;
        }

        public int size() {
            return size;
        }
    }
}
