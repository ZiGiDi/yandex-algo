package sprint2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class G {

//    Нужно реализовать класс StackMax, который поддерживает операцию определения максимума среди всех элементов в стеке. Класс должен поддерживать операции push(x), где x – целое число, pop() и get_max().
//
//    Формат ввода
//    В первой строке записано одно число n — количество команд, которое не превосходит 10000. В следующих n строках идут команды. Команды могут быть следующих видов:
//
//    push(x) — добавить число x в стек;
//    pop() — удалить число с вершины стека;
//    get_max() — напечатать максимальное число в стеке;
//    Если стек пуст, при вызове команды get_max() нужно напечатать «None», для команды pop() — «error».
//
//    Формат вывода
//    Для каждой команды get_max() напечатайте результат её выполнения. Если стек пустой, для команды get_max() напечатайте «None». Если происходит удаление из пустого стека — напечатайте «error».

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int k = readInt(reader);
            List<String> commands = new ArrayList<>(k);
            while (reader.ready()) {
                commands.add(reader.readLine());
            }

            runComands(commands);
        }
    }

    private static void runComands(List<String> commands) {
        MaxEffectiveStack stack = new MaxEffectiveStack();
        for (String command : commands) {
            if (command.contains(" ")) {
                String[] s = command.split(" ");
                if ("push".equals(s[0])) {
                    stack.push(Integer.parseInt(s[1]));
                }
            }
            if ("pop".equals(command)) {
                stack.pop();
            }
            if ("get_max".equals(command)) {
                System.out.println(stack.getMax());
            }
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}

class MaxEffectiveStack {
    private List<Integer> items;
    private List<Integer> max;

    public MaxEffectiveStack() {
        items = new ArrayList<>();
        max = new ArrayList<>();
    }

    public void push(int item) {
        if (items.isEmpty()) {
            max.add(item);
        } else {
            max.add(Math.max(item, max.get(max.size() - 1)));
        }
        items.add(item);
    }

    public void pop() {
        if (items.isEmpty()) {
            System.out.println("error");
            return;
        }
        items.remove(items.size() - 1);
        max.remove(max.size() - 1);
    }

    public String getMax() {
        if (max.isEmpty()) {
            return "None";
        }
        return max.get(max.size() - 1).toString();
    }
}
