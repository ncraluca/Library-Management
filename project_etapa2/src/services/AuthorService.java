package services;

import abstracts.AbstractDBService;
import entities.author.Author;
import entities.book.Book;
import entities.category.Category;

import java.sql.*;
import java.util.ArrayList;

public class AuthorService extends AbstractDBService<Author> {


    private static AuthorService instanta = null;

    private final ArrayList<Author> autori = new ArrayList<Author>();

    // constructor privat
    private AuthorService() {

    }

    public static AuthorService getInstance() {
        if (instanta == null)
            instanta = new AuthorService();
        return instanta;
    }

    @Override
    protected Author mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        return new Author(id, firstName, lastName);
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO author (firstName, lastName) VALUES (?, ?)";
    }

    @Override
    protected void setInsertQueryParameters(PreparedStatement pstmt, Author author) throws SQLException {
        pstmt.setString(1, author.getFirstName());
        pstmt.setString(2, author.getLastName());
    }

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM author WHERE id = ?";
    }

    public void addAuthor(String prenume, String nume) {
        Author author = new Author(prenume, nume);
        create(author);

    }

    public void displayAuthors() {
        String selectSql = "SELECT * FROM author";

        Connection connection = ConnectionDB.getDatabaseConnection();

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(selectSql)) {
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt("id"));
                System.out.println("First Name: " + resultSet.getString("firstName"));
                System.out.println("Last Name: " + resultSet.getString("lastName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAuthor(String firstName, String lastName) {
        String deleteBooksSql = "DELETE FROM Book WHERE author_Id = (SELECT id FROM Author WHERE firstName = ? AND lastName = ?)";
        String deleteAuthorSql = "DELETE FROM Author WHERE firstName = ? AND lastName = ?";

        Connection connection = ConnectionDB.getDatabaseConnection();

        try {
            // Start transaction
            connection.setAutoCommit(false);

            // Delete books by author
            try (PreparedStatement pstmtBooks = connection.prepareStatement(deleteBooksSql)) {
                pstmtBooks.setString(1, firstName);
                pstmtBooks.setString(2, lastName);
                int booksDeleted = pstmtBooks.executeUpdate();
                System.out.println("Au fost șterse " + booksDeleted + " cărți asociate autorului " + firstName + " " + lastName + ".");
            }

            // Delete author
            try (PreparedStatement pstmtAuthor = connection.prepareStatement(deleteAuthorSql)) {
                pstmtAuthor.setString(1, firstName);
                pstmtAuthor.setString(2, lastName);
                int rowsAffected = pstmtAuthor.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Autorul " + firstName + " " + lastName + " a fost șters.");
                } else {
                    System.out.println("Autorul " + firstName + " " + lastName + " nu a fost găsit.");
                }
            }

            // Commit transaction
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Rollback transaction in case of error
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                // Restore auto-commit mode
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    private static Integer getAuthorIdByName(String firstName, String lastName) {
        String query = "SELECT id FROM author WHERE firstName = ? AND lastName = ?";
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
        return null;
    }

    public void updateAuthorName(String currentFirstName, String currentLastName, String newFirstName, String newLastName) {
        Integer authorId = getAuthorIdByName(currentFirstName, currentLastName);
        if (authorId != null) {
            String query = "UPDATE author SET firstName = ?, lastName = ? WHERE id = ?";
            try (Connection conn = ConnectionDB.getDatabaseConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, newFirstName);
                pstmt.setString(2, newLastName);
                pstmt.setInt(3, authorId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Autorul a fost actualizat cu succes.");
                } else {
                    System.out.println("Autorul nu a putut fi actualizat.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Autorul cu numele '" + currentFirstName + " " + currentLastName + "' nu a fost găsit.");
        }
    }

    public Author getAuthorByName(String firstName, String lastName) {
        String query = "SELECT * FROM author WHERE firstName = ? AND lastName = ?";
        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int authorId = rs.getInt("id");
                String firstN = rs.getString("firstName");
                String lastN = rs.getString("lastName");
                return new Author(authorId, firstN, lastN);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Author getAuthorById(int authorId) {
        AuthorService authorService = AuthorService.getInstance();
        return authorService.read(authorId);
    }


}
