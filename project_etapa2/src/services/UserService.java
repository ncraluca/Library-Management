package services;

import entities.category.Category;
import entities.user.Employee;
import entities.user.Member;
import entities.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class UserService {

    private static UserService instanta = null;

    private final ArrayList<User> useri = new ArrayList<User>();
    private final ArrayList<Member> membri = new ArrayList<Member>();
    private final ArrayList<Employee> angajati = new ArrayList<Employee>();

    // constructor privat
    private UserService() {

    }

    public static UserService getInstance() {
        if (instanta == null)
            instanta = new UserService();
        return instanta;
    }
    public static void addMember(String firstName, String lastName, String email, String phoneNumber, Date birthDate, Date subscriptionStartDate, Date subscriptionEndDate) {
        try (Connection conn = ConnectionDB.getDatabaseConnection()) {
            String query = "INSERT INTO User (firstName, lastName, email, phoneNumber, birthDate, subscriptionStartDate, subscriptionEndDate, userType) VALUES (?, ?, ?, ?, ?, ?, ?, 'Member')";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, email);
                pstmt.setString(4, phoneNumber);
                pstmt.setDate(5, new java.sql.Date(birthDate.getTime()));
                pstmt.setDate(6, new java.sql.Date(subscriptionStartDate.getTime()));
                pstmt.setDate(7, new java.sql.Date(subscriptionEndDate.getTime()));
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addEmployee(String firstName, String lastName, String email, String phoneNumber, Date birthDate, int salary, Date hireDate) {
        try (Connection conn = ConnectionDB.getDatabaseConnection()) {
            String query = "INSERT INTO User (firstName, lastName, email, phoneNumber, birthDate, salary, hireDate, userType) VALUES (?, ?, ?, ?, ?, ?, ?, 'Employee')";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, email);
                pstmt.setString(4, phoneNumber);
                pstmt.setDate(5, new java.sql.Date(birthDate.getTime()));
                pstmt.setInt(6, salary);
                pstmt.setDate(7, new java.sql.Date(hireDate.getTime()));
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayUsers() {
        try (Connection conn = ConnectionDB.getDatabaseConnection()) {
            String query = "SELECT * FROM User";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    Date birthDate = rs.getDate("birthDate");
                    String userType = rs.getString("userType");

                    System.out.println("ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName + ", Email: " + email + ", Phone Number: " + phoneNumber + ", Birth Date: " + birthDate + ", User Type: " + userType);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayEmployees() {
        try (Connection conn = ConnectionDB.getDatabaseConnection()) {
            String query = "SELECT * FROM User WHERE userType = 'Employee'";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    Date birthDate = rs.getDate("birthDate");
                    int salary = rs.getInt("salary");
                    Date hireDate = rs.getDate("hireDate");

                    System.out.println("ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName + ", Email: " + email + ", Phone Number: " + phoneNumber + ", Birth Date: " + birthDate + ", Salary: " + salary + ", Hire Date: " + hireDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayMembers() {
        try (Connection conn = ConnectionDB.getDatabaseConnection()) {
            String query = "SELECT * FROM User WHERE userType = 'Member'";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    Date birthDate = rs.getDate("birthDate");
                    Date subscriptionStartDate = rs.getDate("subscriptionStartDate");
                    Date subscriptionEndDate = rs.getDate("subscriptionEndDate");

                    System.out.println("ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName + ", Email: " + email + ", Phone Number: " + phoneNumber + ", Birth Date: " + birthDate + ", Subscription Start Date: " + subscriptionStartDate + ", Subscription End Date: " + subscriptionEndDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEmployee(String firstName, String lastName) {
        String query = "DELETE FROM User WHERE firstName = ? AND lastName = ? AND userType = 'Employee'";
        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMember(String firstName, String lastName) {
        String getMemberIdSql = "SELECT id FROM User WHERE firstName = ? AND lastName = ? AND userType = 'Member'";
        String deleteLoansSql = "DELETE FROM Loan WHERE userId = ?";
        String deleteMemberSql = "DELETE FROM User WHERE firstName = ? AND lastName = ? AND userType = 'Member'";

        Connection connection = ConnectionDB.getDatabaseConnection();

        try (PreparedStatement getMemberIdStmt = connection.prepareStatement(getMemberIdSql)) {
            // Obține ID-ul membrului pe baza numelui și prenumelui
            getMemberIdStmt.setString(1, firstName);
            getMemberIdStmt.setString(2, lastName);
            ResultSet rs = getMemberIdStmt.executeQuery();

            if (rs.next()) {
                int memberId = rs.getInt("id");

                // Șterge împrumuturile asociate membrului
                try (PreparedStatement deleteLoansStmt = connection.prepareStatement(deleteLoansSql)) {
                    deleteLoansStmt.setInt(1, memberId);
                    deleteLoansStmt.executeUpdate();
                }

                // Șterge membrul
                try (PreparedStatement deleteMemberStmt = connection.prepareStatement(deleteMemberSql)) {
                    deleteMemberStmt.setString(1, firstName);
                    deleteMemberStmt.setString(2, lastName);
                    int rowsAffected = deleteMemberStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Membrul " + firstName + " " + lastName + " a fost șters.");
                    } else {
                        System.out.println("Membrul " + firstName + " " + lastName + " nu a fost găsit.");
                    }
                }
            } else {
                System.out.println("Membrul " + firstName + " " + lastName + " nu a fost găsit.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateEmployeeName(String currentFirstName, String currentLastName, String newFirstName, String newLastName) {
        String query = "UPDATE User SET firstName = ?, lastName = ? WHERE firstName = ? AND lastName = ? AND userType = 'Employee'";
        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newFirstName);
            pstmt.setString(2, newLastName);
            pstmt.setString(3, currentFirstName);
            pstmt.setString(4, currentLastName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMemberName(String currentFirstName, String currentLastName, String newFirstName, String newLastName) {
        String query = "UPDATE User SET firstName = ?, lastName = ? WHERE firstName = ? AND lastName = ? AND userType = 'Member'";
        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newFirstName);
            pstmt.setString(2, newLastName);
            pstmt.setString(3, currentFirstName);
            pstmt.setString(4, currentLastName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Integer getMemberIdByName(String firstName, String lastName) {
        String query = "SELECT id FROM User WHERE firstName = ? AND lastName = ? AND userType = 'Member'";
        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Returnează null dacă nu găsește membrul
    }


    public static Member getMemberById(int memberId) {
        String query = "SELECT * FROM User WHERE id = ? AND userType = 'Member'";
        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Member(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getDate("birthDate"),
                        rs.getDate("subscriptionStartDate"),
                        rs.getDate("subscriptionEndDate")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean doesMemberExist(String firstName, String lastName) {

        Integer memberId = getMemberIdByName(firstName, lastName);

        return memberId != null;
    }



}
