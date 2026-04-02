package ra.edu.ra.edu.src.presentation;

import ra.edu.ra.edu.src.model.*;
import ra.edu.ra.edu.src.service.AdminService;
import ra.edu.ra.edu.src.service.AuthService;
import ra.edu.ra.edu.src.service.BookingService;
import ra.edu.ra.edu.src.util.InputUtil;
import ra.edu.ra.edu.src.util.ValidationUtil;

import java.util.List;
import java.util.Scanner;

import static ra.edu.ra.edu.src.util.InputUtil.inputNumber;

public class AdminMenu {
    private Scanner scanner = new Scanner(System.in);
    private AdminService adminService = new AdminService();

    public void showMenu() {
        while (true) {
            System.out.println();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                         MENU ADMIN                                         ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃   1. Quản lý phòng họp       ┃   2. Quản lý thiết bị        ┃      3. Quản lý dịch vụ      ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃   4. Quản lý người dùng      ┃ 5. Duyệt & phân công Booking ┃        0. Đăng xuất          ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
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
                    break;
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
            System.out.println();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                       QUẢN LÝ PHÒNG                                        ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃  1. Xem tất cả các phòng     ┃      2. Thêm phòng           ┃     3. Cập nhật phòng        ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃      4. Xóa phòng            ┃     5. Tìm kiếm phòng        ┃  0. Quay lại Menu Admin      ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("Lựa chọn của bạn: ");
            String input = scanner.nextLine();
            if (!ValidationUtil.isNumber(input)) {
                System.out.println("Vui lòng nhập số hợp lệ");
                continue;
            }
            int choice = Integer.parseInt(input);
            switch (choice) {
                case 1:
                    viewRooms();
                    break;
                case 2:
                    addRoom();
                    break;
                case 3:
                    updateRoom();
                    break;
                case 4:
                    deleteRoom();
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
        String name = InputUtil.inputNonEmpty("Tên phòng: ");

        int capacity;
        while (true) {
            capacity = InputUtil.inputNumber("Sức chứa: ");
            if (capacity > 0) break;
            System.out.println("Sức chứa phải > 0");
        }

        String location = InputUtil.inputNonEmpty("Vị trí: ");

        Room room = new Room(0, name, capacity, location);

        boolean success = adminService.addRoom(room);

        if (success) {
            System.out.println("Thêm phòng thành công");
        } else {
            System.out.println("Tên phòng đã tồn tại!");
        }
    }

    private void viewRooms() {
        List<Room> rooms = adminService.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("Không có phòng nào!");
            return;
        }

        for (Room r : rooms) {
            System.out.println(r.getId() + " | " + r.getName() + " | " + r.getCapacity());
        }
    }

    private void updateRoom() {
        int id = InputUtil.inputNumber("Id phòng cần cập nhật: ");

        String name = InputUtil.inputNonEmpty("Tên phòng mới: ");

        int capacity;
        while (true) {
            capacity = InputUtil.inputNumber("Sức chứa mới: ");
            if (capacity > 0) break;
            System.out.println("Sức chứa phải > 0");
        }

        String location = InputUtil.inputNonEmpty("Địa điểm mới: ");

        Room room = new Room(id, name, capacity, location);

        boolean success = adminService.updateRoom(room);

        if (success) {
            System.out.println("Đã sửa thành công");
        } else {
            System.out.println("Tên phòng đã tồn tại!");
        }

        System.out.println("Đã sửa thành công");
    }

    private void deleteRoom() {
        int id = InputUtil.inputNumber("Id phòng cần xóa: ");

        System.out.print("Bạn có chắc muốn xóa? (y/n): ");
        String confirm = scanner.nextLine();

        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Đã hủy xóa");
            return;
        }

        adminService.deleteRoom(id);
        System.out.println("Xóa phòng thành công");
    }

    private void searchRoomByName() {
        try {
            String keyword = InputUtil.inputNonEmpty("Nhập tên phòng cần tìm: ");

            List<Room> rooms = adminService.searchRoomByName(keyword);

            if (rooms.isEmpty()) {
                System.out.println("Không tìm thấy phòng nào!");
            } else {
                System.out.println("Kết quả tìm kiếm:");
                for (Room room : rooms) {
                    System.out.println(room.getId() + " | " + room.getName()
                            + " | " + room.getCapacity() + " | " + room.getLocation());
                }
            }

        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi khi tìm kiếm!");
        }
    }

    private void equipmentMenu() {
        while (true) {
            System.out.println();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                     QUẢN LÝ THIẾT BỊ                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃  1. Xem tất cả thiết bị      ┃      2. Thêm thiết bị        ┃  3. Sửa thông tin thiết bị   ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃      4. Xóa thiết bị         ┃                              ┃  0. Quay lại Menu Admin      ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
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
        }
    }

    private void viewEquipments() {
        List<Equipment> list = adminService.getAllEquipments();

        if (list.isEmpty()) {
            System.out.println("Không có thiết bị nào!");
            return;
        }

        System.out.println("===== DANH SÁCH THIẾT BỊ =====");
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
            System.out.print("Nhập lại tên: ");
            name = scanner.nextLine();
        }

        int total = inputNumber("Tổng số lượng (>0): ");

        while (total <= 0) {
            System.out.println("Phải > 0!");
            total = inputNumber("Nhập lại: ");
        }

        System.out.print("Trạng thái (GOOD/BROKEN): ");
        String status = scanner.nextLine();

        while (!status.equalsIgnoreCase("GOOD") && !status.equalsIgnoreCase("BROKEN")) {
            System.out.println("Chỉ được nhập GOOD hoặc BROKEN!");
            System.out.print("Nhập lại: ");
            status = scanner.nextLine();
        }

        Equipment e = new Equipment();
        e.setName(name.trim());
        e.setTotalQuantity(total);
        e.setAvailableQuantity(total);
        e.setStatus(status.toUpperCase());

        boolean success = adminService.addEquipment(e);

        if (success) {
            System.out.println("Thêm thiết bị thành công!");
        } else {
            System.out.println("Thêm thất bại! (Có thể trùng tên hoặc dữ liệu không hợp lệ)");
        }
    }

    private void updateEquipment() {
        int id = inputNumber("Id thiết bị: ");

        int qty = inputNumber("Số lượng available mới: ");

        boolean success = adminService.updateEquipment(id, qty);

        if (success) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại! (Sai id hoặc số lượng không hợp lệ)");
        }
    }

    private void deleteEquipment() {
        int id = inputNumber("Id thiết bị cần xóa: ");

        boolean success = adminService.deleteEquipment(id);

        if (success) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Xóa thất bại! (Không tồn tại hoặc đang được sử dụng)");
        }
    }

    public void serviceMenu() {
        while (true) {
            System.out.println();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                     QUẢN LÝ DỊCH VỤ                                        ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃  1. Xem tất cả dịch vụ       ┃      2. Thêm dịch vụ         ┃        3. Sửa dịch vụ        ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃      4. Xóa dịch vụ          ┃                              ┃        0. Quay lại           ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("Lựa chọn của bạn: ");
            String input = scanner.nextLine();
            if (!ValidationUtil.isNumber(input)) {
                System.out.println("Vui lòng nhập số hợp lệ");
                continue;
            }
            int choice = Integer.parseInt(input);

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

        System.out.println("===== DANH SÁCH DỊCH VỤ =====");
        for (Service s : list) {
            System.out.println(
                    s.getId() + " | " +
                            s.getName() + " | " +
                            s.getPrice()
            );
        }
    }

    private void addService() {
        System.out.print("Tên dịch vụ: ");
        String name = scanner.nextLine();

        while (ValidationUtil.isEmpty(name)) {
            System.out.println("Tên không được để trống!");
            System.out.print("Nhập lại: ");
            name = scanner.nextLine();
        }

        double price;
        while (true) {
            System.out.print("Giá (>0): ");
            String priceInput = scanner.nextLine();

            if (!ValidationUtil.isNumber(priceInput)) {
                System.out.println("Giá phải là số!");
                continue;
            }

            price = Double.parseDouble(priceInput);

            if (price <= 0) {
                System.out.println("Giá phải > 0!");
                continue;
            }

            break;
        }

        boolean success = adminService.addService(
                new Service(0, name.trim(), price)
        );

        if (success) {
            System.out.println("Thêm dịch vụ thành công!");
        } else {
            System.out.println("Thêm thất bại! (Có thể trùng tên)");
        }
    }

    private void updateService() {
        viewServices();

        int id = inputNumber("Nhập ID cần sửa: ");

        System.out.print("Tên mới: ");
        String name = scanner.nextLine();

        while (ValidationUtil.isEmpty(name)) {
            System.out.println("Tên không được để trống!");
            System.out.print("Nhập lại: ");
            name = scanner.nextLine();
        }

        double price;
        while (true) {
            System.out.print("Giá mới (>0): ");
            String priceInput = scanner.nextLine();

            if (!ValidationUtil.isNumber(priceInput)) {
                System.out.println("Giá phải là số!");
                continue;
            }

            price = Double.parseDouble(priceInput);

            if (price <= 0) {
                System.out.println("Giá phải > 0!");
                continue;
            }

            break;
        }

        boolean success = adminService.updateService(
                new Service(id, name.trim(), price)
        );

        if (success) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại! (ID không tồn tại / trùng tên / dữ liệu sai)");
        }
    }

    private void deleteService() {
        viewServices();

        int id = inputNumber("Nhập ID cần xóa: ");

        boolean success = adminService.deleteService(id);

        if (success) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Xóa thất bại! (Không tồn tại hoặc đang được sử dụng)");
        }
    }

    private void userMenu() {
        while (true) {
            System.out.println();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                   QUẢN LÝ NGƯỜI DÙNG                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃  1. Xem tất cả người dùng    ┃ 2. Thêm tài khoản SUPPORT    ┃        0. Quay lại           ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("Lựa chọn của bạn: ");
            String input = scanner.nextLine();
            if (!ValidationUtil.isNumber(input)) {
                System.out.println("Vui lòng nhập số hợp lệ");
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
            System.out.println();
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                      QUẢN LÝ BOOKING                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃   1. Xem booking PENDING     ┃ 2. Duyệt / Từ chối booking   ┃  3. Xem booking đã duyệt     ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┃ 4. Phân công NV hỗ trợ       ┃                              ┃        0. Quay lại           ┃");
            System.out.println("┃                              ┃                              ┃                              ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("Lựa chọn của bạn: ");
            String input = scanner.nextLine();
            if (!ValidationUtil.isNumber(input)) {
                System.out.println("Vui lòng nhập số hợp lệ");
                continue;
            }
            int choice = Integer.parseInt(input);

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