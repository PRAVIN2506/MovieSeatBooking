import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingDAO {

    public boolean saveBooking(String movieTitle, String seats, int totalAmount, String userName, String userEmail) throws SQLException {
        String sql = "INSERT INTO bookings (movie_title, seats, total_amount, user_name, user_email, booking_time) " +
                "VALUES (?, ?, ?, ?, ?, NOW())";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, movieTitle);
            stmt.setString(2, seats);
            stmt.setInt(3, totalAmount);
            stmt.setString(4, userName);
            stmt.setString(5, userEmail);
            return stmt.executeUpdate() > 0;
        }
    }
}
