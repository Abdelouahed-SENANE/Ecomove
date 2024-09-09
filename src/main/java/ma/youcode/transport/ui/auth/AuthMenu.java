package ma.youcode.transport.ui.auth;

import java.sql.SQLException;
import java.util.Scanner;

import ma.youcode.transport.ui.Menu;
import ma.youcode.transport.ui.admin.*;
import ma.youcode.transport.ui.admin.CostumerMenu;

public class AuthMenu {

    private static final Scanner sc = new Scanner(System.in);
    private static int choice;

    public static Menu start(Menu menu) {
        do {
            System.out.println("\n================== Authentication Costumers ==================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Back to main menu");
            System.out.println("================== Authentication Costumers ==================\n");
            System.out.println("Choose an option :");
            choice = sc.nextInt();
            choice = getChoice();

            switch (choice) {
                case 1:
                    CostumerMenu.start(menu);
                case 2:
                    CostumerMenu.start(menu);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    break;
            }
        } while (choice != 0);

        return menu;
    }

    public static int getChoice() {

        while (choice < 0 || choice > 3) {
            System.out.println("Enter Number Between 0 - 3");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (choice < 0 || choice > 0) {
                    System.out.println("Number out of range. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a correct number.");
                sc.next();
            }
        }
        return choice;
    }

}
