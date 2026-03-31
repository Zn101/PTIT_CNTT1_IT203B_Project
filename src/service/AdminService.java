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

    public boolean deleteRoom(int id) {
        return roomDAO.delete(id);
    }

    // ================= EQUIPMENT =================

    public List<Equipment> getAllEquipments() {
        return equipmentDAO.findAll();
    }

    public Equipment getEquipmentById(int id) {
        return equipmentDAO.findById(id);
    }

    public void addEquipment(Equipment equipment) {
        equipmentDAO.insert(equipment);
    }

    public boolean updateEquipment(Equipment equipment) {
        Equipment existing = equipmentDAO.findById(equipment.getId());
        if (existing == null) return false;

        equipmentDAO.update(equipment);
        return true;
    }

    public boolean updateEquipmentQuantity(int id, int qty) {
        Equipment existing = equipmentDAO.findById(id);
        if (existing == null) return false;

        if (qty < 0 || qty > existing.getTotalQuantity()) return false;

        equipmentDAO.updateQuantity(id, qty);
        return true;
    }

    public boolean deleteEquipment(int id) {
        Equipment existing = equipmentDAO.findById(id);
        if (existing == null) return false;

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