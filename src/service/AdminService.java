package ra.edu.ra.edu.src.service;

import ra.edu.ra.edu.src.DAO.Booking.BookingDAOImpl;
import ra.edu.ra.edu.src.DAO.Booking.IBookingDAO;
import ra.edu.ra.edu.src.DAO.Equipment.EquipmentDAOImpl;
import ra.edu.ra.edu.src.DAO.Equipment.IEquipmentDAO;
import ra.edu.ra.edu.src.DAO.Room.IRoomDAO;
import ra.edu.ra.edu.src.DAO.Room.RoomDAOImpl;
import ra.edu.ra.edu.src.DAO.Service.IServiceDAO;
import ra.edu.ra.edu.src.DAO.Service.ServiceDAOImpl;
import ra.edu.ra.edu.src.DAO.User.IUserDAO;
import ra.edu.ra.edu.src.DAO.User.UserDAOImpl;
import ra.edu.ra.edu.src.model.*;

import java.util.List;

public class AdminService {
    private IBookingDAO bookingDAO = new BookingDAOImpl();
    private IRoomDAO roomDAO = new RoomDAOImpl();
    private IEquipmentDAO equipmentDAO = new EquipmentDAOImpl();
    private IUserDAO userDAO = new UserDAOImpl();

    // ================= ROOM =================
    public boolean addRoom(Room room) {
        List<Room> list = roomDAO.searchByName(room.getName());

        if (!list.isEmpty()) {
            return false;
        }

        roomDAO.insert(room);
        return true;
    }

    public List<Room> getAllRooms() {
        return roomDAO.findAll();
    }

    public boolean updateRoom(Room room) {
        List<Room> list = roomDAO.searchByName(room.getName());

        for (Room r : list) {
            if (r.getId() != room.getId()) {
                return false;
            }
        }

        roomDAO.update(room);
        return true;
    }

    public boolean deleteRoom(int id) {
        Room existing = roomDAO.findById(id);
        if (existing == null) {
            System.out.println("Phòng không tồn tại!");
            return false;
        }

        List<Booking> bookings = BookingService.findByRoomId(id);

        if (!bookings.isEmpty()) {
            System.out.println("Không thể xóa! Phòng đang có booking.");
            return false;
        }

        boolean result = roomDAO.delete(id);

        if (result) {
            System.out.println("Xóa phòng thành công!");
        } else {
            System.out.println("Xóa thất bại!");
        }

        return result;
    }

    public List<Room> searchRoomByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword không được để trống");
        }

        return roomDAO.searchByName(keyword.trim());
    }

    // ================= EQUIPMENT =================
    public List<Equipment> getAllEquipments() {
        return equipmentDAO.findAll();
    }

    public boolean addEquipment(Equipment equipment) {

        if (equipment.getName() == null || equipment.getName().trim().isEmpty()) {
            return false;
        }

        List<Equipment> list = equipmentDAO.findAll();
        for (Equipment e : list) {
            if (e.getName().equalsIgnoreCase(equipment.getName().trim())) {
                return false;
            }
        }

        if (equipment.getTotalQuantity() <= 0) return false;

        if (equipment.getAvailableQuantity() < 0 ||
                equipment.getAvailableQuantity() > equipment.getTotalQuantity()) {
            return false;
        }

        equipmentDAO.insert(equipment);
        return true;
    }

    public boolean updateEquipment(int id, int qty) {
        Equipment existing = equipmentDAO.findById(id);
        if (existing == null) return false;

        if (qty < 0 || qty > existing.getTotalQuantity()) {
            return false;
        }

        equipmentDAO.updateQuantity(id, qty);
        return true;
    }

    public boolean deleteEquipment(int id) {

        // 1. Check tồn tại
        Equipment existing = equipmentDAO.findById(id);
        if (existing == null) return false;

        return equipmentDAO.delete(id);
    }

    public boolean updateQuantity(int id, int qty) {

        Equipment existing = equipmentDAO.findById(id);
        if (existing == null) return false;

        if (qty < 0 || qty > existing.getTotalQuantity()) return false;

        equipmentDAO.updateQuantity(id, qty);
        return true;
    }

    // ================= USER =================

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    // ================= SERVICE (dịch vụ đi kèm) =================

    private IServiceDAO serviceDAO = new ServiceDAOImpl();

    public List<Service> getAllServices() {
        return serviceDAO.findAll();
    }

    public Service getServiceById(int id) {
        return serviceDAO.findById(id);
    }

    public boolean addService(Service service) {
        if (service == null) return false;

        // validate name
        if (service.getName() == null || service.getName().trim().isEmpty()) {
            return false;
        }

        List<Service> list = serviceDAO.findAll();
        for (Service s : list) {
            if (s.getName().equalsIgnoreCase(service.getName().trim())) {
                return false;
            }
        }

        // validate price
        if (service.getPrice() < 0) {
            return false;
        }

        serviceDAO.insert(service);
        return true;
    }

    public boolean updateService(Service service) {
        if (service == null) return false;

        Service existing = serviceDAO.findById(service.getId());
        if (existing == null) return false;

        // validate name
        if (service.getName() == null || service.getName().trim().isEmpty()) {
            return false;
        }

        // check trùng tên (trừ chính nó)
        List<Service> list = serviceDAO.findAll();
        for (Service s : list) {
            if (s.getId() != service.getId() &&
                    s.getName().equalsIgnoreCase(service.getName().trim())) {
                return false;
            }
        }

        // validate price
        if (service.getPrice() < 0) {
            return false;
        }

        serviceDAO.update(service);
        return true;
    }

    public boolean deleteService(int id) {
        Service existing = serviceDAO.findById(id);
        if (existing == null) return false;

        // (Optional nâng cao)
        // Có thể check nếu service đang được dùng trong booking → không cho xóa

        return serviceDAO.delete(id);
    }
}