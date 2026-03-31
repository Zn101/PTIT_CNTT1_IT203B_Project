package ra.edu.ra.edu.src.DAO.Equipment;

import ra.edu.ra.edu.src.model.Equipment;
import ra.edu.ra.edu.src.util.JDBCConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAOImpl implements IEquipmentDAO {

    // ================= FIND ALL =================
    @Override
    public List<Equipment> findAll() {
        List<Equipment> list = new ArrayList<>();
        String sql = "SELECT * FROM equipments";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= FIND BY ID =================
    @Override
    public Equipment findById(int id) {
        String sql = "SELECT * FROM equipments WHERE id=?";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ================= INSERT =================
    @Override
    public void insert(Equipment equipment) {
        String sql = "INSERT INTO equipments(name, total_quantity, available_quantity, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, equipment.getName());
            ps.setInt(2, equipment.getTotalQuantity());
            ps.setInt(3, equipment.getAvailableQuantity());
            ps.setString(4, equipment.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= UPDATE =================
    @Override
    public void update(Equipment equipment) {
        String sql = "UPDATE equipments SET name=?, total_quantity=?, available_quantity=?, status=? WHERE id=?";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, equipment.getName());
            ps.setInt(2, equipment.getTotalQuantity());
            ps.setInt(3, equipment.getAvailableQuantity());
            ps.setString(4, equipment.getStatus());
            ps.setInt(5, equipment.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= UPDATE QUANTITY =================
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

    // ================= DELETE =================
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM equipments WHERE id=?";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= HELPER =================
    private Equipment map(ResultSet rs) throws SQLException {
        return new Equipment(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("total_quantity"),
                rs.getInt("available_quantity"),
                rs.getString("status")
        );
    }
}