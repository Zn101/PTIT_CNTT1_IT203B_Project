package ra.edu.ra.edu.src.service;

import ra.edu.ra.edu.src.model.User;

import ra.edu.ra.edu.src.DAO.User.IUserDAO;
import ra.edu.ra.edu.src.DAO.User.UserDAOImpl;
import ra.edu.ra.edu.src.util.PasswordHash;

public class AuthService {
    private IUserDAO userDAO;
    public AuthService() {
        this.userDAO = new UserDAOImpl();
    }
    public boolean register(User user) {
        User existingUser = userDAO.findByUsername(user.getUsername());
        if (existingUser != null) {
            return false;
        }
        String hashedPassword = PasswordHash.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userDAO.insert(user);
        return true;
    }
    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return null;
        }
        boolean isMatch = PasswordHash.checkPassword(password, user.getPassword());
        if (isMatch) {
            return user;
        }
        return null;
    }
}