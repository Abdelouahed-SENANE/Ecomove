package ma.youcode.transport.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final Connection connection;
    private static Database instance;
    private static final String URL = "jdbc:postgresql://localhost:5432/db_transport";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";

    // Private constructor
    private Database() throws SQLException {
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            // Establish the connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found.", e);
        } catch (SQLException e) {
            throw new SQLException("Error connecting to the database", e);
        }
    }

    // Get the database connection
    public Connection getConnection() {
        return connection;
    }

    // Singleton pattern for Database instance
    public static  Database getInstance()  {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Database();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;

    }
}
