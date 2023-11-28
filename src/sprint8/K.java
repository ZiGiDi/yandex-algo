package sprint8;

import java.io.*;

public class K {

    /*
    Алла придумала новый способ сравнивать две строки: чтобы сравнить строки a и b, в них надо оставить только те буквы,
    которые в английском алфавите стоят на четных позициях. Затем полученные строки сравниваются по обычным правилам.
    Помогите Алле реализовать новое сравнение строк.
     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String string1 = reader.readLine();
            String string2 = reader.readLine();

            int result = compareStrings(string1, string2);

            writer.write(String.valueOf(result));
        }
    }

    private static int compareStrings(String string1, String string2) {
        int i = 0;
        int j = 0;
        char[] charArray1 = string1.toCharArray();
        char[] charArray2 = string2.toCharArray();

        while (i < string1.length() || j < string2.length()) {
            if (i < string1.length() && charArray1[i] % 2 != 0) {
                i++;
            } else if (j < string2.length() && charArray2[j] % 2 != 0) {
                j++;
            } else {
                char char1 = i < string1.length() ? charArray1[i] : Character.MIN_VALUE;
                char char2 = j < string2.length() ? charArray2[j] : Character.MIN_VALUE;
                int compare = Character.compare(char1, char2);
                if (compare != 0) {
                    return compare > 0 ? 1 : -1;
                }
                i++;
                j++;
            }
        }
        return 0;
    }
}
