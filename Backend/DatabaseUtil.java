import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/moviedb";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "2506";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL driver

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "2506";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }
}