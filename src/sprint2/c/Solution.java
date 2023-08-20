package sprint2.c;


//Comment it before submitting
class Node<V> {
    public V value;
    public Node<V> next;

    public Node(V value, Node<V> next) {
        this.value = value;
        this.next = next;
    }
}

// Вася размышляет, что ему можно не делать из того списка дел, который он составил. Но, кажется, все пункты очень важные! Вася решает загадать число и удалить дело, которое идёт под этим номером. Список дел представлен в виде односвязного списка. Напишите функцию solution, которая принимает на вход голову списка и номер удаляемого дела и возвращает голову обновлённого списка.
// Внимание: в этой задаче не нужно считывать входные данные. Нужно написать только функцию, которая принимает на вход голову списка и номер удаляемого элемента и возвращает голову обновлённого списка.

//Формат ввода
//Функция принимает голову списка и индекс элемента, который надо удалить (нумерация с нуля). Список содержит не более 5000 элементов. Список не бывает пустым.
//Следуйте следующим правилам при отправке решений:
//
//По умолчанию выбран компилятор Make, выбор компилятора в данной задаче недоступен.
//Решение нужно отправлять в виде файла с расширением соответствующем вашему языку программирования.
//Для Java файл должен называться Solution.java, для C# – Solution.cs
//Для остальных языков программирования это имя использовать нельзя (имя «solution» тоже).
//Для Go укажите package main.
//Формат вывода
//Верните голову списка, в котором удален нужный элемент.

public class Solution {
    public static Node<String> solution(Node<String> head, int idx) {

        if (idx == 0) {
            return head.next;
        }
        Node<String> oldHead = head;

        while (idx != 0) {
            if (idx == 1) {
                head.next = head.next.next;
            }
            head = head.next;
            idx--;
        }
        return oldHead;

    }

    public static void main(String[] args) {
        Node<String> node3 = new Node<>("node3", null);
        Node<String> node2 = new Node<>("node2", node3);
        Node<String> node1 = new Node<>("node1", node2);
        Node<String> node0 = new Node<>("node0", node1);
        Node<String> newHead = solution(node0, 3);
        assert newHead == node0;
        assert newHead.next == node2;
        assert newHead.next.next == node3;
        assert newHead.next.next.next == null;
        // result is : node0 -> node2 -> node3
    }
}