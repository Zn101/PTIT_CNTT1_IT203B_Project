package ra.edu.ra.edu.src.service;

import ra.edu.ra.edu.src.DAO.Booking.IBookingDAO;
import ra.edu.ra.edu.src.DAO.Booking.BookingDAOImpl;
import ra.edu.ra.edu.src.DAO.Room.RoomDAOImpl;
import ra.edu.ra.edu.src.DAO.User.IUserDAO;
import ra.edu.ra.edu.src.DAO.User.UserDAOImpl;
import ra.edu.ra.edu.src.model.Booking;
import ra.edu.ra.edu.src.model.Room;
import ra.edu.ra.edu.src.model.User;
import ra.edu.ra.edu.src.util.NotificationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingService {

    private IBookingDAO bookingDAO;
    private IUserDAO userDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAOImpl();
        this.userDAO = new UserDAOImpl();
    }

    // ================= CREATE =================
    public boolean createBooking(int userId, int roomId,
                                 LocalDateTime start, LocalDateTime end, int people) {

        if (start.isBefore(LocalDateTime.now())) return false;
        if (!start.isBefore(end)) return false;

        List<Booking> list = bookingDAO.findByRoomId(roomId);

        for (Booking b : list) {
            if ("APPROVED".equals(b.getStatus()) &&
                    isConflict(start, end, b.getStartTime(), b.getEndTime())) {
                return false;
            }
        }

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setRoomId(roomId);
        booking.setStartTime(start);
        booking.setEndTime(end);
        booking.setStatus("PENDING");

        bookingDAO.insert(booking);

        return true;
    }

    // ================= ADMIN =================

    // Approve booking (có check conflict)
    public boolean approveBooking(int bookingId, int supportId) {
        Booking booking = bookingDAO.findById(bookingId);
        if (booking == null) return false;

        List<Booking> list = bookingDAO.findByRoomId(booking.getRoomId());

        for (Booking b : list) {
            if (b.getId() != bookingId &&
                    "APPROVED".equals(b.getStatus()) &&
                    isConflict(booking.getStartTime(), booking.getEndTime(),
                            b.getStartTime(), b.getEndTime())) {
                return false;
            }
        }

        bookingDAO.updateStatus(bookingId, "APPROVED");
        bookingDAO.assignSupport(bookingId, supportId);
        bookingDAO.updatePrepareStatus(bookingId, "PREPARING");

        NotificationUtil.notify("Booking " + bookingId + " APPROVED");

        return true;
    }

    // Reject booking
    public boolean rejectBooking(int bookingId) {
        Booking booking = bookingDAO.findById(bookingId);
        if (booking == null) return false;

        bookingDAO.updateStatus(bookingId, "REJECTED");

        NotificationUtil.notify("Booking " + bookingId + " REJECTED");
        return true;
    }

    // Assign support (task riêng của bạn)
    public boolean assignSupport(int bookingId, int supportId) {
        Booking booking = bookingDAO.findById(bookingId);

        if (booking == null || !"APPROVED".equals(booking.getStatus())) {
            return false;
        }

        User support = userDAO.findById(supportId);

        if (support == null || !"SUPPORT".equals(support.getRole())) {
            return false;
        }

        bookingDAO.assignSupport(bookingId, supportId);

        NotificationUtil.notify("Support assigned to booking " + bookingId);
        return true;
    }

    // ================= GET DATA =================

    public List<Booking> getPendingBookings() {
        return bookingDAO.findAllPending();
    }

    public List<Booking> getApprovedBookings() {
        return bookingDAO.findByStatus("APPROVED");
    }

    public Booking findById(int id) {
        return bookingDAO.findById(id);
    }

    // ================= SUPPORT =================

    public List<Booking> getBookingsBySupport(int supportId) {
        return bookingDAO.findBySupportId(supportId);
    }

    public void updatePrepareStatus(int bookingId, String status) {
        bookingDAO.updatePrepareStatus(bookingId, status);
        NotificationUtil.notify("Booking " + bookingId + " updated to " + status);
    }

    // ================= EMPLOYEE =================

    public List<Booking> getBookingsByUser(int userId) {
        return bookingDAO.findByUserId(userId);
    }

    public boolean cancelBooking(int bookingId, int userId) {
        List<Booking> list = bookingDAO.findByUserId(userId);

        for (Booking b : list) {
            if (b.getId() == bookingId && "PENDING".equals(b.getStatus())) {
                bookingDAO.cancelBooking(bookingId);
                return true;
            }
        }

        return false;
    }

    // ================= ANALYZE =================

    public void analyzeRooms() {
        List<Booking> list = bookingDAO.findAll();

        Map<Integer, Integer> countMap = new HashMap<>();

        for (Booking b : list) {
            countMap.put(b.getRoomId(),
                    countMap.getOrDefault(b.getRoomId(), 0) + 1);
        }

        int maxRoom = -1, minRoom = -1;
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;

        for (var entry : countMap.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maxRoom = entry.getKey();
            }
            if (entry.getValue() < min) {
                min = entry.getValue();
                minRoom = entry.getKey();
            }
        }

        System.out.println("Room dùng nhiều nhất: " + maxRoom);
        System.out.println("Room dùng ít nhất: " + minRoom);
    }

    // ================= CONFLICT =================

    private boolean isConflict(LocalDateTime newStart, LocalDateTime newEnd,
                               LocalDateTime oldStart, LocalDateTime oldEnd) {

        return newStart.isBefore(oldEnd) && newEnd.isAfter(oldStart);
    }

    public List<Room> getAvailableRooms(LocalDateTime start, LocalDateTime end) {
        List<Room> allRooms = new RoomDAOImpl().findAll();
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : allRooms) {
            List<Booking> bookings = bookingDAO.findByRoomId(room.getId());

            boolean isFree = true;

            for (Booking b : bookings) {
                if ("APPROVED".equals(b.getStatus()) &&
                        isConflict(start, end, b.getStartTime(), b.getEndTime())) {
                    isFree = false;
                    break;
                }
            }

            if (isFree) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    public boolean createFullBooking(int userId, int roomId,
                                     LocalDateTime start, LocalDateTime end,
                                     int people,
                                     String equipmentIds,
                                     String serviceIds) {

        boolean created = createBooking(userId, roomId, start, end, people);

        if (!created) return false;

        // TODO: nếu có bảng trung gian thì insert ở đây
        // booking_equipment, booking_service

        NotificationUtil.notify("Booking created with equipment & services");

        return true;
    }

    public void updateProfile(User user) {
        userDAO.update(user);
    }
}