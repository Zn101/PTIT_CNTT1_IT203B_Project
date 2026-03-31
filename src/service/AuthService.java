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

    public enum LoginStatus {
        SUCCESS,
        USER_NOT_FOUND,
        WRONG_PASSWORD
    }

    public boolean register(User user) {
        try {
            User existingUser = userDAO.findByUsername(user.getUsername());
            if (existingUser != null) {
                return false;
            }
            String hashedPassword = PasswordHash.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
            userDAO.insert(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đăng ký", e);
        }
    }

    public class LoginResult {
        private LoginStatus status;
        private User user;

        public LoginResult(LoginStatus status, User user) {
            this.status = status;
            this.user = user;
        }

        public LoginStatus getStatus() {
            return status;
        }

        public User getUser() {
            return user;
        }
    }

    public LoginResult login(String username, String password) {
        try {
            User user = userDAO.findByUsername(username);

            if (user == null) {
                return new LoginResult(LoginStatus.USER_NOT_FOUND, null);
            }

            boolean isMatch = PasswordHash.checkPassword(password, user.getPassword());

            if (!isMatch) {
                return new LoginResult(LoginStatus.WRONG_PASSWORD, null);
            }

            return new LoginResult(LoginStatus.SUCCESS, user);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đăng nhập", e);
        }
    }
}