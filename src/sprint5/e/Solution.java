package sprint5.e;

/*
Гоша понял, что такое дерево поиска, и захотел написать функцию, которая определяет,
является ли заданное дерево деревом поиска. Значения в левом поддереве должны быть строго меньше,
в правом —- строго больше значения в узле.
Помогите Гоше с реализацией этого алгоритма.
 */

public class Solution {
    public static boolean treeSolution(Node head) {
        return checkTree(head, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean checkTree(Node node, int minValue, int maxValue) {

        if (node == null) {
            return true;
        }

        if (node.value < maxValue && node.value > minValue) {
            return checkTree(node.left, minValue, node.value) &&
                    checkTree(node.right, node.value, maxValue);
        }
        return false;
    }

    // <template>
    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }

        Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
    // <template>


    public static void main(String[] args) {
        Node node1 = new Node(1, null, null);
        Node node2 = new Node(4, null, null);
        Node node3 = new Node(3, node1, node2);
        Node node4 = new Node(8, null, null);
        Node node5 = new Node(5, node3, node4);
        System.out.println(treeSolution(node5));
        node2.value = 5;
        System.out.println(treeSolution(node5));
    }
}