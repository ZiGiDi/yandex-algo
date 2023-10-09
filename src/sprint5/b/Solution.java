/*
Гоше очень понравилось слушать рассказ Тимофея про деревья. Особенно часть про сбалансированные деревья.
Он решил написать функцию, которая определяет, сбалансировано ли дерево.
Дерево считается сбалансированным, если левое и правое поддеревья каждой вершины отличаются по высоте не больше,
чем на единицу.
 */
public class Solution {
    public static boolean treeSolution(Node head) {
        try {
            findDepth(head);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static int findDepth(Node node) {
        if (node == null) {
            return 0;
        }

        if (node.left == null && node.right == null) {
            return 1;
        }

        int left = findDepth(node.left);
        int right = findDepth(node.right);
        if (Math.abs(left - right) > 1) {
            throw new RuntimeException();
        }
        return 1 + Math.max(left, right);
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
    }
    // <template>

    private static void test() {
        Node node1 = new Node(1);
        Node node2 = new Node(-5);
        Node node3 = new Node(3);
        node3.left = node1;
        node3.right = node2;
        Node node4 = new Node(10);
        Node node5 = new Node(2);
        node5.left = node3;
        node5.right = node4;
        System.out.println(treeSolution(node5));
    }
}
