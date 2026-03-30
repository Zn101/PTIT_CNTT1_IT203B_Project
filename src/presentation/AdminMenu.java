package ra.edu.ra.edu.src.presentation;

import ra.edu.ra.edu.src.service.AdminService;
import ra.edu.ra.edu.src.model.Room;
import ra.edu.ra.edu.src.model.Equipment;
import ra.edu.ra.edu.src.model.User;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private Scanner scanner = new Scanner(System.in);
    private AdminService adminService = new AdminService();

    public void showMenu() {
        while (true) {
            System.out.println("===== Menu Admin =====");
            System.out.println("1. Quản lý phòng họp");
            System.out.println("2. Quản lý thiết bị");
            System.out.println("3. Quản lý người dùng");
            System.out.println("4. Duyệt Bookings");
            System.out.println("0. Đăng xuất");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    roomMenu();
                    break;
                case 2:
                    equipmentMenu();
                    break;
                case 3:
                    userMenu();
                    break;
                case 4:
                    approveBooking();
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
            System.out.println("===== Quản lý phòng =====");
            System.out.println("1. Thêm phòng");
            System.out.println("2. Xem phòng");
            System.out.println("3. Cập nhật phòng");
            System.out.println("4. Xóa phòng");
            System.out.println("0. Quay lại Menu Admin");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    viewRooms();
                    break;
                case 3:
                    updateRoom();
                    break;
                case 4:
                    deleteRoom();
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
            System.out.println("===== Quản lý thiết bị =====");
            System.out.println("1. Xem thiết bị");
            System.out.println("2. Sửa thông tin thiết bị");
            System.out.println("0. Quay lại Menu Admin");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    viewEquipments();
                    break;
                case 2:
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

    private void userMenu() {
        while (true) {
            System.out.println("===== Quản lý người dùng =====");
            System.out.println("1. Thêm nhân viên hỗ trợ");
            System.out.println("2. Xem người dùng");
            System.out.println("0. Quay lại Menu Admin");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    createSupport();
                    break;
                case 2:
                    viewUsers();
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

    private void approveBooking() {
        System.out.println("WIP");
        // TODO: implement
    }
}
