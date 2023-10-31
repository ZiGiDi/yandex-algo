package sprint5.review.b;

// <template>
class Node {

    /*
    Дано бинарное дерево поиска, в котором хранятся ключи. Ключи — уникальные целые числа. Найдите вершину с заданным ключом
    и удалите её из дерева так, чтобы дерево осталось корректным бинарным деревом поиска. Если ключа в дереве нет,
    то изменять дерево не надо.
    На вход вашей функции подаётся корень дерева и ключ, который надо удалить. Функция должна вернуть корень изменённого дерева.
    Сложность удаления узла должна составлять O(h), где h –— высота дерева.
    Создавать новые вершины (вдруг очень захочется) нельзя.
     */

    /*
id: https://contest.yandex.ru/contest/24810/run-report/95051127/

-- ПРИНЦИП РАБОТЫ --
Аналогично прошлой задачи, это просто реализация удаления, которая полностью описана в самом курсе: https://practicum.yandex.ru/learn/algorithms/courses/7f101a83-9539-4599-b6e8-8645c3f31fad/sprints/134510/topics/e7dbf42a-fd5a-434b-990d-9cfe0e3a10c8/lessons/03eb9b46-4c74-43b4-8d00-a125aeed47bf/
Ищем нужную ноду для удаления, используя свойства двоичного дерева поиска. В случае нахождения, заменяем ноду на ребенка:
1) Если у ноды только один ребенок, то на него.
2) Если 2, то на самую левую ноду правого поддерева, при ее удалении запускаем процесс заново
3) Если детей нет, то просто удаляем

-- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
Пользуясь свойствами дерева, которое уже является отсортированным.
На место удаленной ноды мы точно поставим то значение, которое будет удовлетворять условиям:
1)Если в узле дерева записано значение, то в левом поддереве располагаются только узлы со значениями, меньшими или равными.
2)Если в узле дерева записано значение, то в правом поддереве располагаются только узлы со значениями, большими или равными.
3)Левое и правое поддеревья отвечают тем же двум условиям. Это рекурсивное правило. Из него следует, что любое поддерево в дереве поиска будет являться деревом поиска.
https://practicum.yandex.ru/learn/algorithms/courses/7f101a83-9539-4599-b6e8-8645c3f31fad/sprints/134510/topics/e7dbf42a-fd5a-434b-990d-9cfe0e3a10c8/lessons/2d8a74f0-a697-4210-97a8-c9cae9b9cadc/

-- ВРЕМЕННАЯ СЛОЖНОСТЬ --
 O(h), где h – высота дерева.
Если дерево сбалансированно O(logN). Для вырожденного дерева – O(N), т.к. все элементы выстроились в ряд

-- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
 О(1) для хранения ссылок на ноды и временных переменных,
 Но мы находимся в рекурсии и тратим память на стэк: O(h), где h – высота дерева.
 */
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