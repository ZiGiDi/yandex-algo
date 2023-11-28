package sprint8;

import java.io.*;

public class B {

    /*
    Представьте, что вы работаете пограничником и постоянно проверяете документы людей по записи из базы.
    При этом допустима ситуация, когда имя человека в базе отличается от имени в паспорте на одну замену, одно удаление
    или одну вставку символа. Если один вариант имени может быть получен из другого удалением одного символа,
    то человека пропустят через границу. А вот если есть какое-либо второе изменение, то человек грустно поедет домой или в посольство.

    Например, если первый вариант —– это «Лена», а второй — «Лера», то девушку пропустят. Также человека пропустят,
    если в базе записано «Коля», а в паспорте — «оля».

    Однако вариант, когда в базе числится «Иннокентий», а в паспорте написано «ннакентий», уже не сработает.
    Не пропустят также человека, у которого в паспорте записан «Иинннокентий», а вот «Инннокентий» спокойно пересечёт границу.

    Напишите программу, которая сравнивает имя в базе с именем в паспорте и решает, пропускать человека или нет.
    В случае равенства двух строк — путешественника, естественно, пропускают.
     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String string1 = reader.readLine();
            String string2 = reader.readLine();

            boolean isOk = check(string1, string2);
            String result = isOk ? "OK" : "FAIL";

            writer.write(result);
        }
    }

    private static boolean check(String string1, String string2) {
        if (Math.abs(string1.length() - string2.length()) > 1) {
            return false;
        }
        if (string1.length() >= string2.length()) {
            return compare(string1, string2);
        }
        return compare(string2, string1);
    }

    private static boolean compare(String string1, String string2) {
        int i = 0;
        int j = 0;
        char[] charArray1 = string1.toCharArray();
        char[] charArray2 = string2.toCharArray();

        boolean isChanged = false;

        while (i < string1.length() && j != string2.length()) {
            if (charArray1[i] == charArray2[j]) {
                i++;
                j++;
            } else if (string1.length() == string2.length() && !isChanged) {
                i++;
                j++;
                isChanged = true;
            } else if(!isChanged) {
                i++;
                isChanged = true;
            } else {
                return false;
            }
        }
        return true;
    }

}
