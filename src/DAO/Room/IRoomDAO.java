package ra.edu.ra.edu.src.DAO.Room;

import ra.edu.ra.edu.src.model.Room;
import java.util.List;

public interface IRoomDAO {
    void insert(Room room);
    List<Room> findAll();
    void update(Room room);
    boolean delete(int id);
}