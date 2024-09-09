package ma.youcode.transport.ui.admin;

import java.sql.SQLException;
import java.util.Scanner;

import ma.youcode.transport.ui.Menu;
import ma.youcode.transport.ui.admin.ContractUI;
import ma.youcode.transport.ui.admin.PartnerUI;
import ma.youcode.transport.ui.admin.SpecialOfferUI;
import ma.youcode.transport.ui.admin.TicketUI;

public class CostumerMenu  {

    private final Scanner sc;
    private PartnerUI partnerUI;
    private ContractUI contractUI;
    private SpecialOfferUI specialOfferUI;
    private TicketUI ticketsUI;
    private int choice;

    public CostumerMenu()  {
        this.sc = new Scanner(System.in);

    }

    public Menu start(Menu menu) {
        do {
            System.out.println("\n================== Transport app Menu ==================");
            System.out.println("1. Manage Partners");
            System.out.println("2. Manage Contracts");
            System.out.println("3. Manage Special Offers");
            System.out.println("4. Manage Tickets");
            System.out.println("0. Exit");
            System.out.println("================== Transport app Menu ==================\n");
            System.out.println("Choose an option :");
            this.choice = sc.nextInt();
            this.choice = getChoice();

            switch (this.choice) {
                case 1:
                    System.out.println("Costumer: new access ");
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        } while (this.choice != 0);

        return menu;
    }

    public int getChoice() {

        while (this.choice < 0 || this.choice > 4) {
            System.out.println("Enter Number Between 0 - 4");
            if (sc.hasNextInt()) {
                this.choice = sc.nextInt();
                if (this.choice < 0 || this.choice > 0) {
                    System.out.println("Number out of range. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a correct number.");
                sc.next();
            }
        }
        return this.choice;
    }

}
