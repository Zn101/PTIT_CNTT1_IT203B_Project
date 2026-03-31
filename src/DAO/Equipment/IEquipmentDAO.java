package ra.edu.ra.edu.src.DAO.Equipment;

import ra.edu.ra.edu.src.model.Equipment;
import java.util.List;

public interface IEquipmentDAO {
    List<Equipment> findAll();

    // ================= FIND BY ID =================
    Equipment findById(int id);

    // ================= INSERT =================
    void insert(Equipment equipment);

    // ================= UPDATE =================
    void update(Equipment equipment);

    void updateQuantity(int id, int qty);

    // ================= DELETE =================
    boolean delete(int id);
}