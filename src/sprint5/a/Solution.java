package sprint5.a;

/*
Гоша повесил на стену гирлянду в виде бинарного дерева,
в узлах которого находятся лампочки. У каждой лампочки есть своя яркость.
Уровень яркости лампочки соответствует числу, расположенному в узле дерева.
Помогите Гоше найти самую яркую лампочку в гирлянде, то есть такую, у которой яркость наибольшая.
 */
public class Solution {
    public static int treeSolution(Node head) {

        return findBiggestValue(head);
    }

    private static int findBiggestValue(Node node) {
        if (node == null) {
            return Integer.MIN_VALUE;
        }
        return Math.max(node.value, Math.max(findBiggestValue(node.left), findBiggestValue(node.right)));
    }

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(-5);
        Node node3 = new Node(3);
        node3.left = node1;
        node3.right = node2;
        Node node4 = new Node(2);
        node4.left = node3;
        System.out.println("solution = " + treeSolution(node4));
        assert treeSolution(node4) == 3;
    }
}
