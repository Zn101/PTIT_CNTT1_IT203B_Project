package ra.edu.ra.edu.src.DAO.Equipment;

import ra.edu.ra.edu.src.model.Equipment;
import ra.edu.ra.edu.src.util.JDBCConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAOImpl implements IEquipmentDAO {

    @Override
    public List<Equipment> findAll() {
        List<Equipment> list = new ArrayList<>();
        String sql = "SELECT * FROM equipments";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Equipment(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("total_quantity"),
                        rs.getInt("available_quantity"),
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void updateQuantity(int id, int qty) {
        String sql = "UPDATE equipments SET available_quantity=? WHERE id=?";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, id);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}