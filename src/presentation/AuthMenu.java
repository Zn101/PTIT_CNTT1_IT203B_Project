package ra.edu.ra.edu.src.presentation;

import ra.edu.ra.edu.src.model.User;
import ra.edu.ra.edu.src.service.AuthService;
import ra.edu.ra.edu.src.util.ConsoleUtil;
import ra.edu.ra.edu.src.util.TimeUtil;
import ra.edu.ra.edu.src.util.ValidationUtil;

import java.util.Scanner;

public class AuthMenu {
    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService = new AuthService();

    public void showMenu() {
        while (true) {
            try {
                System.out.println();
                System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                   HỆ THỐNG QUẢN LÝ ĐẶT PHÒNG                         ┃");
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                System.out.println("┃                                    ┃                                 ┃");
                System.out.println("┃        1. Đăng nhập                ┃     2. Đăng ký (Nhân viên)      ┃");
                System.out.println("┃                                    ┃                                 ┃");
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                System.out.println("┃                                                                      ┃");
                System.out.println("┃                             0. Thoát                                 ┃");
                System.out.println("┃                                                                      ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                System.out.print("Lựa chọn của bạn: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        
                        login();
                        break;
                    case "2":
                        
                        register();
                        break;
                    case "0":
                        System.out.println("Đang thoát chương trình...");
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                }
            } catch (Exception e) {
                System.out.println("Có lỗi xảy ra. Vui lòng thử lại!");
            }
        }
    }

    private static void login() {
        try {
            System.out.print("Tên người dùng: ");
            String username = scanner.nextLine();
            while (ValidationUtil.isEmpty(username)) {
                System.out.println("Tên người dùng không được để trống");
                System.out.print("Nhập lại: ");
                username = scanner.nextLine();
            }

            System.out.print("Mật khẩu: ");
            String password = scanner.nextLine();
            while (ValidationUtil.isEmpty(password)) {
                System.out.println("Mật khẩu không được để trống");
                System.out.print("Nhập lại: ");
                password = scanner.nextLine();
            }

            AuthService.LoginResult result = authService.login(username, password);

            switch (result.getStatus()) {
                case USER_NOT_FOUND:
                    System.out.println("Tên người dùng không tồn tại");
                    return;

                case WRONG_PASSWORD:
                    System.out.println("Mật khẩu không đúng");
                    return;

                case SUCCESS:
                    System.out.println("Đăng nhập thành công");
                    navigateByRole(result.getUser());
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đăng nhập");
        }
    }

    private static void register() {
        try {
            System.out.print("Tên người dùng: ");
            String username = scanner.nextLine();
            while (ValidationUtil.isEmpty(username)) {
                System.out.println("Tên người dùng không được để trống");
                System.out.print("Nhập lại: ");
                username = scanner.nextLine();
            }

            System.out.print("Mật khẩu: ");
            String password = scanner.nextLine();
            while (ValidationUtil.isEmpty(password)) {
                System.out.println("Mật khẩu không được để trống");
                System.out.print("Nhập lại: ");
                password = scanner.nextLine();
            }

            System.out.print("Số điện thoại: ");
            String phone = scanner.nextLine();
            while (ValidationUtil.isPhone(phone)) {
                System.out.println("Số điện thoại không được để trống");
                System.out.print("Nhập lại: ");
                phone = scanner.nextLine();
            }

            System.out.print("Phòng ban: ");
            String department = scanner.nextLine();
            while (ValidationUtil.isEmpty(department)) {
                System.out.println("Phòng ban không được để trống");
                System.out.print("Nhập lại: ");
                department = scanner.nextLine();
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole("EMPLOYEE");
            user.setPhone(phone);
            user.setDepartment(department);
            boolean success = authService.register(user);

            if (success) {
                System.out.println("Đăng ký thành công");
                navigateByRole(user);
            } else {
                System.out.println("Tên người dùng đã tồn tại");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đăng ký");
        }
    }

    private static void navigateByRole(User user) {
        switch (user.getRole()) {
            case "ADMIN":
                new AdminMenu().showMenu();
                break;
            case "EMPLOYEE":
                new EmployeeMenu(user).showMenu();
                break;
            case "SUPPORT":
                new SupportMenu(user).showMenu();
                break;
            default:
                System.out.println("Role không hợp lệ");
        }
    }
}
