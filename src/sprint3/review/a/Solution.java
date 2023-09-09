package sprint3.review.a;

public class Solution {

//    Алла ошиблась при копировании из одной структуры данных в другую.
//    Она хранила массив чисел в кольцевом буфере. Массив был отсортирован по возрастанию,
//    и в нём можно было найти элемент за логарифмическое время.
//    Алла скопировала данные из кольцевого буфера в обычный массив,
//    но сдвинула данные исходной отсортированной последовательности.
//    Теперь массив не является отсортированным. Тем не менее, нужно обеспечить возможность находить
//    в нем элемент за O(logn).
//  Можно предполагать, что в массиве только уникальные элементы.
//  От вас требуется реализовать функцию, осуществляющую поиск в сломанном массиве.
// Обратите внимание, что считывать данные и выводить ответ не требуется.
    public static int brokenSearch(int[] arr, int k) {

        int leftIndex = 0;
        int rightIndex = arr.length - 1;

        while (leftIndex <= rightIndex) {

            int midIndex = (leftIndex + rightIndex) / 2;
            if (arr[midIndex] == k) {
                return midIndex;
            }

            if (arr[leftIndex] <= arr[midIndex]) {
                if (arr[leftIndex] <= k && k < arr[midIndex]) {
                    rightIndex = midIndex - 1;
                } else {
                    leftIndex = midIndex + 1;
                }
            } else {
                if (arr[midIndex] < k && k <= arr[rightIndex]) {
                    leftIndex = midIndex + 1;
                } else {
                    rightIndex = midIndex - 1;
                }
            }
        }
        return -1;
    }

    private static void test() {
        int[] arr = {19, 21, 100, 101, 1, 4, 5, 7, 12};
        assert 6 == brokenSearch(arr, 5);
    }
}
