package ra.edu.ra.edu.src.presentation;

import ra.edu.ra.edu.src.model.User;
import ra.edu.ra.edu.src.service.AuthService;
import ra.edu.ra.edu.src.util.ConsoleUtil;
import ra.edu.ra.edu.src.util.TimeUtil;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService = new AuthService();
    public static void main(String[] args) {
        while (true) {
            System.out.println("===== Hệ thống quản lý đặt phòng =====");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký (Nhân viên)");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    ConsoleUtil.clear();
                    login();
                    break;
                case "2":
                    ConsoleUtil.clear();
                    register();
                    break;
                case "0":
                    System.out.println("Đang thoát chương trình...");
                    TimeUtil.sleep(2000);
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }

    private static void login() {
        System.out.print("Tên người dùng: ");
        String username = scanner.nextLine();
        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();
        User user = authService.login(username, password);
        if (user == null) {
            System.out.println("Không tìm thấy người dùng");
            TimeUtil.sleep(2000);
            ConsoleUtil.clear();
            return;
        }
        System.out.println("Login thành công");
        TimeUtil.sleep(2000);
        ConsoleUtil.clear();
        if (user != null) {
            switch (user.getRole()) {
                case "ADMIN":
                    new AdminMenu().showMenu();
                    return;
                case "EMPLOYEE":
                    new EmployeeMenu().showMenu();
                    return;
                case "SUPPORT":
                    new SupportMenu().showMenu();
                    return;
            }
        }
    }

    private static void register() {
        System.out.print("Tên người dùng: ");
        String username = scanner.nextLine();
        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();
        System.out.print("Số điện thoại: ");
        String phone = scanner.nextLine();
        System.out.print("Phòng ban: ");
        String department = scanner.nextLine();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("EMPLOYEE");
        user.setPhone(phone);
        user.setDepartment(department);
        boolean success = authService.register(user);
        if (success) {
            System.out.println("Đăng ký thành công");
            TimeUtil.sleep(2000);
            ConsoleUtil.clear();
        } else {
            System.out.println("Tên người dùng đã tồn tại");
            TimeUtil.sleep(2000);
            ConsoleUtil.clear();
        }
    }
}