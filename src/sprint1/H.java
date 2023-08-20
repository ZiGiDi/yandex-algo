package sprint1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class H {
    private static String sumOfBinaries(String a, String b) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] aCharArray = new StringBuilder(a).reverse().toString().toCharArray();
        char[] bCharArray = new StringBuilder(b).reverse().toString().toCharArray();
        int balance = 0;
        for (int i = 0; i < Math.max(aCharArray.length, bCharArray.length); i++) {
            if (getValue(aCharArray, i) == '0' && getValue(bCharArray, i) == '0') {
                stringBuilder.append(balance);
                balance = 0;
            } else if (getValue(aCharArray, i) == '1' && getValue(bCharArray, i) == '1') {
                stringBuilder.append(balance);
                balance = 1;
            } else if ((getValue(aCharArray, i) != '1' || getValue(bCharArray, i) != '1')) {
                if (balance == 0) {
                    stringBuilder.append(1);
                } else {
                    stringBuilder.append(0);
                    balance = 1;
                }
            }
        }
        if (balance == 1) {
            stringBuilder.append(balance);
        }
        return stringBuilder.reverse().toString();
    }

    private static char getValue(char[] array, int index) {
        if (index >= array.length) {
            return '0';
        }
        return array[index];
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String a = reader.readLine();
            String b = reader.readLine();
            System.out.println(sumOfBinaries(a, b));
        }
    }
}