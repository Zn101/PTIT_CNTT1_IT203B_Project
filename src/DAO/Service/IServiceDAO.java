package ra.edu.ra.edu.src.DAO.Service;

import ra.edu.ra.edu.src.model.Service;
import java.util.List;

public interface IServiceDAO {
    List<Service> findAll();
    Service findById(int id);
    void insert(Service service);
    void update(Service service);
    boolean delete(int id);
}
