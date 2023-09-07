package sprint3.k;

import java.util.Arrays;

//Гоше дали задание написать красивую сортировку слиянием. Поэтому Гоше обязательно надо реализовать отдельно функцию merge и функцию merge_sort.
//        Функция merge принимает два отсортированных массива, сливает их в один отсортированный массив и возвращает его. Если требуемая сигнатура имеет вид merge(array, left, mid, right), то первый массив задаётся полуинтервалом
//        [l e f t,m i d)массива array, а второй – полуинтервалом[m i d,r i g h t) массива array.
//        Функция merge_sort принимает некоторый подмассив, который нужно отсортировать. Подмассив задаётся полуинтервалом — его началом и концом. Функция должна отсортировать передаваемый в неё подмассив, она ничего не возвращает.
//        Функция merge_sort разбивает полуинтервал на две половинки и рекурсивно вызывает сортировку отдельно для каждой. Затем два отсортированных массива сливаются в один с помощью merge.

public class Solution {
    public static int[] merge(int[] arr, int left, int mid, int right) {
        int[] result = new int[arr.length];
        int[] leftArr = Arrays.copyOfRange(arr, left, mid);
        int[] rightArr = Arrays.copyOfRange(arr, mid, right);

        // сливаем результаты
        int l = 0, r = 0, k = 0;
        while (l < leftArr.length && r < rightArr.length) {
            // выбираем, из какого массива забрать минимальный элемент
            if (leftArr[l] <= rightArr[r]) {
                result[k] = leftArr[l];
                l++;
            } else {
                result[k] = rightArr[r];
                r++;
            }
            k++;
        }

        // Если один массив закончился раньше, чем второй, то
        // переносим оставшиеся элементы второго массива в результирующий
        while (l < leftArr.length) {
            result[k] = leftArr[l];   // перенеси оставшиеся элементы left в result
            l++;
            k++;
        }
        while (r < rightArr.length) {
            result[k] = rightArr[r];  // перенеси оставшиеся элементы right в result
            r++;
            k++;
        }
        return result;
    }

    public static void merge_sort(int[] arr, int left, int right) {
        int[] sortedPart = Arrays.copyOfRange(arr, left, right);
        sortedPart = mergeSort(sortedPart);
        int j = 0;
        for (int i = left; i < right; i++) {
            arr[i] = sortedPart[j];
            j++;
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 4, 9, 2, 10, 11};
        int[] b = merge(a, 0, 3, 6);
        int[] expected = {1, 2, 4, 9, 10, 11};
        assert Arrays.equals(b, expected);
        int[] c = {1, 4, 2, 10, 1, 2};
        merge_sort(c, 0, 6);
        int[] expected2 = {1, 1, 2, 2, 4, 10};
        assert Arrays.equals(c, expected2);
    }

    public static int[] mergeSort(int[] array) {
        if (array.length == 1) {  // базовый случай рекурсии
            return array;
        }

        // запускаем сортировку рекурсивно на левой половине
        int[] left = mergeSort(Arrays.copyOfRange(array, 0, array.length / 2));

        // запускаем сортировку рекурсивно на правой половине
        int[] right = mergeSort(Arrays.copyOfRange(array, array.length / 2, array.length));

        // заводим массив для результата сортировки
        int[] result = new int[array.length];

        // сливаем результаты
        int l = 0, r = 0, k = 0;
        while (l < left.length && r < right.length) {
            // выбираем, из какого массива забрать минимальный элемент
            if (left[l] <= right[r]) {
                result[k] = left[l];
                l++;
            } else {
                result[k] = right[r];
                r++;
            }
            k++;
        }

        // Если один массив закончился раньше, чем второй, то
        // переносим оставшиеся элементы второго массива в результирующий
        while (l < left.length) {
            result[k] = left[l];   // перенеси оставшиеся элементы left в result
            l++;
            k++;
        }
        while (r < right.length) {
            result[k] = right[r];  // перенеси оставшиеся элементы right в result
            r++;
            k++;
        }

        return result;
    }
}