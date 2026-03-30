package ra.edu.ra.edu.src.DAO.Equipment;

import ra.edu.ra.edu.src.model.Equipment;
import java.util.List;

public interface IEquipmentDAO {
    List<Equipment> findAll();
    void updateQuantity(int id, int qty);
}