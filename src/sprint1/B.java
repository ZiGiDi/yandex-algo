package sprint1;

import java.util.Scanner;

public class B {

    private static boolean checkParity(int a, int b, int c) {
        return (a % 2 == 0 && b % 2 == 0 && c % 2 == 0)
                || (a % 2 != 0 && b % 2 != 0 && c % 2 != 0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        if (checkParity(a, b, c)) {
            System.out.println("WIN");
        } else {
            System.out.println("FAIL");
        }
        scanner.close();
    }

}