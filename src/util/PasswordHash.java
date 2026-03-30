package ra.edu.ra.edu.src.util;

import java.security.MessageDigest;

public class PasswordHash {

    public static String hashPassword(String password) {
        return password;
    }

    public static boolean checkPassword(String input, String stored) {
        return input.equals(stored);
    }
}