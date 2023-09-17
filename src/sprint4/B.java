package sprint4;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class B {

    /*
    Гоша написал программу, которая сравнивает строки исключительно по их хешам. Если хеш равен, то и строки равны.
    Тимофей увидел это безобразие и поручил вам сломать программу Гоши, чтобы остальным неповадно было.

    В этой задаче вам надо будет лишь найти две различные строки, которые для заданной хеш-функции будут
    давать одинаковое значение.
     */

    public static void main(String[] args) {
        int a = 1000;
        int m = 123_987_123;
        boolean isFounded = false;
        Map<Integer, String> hashWithStringMap = new HashMap<>();
        while (!isFounded) {
            String generatedString = generateString(20);
            int hash = getHash(a, m, generatedString);
            if (hashWithStringMap.containsKey(hash)) {
                System.out.println("Strings founded : ");
                System.out.println(generatedString);
                System.out.println(hashWithStringMap.get(hash));
                isFounded = true;
            } else {
                hashWithStringMap.put(hash, generatedString);
            }
        }

    }

    private static String generateString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static int getHash(int a, int m, String string) {
        if (string.isEmpty()) {
            return 0;
        }
        char[] charArray = string.toCharArray();
        long aPowOfN = a;
        int length = charArray.length;
        int sum = charArray[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            int charValue = charArray[i];
            sum = (int) ((sum + charValue * aPowOfN) % m);
            aPowOfN = (aPowOfN * a) % m;
        }
        return sum;
    }
}
