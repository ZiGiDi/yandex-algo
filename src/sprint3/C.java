package sprint3;

import java.io.*;

public class C {

    //    Гоша любит играть в игру «Подпоследовательность»: даны 2 строки, и нужно понять,
//    является ли первая из них подпоследовательностью второй. Когда строки достаточно длинные,
//    очень трудно получить ответ на этот вопрос, просто посмотрев на них. Помогите Гоше написать функцию, которая решает эту задачу.
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String a = reader.readLine();
            String b = reader.readLine();
            boolean result = isSubsequence(a, b);

            if (result) {
                System.out.println("True");
            } else {
                System.out.println("False");
            }
        }
    }

    private static boolean isSubsequence(String a, String b) {
        int startIndex = -1;
        char[] charArray = a.toCharArray();
        for (char c : charArray) {
            startIndex = b.indexOf(c, startIndex + 1);
            if (startIndex == -1) {
                return false;
            }
        }
        return true;
    }
}
