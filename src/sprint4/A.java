package sprint4;

import java.io.*;

public class A {

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int a = readInt(reader);
            int m = readInt(reader);
            String string = reader.readLine();
            int hash = getHash(a, m, string);
            writer.write(String.valueOf(hash));
        }
    }

    private static int getHash(int a, int m, String string) {
        if (string.isEmpty()) {
            return 0;
        }
        char[] charArray = string.toCharArray();
        int aPowOfN = a;
        int length = charArray.length;
        int sum = charArray[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            int charValue = charArray[i];
            sum = (sum + charValue * aPowOfN) % m;
            aPowOfN = (aPowOfN * a) % m;
        }
        return sum;
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}
