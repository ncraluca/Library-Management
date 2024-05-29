package services;


import java.sql.*;

public class ConnectionDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "1213.Ralu";

    private static Connection databaseConnection;

    private ConnectionDB() {
    }

    public static Connection getDatabaseConnection() {
        try {
            if (databaseConnection == null || databaseConnection.isClosed()) {
                databaseConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return databaseConnection;
    }

    public static void closeDatabaseConnection() {
        try {
            if (databaseConnection != null && !databaseConnection.isClosed()) {
                databaseConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // testam
    public static void testConnection() {
        Connection conn = getDatabaseConnection();
        try (Statement stmt = conn.createStatement()) {
            // Creează un tabel temporar
            String createTableSQL = "CREATE TEMPORARY TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, name VARCHAR(50))";
            stmt.execute(createTableSQL);
            System.out.println("Tabelul temporar a fost creat.");

            // Inserează date în tabelul temporar
            String insertDataSQL = "INSERT INTO test_table (id, name) VALUES (1, 'Test Name')";
            stmt.execute(insertDataSQL);
            System.out.println("Datele au fost inserate.");

            // Selectează datele din tabelul temporar
            String selectDataSQL = "SELECT * FROM test_table";
            ResultSet rs = stmt.executeQuery(selectDataSQL);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }
    }

    public static void main(String[] args) {
        testConnection();
    }
}
