package sprint2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A {

//Алла получила задание, связанное с мониторингом работы различных серверов. Требуется понять, сколько времени обрабатываются определённые запросы на конкретных серверах. Эту информацию нужно хранить в матрице, где номер столбца соответствуют идентификатору запроса, а номер строки — идентификатору сервера. Алла перепутала строки и столбцы местами. С каждым бывает. Помогите ей исправить баг.
//
//Есть матрица размера m × n. Нужно написать функцию, которая её транспонирует.
//
//Транспонированная матрица получается из исходной заменой строк на столбцы.
//
//Например, для матрицы А (слева) транспонированной будет следующая матрица (справа):
//
//Формат ввода
//В первой строке задано число n — количество строк матрицы.
//Во второй строке задано m — число столбцов, m и n не превосходят 1000. В следующих n строках задана матрица. Числа в ней не превосходят по модулю 1000.
//
//Формат вывода
//Напечатайте транспонированную матрицу в том же формате, который задан во входных данных. Каждая строка матрицы выводится на отдельной строке, элементы разделяются пробелами.

    private static List<List<Integer>> getCorrectMonitoringData(List<List<Integer>> matrix, int colCount, int rowCount) {
        List<List<Integer>> result = new ArrayList<>(rowCount);
        for (int i = 0; i < rowCount; i++) {
            result.add(new ArrayList<>(colCount));
        }

        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                List<Integer> integers = matrix.get(i);
                result.get(j).add(i, integers.get(j));
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int rowsCount = readInt(reader);
            int colsCount = readInt(reader);
            List<List<Integer>> matrix = readMatrix(reader, rowsCount);

            List<List<Integer>> result = getCorrectMonitoringData(matrix, rowsCount, colsCount);
            for (List<Integer> rows : result) {
                for (int element : rows) {
                    writer.write(element + " ");
                }
                writer.newLine();
            }
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static List<List<Integer>> readMatrix(BufferedReader reader, int rowsCount) throws IOException {
        List<List<Integer>> matrix = new ArrayList<>(rowsCount);
        for (int i = 0; i < rowsCount; i++) {
            matrix.add(readList(reader));
        }
        return matrix;
    }

    private static List<Integer> readList(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}