package sprint5.k;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/*
Напишите функцию, которая будет выводить по неубыванию все ключи от L до R
включительно в заданном бинарном дереве поиска.
Ключи в дереве могут повторяться. Решение должно иметь сложность O(h+k), где h –— глубина дерева,
k — число элементов в ответе.
В данной задаче если в узле содержится ключ x, то другие ключи, равные x, могут быть как в правом,
 так и в левом поддереве данного узла. (Дерево строил стажёр, так что ничего страшного).
 */

public class Solution {

    public static void printRange(Node root, int L, int R, BufferedWriter writer) throws IOException {
        List<Integer> result = findRange(root, L, R)
                .stream().sorted().collect(Collectors.toList());
        for (Integer value : result) {
            writer.write(String.valueOf(value));
            writer.newLine();
        }
        writer.flush();
    }

    private static Deque<Integer> findRange(Node root, int L, int R) {

        Deque<Integer> response = new LinkedList<>();
        if (root == null) {
            return response;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node temp = stack.pop();
            int value = temp.getValue();

            if (L <= value && value <= R) {
                if (response.isEmpty()) {
                    response.add(value);
                } else if (value <= response.getFirst()) {
                    response.addFirst(value);
                } else if (value >= response.getLast()) {
                    response.addLast(value);
                } else {
                    Integer tempValue = response.pollLast();
                    response.addLast(value);
                    response.addLast(tempValue);
                }
            }

            if (temp.getRight() != null && value <= R) {
                stack.add(temp.getRight());
            }
            if (temp.getLeft() != null && value >= L) {
                stack.add(temp.getLeft());
            }
        }

        return response;
    }


    // <template>
    private static class Node {
        private int value;
        private Node left;
        private Node right;

        Node(Node left, Node right, int value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
    // <template>

    //    public static void main(String[] args) throws IOException {
    public static void main(String[] args) throws IOException {
        Node node1 = new Node(null, null, 2);
        Node node2 = new Node(null, node1, 1);
        Node node3 = new Node(null, null, 8);
        Node node4 = new Node(null, node3, 8);
        Node node5 = new Node(node4, null, 9);
        Node node6 = new Node(node5, null, 10);
        Node node7 = new Node(node2, node6, 5);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        printRange(node7, 2, 8, writer);
//        writer.flush();
        // expected output: 2 5 8 8
    }
}
