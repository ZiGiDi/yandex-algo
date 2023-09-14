package sprint4;

import java.io.*;
import java.util.ArrayList;

public class D {


    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int numberOfGroups = readInt(reader);
            ArrayList<String> groups = new ArrayList<>(numberOfGroups);
            for (int i = 0; i < numberOfGroups; i++) {
                String line = reader.readLine();
                if (!groups.contains(line)) {
                    groups.add(line);
                }
            }
            for (String line : groups) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}
