package ra.edu.ra.edu.src.service;

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

    private IRoomDAO roomDAO = new RoomDAOImpl();
    private IEquipmentDAO equipmentDAO = new EquipmentDAOImpl();
    private IUserDAO userDAO = new UserDAOImpl();

    // ================= ROOM =================

    public void addRoom(Room room) {
        roomDAO.insert(room);
    }

    public List<Room> getAllRooms() {
        return roomDAO.findAll();
    }

    public void updateRoom(Room room) {
        roomDAO.update(room);
    }

    public boolean deleteRoom(int id) { return roomDAO.delete(id); }

    public List<Room> searchRoomByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword cannot be empty");
        }
        return roomDAO.searchByName(keyword);
    }

    // ================= EQUIPMENT =================

    // Thêm thiết bị
    public void addEquipment(Equipment e) {
        equipmentDAO.insert(e);
    }

    // Lấy tất cả
    public List<Equipment> getAllEquipments() {
        return equipmentDAO.findAll();
    }

    // Tìm theo ID
    public Equipment findEquipmentById(int id) {
        return equipmentDAO.findById(id);
    }

    // Update full
    public void updateEquipment(Equipment e) {
        equipmentDAO.update(e);
    }

    // Update số lượng
    public void updateEquipmentQuantity(int id, int qty) {
        equipmentDAO.updateQuantity(id, qty);
    }

    // Xóa (có check)
    public boolean deleteEquipment(int id) {
        Equipment e = equipmentDAO.findById(id);

        if (e == null) {
            return false;
        }

        return equipmentDAO.delete(id);
    }

    // ================= USER =================

    public void createUser(User user) {
        userDAO.insert(user);
    }

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

    public void addService(Service service) {
        serviceDAO.insert(service);
    }

    public boolean updateService(Service service) {
        Service existing = serviceDAO.findById(service.getId());
        if (existing == null) return false;

        if (service.getPrice() < 0) return false;

        serviceDAO.update(service);
        return true;
    }

    public boolean deleteService(int id) {
        Service existing = serviceDAO.findById(id);
        if (existing == null) return false;

        return serviceDAO.delete(id);
    }
}