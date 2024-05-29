package services.menu;

import services.*;

import java.util.Scanner;

import static services.Service.handleUpdateAuthorMenu;

public class EmployeeMenu {

    private static AuditService audit = AuditService.getInstance();
    private FileWriterService fws = FileWriterService.getInstance();
    private static CategoryService categoryService = CategoryService.getInstance();
    private static AuthorService authorService = AuthorService.getInstance();
    private static BookService bookService = BookService.getInstance();
    private UserService userService = UserService.getInstance();
    private static LoanService loanService = LoanService.getInstance();
    public static void displayEmployeeMenu() {
        System.out.println("\nMeniu angajat:");
        System.out.println("1. Gestiune autori");
        System.out.println("2. Gestiune categorii");
        System.out.println("3. Adăugare carte nouă");
        System.out.println("4. Cautare carte");
        System.out.println("5. Vizualizare carti");
        System.out.println("6. Vizualizare istoric imprumuturi");
        System.out.println("7. Cautare imprumut dupa nume de familie");
        System.out.println("0. Înapoi la meniul principal");
    }
//
    public static void handleEmployeeMenu(Scanner scanner) {
        int choice;

        do {
            displayEmployeeMenu();
            System.out.print("Alegeți opțiunea: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Gestiune autori
                    //System.out.println("Gestiune autori");
                    handleAuthorManagementMenu(scanner);
                    break;
                case 2:
                    // Gestiune categorii
                    //System.out.println("Gestiune categorii");
                    handleCategoryManagementMenu(scanner);
                    break;
                case 3:
                    // Adăugare carte nouă
                    //System.out.println("Adaugare carte noua");
                    handleAddBookMenu(scanner);
                    break;
                case 4:
                    //System.out.println("Cautare carte");
                    handleSearchBookMenu(scanner);
                    break;
                case 5:
                    //System.out.println("Vizualizare carte");
                    handleViewBooksMenu(scanner);
                    break;
                case 6:
                    //System.out.println("Afisare imprumuturi");
                    loanService.displayAllLoans();
                    break;
                case 7:
                    // Cautare imprumut dupa nume de familie
                    //System.out.println("Cautare imprumut dupa numele de fam");
                    Service.handleFindLoansByMemberLastName(scanner);
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
//
//    //--------option 1
    private static void displayAuthorManagementMenu() {
        System.out.println("\nGestiune autori:");
        System.out.println("1. Adăugare autor nou");
        System.out.println("2. Modificare detalii autor");
        System.out.println("3. Ștergere autor");
        System.out.println("4. Vizualizare lista autori");
        System.out.println("0. Înapoi la meniul principal");
    }

    public static void handleAuthorManagementMenu(Scanner scanner) {
        int choice;

        do {
            displayAuthorManagementMenu();
            System.out.print("Alegeți opțiunea: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Adăugare autor nou
                    //System.out.println("Autor nou");
                    Service.handleAddNewAuthor(scanner);

                    break;
                case 2:
                    // Modificare detalii autor
                    //System.out.println("Modif detalii autor");
                    Service.handleUpdateAuthorMenu(scanner);
                    break;
                case 3:
                    // Ștergere autor
                    //System.out.println("Stergere autor");
                    Service.handleRemoveAuthorMenu(scanner);
                    break;
                case 4:
                    //System.out.println("afisare toti autorii");
                    authorService.displayAuthors();
                    audit.log("vizualizeaza_autori");
                    break;
                case 0:
                    // Înapoi la meniul principal
                    break;
                default:
                    System.out.println("Opțiune invalidă. Vă rugăm să alegeți din nou.");
                    break;
            }
        } while (choice != 0);
    }

    //--------option 2
    private static void displayCategoryManagementMenu() {
        System.out.println("\nMeniu gestionare categorii:");
        System.out.println("1. Adăugare categorie");
        System.out.println("2. Modificare categorie");
        System.out.println("3. Ștergere categorie");
        System.out.println("4. Vizualizare listă categorii");
        System.out.println("0. Înapoi la meniul anterior");
    }
    private static void handleCategoryManagementMenu(Scanner scanner) {
        int choice;

        do {
            displayCategoryManagementMenu();
            System.out.print("Alegeți opțiunea: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Service.handleAddNewCategory(scanner);
                    break;
                case 2:
                    //System.out.println("modify");
                    Service.handleUpdateCategoryMenu(scanner);
                    break;
                case 3:
                    Service.handleRemoveCategoryMenu(scanner);
                    break;
                case 4:
                    //System.out.println("list");
                    categoryService.displayCategories();
                    audit.log("vizualizeaza_categorii");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opțiune invalidă. Vă rugăm să alegeți din nou.");
                    break;
            }
        } while (choice != 0);
    }

    //----- option 3
    private static void handleAddBookMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("1. Adăugare carte nouă");
            System.out.println("0. Înapoi la meniul anterior");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Service.handleAddBook(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opțiune invalidă. Vă rugăm să alegeți din nou.");
                    break;
            }
        }while(choice != 0);
    }


    //------option 4
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
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Cautare dupa titlu
                    Service.handleSearchByTitle(scanner);
                    break;
                case 2:
                    // Cautare dupa autor
                    // Adăugați aici logica pentru căutarea cărților după autor
                    System.out.println("Ai ales opțiunea: Cautare dupa autor.");
                    break;
                case 3:
                    // Cautare dupa categorie
                    // Adăugați aici logica pentru căutarea cărților după categorie
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

    //-------option 5
    private static void displayViewBooksMenu() {
        System.out.println("----- Vizualizare Cărți -----");
        System.out.println("1. Vizualizare detalii carte specifica");
        System.out.println("2. Vizualizarea tuturor cartilor");
        System.out.println("3. Vizualizarea tuturor cartilor sortate alfabetic dupa titlu");
        System.out.println("0. Înapoi");
        System.out.println("--------------------------");
    }
    public static void handleViewBooksMenu(Scanner scanner) {
        int choice;

        do {
            displayViewBooksMenu();
            System.out.print("Alegeți opțiunea: ");
            choice = scanner.nextInt();
            scanner.nextLine();

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

    //-----option 6
    private static void displayViewLoansMenu() {
        System.out.println("----- Vizualizare Împrumuturi -----");
        System.out.println("1. Vizualizarea tuturor imprumuturilor");
        System.out.println("2. Vizualizarea tuturor imprumuturilor sortate dupa data efectuarii");
        System.out.println("3. Cautare imprumut dupa numele de familie al unui membru");
        System.out.println("0. Înapoi");
        System.out.println("--------------------------");
    }
    private static void handleViewLoansMenu(Scanner scanner) {
        int choice;

        do {
            displayViewLoansMenu();
            System.out.print("Alegeți opțiunea: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Vizualizare tutror imprumuturilor
                    System.out.println("Ai ales opțiunea: Vizualizare tuturor imprumuturilor.");
                    break;
                case 2:
                    // Vizualizarea tuturor imprumuturilor sortate dupa data efectuarii
                    System.out.println("Ai ales opțiunea: Vizualizare tuturor imprumuturilor sortate dupa data efectuarii.");
                    break;
                case 3:
                    // Cautare imprumut dupa numele de familie al unui membru
                    System.out.println("Ai ales opțiunea: Cautare imprumut dupa numele de familie al unui membru.");
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
