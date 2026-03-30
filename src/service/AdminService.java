package ra.edu.ra.edu.src.service;

import ra.edu.ra.edu.src.DAO.Equipment.EquipmentDAOImpl;
import ra.edu.ra.edu.src.DAO.Equipment.IEquipmentDAO;
import ra.edu.ra.edu.src.DAO.Room.IRoomDAO;
import ra.edu.ra.edu.src.DAO.Room.RoomDAOImpl;
import ra.edu.ra.edu.src.DAO.User.IUserDAO;
import ra.edu.ra.edu.src.DAO.User.UserDAOImpl;
import ra.edu.ra.edu.src.model.*;
import java.util.ArrayList;
import java.util.List;

import ra.edu.ra.edu.src.model.Room;
import ra.edu.ra.edu.src.model.Equipment;
import ra.edu.ra.edu.src.model.User;

import java.util.List;

public class AdminService {

    private IRoomDAO roomDAO = new RoomDAOImpl();
    private IEquipmentDAO equipmentDAO = new EquipmentDAOImpl();
    private IUserDAO userDAO = new UserDAOImpl();

    // ROOM
    public void addRoom(Room room) {
        roomDAO.insert(room);
    }

    public List<Room> getAllRooms() {
        return roomDAO.findAll();
    }

    public void updateRoom(Room room) {
        roomDAO.update(room);
    }

    public void deleteRoom(int id) {
        roomDAO.delete(id);
    }

    // EQUIPMENT
    public List<Equipment> getAllEquipments() {
        return equipmentDAO.findAll();
    }

    public void updateEquipmentQuantity(int id, int qty) {
        equipmentDAO.updateQuantity(id, qty);
    }

    // USER
    public void createUser(User user) {
        userDAO.insert(user);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
}