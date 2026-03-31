package ra.edu.ra.edu.src.util;

import java.util.Scanner;

public class InputUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static int inputNumber(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            if (ValidationUtil.isNumber(input)) {
                return Integer.parseInt(input);
            }

            System.out.println("Vui lòng nhập số hợp lệ!");
        }
    }
}
