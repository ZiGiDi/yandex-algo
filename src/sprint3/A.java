package sprint3;

import java.io.*;

public class A {

//Рита по поручению Тимофея наводит порядок в правильных скобочных последовательностях (ПСП), состоящих только из круглых скобок ().
//Для этого ей надо сгенерировать все ПСП длины 2n в алфавитном порядке —– алфавит состоит из ( и ) и открывающая скобка идёт раньше закрывающей.
//
//Помогите Рите —– напишите программу, которая по заданному n выведет все ПСП в нужном порядке.
//
//Рассмотрим второй пример. Надо вывести ПСП из четырёх символов. Таких всего две:
//
//(())
//()()
//(()) идёт раньше ()(), так как первый символ у них одинаковый, а на второй позиции у первой ПСП стоит (, который идёт раньше ).

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int numberOfBracketsPairs = readInt(reader);
            generateBrackets(numberOfBracketsPairs * 2, 0, "", writer);

        }
    }

    private static void generateBrackets(int numberOfBrackets, int unclosedBrackets, String prefix, BufferedWriter writer) throws IOException {
        if (numberOfBrackets == 0) {
            writer.write(prefix);
            writer.newLine();
            return;
        }
        if (unclosedBrackets == 0) {
            generateBrackets(numberOfBrackets - 1, unclosedBrackets + 1, prefix + "(", writer);
        } else if (numberOfBrackets <= unclosedBrackets) {
            generateBrackets(numberOfBrackets - 1, unclosedBrackets - 1, prefix + ")", writer);
        } else {
            generateBrackets(numberOfBrackets - 1, unclosedBrackets + 1, prefix + "(", writer);
            generateBrackets(numberOfBrackets - 1, unclosedBrackets - 1, prefix + ")", writer);
        }
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }
}
