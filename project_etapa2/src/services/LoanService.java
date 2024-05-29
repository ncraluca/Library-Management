package services;

import abstracts.AbstractDBService;
import entities.book.Book;
import entities.loan.Loan;
import entities.user.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class LoanService extends AbstractDBService<Loan> {
    private static LoanService instanta = null;

    private final ArrayList<Loan> imprumuturi = new ArrayList<Loan>();

    // constructor privat
    private LoanService() {
    }

    public static LoanService getInstance() {
        if (instanta == null)
            instanta = new LoanService();
        return instanta;
    }

    @Override
    protected Loan mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("userId");
        int bookId = rs.getInt("bookId");
        Date loanDate = rs.getDate("loanDate");
        Date dueDate = rs.getDate("dueDate");
        boolean isReturned = rs.getBoolean("isReturned");
        Member member = UserService.getMemberById(userId); // Presupunem că avem această metodă
        Book book = BookService.getBookById(bookId); // Presupunem că avem această metodă
        return new Loan(id, member, book, loanDate, dueDate, isReturned);
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO Loan (userId, bookId, loanDate, dueDate, isReturned) VALUES (?, ?, ?, ?, false)";
    }

    @Override
    protected void setInsertQueryParameters(PreparedStatement pstmt, Loan loan) throws SQLException {
        Integer memberId = UserService.getMemberIdByName(loan.getMember().getFirstName(), loan.getMember().getLastName());
        pstmt.setInt(1, memberId);
        pstmt.setInt(2, loan.getBook().getId());
        pstmt.setDate(3, new java.sql.Date(loan.getLoanDate().getTime()));
        pstmt.setDate(4, new java.sql.Date(loan.getDueDate().getTime()));
    }

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM Loan WHERE id = ?";
    }

    public void addLoan(Member member, Book book, Date loanDate, Date dueDate) {
        Loan loan = new Loan(member, book, loanDate, dueDate);
        create(loan);
    }

    public void displayAllLoans() {
        String query = "SELECT * FROM Loan";
        ArrayList<Integer> userIds = new ArrayList<>();
        ArrayList<Integer> bookIds = new ArrayList<>();
        ArrayList<Integer> loanIds = new ArrayList<>();
        ArrayList<Date> loanDates = new ArrayList<>();
        ArrayList<Date> dueDates = new ArrayList<>();
        ArrayList<Boolean> isReturnedList = new ArrayList<>();

        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                loanIds.add(rs.getInt("id"));
                userIds.add(rs.getInt("userId"));
                bookIds.add(rs.getInt("bookId"));
                loanDates.add(rs.getDate("loanDate"));
                dueDates.add(rs.getDate("dueDate"));
                isReturnedList.add(rs.getBoolean("isReturned"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        for (int i = 0; i < loanIds.size(); i++) {
            Member member = UserService.getMemberById(userIds.get(i)); // Presupunem că avem această metodă
            Book book = BookService.getBookById(bookIds.get(i)); // Presupunem că avem această metodă

            System.out.println("Loan ID: " + loanIds.get(i) + ", Member: " + member + ", Book: " + book +
                    ", Loan Date: " + loanDates.get(i) + ", Due Date: " + dueDates.get(i) +
                    ", Returned: " + isReturnedList.get(i));
        }
    }

    public void displayLoansByLastName(String lastName) {
        String query = "SELECT l.id AS loanId, l.loanDate, l.dueDate, l.isReturned, " +
                "b.id AS bookId, b.title, b.publicationYear, b.numberOfPages, b.acquisitionDate, " +
                "m.id AS memberId, m.firstName, m.lastName " +
                "FROM Loan l " +
                "JOIN Member m ON l.userId = m.id " +
                "JOIN Book b ON l.bookId = b.id " +
                "WHERE m.lastName = ?";

        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, lastName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int loanId = rs.getInt("loanId");
                    Date loanDate = rs.getDate("loanDate");
                    Date dueDate = rs.getDate("dueDate");
                    boolean isReturned = rs.getBoolean("isReturned");

                    int bookId = rs.getInt("bookId");
                    String title = rs.getString("title");
                    int publicationYear = rs.getInt("publicationYear");
                    int numberOfPages = rs.getInt("numberOfPages");
                    Date acquisitionDate = rs.getDate("acquisitionDate");

                    int memberId = rs.getInt("memberId");
                    String firstName = rs.getString("firstName");
                    String memberLastName = rs.getString("lastName");

                    System.out.println("Loan ID: " + loanId);
                    System.out.println("Member: " + firstName + " " + memberLastName);
                    System.out.println("Book: " + title + " (" + publicationYear + ")");
                    System.out.println("Loan Date: " + loanDate);
                    System.out.println("Due Date: " + dueDate);
                    System.out.println("Returned: " + isReturned);
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
