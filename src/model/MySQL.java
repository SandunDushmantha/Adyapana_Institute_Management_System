package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

    private static Connection connection;

    public static void createConnection() throws Exception {
        if (connection == null || connection.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/adyapana_institute", "root", "Sandun@2004#");
        }

    }
    // Get the connection object

    public static Connection getConnection() throws SQLException, Exception {
        createConnection();  // Ensure the connection is created
        return connection;    // Return the connection
    }

    public static ResultSet executeSearch(String query) throws Exception {
        createConnection();
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    public static Integer executeIUD(String query) throws Exception {
        createConnection();
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(query);
        }
    }

}
