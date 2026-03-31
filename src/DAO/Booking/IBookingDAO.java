package ra.edu.ra.edu.src.DAO.Booking;

import ra.edu.ra.edu.src.model.Booking;
import java.util.List;

public interface IBookingDAO {

    void insert(Booking booking);

    List<Booking> findByRoomId(int roomId);

    // ===== DAY 4 =====
    List<Booking> findAllPending();

    void updateStatus(int bookingId, String status);

    void assignSupport(int bookingId, int supportId);

    void updatePrepareStatus(int bookingId, String prepareStatus);

    List<Booking> findBySupportId(int supportId);

    List<Booking> findByUserId(int userId);

    List<Booking> findAll();

    void cancelBooking(int bookingId);

    Booking findById(int bookingId);

    List<Booking> findByStatus(String approved);
}