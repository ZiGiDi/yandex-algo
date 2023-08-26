package sprint2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class K {

    //    У Тимофея было n(0≤n≤32) стажёров. Каждый стажёр хотел быть лучше своих предшественников,
//    поэтому i-й стажёр делал столько коммитов, сколько делали два предыдущих стажёра в сумме.
//    Два первых стажёра были менее инициативными —– они сделали по одному коммиту.
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int n = readInt(reader);
            System.out.println(getFibonachi(n));
        }
    }

    private static int getFibonachi(int n) {
        if (n == 1 || n == 0) {
            return 1;
        }
        return getFibonachi(n - 1) + getFibonachi(n - 2);
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}
