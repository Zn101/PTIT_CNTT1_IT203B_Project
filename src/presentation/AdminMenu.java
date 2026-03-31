package ra.edu.ra.edu.src.presentation;

import ra.edu.ra.edu.src.model.*;
import ra.edu.ra.edu.src.service.AdminService;
import ra.edu.ra.edu.src.service.BookingService;
import ra.edu.ra.edu.src.util.ConsoleUtil;
import ra.edu.ra.edu.src.util.TimeUtil;
import ra.edu.ra.edu.src.util.ValidationUtil;

import java.util.List;
import java.util.Scanner;

import static ra.edu.ra.edu.src.util.InputUtil.inputNumber;

public class AdminMenu {
    private Scanner scanner = new Scanner(System.in);
    private AdminService adminService = new AdminService();

    public void showMenu() {
        while (true) {
            ;
            System.out.println("===== Menu Admin =====");
            System.out.println("1. Quản lý phòng họp");
            System.out.println("2. Quản lý thiết bị");
            System.out.println("3. Quản lý dịch vụ đi kèm");
            System.out.println("4. Quản lý người dùng");
            System.out.println("5. Duyệt Bookings, phân công");
            System.out.println("0. Đăng xuất");
            System.out.print("Lựa chọn của bạn: ");
            String input = scanner.nextLine();
            if (!ValidationUtil.isNumber(input)) {
                System.out.println("Vui lòng nhập số hợp lệ");
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    roomMenu();
                    break;
                case 2:
                    equipmentMenu();
                    break;
                case 3:
                    serviceMenu();
                case 4:
                    userMenu();
                    break;
                case 5:
                    bookingMenu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }

    private void roomMenu() {
        while (true) {
            ;
            System.out.println("===== Quản lý phòng =====");
            System.out.println("1. Xem tất cả các phòng");
            System.out.println("2. Thêm phòng");
            System.out.println("3. Cập nhật phòng");
            System.out.println("4. Xóa phòng");
            System.out.println("5. Tìm kiếm phòng");
            System.out.println("0. Quay lại Menu Admin");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    ;
                    viewRooms();
                    TimeUtil.sleep(3000);
                    break;
                case 2:
                    ;
                    addRoom();
                    TimeUtil.sleep(3000);
                    break;
                case 3:
                    ;
                    updateRoom();
                    TimeUtil.sleep(3000);
                    break;
                case 4:
                    ;
                    deleteRoom();
                    TimeUtil.sleep(3000);
                    break;
                case 0:
                    return;
            }
        }
    }

    private void addRoom() {
        System.out.print("Tên phòng: ");
        String name = scanner.nextLine();

        System.out.print("Sức chứa: ");
        int capacity = Integer.parseInt(scanner.nextLine());

        System.out.print("Vị trí: ");
        String location = scanner.nextLine();

        Room room = new Room(0, name, capacity, location);
        adminService.addRoom(room);

        System.out.println("Thêm phòng thành công");
    }

    private void viewRooms() {
        List<Room> rooms = adminService.getAllRooms();

        for (Room r : rooms) {
            System.out.println(r.getId() + " | " + r.getName() + " | " + r.getCapacity());
        }
    }

    private void updateRoom() {
        System.out.print("Id phòng cần cập nhật: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Tên phòng mới: ");
        String name = scanner.nextLine();

        System.out.print("Sức chứa mới: ");
        int capacity = Integer.parseInt(scanner.nextLine());

        System.out.print("Địa điểm mới: ");
        String location = scanner.nextLine();

        Room room = new Room(id, name, capacity, location);
        adminService.updateRoom(room);

        System.out.println("Đã sửa thành công");
    }

    private void deleteRoom() {
        System.out.print("Id phòng cần xóa: ");
        int id = Integer.parseInt(scanner.nextLine());

        adminService.deleteRoom(id);

        System.out.println("Xóa phòng thành công");
    }

    private void equipmentMenu() {
        while (true) {
            ;
            System.out.println("===== Quản lý thiết bị =====");
            System.out.println("1. Xem tất cả thiết bị");
            System.out.println("2. Thêm thiết bị");
            System.out.println("3. Sửa thông tin thiết bị");
            System.out.println("4. Xóa thiết bị");
            System.out.println("0. Quay lại Menu Admin");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    viewEquipments();
                    break;
                case 3:
                    updateEquipment();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void viewEquipments() {
        List<Equipment> list = adminService.getAllEquipments();

        for (Equipment e : list) {
            System.out.println(e.getId() + " | " + e.getName() + " | Available: " + e.getAvailableQuantity());
        }
    }

    private void updateEquipment() {
        System.out.print("Id thiết bị cần cập nhật: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Số lượng thiết bị mới: ");
        int qty = Integer.parseInt(scanner.nextLine());

        adminService.updateEquipmentQuantity(id, qty);

        System.out.println("Sửa thành công");
    }

    public void serviceMenu() {
        while (true) {
            ;
            System.out.println("===== Quản lý dịch vụ đi kèm =====");
            System.out.println("1. Xem tất cả dịch vụ");
            System.out.println("2. Thêm dịch vụ");
            System.out.println("3. Sửa thông tin dịch vụ");
            System.out.println("4. Xóa dịch vụ");
            System.out.println("0. Đăng xuất");
            System.out.print("Lựa chọn của bạn: ");
            String input = scanner.nextLine();
            if (!ValidationUtil.isNumber(input)) {
                System.out.println("Vui lòng nhập số hợp lệ");
                continue;
            }
            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    adminService.getAllServices()
                            .forEach(s -> System.out.println(s.getId() + " | " + s.getName()));
                    break;
                case 2:
                    System.out.print("Tên: ");
                    String name = scanner.nextLine();
                    double price = Double.parseDouble(scanner.nextLine());
                    adminService.addService(new Service(0, name, price));
                    break;
                case 3:
                    int id = inputNumber("ID: ");
                    System.out.print("Tên: ");
                    String n = scanner.nextLine();
                    double p = Double.parseDouble(scanner.nextLine());
                    adminService.updateService(new Service(id, n, p));
                case 4:
                    int delId = inputNumber("ID: ");
                    adminService.deleteService(delId);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }

    private void userMenu() {
        while (true) {
            ;
            System.out.println("===== Quản lý người dùng =====");
            System.out.println("1. Xem tất cả người dùng");
            System.out.println("2. Thêm tài khoản");
            System.out.println("0. Quay lại Menu Admin");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    viewUsers();
                    break;
                case 2:
                    createSupport();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void createSupport() {
        System.out.print("Tên người dùng: ");
        String username = scanner.nextLine();

        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("Nhân viên");

        adminService.createUser(user);

        System.out.println("Nhân viên thêm mới thành công");
    }

    private void viewUsers() {
        List<User> users = adminService.getAllUsers();

        for (User u : users) {
            System.out.println(u.getId() + " | " + u.getUsername() + " | " + u.getRole());
        }
    }

    private void bookingMenu() {
        BookingService bookingService = new BookingService();

        while (true) {
            ;
            System.out.println("===== QUẢN LÝ BOOKING =====");
            System.out.println("1. Xem booking PENDING");
            System.out.println("2. Duyệt / Từ chối booking");
            System.out.println("3. Xem booking đã duyệt");
            System.out.println("4. Phân công nhân viên hỗ trợ");
            System.out.println("0. Quay lại");

            int choice = inputNumber("Lựa chọn: ");

            switch (choice) {
                case 1:
                    showPendingBookings(bookingService);
                    break;
                case 2:
                    approveBookingFlow(bookingService);
                    break;
                case 3:
                    showApprovedBookings(bookingService);
                    break;
                case 4:
                    assignSupportFlow(bookingService);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Không hợp lệ!");
            }
        }
    }

    private void showPendingBookings(BookingService bookingService) {
        ;
        System.out.println("===== BOOKING PENDING =====");

        List<Booking> list = bookingService.getPendingBookings();

        if (list.isEmpty()) {
            System.out.println("Không có booking nào!");
            return;
        }

        for (Booking b : list) {
            System.out.println(b.getId() + " | Room: " + b.getRoomId()
                    + " | Time: " + b.getStartTime() + " -> " + b.getEndTime());
        }
    }

    private void showApprovedBookings(BookingService bookingService) {
        ;
        System.out.println("===== BOOKING ĐÃ DUYỆT =====");

        List<Booking> list = bookingService.getApprovedBookings();

        if (list.isEmpty()) {
            System.out.println("Không có booking nào!");
            return;
        }

        for (Booking b : list) {
            System.out.println(b.getId()
                    + " | Room: " + b.getRoomId()
                    + " | Time: " + b.getStartTime() + " -> " + b.getEndTime()
                    + " | Support: " + b.getSupportId());
        }
    }

    private void approveBookingFlow(BookingService bookingService) {
        ;
        System.out.println("===== DANH SÁCH BOOKING PENDING =====");

        List<Booking> list = new BookingService().getPendingBookings();

        if (list.isEmpty()) {
            System.out.println("Không có booking nào!");
            return;
        }

        for (Booking b : list) {
            System.out.println(b.getId() + " | Room: " + b.getRoomId()
                    + " | Time: " + b.getStartTime() + " -> " + b.getEndTime());
        }

        System.out.print("Nhập ID booking: ");
        int bookingId = Integer.parseInt(scanner.nextLine());

        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.print("Chọn: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1) {
            System.out.print("Nhập ID Support Staff: ");
            int supportId = Integer.parseInt(scanner.nextLine());

            bookingService.approveBooking(bookingId, supportId);
            System.out.println("Đã duyệt và phân công!");

        } else if (choice == 2) {
            bookingService.rejectBooking(bookingId);
            System.out.println("Đã từ chối!");
        } else {
            System.out.println("Không hợp lệ!");
        }
    }

    private void assignSupportFlow(BookingService bookingService) {
        ;
        System.out.println("===== PHÂN CÔNG SUPPORT =====");

        List<Booking> list = bookingService.getApprovedBookings();

        if (list.isEmpty()) {
            System.out.println("Không có booking đã duyệt!");
            return;
        }

        for (Booking b : list) {
            System.out.println(b.getId()
                    + " | Room: " + b.getRoomId()
                    + " | Time: " + b.getStartTime() + " -> " + b.getEndTime()
                    + " | Support: " + b.getSupportId());
        }

        int bookingId = inputNumber("Nhập ID booking: ");
        int supportId = inputNumber("Nhập ID nhân viên hỗ trợ: ");

        boolean success = bookingService.assignSupport(bookingId, supportId);

        if (success) {
            System.out.println("Phân công thành công!");
        } else {
            System.out.println("Support không hợp lệ hoặc booking không tồn tại!");
        }
    }
}