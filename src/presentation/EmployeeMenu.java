package ra.edu.ra.edu.src.presentation;

import ra.edu.ra.edu.src.model.Booking;
import ra.edu.ra.edu.src.model.Room;
import ra.edu.ra.edu.src.model.User;
import ra.edu.ra.edu.src.service.BookingService;
import ra.edu.ra.edu.src.service.AdminService;
import ra.edu.ra.edu.src.util.InputUtil;
import ra.edu.ra.edu.src.util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenu {

    private Scanner scanner = new Scanner(System.in);
    private AdminService adminService = new AdminService();
    private BookingService bookingService = new BookingService();

    private User currentUser;

    public EmployeeMenu(User user) {
        this.currentUser = user;
    }

    public void showMenu() {
        while (true) {
            System.out.println();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                        EMPLOYEE MENU                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃ 1. Xem phòng trống theo giờ  ┃        2. Đặt phòng          ┃   3. Xem lịch của tôi        ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃     4. Hủy booking           ┃    5. Hồ sơ cá nhân          ┃        0. Đăng xuất          ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            String input = scanner.nextLine();
            if (!ValidationUtil.isNumber(input)) {
                System.out.println("Vui lòng nhập số hợp lệ");
                continue;
            }
            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    viewAvailableRooms();
                    break;
                case 2:
                    createBooking();
                    break;
                case 3:
                    viewMyBookings();
                    break;
                case 4:
                    cancelBooking();
                    break;
                case 5:
                    profileMenu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Không hợp lệ!");
            }
        }
    }

    // ================= VIEW AVAILABLE ROOMS =================
    private void viewAvailableRooms() {
        System.out.println("===== XEM PHÒNG TRỐNG =====");

        LocalDateTime start = inputDateTime("Nhập thời gian bắt đầu: ");
        LocalDateTime end = inputDateTime("Nhập thời gian kết thúc: ");

        List<Room> rooms = bookingService.getAvailableRooms(start, end);

        if (rooms.isEmpty()) {
            System.out.println("Không có phòng trống!");
            return;
        }

        for (Room r : rooms) {
            System.out.println(r.getId() + " | " + r.getName()
                    + " | Capacity: " + r.getCapacity());
        }
    }

    // ================= CREATE BOOKING =================
    private void createBooking() {
        System.out.println("===== ĐẶT PHÒNG =====");

        int roomId = InputUtil.inputNumber("Nhập ID phòng: ");

        System.out.println("1. Ca sáng (8h - 12h)");
        System.out.println("2. Ca chiều (13h - 17h)");
        System.out.println("3. Tùy chỉnh");

        int choice = InputUtil.inputNumber("Chọn: ");

        LocalDateTime start;
        LocalDateTime end;

        if (choice == 1) {
            start = LocalDateTime.now().withHour(8).withMinute(0);
            end = LocalDateTime.now().withHour(12).withMinute(0);
        } else if (choice == 2) {
            start = LocalDateTime.now().withHour(13).withMinute(0);
            end = LocalDateTime.now().withHour(17).withMinute(0);
        } else {
            start = inputDateTime("Start: ");
            end = inputDateTime("End: ");
        }

        int people = InputUtil.inputNumber("Số người: ");

        // chọn thiết bị
        System.out.println("===== CHỌN THIẾT BỊ =====");
        adminService.getAllEquipments().forEach(e ->
                System.out.println(e.getId() + " | " + e.getName()));
        System.out.print("Nhập danh sách ID thiết bị (vd: 1,2,3): ");
        String equipmentInput = scanner.nextLine();

        // chọn dịch vụ
        System.out.println("===== CHỌN DỊCH VỤ =====");
        adminService.getAllServices().forEach(s ->
                System.out.println(s.getId() + " | " + s.getName()));
        System.out.print("Nhập danh sách ID dịch vụ (vd: 1,2): ");
        String serviceInput = scanner.nextLine();

        boolean success = bookingService.createFullBooking(
                currentUser.getId(),
                roomId,
                start,
                end,
                people,
                equipmentInput,
                serviceInput
        );

        if (success) {
            System.out.println("Đặt phòng thành công (PENDING)");
        } else {
            System.out.println("Đặt phòng thất bại!");
        }
    }

    // ================= VIEW MY BOOKINGS =================
    private void viewMyBookings() {
        System.out.println("===== LỊCH CỦA TÔI =====");

        List<Booking> list = bookingService.getBookingsByUser(currentUser.getId());

        for (Booking b : list) {
            System.out.println(
                    "ID: " + b.getId() +
                            " | Room: " + b.getRoomId() +
                            " | " + b.getStartTime() + " -> " + b.getEndTime() +
                            " | Status: " + b.getStatus() +
                            " | Prepare: " + b.getPrepare_status()
            );
        }
    }

    // ================= CANCEL BOOKING =================
    private void cancelBooking() {
        int bookingId = InputUtil.inputNumber("Nhập ID booking: ");

        System.out.print("Bạn có chắc muốn hủy? (y/n): ");
        String confirm = scanner.nextLine();

        if (!confirm.equalsIgnoreCase("y")) {
            return;
        }

        bookingService.cancelBooking(bookingId, currentUser.getId());
        System.out.println("Đã xử lý hủy booking!");
    }

    // ================= PROFILE =================
    private void profileMenu() {
        while (true) {
            System.out.println("===== HỒ SƠ CÁ NHÂN =====");
            System.out.println("1. Xem thông tin");
            System.out.println("2. Cập nhật thông tin");
            System.out.println("0. Quay lại");

            int choice = InputUtil.inputNumber("Chọn: ");

            switch (choice) {
                case 1:
                    System.out.println("Username: " + currentUser.getUsername());
                    System.out.println("Phone: " + currentUser.getPhone());
                    System.out.println("Department: " + currentUser.getDepartment());
                    break;

                case 2:
                    System.out.print("Số điện thoại mới: ");
                    currentUser.setPhone(scanner.nextLine());

                    System.out.print("Phòng ban mới: ");
                    currentUser.setDepartment(scanner.nextLine());

                    bookingService.updateProfile(currentUser);

                    System.out.println("Cập nhật thành công!");
                    break;

                case 0:
                    return;
            }
        }
    }

    // ================= UTIL =================
    private LocalDateTime inputDateTime(String msg) {
        while (true) {
            try {
                System.out.print(msg + " (yyyy-MM-ddTHH:mm): ");
                return LocalDateTime.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Sai format!");
            }
        }
    }
}