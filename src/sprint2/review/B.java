package sprint2.review;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class B {

//Задание связано с обратной польской нотацией. Она используется для парсинга арифметических выражений. Еще её иногда называют постфиксной нотацией.
//
//В постфиксной нотации операнды расположены перед знаками операций.
//
//Пример 1:
//3 4 +
//означает 3 + 4 и равно 7
//
//Пример 2:
//12 5 /
//Так как деление целочисленное, то в результате получим 2.
//
//Пример 3:
//10 2 4 * -
//означает 10 - 2 * 4 и равно 2

        /*
    id: https://contest.yandex.ru/contest/22781/run-report/90042935/

-- ПРИНЦИП РАБОТЫ --
На вход подается строка состоящая из чисел и оперантов.
Если элемент строки является числом, то он помещается в стэк.
Если оперант, то из стэка достаются 2 элемента и производится математическая операция,
затем результат операции помещается в стэк. Нужно учитывать, что порядок чисел в математических операциях
с обратной польской натацией идет слева направо в 2 последних числах перед операндом.

-- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
Т.к. все числа мы ложем в стэк  итерируясь от начала входного списка элементов,
дойдя до операнда мы в любой момент времени можем вытащить только те числа, которые были перед операндом (LIFO),
или результат предыдущего операнда, т.к. после математической операции результат помещается в Стэк.
Порядок элементов гарантирован из-за стэка, и порядок операций гарантирован, т.к. мы итерируемся последовательно.

-- ВРЕМЕННАЯ СЛОЖНОСТЬ --
Добавление в стэк стоит O(1), потому что в основании хранилища элементов используемого стэка лежит массив и указатель на индекс последнего элемента,
 а добавить элемент в свободный слот массива O(1). Но если массив полный, то стоимсоть операции будет О(n) где n - количество элементов в массиве,
 т.к. придется расширять массив и копировать все элементы из старого в новый

Извлечение из стэка стоит O(1), потому что в основании хранилища элементов используемого стэка лежит массив и указатель на индекс последнего элемента,
А получение элемента из массива по индексу O(1)

-- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
В итерации по элемнтам используется стэк, в основании которого лежит массив с указателем,
куда будут помещаться только числа. В худшем случае все элементы входой строки будут являться символами,
и тогда они попадут в стэк и займут память O(n), где n - количество элементов

Итого O(n);
*/

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int chaosNumber = calculate(tokenizer);
            System.out.println(chaosNumber);
        }
    }

    private static int calculate(StringTokenizer tokenizer) {
        Stack<Integer> stackForNumbers = new Stack<>();
        while (tokenizer.hasMoreElements()) {
            String symbol = tokenizer.nextToken();
            if ("+".equals(symbol)) {
                int secondElem = stackForNumbers.pop();
                int firstElem = stackForNumbers.pop();
                stackForNumbers.push(firstElem + secondElem);
            } else if ("-".equals(symbol)) {
                int secondElem = stackForNumbers.pop();
                int firstElem = stackForNumbers.pop();
                stackForNumbers.push(firstElem - secondElem);
            } else if ("*".equals(symbol)) {
                int secondElem = stackForNumbers.pop();
                int firstElem = stackForNumbers.pop();
                stackForNumbers.push(firstElem * secondElem);
            } else if ("/".equals(symbol)) {
                int secondElem = stackForNumbers.pop();
                int firstElem = stackForNumbers.pop();
                stackForNumbers.push(Math.floorDiv(firstElem, secondElem));
            } else {
                stackForNumbers.push(Integer.parseInt(symbol));
            }
        }
        return stackForNumbers.pop();
    }
}
