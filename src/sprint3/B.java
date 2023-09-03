package sprint3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class B {

//    На клавиатуре старых мобильных телефонов каждой цифре соответствовало несколько букв. Примерно так:
//
//2:'abc',
//3:'def',
//4:'ghi',
//5:'jkl',
//6:'mno',
//7:'pqrs',
//8:'tuv',
//9:'wxyz'
//
//Вам известно в каком порядке были нажаты кнопки телефона, без учета повторов. Напечатайте все комбинации букв, которые можно набрать такой последовательностью нажатий.

    private static final List<List<String>> LETTERS_DICTIONARY = getSymbolsDictionary();

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String numbers = reader.readLine();
            String[] split = numbers.split("");
            generateAllSymbols(0, "", split, writer);

        }
    }

    private static void generateAllSymbols(int index,
                                           String prefix,
                                           String[] symbols,
                                           BufferedWriter writer) throws IOException {

        if (index == symbols.length) {
            writer.write(prefix + " ");
            return;
        }
        List<String> letters = LETTERS_DICTIONARY.get(Integer.parseInt(symbols[index]));
        for (String letter : letters) {
            generateAllSymbols(index + 1, prefix + letter, symbols, writer);
        }
    }


    //    Т.к. мапу использовать нельзя, внесем символы по индексу
    private static List<List<String>> getSymbolsDictionary() {
        List<List<String>> dictionary = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            dictionary.add(null);
        }
        dictionary.set(2, List.of("a", "b", "c"));
        dictionary.set(3, List.of("d", "e", "f"));
        dictionary.set(4, List.of("g", "h", "i"));
        dictionary.set(5, List.of("j", "k", "l"));
        dictionary.set(6, List.of("m", "n", "o"));
        dictionary.set(7, List.of("p", "q", "r", "s"));
        dictionary.set(8, List.of("t", "u", "v"));
        dictionary.set(9, List.of("w", "x", "y", "z"));
        return dictionary;
    }
}
