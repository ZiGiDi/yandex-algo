package sprint8.review;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class A {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int linesNumber = readInt(reader);
            String prefix = null;
            for (int i = 0; i < linesNumber; i++) {
                String line = unpackLine(reader.readLine());
                prefix = findCommonPrefix(prefix, line);
            }

            if (prefix == null) {
                prefix = "";
            }
            writer.write(prefix);

        }
    }

    private static String findCommonPrefix(String prefix, String line) {
        if (prefix == null) {
            prefix = line;
        }

        if (line.isEmpty()) {
            return "";
        }

        while (line.indexOf(prefix) != 0) {
            prefix = prefix.substring(0, prefix.length() - 1);
            if (prefix.isEmpty()) {
                return "";
            }
        }

        return prefix;
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static String unpackLine(String line) {

        while (line.contains("[")) {

            int indexOfBegin = line.lastIndexOf("[");
            int indexOfEnd = line.indexOf("]", indexOfBegin);
            String packedWord = line.substring(indexOfBegin + 1, indexOfEnd);

            char[] charArray = line.substring(0, indexOfBegin)
                    .toCharArray();
            int i = indexOfBegin - 1;
            while (i > 0 && Character.isDigit(charArray[i - 1])) {
                i--;
            }
            int increment = Integer.parseInt(line.substring(i, indexOfBegin));

            String unpackedPart = packedWord.repeat(increment);
            String regex = increment + line.substring(indexOfBegin, indexOfEnd + 1);
            line = line.replace(regex, unpackedPart);
        }

        return line;
    }


}
