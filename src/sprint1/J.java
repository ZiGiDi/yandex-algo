package sprint1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class J {

//    Основная теорема арифметики говорит: любое число раскладывается на произведение простых множителей единственным образом, с точностью до их перестановки. Например:
//
//    Число 8 можно представить как 2 × 2 × 2.
//    Число 50 –— как 2 × 5 × 5 (или 5 × 5 × 2, или 5 × 2 × 5). Три варианта отличаются лишь порядком следования множителей.
//    Разложение числа на простые множители называется факторизацией числа.
//
//    Напишите программу, которая производит факторизацию переданного числа.
//
//    Формат ввода
//    В единственной строке дано число n (2 ≤ n ≤ 109), которое нужно факторизовать.

    private static List<Integer> factorize(int n) {
        List<Integer> result = new ArrayList<>();
        int denominator = 2;

        while (denominator * denominator <= n) {
            while (n % denominator == 0) {
                result.add(denominator);
                n = n / denominator;
            }
            denominator++;
        }
        if (n > 1) {
            result.add(n);
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int n = readInt(reader);
            List<Integer> factorization = factorize(n);
            for (int elem : factorization) {
                writer.write(elem + " ");
            }
        }
    }


    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}