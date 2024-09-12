package ma.youcode.transport.ui.costumer;

import java.util.Scanner;

import ma.youcode.transport.ui.Menu;
import ma.youcode.transport.utils.Session;

public class ReservationUI {

    private static final Scanner sc = new Scanner(System.in);
    private static int choice;


    public static Menu start(Menu menu) {
        do {
            System.out.println("\n================== Costumer Menu ==================");
            System.out.println("1. Make Reservation");
            System.out.println("2. View my Reservations");
            System.out.println("3. Cancel Reservation");
            System.out.println("0. Exit");
            System.out.println("================== Costumer Menu ==================\n");
            System.out.println("Choose an option :");
            choice = sc.nextInt();
            choice = getChoice();

            switch (choice) {
                case 1:
                    System.out.println("You are choice make reservation option \n");
                    System.out.println("Enter Departure : ");
                    String departure = sc.next();
                    System.out.println("Enter Destination : ");
                    String destination = sc.next();


                    break;
                case 2:
                    break;
                case 0:
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
                if (choice < 0 || choice > 3) {
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
