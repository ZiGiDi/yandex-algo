package sprint5.j;

/*
Дано BST. Надо вставить узел с заданным ключом. Ключи в дереве могут повторяться.
На вход функции подаётся корень корректного бинарного дерева поиска и ключ, который надо вставить в дерево.
Осуществите вставку этого ключа. Если ключ уже есть в дереве, то его дубликаты уходят в правого сына.
Таким образом вид дерева после вставки определяется однозначно. Функция должна вернуть корень дерева после
вставки вершины.
 */

public class Solution {
    public static Node insert(Node root, int key) {
        insertNode(root, key);
        return root;
    }

    private static void insertNode(Node root, int key) {
        if (key < root.getValue()) {
            if (root.getLeft() == null) {
                root.setLeft(new Node(null, null, key));
            } else {
                insertNode(root.getLeft(), key);
            }
        } else {
            if (root.getRight() == null) {
                root.setRight(new Node(null, null, key));
            } else {
                insertNode(root.getRight(), key);
            }
        }
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

    public static void main(String[] args) {
        Node node1 = new Node(null, null, 7);
        Node node2 = new Node(node1, null, 8);
        Node node3 = new Node(null, node2, 7);
        Node newHead = insert(node3, 6);
        assert newHead == node3;
        int value = newHead.getLeft().getValue();
        assert value == 6;
    }
}
