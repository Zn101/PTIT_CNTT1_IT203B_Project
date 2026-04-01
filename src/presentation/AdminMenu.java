package ra.edu.ra.edu.src.presentation;

import ra.edu.ra.edu.src.model.*;
import ra.edu.ra.edu.src.service.AdminService;
import ra.edu.ra.edu.src.service.AuthService;
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
                    viewRooms();
                    TimeUtil.sleep(3000);
                    break;
                case 2:
                    addRoom();
                    TimeUtil.sleep(3000);
                    break;
                case 3:
                    updateRoom();
                    TimeUtil.sleep(3000);
                    break;
                case 4:
                    deleteRoom();
                    TimeUtil.sleep(3000);
                    break;
                case 5:
                    searchRoomByName();
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

    private void searchRoomByName() {
        try {
            System.out.print("Nhập tên phòng cần tìm: ");
            String keyword = scanner.nextLine();

            List<Room> rooms = adminService.searchRoomByName(keyword);

            if (rooms.isEmpty()) {
                System.out.println("Không tìm thấy phòng nào!");
            } else {
                System.out.println("Kết quả tìm kiếm:");
                for (Room room : rooms) {
                    System.out.println(room);
                }
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi khi tìm kiếm!");
        }

        TimeUtil.sleep(3000);
    }

    private void equipmentMenu() {
        while (true) {
            ConsoleUtil.clear();
            System.out.println("===== Quản lý thiết bị =====");
            System.out.println("1. Xem tất cả thiết bị");
            System.out.println("2. Thêm thiết bị");
            System.out.println("3. Sửa thông tin thiết bị");
            System.out.println("4. Xóa thiết bị");
            System.out.println("0. Quay lại Menu Admin");
            System.out.print("Lựa chọn của bạn: ");

            String input = scanner.nextLine();
            if (!ValidationUtil.isNumber(input)) {
                System.out.println("Vui lòng nhập số!");
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    viewEquipments();
                    break;
                case 2:
                    addEquipment();
                    break;
                case 3:
                    updateEquipment();
                    break;
                case 4:
                    deleteEquipment();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Không hợp lệ!");
            }

            TimeUtil.sleep(2000);
        }
    }

    private void viewEquipments() {
        List<Equipment> list = adminService.getAllEquipments();

        if (list.isEmpty()) {
            System.out.println("Không có thiết bị nào!");
            return;
        }

        for (Equipment e : list) {
            System.out.println(
                    e.getId() + " | " +
                            e.getName() + " | Total: " +
                            e.getTotalQuantity() + " | Available: " +
                            e.getAvailableQuantity() + " | " +
                            e.getStatus()
            );
        }
    }

    private void addEquipment() {
        System.out.print("Tên thiết bị: ");
        String name = scanner.nextLine();

        while (ValidationUtil.isEmpty(name)) {
            System.out.println("Không được để trống!");
            name = scanner.nextLine();
        }

        System.out.print("Tổng số lượng: ");
        int total = inputNumber("Nhập lại số: ");

        System.out.print("Trạng thái (GOOD/BROKEN): ");
        String status = scanner.nextLine();

        Equipment e = new Equipment();
        e.setName(name);
        e.setTotalQuantity(total);
        e.setAvailableQuantity(total); // mới tạo = total
        e.setStatus(status);

        adminService.addEquipment(e);

        System.out.println("Thêm thiết bị thành công!");
    }

    private void updateEquipment() {
        int id = inputNumber("Id thiết bị: ");

        System.out.print("Số lượng available mới: ");
        int qty = inputNumber("Nhập lại số: ");

        adminService.updateEquipmentQuantity(id, qty);

        System.out.println("Cập nhật thành công!");
    }

    private void deleteEquipment() {
        int id = inputNumber("Id thiết bị cần xóa: ");

        boolean success = adminService.deleteEquipment(id);

        if (success) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Không tìm thấy thiết bị!");
        }
    }

    public void serviceMenu() {
        while (true) {
            ConsoleUtil.clear();
            System.out.println("===== QUẢN LÝ DỊCH VỤ =====");
            System.out.println("1. Xem tất cả dịch vụ");
            System.out.println("2. Thêm dịch vụ");
            System.out.println("3. Sửa dịch vụ");
            System.out.println("4. Xóa dịch vụ");
            System.out.println("0. Quay lại");

            int choice = inputNumber("Lựa chọn: ");

            switch (choice) {
                case 1:
                    viewServices();
                    break;
                case 2:
                    addService();
                    break;
                case 3:
                    updateService();
                    break;
                case 4:
                    deleteService();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Không hợp lệ!");
            }
        }
    }

    private void viewServices() {
        List<Service> list = adminService.getAllServices();

        if (list.isEmpty()) {
            System.out.println("Không có dịch vụ!");
            return;
        }

        for (Service s : list) {
            System.out.println(s.getId() + " | " + s.getName() + " | " + s.getPrice());
        }
    }

    private void addService() {
        System.out.print("Tên dịch vụ: ");
        String name = scanner.nextLine();

        if (ValidationUtil.isEmpty(name)) {
            System.out.println("Tên không được để trống!");
            return;
        }

        System.out.print("Giá: ");
        String priceInput = scanner.nextLine();

        if (!ValidationUtil.isNumber(priceInput)) {
            System.out.println("Giá phải là số!");
            return;
        }

        double price = Double.parseDouble(priceInput);

        adminService.addService(new Service(0, name, price));

        System.out.println("Thêm dịch vụ thành công!");
    }

    private void updateService() {
        viewServices();

        int id = inputNumber("Nhập ID cần sửa: ");

        System.out.print("Tên mới: ");
        String name = scanner.nextLine();

        if (ValidationUtil.isEmpty(name)) {
            System.out.println("Tên không được để trống!");
            return;
        }

        System.out.print("Giá mới: ");
        String priceInput = scanner.nextLine();

        if (!ValidationUtil.isNumber(priceInput)) {
            System.out.println("Giá phải là số!");
            return;
        }

        double price = Double.parseDouble(priceInput);

        boolean success = adminService.updateService(new Service(id, name, price));

        if (success) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("ID không tồn tại hoặc dữ liệu không hợp lệ!");
        }
    }

    private void deleteService() {
        viewServices();

        int id = inputNumber("Nhập ID cần xóa: ");

        boolean success = adminService.deleteService(id);

        if (success) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Không tìm thấy dịch vụ!");
        }
    }

    private void userMenu() {
        while (true) {
            System.out.println("===== Quản lý người dùng =====");
            System.out.println("1. Xem tất cả người dùng");
            System.out.println("2. Thêm tài khoản SUPPORT");
            System.out.println("0. Quay lại");

            String input = scanner.nextLine();

            if (!ValidationUtil.isNumber(input)) {
                System.out.println("Vui lòng nhập số!");
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    viewUsers();
                    break;
                case 2:
                    createSupport();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Không hợp lệ!");
            }
        }
    }

    private void createSupport() {
        System.out.print("Tên người dùng: ");
        String username = scanner.nextLine();

        if (ValidationUtil.isEmpty(username)) {
            System.out.println("Username không được để trống!");
            return;
        }

        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();

        if (ValidationUtil.isEmpty(password)) {
            System.out.println("Mật khẩu không được để trống!");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // FIX QUAN TRỌNG
        user.setRole("SUPPORT");

        boolean success = new AuthService().register(user);

        if (success) {
            System.out.println("Thêm nhân viên thành công!");
        } else {
            System.out.println("Username đã tồn tại!");
        }
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