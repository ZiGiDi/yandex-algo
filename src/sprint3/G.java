package sprint3;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class G {

//    Рита решила оставить у себя одежду только трёх цветов: розового, жёлтого и малинового.
//    После того как вещи других расцветок были убраны, Рита захотела отсортировать свой новый гардероб по цветам.
//    Сначала должны идти вещи розового цвета, потом —– жёлтого, и в конце —– малинового.
//    Помогите Рите справиться с этой задачей.
//
//    Примечание: попробуйте решить задачу за один проход по массиву!

    private static List<Integer> sortWardrobe(List<Integer> unsortedList) {
        int[] dressType = {0, 0, 0};
        for (Integer dress : unsortedList) {
            dressType[dress] = dressType[dress] + 1;
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < dressType.length; i++) {
            for (int count = 0; count < dressType[i]; count++) {
                result.add(i);
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int count = readInt(reader);
            List<Integer> unsortedList = readList(reader);
            List<Integer> result = sortWardrobe(unsortedList);
            for (Integer element : result) {
                writer.write(element.toString() + " ");
            }
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        if (s.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(s.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
