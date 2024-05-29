package entities.book;

import entities.author.Author;
import entities.category.Category;

import java.util.Date;

public class Book {
    private Integer id;
    private String title;
    private Author author;
    private Category genre;
    private int publicationYear;
    private int numberOfPages;
    private Date acquisitionDate;

    public Book(Integer id, String title, Author author, Category genre, int publicationYear, int numberOfPages, Date acquisitionDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.numberOfPages = numberOfPages;
        this.acquisitionDate = acquisitionDate;
    }
    public Book(String title, Author author, Category genre, int publicationYear, int numberOfPages, Date acquisitionDate) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.numberOfPages = numberOfPages;
        this.acquisitionDate = acquisitionDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public Category getCategory() {
        return genre;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setCategory(Category genre) {
        this.genre = genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", publicationYear=" + publicationYear +
                ", numberOfPages=" + numberOfPages +
                ", acquisitionDate=" + acquisitionDate +
                '}';
    }
}
