package ra.edu.ra.edu.src.util;

public class ValidationUtil {
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }
    public static boolean isNumber(String input) {
        return input.matches("\\d+");
    }
    public static boolean isPhone(String input) {
        return input.matches("\\d{9,11}");
    }
}