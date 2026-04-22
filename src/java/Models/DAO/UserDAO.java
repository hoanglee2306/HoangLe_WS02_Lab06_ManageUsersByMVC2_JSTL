package Models.DAO;

import Models.DTO.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = System.getenv().getOrDefault(
            "APP_DB_URL",
            "jdbc:sqlserver://localhost:1433;databaseName=SampleDB;encrypt=true;trustServerCertificate=true"
    );
    private static final String DB_USER = System.getenv("APP_DB_USER");
    private static final String DB_PASSWORD = System.getenv("APP_DB_PASSWORD");

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        if (DB_USER == null || DB_PASSWORD == null) {
            throw new SQLException("Database credentials are not configured. Set APP_DB_USER and APP_DB_PASSWORD environment variables.");
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public User login(String userName, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT userName, password, lastName, isAdmin FROM Registration WHERE userName = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("userName"),
                            rs.getString("password"),
                            rs.getString("lastName"),
                            rs.getBoolean("isAdmin")
                    );
                }
            }
        }
        return null;
    }

    public List<User> searchUserByLastName(String searchValue) throws SQLException, ClassNotFoundException {
        List<User> result = new ArrayList<>();
        String sql = "SELECT userName, password, lastName, isAdmin FROM Registration WHERE lastName LIKE ? ESCAPE '\\\\' ORDER BY userName";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + escapeLikePattern(searchValue) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new User(
                            rs.getString("userName"),
                            rs.getString("password"),
                            rs.getString("lastName"),
                            rs.getBoolean("isAdmin")
                    ));
                }
            }
        }
        return result;
    }

    public User getUserByUserName(String userName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT userName, password, lastName, isAdmin FROM Registration WHERE userName = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("userName"),
                            rs.getString("password"),
                            rs.getString("lastName"),
                            rs.getBoolean("isAdmin")
                    );
                }
            }
        }
        return null;
    }

    public boolean addUser(User user) throws SQLException, ClassNotFoundException {
        if (getUserByUserName(user.getUserName()) != null) {
            return false;
        }
        String sql = "INSERT INTO Registration(userName, password, lastName, isAdmin) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getLastName());
            ps.setBoolean(4, user.isIsAdmin());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateUser(User user) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Registration SET password = ?, lastName = ?, isAdmin = ? WHERE userName = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getLastName());
            ps.setBoolean(3, user.isIsAdmin());
            ps.setString(4, user.getUserName());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(String userName) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Registration WHERE userName = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            return ps.executeUpdate() > 0;
        }
    }

    private String escapeLikePattern(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
    }
}
