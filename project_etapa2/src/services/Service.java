package services;

import entities.author.Author;
import entities.book.Book;
import entities.category.Category;
import entities.user.Member;

import java.util.Date;
import java.util.Scanner;

public class Service {
    private static AuditService audit = AuditService.getInstance();
    private FileWriterService fws = FileWriterService.getInstance();
    private static final CategoryService categoryService = CategoryService.getInstance();
    private static final AuthorService authorService = AuthorService.getInstance();
    private static final BookService bookService = BookService.getInstance();
    private static final LoanService loanService = LoanService.getInstance();
    private UserService userService = UserService.getInstance();
//
//    //---------------- functiile din meniu
//
//    //-------------daca esti Employee
//    //-----option 1
//    //---option 1.1
//
    public static void handleAddNewAuthor(Scanner scanner) {

        System.out.print("Introduceți numele de familie: ");
        String lastName = scanner.next();
        System.out.print("Introduceți prenumele: ");
        String firstName = scanner.next();
        authorService.addAuthor(firstName, lastName);
        System.out.println("Autorul " + firstName + " " + lastName + " a fost adăugat cu succes.");
        audit.log("adauga_autor");
    }
//
//    //---option 1.2
public static void handleUpdateAuthorMenu(Scanner scanner) {

    // Solicită utilizatorului să introducă prenumele autorului de modificat
    System.out.print("Introduceți prenumele autorului pe care doriți să-l modificați: ");
    String firstName = scanner.nextLine();

    // Solicită utilizatorului să introducă numele de familie al autorului de modificat
    System.out.print("Introduceți numele de familie al autorului pe care doriți să-l modificați: ");
    String lastName = scanner.nextLine();

    // Solicită utilizatorului să introducă numele de familie și prenumele noului autor
    System.out.print("Introduceți prenumele noului autor: ");
    String newFirstName = scanner.nextLine();
    System.out.print("Introduceți numele de familie al noului autor: ");
    String newLastName = scanner.nextLine();

    authorService.updateAuthorName(firstName, lastName, newFirstName, newLastName);
    audit.log("modifica_autor");
}


//    //---option 1.3
    public static void handleRemoveAuthorMenu(Scanner scanner) {
        // Solicită utilizatorului să introducă ID-ul autorului de șters
        System.out.print("Introduceți prenumele autorului pe care doriți să-l ștergeți: ");
        String firstName = scanner.nextLine();

        System.out.print("Introduceți numele de familie al autorului pe care doriți să-l ștergeți: ");
        String lastName = scanner.nextLine();

        // Șterge autorul cu ID-ul specificat
        authorService.deleteAuthor(firstName, lastName);
        audit.log("sterge_autor");

    }

    //-----option 2
    //---option 2.1
    public static void handleAddNewCategory(Scanner scanner) {

        System.out.print("Introduceți numele categoriei: ");
        String name = scanner.next();

        categoryService.addCategory(name);
        System.out.println("Categoria " + name + " a fost adăugat cu succes.");
        audit.log("adauga_categorie");
    }

    //---option 2.2
    public static void handleUpdateCategoryMenu(Scanner scanner) {

        // Solicită utilizatorului să introducă ID-ul categoriei de modificat
        System.out.print("Introduceți numele categoriei pe care doriți să o modificați: ");
        String name = scanner.nextLine();

        // Solicită utilizatorului să introducă numele noii categorii
        System.out.print("Introduceți numele noii categorii: ");
        String newname = scanner.nextLine();

        categoryService.updateCategoryName(name, newname);
        //System.out.println("Categoria a fost actualizata.");
        audit.log("modifica_categorie");
    }

    //---option 2.3
    public static void handleRemoveCategoryMenu(Scanner scanner) {
        // Solicită utilizatorului să introducă ID-ul categoriei de șters
        System.out.print("Introduceți numele categoriei pe care doriți să o ștergeți: ");
        String name = scanner.nextLine();

        // Șterge categoria cu ID-ul specificat
        categoryService.deleteCategory(name);
        //System.out.println("Categoria a fost ștearsa.");
        audit.log("sterge_categorie");
    }

    //-----option 3
    //---option 3.1
    public static void handleAddBook(Scanner scanner) {

        System.out.println("Introduceți detalii pentru cartea nouă:");

        System.out.print("Titlu: ");
        String title = scanner.nextLine();

        System.out.print("Prenume autor: ");
        String authorFirstName = scanner.nextLine();

        System.out.print("Nume autor: ");
        String authorLastName = scanner.nextLine();
        Author autor = authorService.getAuthorByName(authorFirstName, authorLastName);


        System.out.print("Gen: ");
        String genreName = scanner.nextLine();
        Category categorie = categoryService.getCategoryByName(genreName);


        System.out.print("Anul publicării: ");
        int publicationYear = scanner.nextInt();

        System.out.print("Număr de pagini: ");
        int numberOfPages = scanner.nextInt();

        Date dataAchizitie = new Date();


        bookService.addBook(title, autor, categorie, publicationYear, numberOfPages, dataAchizitie);

        System.out.println("Cartea a fost adăugată cu succes!");
        audit.log("adauga_carte");
    }

    //-----option 4
    //---option 4.1


    //sunt incluse amandoua cu searchbytitle
    //-----option 5
    //---option 5.1
    public static void handleSearchByTitle(Scanner scanner) {
        scanner.nextLine(); // Consumăm newline-ul rămas dinainte
        System.out.print("Introduceți titlul cărții: ");
        String title = scanner.nextLine();
        bookService.displayBookDetailsByTitle(title);
        audit.log("afiseaza_carte");
    }



    //-----option 7
    //---option 7.1
    //----------option 7
    public static void handleFindLoansByMemberLastName(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Introduceți numele de familie al membrului: ");
        String lastName = scanner.nextLine();

        loanService.displayLoansByLastName(lastName);
        audit.log("cauta_imprumut");

    }



    //-------------daca esti Member
    //-----option 1
    //---option 1.1

    // la fel ca optiunea 5 de la employee

    //-----option 2
    //---option 2.1
    //------------ nimic nou
    //---option 2.3

    //-----option 3

    public static void handleLoanMenu(Scanner scanner) {
        scanner.nextLine(); // Consumăm newline-ul rămas dinainte
        System.out.print("Introduceți numele dumneavoastra de familie: ");
        String memberLastName = scanner.nextLine();
        System.out.print("Introduceți prenumele dumneavoastra: ");
        String memberFirstName = scanner.nextLine();

        boolean exists = UserService.doesMemberExist(memberFirstName, memberLastName);

        if (!exists) {
            System.out.println("Membrul cu numele de familie \"" + memberLastName + "\" nu a fost găsit.");
            return;
        }

        System.out.print("Introduceți titlul cărții pe care doriți să o împrumutați: ");
        String bookTitle = scanner.nextLine();

        boolean existsb = BookService.doesBookExist(bookTitle);

        if (!existsb) {
            System.out.println("Cartea cu titlul \"" + bookTitle + "\" nu a fost găsită.");
            return;
        }

        Member member = UserService.getMemberById(UserService.getMemberIdByName(memberFirstName, memberLastName));
        Book book = BookService.getBookById(BookService.getBookIdByTitle(bookTitle));
        Date loanDate = new Date();
        Date dueDate = new Date(loanDate.getTime() + (14 * 24 * 60 * 60 * 1000L)); // 2 săptămâni de la data împrumutului

        LoanService loanService = LoanService.getInstance();
        loanService.addLoan(member, book, loanDate, dueDate);

        System.out.println("Împrumutul a fost înregistrat cu succes.");
    }



}
