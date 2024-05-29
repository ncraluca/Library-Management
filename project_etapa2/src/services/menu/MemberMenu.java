package services.menu;

import services.*;

import java.util.Scanner;

public class MemberMenu {
    private static AuditService audit = AuditService.getInstance();
    private FileWriterService fws = FileWriterService.getInstance();
    private static CategoryService categoryService = CategoryService.getInstance();
    private static AuthorService authorService = AuthorService.getInstance();
    private static BookService bookService = BookService.getInstance();
    private UserService userService = UserService.getInstance();
    private static LoanService loanService = LoanService.getInstance();

    private static void displayMemberMenu() {
        System.out.println("\nMeniu membru:");
        System.out.println("1. Cautare carte");
        System.out.println("2. Vizualizare carti");
        System.out.println("3. Împrumutare carte");
        System.out.println("0. Înapoi la meniul principal");
    }

    public static void handleMemberMenu(Scanner scanner) {
        int choice;

        do {
            displayMemberMenu();
            System.out.print("Alegeți opțiunea: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleSearchBookMenu(scanner);
                    break;
                case 2:
                    handleViewBooksMenu(scanner);
                    break;
                case 3:
                    // Împrumutare carte
                    Service.handleLoanMenu(scanner);
                    break;
                case 0:
                    // Opțiune pentru ieșire
                    break;
                default:
                    System.out.println("Opțiune invalidă. Vă rugăm să alegeți din nou.");
                    break;
            }
        } while (choice != 0);
    }

    //-----option 1

    private static void displaySearchBookMenu() {
        System.out.println("----- Căutare Carte -----");
        System.out.println("1. Căutare după titlu");
        System.out.println("2. Căutare după autor");
        System.out.println("3. Căutare după categorie");
        System.out.println("0. Înapoi");
        System.out.println("--------------------------");
    }

    private static void handleSearchBookMenu(Scanner scanner) {
        int choice;

        do {
            displaySearchBookMenu();
            System.out.print("Alegeți opțiunea: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Cautare dupa titlu
                    Service.handleSearchByTitle(scanner);
                    break;
                case 2:
                    // Cautare dupa autor
                    System.out.println("Ai ales opțiunea: Cautare dupa autor.");
                    break;
                case 3:
                    // Cautare dupa categorie
                    System.out.println("Ai ales opțiunea: Cautare dupa categorie.");
                    break;
                case 0:
                    // Opțiune pentru revenire la meniul anterior
                    break;
                default:
                    System.out.println("Opțiune invalidă. Vă rugăm să alegeți din nou.");
                    break;
            }
        } while (choice != 0);



    }

    //--- option 2
    private static void displayViewBooksMenu() {
        System.out.println("----- Vizualizare Cărți -----");
        System.out.println("1. Vizualizare detalii carte specifica");
        System.out.println("2. Vizualizarea tuturor cartilor");
        System.out.println("3. Vizualizarea tuturor cartilor sortate alfabetic dupa titlu");
        System.out.println("0. Înapoi");
        System.out.println("--------------------------");
    }

    private static void handleViewBooksMenu(Scanner scanner) {
        int choice;

        do {
            displayViewBooksMenu();
            System.out.print("Alegeți opțiunea: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Vizualizare detalii carte specifica
                    Service.handleSearchByTitle(scanner);
                    break;
                case 2:
                    // Vizualizarea tuturor cartilor
                    bookService.displayBooks();
                    audit.log("vizualizeaza_carti");
                    break;
                case 3:
                    // Vizualizarea tuturor cartilor sortate alfabetic dupa titlu
                    bookService.displayBooksSortedByTitle();
                    audit.log("vizualizeaza_carti_sortate");
                    break;
                case 0:
                    // Opțiune pentru revenire la meniul anterior
                    break;
                default:
                    System.out.println("Opțiune invalidă. Vă rugăm să alegeți din nou.");
                    break;
            }
        } while (choice != 0);
    }



}
