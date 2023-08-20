package sprint2.e;


//Comment it before submitting
class Node<V> {
    public V value;
    public Node<V> next;
    public Node<V> prev;

    public Node(V value, Node<V> next, Node<V> prev) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }
}

//Вася решил запутать маму —– делать дела в обратном порядке. Список его дел теперь хранится в двусвязном списке. Напишите функцию, которая вернёт список в обратном порядке.
//Внимание: в этой задаче не нужно считывать входные данные. Нужно написать только функцию, которая принимает на вход голову двусвязного списка и возвращает голову перевёрнутого списка. Ниже дано описание структуры, которая задаёт вершину списка.

public class Solution {
    public static Node<String> solution(Node<String> head) {

        while (head.next != null) {
            Node<String> nextElem = head.next;
            Node<String> prevElem = head.prev;
            head.next = prevElem;
            head.prev = nextElem;
            head = nextElem;
        }
        Node<String> nextElem = head.next;
        Node<String> prevElem = head.prev;
        head.next = prevElem;
        head.prev = nextElem;
        return head;

    }

    public static void main(String... strings) {
        Node<String> node3 = new Node<>("node3", null, null);
        Node<String> node2 = new Node<>("node2", node3, null);
        Node<String> node1 = new Node<>("node1", node2, null);
        Node<String> node0 = new Node<>("node0", node1, null);
        node1.prev = node0;
        node2.prev = node1;
        node3.prev = node2;
        Node<String> newNode = solution(node0);
        /* result is :*/
        assert newNode == node3;
        assert node3.next == node2;
        assert node2.next == node1;
        assert node2.prev == node3;
        assert node1.next == node0;
        assert node1.prev == node2;
        assert node0.prev == node1;
    }
}