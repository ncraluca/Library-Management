
import entities.author.Author;
import entities.book.Book;
import entities.category.Category;
import entities.user.Member;
import entities.user.User;
import services.*;
import services.menu.EmployeeMenu;
import services.menu.MainMenu;
import services.menu.MemberMenu;

import java.util.Date;
import java.util.Scanner;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Bun venit!");
        System.out.println("1. Angajat");
        System.out.println("2. Membru");
        System.out.println("0. Ieșire");

        System.out.print("Alegeți opțiunea: ");
        choice = scanner.nextInt();

        do {
            switch (choice) {
                case 1:
                    EmployeeMenu.handleEmployeeMenu(scanner);
                    break;
                case 2:
                    MemberMenu.handleMemberMenu(scanner);
                    break;
                case 0:
                    System.out.println("La revedere!");
                    break;
                default:
                    System.out.println("Opțiune invalidă. Vă rugăm să alegeți din nou.");
                    break;
            }

            if (choice != 0) {
                MainMenu.displayMainMenu();
                System.out.print("Alegeți opțiunea: ");
                choice = scanner.nextInt();
            }
        } while (choice != 0);

        scanner.close();

    }
}