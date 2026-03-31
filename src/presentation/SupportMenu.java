package ra.edu.ra.edu.src.presentation;

import ra.edu.ra.edu.src.model.Booking;
import ra.edu.ra.edu.src.model.User;
import ra.edu.ra.edu.src.service.BookingService;

import java.util.List;
import java.util.Scanner;

public class SupportMenu {

    private Scanner scanner = new Scanner(System.in);
    private BookingService bookingService = new BookingService();
    private User currentUser;

    public SupportMenu(User user) {
        this.currentUser = user;
    }

    public void showMenu() {
        while (true) {
            System.out.println("===== SUPPORT MENU =====");
            System.out.println("1. Xem công việc");
            System.out.println("2. Cập nhật trạng thái");
            System.out.println("0. Đăng xuất");
            System.out.print("Chọn: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewTasks();
                    break;
                case 2:
                    updateStatus();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Không hợp lệ");
            }
        }
    }

    // ================= VIEW TASK =================
    private void viewTasks() {
        List<Booking> list = bookingService.getBookingsBySupport(currentUser.getId());

        System.out.println("===== CÔNG VIỆC =====");

        for (Booking b : list) {
            System.out.println(
                    "ID: " + b.getId() +
                            " | Room: " + b.getRoomId() +
                            " | " + b.getStartTime() +
                            " | Status: " + b.getPrepare_status()
            );
        }
    }

    // ================= UPDATE STATUS =================
    private void updateStatus() {
        System.out.print("Nhập ID booking: ");
        int bookingId = Integer.parseInt(scanner.nextLine());

        System.out.println("1. PREPARING");
        System.out.println("2. READY");
        System.out.println("3. MISSING");
        System.out.print("Chọn: ");

        int choice = Integer.parseInt(scanner.nextLine());

        String status = "PREPARING";

        if (choice == 2) status = "READY";
        else if (choice == 3) status = "MISSING";

        bookingService.updatePrepareStatus(bookingId, status);

        System.out.println("Cập nhật thành công!");
    }
}
