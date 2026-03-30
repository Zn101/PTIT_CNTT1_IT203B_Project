package ra.edu.ra.edu.src.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/meeting_management";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private static Connection connection;

    private JDBCConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to database");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return connection;
    }
}