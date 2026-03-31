package ra.edu.ra.edu.src.DAO.Booking;

import ra.edu.ra.edu.src.model.Booking;
import ra.edu.ra.edu.src.util.JDBCConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements IBookingDAO {

    // ================= INSERT =================
    @Override
    public void insert(Booking booking) {
        String sql = "INSERT INTO bookings(user_id, room_id, start_time, end_time, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getRoomId());
            ps.setTimestamp(3, Timestamp.valueOf(booking.getStartTime()));
            ps.setTimestamp(4, Timestamp.valueOf(booking.getEndTime()));
            ps.setString(5, booking.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= FIND BY ID =================
    @Override
    public Booking findById(int id) {
        String sql = "SELECT * FROM bookings WHERE id=?";

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

    // ================= FIND BY ROOM =================
    @Override
    public List<Booking> findByRoomId(int roomId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE room_id=? AND status!='REJECTED'";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= FIND BY STATUS =================
    @Override
    public List<Booking> findByStatus(String status) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE status=?";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= FIND ALL PENDING =================
    @Override
    public List<Booking> findAllPending() {
        return findByStatus("PENDING");
    }

    // ================= UPDATE STATUS =================
    @Override
    public void updateStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET status=? WHERE id=?";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, bookingId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= ASSIGN SUPPORT =================
    @Override
    public void assignSupport(int bookingId, int supportId) {
        String sql = "UPDATE bookings SET support_id=? WHERE id=?";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, supportId);
            ps.setInt(2, bookingId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= UPDATE PREPARE STATUS =================
    @Override
    public void updatePrepareStatus(int bookingId, String prepareStatus) {
        String sql = "UPDATE bookings SET prepare_status=? WHERE id=?";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, prepareStatus);
            ps.setInt(2, bookingId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= FIND BY SUPPORT =================
    @Override
    public List<Booking> findBySupportId(int supportId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE support_id=?";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, supportId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= FIND BY USER =================
    @Override
    public List<Booking> findByUserId(int userId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id=?";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= FIND ALL =================
    @Override
    public List<Booking> findAll() {
        return findByStatus("APPROVED");
    }

    // ================= CANCEL =================
    @Override
    public void cancelBooking(int bookingId) {
        String sql = "UPDATE bookings SET status='REJECTED' WHERE id=? AND status='PENDING'";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= MAP =================
    private Booking map(ResultSet rs) throws SQLException {
        Booking b = new Booking();

        b.setId(rs.getInt("id"));
        b.setUserId(rs.getInt("user_id"));
        b.setRoomId(rs.getInt("room_id"));
        b.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
        b.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
        b.setStatus(rs.getString("status"));

        // xử lý NULL support_id
        int supportId = rs.getInt("support_id");
        if (rs.wasNull()) {
            b.setSupportId(null);
        } else {
            b.setSupportId(supportId);
        }

        b.setPrepare_status(rs.getString("prepare_status"));

        return b;
    }
}