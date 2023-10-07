package sprint5;

import java.io.*;
import java.math.BigInteger;

/*
Ребятам стало интересно, сколько может быть различных деревьев поиска, содержащих в своих узлах все уникальные
числа от 1 до n. Помогите им найти ответ на этот вопрос.
 */
public class I {

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int n = readInt(reader);
            BigInteger result = findCatalanNumber(n);
            writer.write(result.toString());
        }
    }

    private static BigInteger findCatalanNumber(int n) {

        // for n = 0 & 1, the catalan number is 1
        if (n <= 1) {
            return BigInteger.ONE;
        }

        // calculating 2n!
        BigInteger fact = findFactorial(2 * n);

        // calculating 2nCn
        BigInteger factorial = findFactorial(n);
        fact = fact.divide(factorial);
        fact = fact.divide(factorial);

        // caculating ((1 / (n + 1))* 2nCn)
        fact = fact.divide(BigInteger.valueOf(n + 1));

        // returning the calculated Catalan number
        return fact;
    }

    public static BigInteger findFactorial(int n) {
        BigInteger res = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            res = res.multiply(BigInteger.valueOf(i));
        }
        return res;
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}
