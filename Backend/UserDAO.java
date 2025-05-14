import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean isUsernameExists(String username) throws SQLException {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean createUser(String username, String email, String password) throws SQLException {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
<<<<<<< HEAD
            stmt.setString(3, password); // For real apps, hash passwords before storing
=======
            stmt.setString(3, password);
>>>>>>> 1672aae (completed)
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean validateUser(String username, String password) throws SQLException {
        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
<<<<<<< HEAD
            stmt.setString(2, password); // Again, compare hashed passwords in real use
=======
            stmt.setString(2, password);
>>>>>>> 1672aae (completed)
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
