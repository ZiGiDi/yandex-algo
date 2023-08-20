package sprint2.d;


//Comment it before submitting
class Node<V> {  
    public V value;  
    public Node<V> next;  
 
    public Node(V value, Node<V> next) {  
        this.value = value;  
        this.next = next;  
    }  
}

//    Мама Васи хочет знать, что сын планирует делать и когда. Помогите ей: напишите функцию solution, определяющую индекс первого вхождения передаваемого ей на вход значения в связном списке, если значение присутствует.
//        Внимание: в этой задаче не нужно считывать входные данные. Нужно написать только функцию, которая принимает на вход голову списка и искомый элемент, а возвращает целое число — индекс найденного элемента или -1.

public class Solution {
    public static int solution(Node<String> head, String elem) {

        int index = 0;
        while (head != null){
            if( elem.equals(head.value)){
                return index;
            }
            head = head.next;
            index++;
        }
        return -1;
    }

    public static void main(String[] args)  {
        Node<String> node3 = new Node<>("node3", null);
        Node<String> node2 = new Node<>("node2", node3);
        Node<String> node1 = new Node<>("node1", node2);
        Node<String> node0 = new Node<>("node0", node1);
        int idx = solution(node0, "node00");
        assert idx == 2;
    }
}