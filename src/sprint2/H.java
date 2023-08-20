package sprint2;

import java.io.*;
import java.util.Stack;

public class H {

//    Вот какую задачу Тимофей предложил на собеседовании одному из кандидатов. Если вы с ней ещё не сталкивались, то наверняка столкнётесь –— она довольно популярная.
//
//Дана скобочная последовательность. Нужно определить, правильная ли она.
//
//Будем придерживаться такого определения:
//
//пустая строка —– правильная скобочная последовательность;
//правильная скобочная последовательность, взятая в скобки одного типа, –— правильная скобочная последовательность;
//правильная скобочная последовательность с приписанной слева или справа правильной скобочной последовательностью —– тоже правильная.
//На вход подаётся последовательность из скобок трёх видов: [], (), {}.
//Напишите функцию is_correct_bracket_seq, которая принимает на вход скобочную последовательность и возвращает True, если последовательность правильная, а иначе False.

    private static boolean isCorrect(String line) {
        Stack<String> queue = new Stack<>();
        if (line.isEmpty()) {
            return true;
        }
        for (String elem : line.split("")) {
            if ("{".equals(elem) || "[".equals(elem) || "(".equals(elem)) {
                queue.add(elem);
            } else {
                if (queue.isEmpty()) {
                    return false;
                }
                String peek = queue.peek();
                if ("{".equals(peek) && "}".equals(elem)
                        || "[".equals(peek) && "]".equals(elem)
                        || "(".equals(peek) && ")".equals(elem)) {
                    queue.pop();
                } else {
                    return false;
                }
            }
        }
        return queue.isEmpty();
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line = reader.readLine();

            if (isCorrect(line)) {
                System.out.println("True");
            } else {
                System.out.println("False");
            }
        }
    }
}
