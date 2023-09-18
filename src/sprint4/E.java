package sprint4;

import java.io.*;

public class E {

    /*
    На вход подается строка. Нужно определить длину наибольшей подстроки, которая не содержит повторяющиеся символы.
     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String string = reader.readLine();
            int result = findaMaxLengthOfSubstring(string);
            writer.write(String.valueOf(result));
        }
    }

    private static int findaMaxLengthOfSubstring(String string) {
        if (string.isEmpty()) {
            return 0;
        }
        int maxLengh = 0;
        String maxString = "";
        int length = 0;
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            String charString = String.valueOf(charArray[i]);
            if (!maxString.contains(charString)) {
                maxString = maxString.concat(charString);
                length++;
            } else {
                int index = maxString.indexOf(string.charAt(i));
                maxString = maxString.substring(index + 1) + string.charAt(i);
                length = maxString.length();
            }
            if (length > maxLengh) {
                maxLengh = length;
            }
        }
        return maxLengh;
    }
}
