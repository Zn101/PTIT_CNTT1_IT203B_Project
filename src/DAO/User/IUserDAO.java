package ra.edu.ra.edu.src.DAO.User;

import ra.edu.ra.edu.src.model.User;
import java.util.List;

public interface IUserDAO {

    void insert(User user);

    User findByUsername(String username);

    User findById(int id);

    List<User> findAll();

    void update(User user);

    void delete(int id);
}