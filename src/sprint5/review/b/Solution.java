package sprint5.review.b;

// <template>
class Node {
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

public class Solution {
    public static Node remove(Node root, int key) {
        if (root == null) {
            return null;
        }

        if (root.getValue() > key) {
            return deleteInLeftSubtree(root, key);
        } else if (root.getValue() < key) {
            return deleteInRightSubtree(root, key);
        }
        return deleteNode(root);
    }

    private static Node deleteNode(Node node) {
        if (node.getLeft() == null) {
            return node.getRight();
        }

        if (node.getRight() == null) {
            return node.getLeft();
        }

        //В правом дереве ищем самую левую ноду для замены удаленной
        int leftmostValueOfRightSubtree = findLeftmostValue(node.getRight());
        node.setValue(leftmostValueOfRightSubtree);

        //Удаляем ноду, которой заменили
        return deleteInRightSubtree(node, leftmostValueOfRightSubtree);
    }

    private static Node deleteInRightSubtree(Node root, int key) {
        Node newHead = remove(root.getRight(), key);
        root.setRight(newHead);
        return root;
    }

    private static Node deleteInLeftSubtree(Node root, int key) {
        Node newHead = remove(root.getLeft(), key);
        root.setLeft(newHead);
        return root;
    }

    private static int findLeftmostValue(Node root) {
        if (root.getLeft() == null) {
            return root.getValue();
        }
        return findLeftmostValue(root.getLeft());
    }

    public static void main(String[] args) {
        Node node1 = new Node(null, null, 2);
        Node node2 = new Node(node1, null, 3);
        Node node3 = new Node(null, node2, 1);
        Node node4 = new Node(null, null, 6);
        Node node5 = new Node(node4, null, 8);
        Node node6 = new Node(node5, null, 10);
        Node node7 = new Node(node3, node6, 5);
        Node newHead = remove(node7, 10);
        assert newHead.getValue() == 5;
        assert newHead.getRight() == node5;
        assert newHead.getRight().getValue() == 8;
    }
}