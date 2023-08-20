package sprint2.b;

//Comment it before submittin
class Node<V> {
    public V value;
    public Node<V> next;

    public Node(V value, Node<V> next) {
        this.value = value;
        this.next = next;
    }

}

//    Васе нужно распечатать свой список дел на сегодня. Помогите ему: напишите функцию, которая печатает все его дела. Известно, что дел у Васи не больше
//        5000.
//        Внимание: в этой задаче не нужно считывать входные данные. Нужно написать только функцию, которая принимает на вход голову списка и печатает его элементы. Ниже дано описание структуры, которая задаёт узел списка.
//        Используйте заготовки кода для данной задачи, расположенные по ссылкам:
//
//        c++
//        Java
//        js
//        Python
//        C#
//        go
//        Решение надо отправлять только в виде файла с расширением, которое соответствует вашему языку. Иначе даже корректно написанное решение не пройдет тесты.
//
//        Формат ввода
//        В качестве ответа сдайте только код функции, которая печатает элементы списка. Длина списка не превосходит
//        5000 элементов. Список не бывает пустым.
//        Следуйте следующим правилам при отправке решений:
//
//        По умолчанию выбран компилятор Make, выбор компилятора в данной задаче недоступен.
//        Решение нужно отправлять в виде файла с расширением соответствующем вашему языку программирования.
//        Для Java файл должен называться Solution.java, для C# – Solution.cs
//        Для остальных языков программирования это имя использовать нельзя (имя «solution» тоже).
//        Для Go укажите package main.
//        Формат вывода
//        Функция должна напечатать элементы списка по одному в строке.


public class Solution {
    public static void solution(Node<String> head) {

        while (head != null) {
            System.out.println(head.value);
            head = head.next;
        }
    }

    private static void main() {
        Node<String> node3 = new Node<>("node3", null);
        Node<String> node2 = new Node<>("node2", node3);
        Node<String> node1 = new Node<>("node1", node2);
        Node<String> node0 = new Node<>("node0", node1);
        solution(node0);
        /*
        Output is:
        node0
        node1
        node2
        node3
        */
    }
}