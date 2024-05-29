package entities.loan;

import entities.book.Book;
import entities.user.Member;

import java.util.Date;

public class Loan {
    Integer id;
    private Member member;
    private Book book;
    private Date loanDate;
    private Date dueDate;
    private boolean isReturned;

    public Loan(Integer id, Member member, Book book, Date loanDate, Date dueDate, boolean isReturned) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.isReturned = isReturned;
    }

    public Loan(Member member, Book book, Date loanDate, Date dueDate) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }
    public Loan(Member member, Book book, Date loanDate) {
        this.member = member;
        this.book = book;
        this.loanDate = loanDate;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
