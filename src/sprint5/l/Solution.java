package sprint5.l;

/*
    Напишите функцию, совершающую просеивание вниз в куче на максимум. Гарантируется, что порядок элементов
в куче может быть нарушен только элементом, от которого запускается просеивание.
    Функция принимает в качестве аргументов массив, в котором хранятся элементы кучи, и индекс элемента,
от которого надо сделать просеивание вниз. Функция должна вернуть индекс, на котором элемент оказался
после просеивания. Также необходимо изменить порядок элементов в переданном в функцию массиве.
Индексация в массиве, содержащем кучу, начинается с единицы. Таким образом, сыновья вершины
на позиции v это 2v и 2v+1. Обратите внимание, что нулевой элемент в передаваемом массиве фиктивный,
вершина кучи соответствует 1-му элементу.
 */
public class Solution {
    public static int siftDown(int[] heap, int index) {
        int left = 2 * index;
        int right = 2 * index + 1;

        if (left >= heap.length) {
            return index;
        }

        int indexLargest = left;
        if (right < heap.length && heap[left] < heap[right]) {
            indexLargest = right;
        }

        if (heap[index] < heap[indexLargest]) {
            int temp = heap[index];
            heap[index] = heap[indexLargest];
            heap[indexLargest] = temp;
            return siftDown(heap, indexLargest);
        }
        return index;
    }

    public static void main(String[] args) {
        int[] sample = {-1, 12, 1, 8, 3, 4, 7};
        int result = siftDown(sample, 2);
        System.out.println(result);
        assert result == 5;
    }
}
