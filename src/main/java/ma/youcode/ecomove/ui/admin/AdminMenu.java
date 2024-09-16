package ma.youcode.ecomove.ui.admin;

import java.sql.SQLException;
import java.util.Scanner;

import ma.youcode.ecomove.ui.Menu;

public class AdminMenu {

    private static final Scanner sc = new Scanner(System.in);

    // Keeping these static so they can be used in a static context
    private static PartnerUI partnerUI;
    private static ContractUI contractUI;
    private static SpecialOfferUI specialOfferUI;
    private static TicketUI ticketsUI;

    // Static block to initialize static members
    static {
        try {
            partnerUI = new PartnerUI();
            contractUI = new ContractUI();
            specialOfferUI = new SpecialOfferUI();
            ticketsUI = new TicketUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Static start method
    public static Menu start(Menu menu)  {
        int choice;
        do {
            System.out.println("\n================== Transport app Menu ==================");
            System.out.println("1. Manage Partners");
            System.out.println("2. Manage Contracts");
            System.out.println("3. Manage Special Offers");
            System.out.println("4. Manage Tickets");
            System.out.println("0. Exit");
            System.out.println("================== Transport app Menu ==================\n");
            System.out.println("Choose an option :");

            choice = getChoice();

            switch (choice) {
                case 1:
                    partnerUI.start(menu);
                    break;
                case 2:
                    contractUI.start(menu);
                    break;
                case 3:
                    specialOfferUI.start(menu);
                    break;
                case 4:
                    ticketsUI.start(menu);
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        } while (choice != 0);

        return menu;
    }

    // Static method for getting a valid choice
    public static int getChoice() {
        int choice = -1;
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (choice >= 0 && choice <= 4) {
                    return choice;
                } else {
                    System.out.println("Number out of range. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a correct number.");
                sc.next();
            }
        }
    }
}
