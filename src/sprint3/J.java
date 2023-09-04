package sprint3;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class J {

//    Чтобы выбрать самый лучший алгоритм для решения задачи, Гоша продолжил изучать разные сортировки. На очереди сортировка пузырьком — https://ru.wikipedia.org/wiki/Сортировка_пузырьком
//
//Её алгоритм следующий (сортируем по неубыванию):
//
//На каждой итерации проходим по массиву, поочередно сравнивая пары соседних элементов. Если элемент на позиции i больше элемента на позиции i + 1, меняем их местами. После первой итерации самый большой элемент всплывёт в конце массива.
//Проходим по массиву, выполняя указанные действия до тех пор, пока на очередной итерации не окажется, что обмены больше не нужны, то есть массив уже отсортирован.
//После не более чем n – 1 итераций выполнение алгоритма заканчивается, так как на каждой итерации хотя бы один элемент оказывается на правильной позиции.
//
//Помогите Гоше написать код алгоритма.

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int count = readInt(reader);
            List<Integer> unsortedList = readList(reader);
            bubbleSort(unsortedList, writer);
        }
    }

    private static void bubbleSort(List<Integer> unsortedList, BufferedWriter writer) throws IOException {
        boolean isSorted = false;
        boolean isSortedFromBeginning = true;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < unsortedList.size() - 1; i++) {
                if (unsortedList.get(i) > unsortedList.get(i + 1)) {
                    int temp = unsortedList.get(i);
                    unsortedList.set(i, unsortedList.get(i + 1));
                    unsortedList.set(i + 1, temp);
                    isSorted = false;
                    isSortedFromBeginning = false;
                }
            }
            if (!isSorted || isSortedFromBeginning) {
                for (int element : unsortedList) {
                    writer.write(element + " ");
                }
                writer.newLine();
            }
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
