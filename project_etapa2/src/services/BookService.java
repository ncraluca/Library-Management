package services;

import abstracts.AbstractDBService;
import entities.author.Author;
import entities.book.Book;
import entities.category.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BookService extends AbstractDBService<Book> {

    private static BookService instanta = null;

    private final ArrayList<Book> carti = new ArrayList<Book>();

    private BookService() {

    }

    public static BookService getInstance() {
        if (instanta == null)
            instanta = new BookService();
        return instanta;
    }

    @Override
    protected Book mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        int authorId = rs.getInt("author_id");
        int categoryId = rs.getInt("category_id");
        int publicationYear = rs.getInt("publicationYear");
        int numberOfPages = rs.getInt("numberOfPages");
        Date acquisitionDate = rs.getDate("acquisitionDate");

        Author author = AuthorService.getAuthorById(authorId);
        Category category = CategoryService.getCategoryById(categoryId);

        return new Book(id, title, author, category, publicationYear, numberOfPages, acquisitionDate);
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO book (title, author_id, category_id, publicationYear, numberOfPages, acquisitionDate) VALUES (?, ?, ?, ?, ?, ?)";
    }
    @Override
    protected void setInsertQueryParameters(PreparedStatement pstmt, Book book) throws SQLException {
        pstmt.setString(1, book.getTitle());
        pstmt.setInt(2, book.getAuthor().getId());
        pstmt.setInt(3, book.getCategory().getId());
        pstmt.setInt(4, book.getPublicationYear());
        pstmt.setInt(5, book.getNumberOfPages());
        pstmt.setDate(6, new java.sql.Date(book.getAcquisitionDate().getTime()));
    }

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM book WHERE id = ?";
    }
    public void addBook(String title, Author author, Category category, int publicationYear, int numberOfPages, Date acquisitionDate) {
        Book book = new Book(title, author, category, publicationYear, numberOfPages, acquisitionDate);
        create(book);
    }

    public void displayBooks() {
        String selectSql = "SELECT * FROM book";

        Connection connection = ConnectionDB.getDatabaseConnection();

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(selectSql)) {
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt("id"));
                System.out.println("Title: " + resultSet.getString("title"));
                System.out.println("Author ID: " + resultSet.getInt("author_id"));
                System.out.println("Category ID: " + resultSet.getInt("category_id"));
                System.out.println("Publication Year: " + resultSet.getInt("publicationYear"));
                System.out.println("Number of Pages: " + resultSet.getInt("numberOfPages"));
                System.out.println("Acquisition Date: " + resultSet.getDate("acquisitionDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(String titlu) {
        String getBookIdSql = "SELECT id FROM book WHERE title = ?";
        String deleteLoansSql = "DELETE FROM loan WHERE bookId = ?";
        String deleteBookSql = "DELETE FROM book WHERE title = ?";

        Connection connection = ConnectionDB.getDatabaseConnection();

        try (PreparedStatement getBookIdStmt = connection.prepareStatement(getBookIdSql)) {

            getBookIdStmt.setString(1, titlu);
            ResultSet rs = getBookIdStmt.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("id");

                //se sterg imprumuturile
                try (PreparedStatement deleteLoansStmt = connection.prepareStatement(deleteLoansSql)) {
                    deleteLoansStmt.setInt(1, bookId);
                    deleteLoansStmt.executeUpdate();
                }

                // se sterge cartea
                try (PreparedStatement deleteBookStmt = connection.prepareStatement(deleteBookSql)) {
                    deleteBookStmt.setString(1, titlu);
                    int rowsAffected = deleteBookStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Cartea cu titlul " + titlu + " a fost ștearsă.");
                    } else {
                        System.out.println("Cartea cu titlul " + titlu + " nu a fost găsită.");
                    }
                }
            } else {
                System.out.println("Cartea cu titlul " + titlu + " nu a fost găsită.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Integer getBookIdByTitle(String title) {
        String query = "SELECT id FROM book WHERE title = ?";
        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBookTitle(String currentTitle, String newTitle) {
        Integer bookId = getBookIdByTitle(currentTitle);
        if (bookId != null) {
            String query = "UPDATE book SET title = ? WHERE id = ?";
            try (Connection conn = ConnectionDB.getDatabaseConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, newTitle);
                pstmt.setInt(2, bookId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Titlul cărții a fost actualizat cu succes.");
                } else {
                    System.out.println("Titlul cărții nu a putut fi actualizat.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Cartea cu titlul '" + currentTitle + "' nu a fost găsită.");
        }
    }

    public static Book getBookById(int bookId) {
        BookService bookService = BookService.getInstance();
        return bookService.read(bookId);
    }


    public void displayBookDetailsByTitle(String title) {
        String query = "SELECT * FROM book WHERE title = ?";
        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, title);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String bookTitle = rs.getString("title");
                    int authorId = rs.getInt("author_id");
                    int categoryId = rs.getInt("category_id");
                    int publicationYear = rs.getInt("publicationYear");
                    int numberOfPages = rs.getInt("numberOfPages");
                    Date acquisitionDate = rs.getDate("acquisitionDate");

                    Author author = AuthorService.getAuthorById(authorId);
                    Category category = CategoryService.getCategoryById(categoryId);

                    // Afișează detaliile cărții
                    System.out.println("ID: " + id);
                    System.out.println("Title: " + bookTitle);
                    System.out.println("Author: " + (author != null ? author.getFirstName() + " " + author.getLastName() : "N/A"));
                    System.out.println("Category: " + (category != null ? category.getName() : "N/A"));
                    System.out.println("Publication Year: " + publicationYear);
                    System.out.println("Number of Pages: " + numberOfPages);
                    System.out.println("Acquisition Date: " + acquisitionDate);
                } else {
                    System.out.println("Cartea cu titlul '" + title + "' nu a fost găsită.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayBooksSortedByTitle() {
        String query = "SELECT b.id, b.title, b.publicationYear, b.numberOfPages, b.acquisitionDate, " +
                "a.firstName AS authorFirstName, a.lastName AS authorLastName, " +
                "c.name AS categoryName " +
                "FROM book b " +
                "JOIN author a ON b.author_id = a.id " +
                "JOIN category c ON b.category_id = c.id " +
                "ORDER BY b.title ASC";

        try (Connection conn = ConnectionDB.getDatabaseConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int publicationYear = rs.getInt("publicationYear");
                int numberOfPages = rs.getInt("numberOfPages");
                Date acquisitionDate = rs.getDate("acquisitionDate");
                String authorFirstName = rs.getString("authorFirstName");
                String authorLastName = rs.getString("authorLastName");
                String categoryName = rs.getString("categoryName");

                // Afișează detaliile cărții
                System.out.println("ID: " + id);
                System.out.println("Title: " + title);
                System.out.println("Author: " + authorFirstName + " " + authorLastName);
                System.out.println("Category: " + categoryName);
                System.out.println("Publication Year: " + publicationYear);
                System.out.println("Number of Pages: " + numberOfPages);
                System.out.println("Acquisition Date: " + acquisitionDate);
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean doesBookExist(String title) {
        Integer bookId = getBookIdByTitle(title);

        return bookId != null;
    }



}
