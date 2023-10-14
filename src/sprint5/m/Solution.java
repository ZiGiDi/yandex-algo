package sprint5.m;


/*
    Напишите функцию, совершающую просеивание вверх в куче на максимум. Гарантируется, что порядок элементов в куче
может быть нарушен только элементом, от которого запускается просеивание.
    Функция принимает в качестве аргументов массив, в котором хранятся элементы кучи, и индекс элемента,
от которого надо сделать просеивание вверх. Функция должна вернуть индекс, на котором элемент оказался после
просеивания. Также необходимо изменить порядок элементов в переданном в функцию массиве.
    Индексация в массиве, содержащем кучу, начинается с единицы. Таким образом, сыновья вершины
на позиции v это 2v и 2v+1. Обратите внимание, что нулевой элемент в передаваемом массиве фиктивный,
вершина кучи соответствует 1-му элементу.
 */
public class Solution {
    public static int siftUp(int[] heap, int index) {
        if (index == 1) {
            return index;
        }

        int parentIndex = index / 2;
        if (heap[parentIndex] < heap[index]) {
            int temp = heap[parentIndex];
            heap[parentIndex] = heap[index];
            heap[index] = temp;
            return siftUp(heap, parentIndex);
        }
        return index;
    }

    public static void main(String[] args) {
        int[] sample = {-1, 12, 6, 8, 3, 15, 7};
        int result = siftUp(sample, 5);
        System.out.println(result);
        assert result == 1;
    }
}
