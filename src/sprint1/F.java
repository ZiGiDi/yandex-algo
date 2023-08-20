package sprint1;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class F {

    private static boolean isPalindrome(String text) {
        text = text.replaceAll("\\W+","").replace("_", "").toLowerCase();
        String reversedString = new StringBuilder(text).reverse().toString();
        return reversedString.equals(text);
    }
    public static void main(String[] args) throws IOException{
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String text = reader.readLine();
            if (isPalindrome(text)) {
                System.out.println("True");
            } else {
                System.out.println("False");
            }
        }
    }
}