package ra.edu.ra.edu.src.util;

import java.time.LocalDateTime;

public class NotificationUtil {

    public static void notify(String message) {
        System.out.println("[NOTIFICATION] " + message + " | " + LocalDateTime.now());
    }
}
