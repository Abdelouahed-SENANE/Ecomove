package ma.youcode.transport.ui;

import java.sql.SQLException;
import java.util.Scanner;

import ma.youcode.transport.ui.SubMenu.ContractUI;
import ma.youcode.transport.ui.SubMenu.PartnerUI;
import ma.youcode.transport.ui.SubMenu.SpecialOfferUI;
import ma.youcode.transport.ui.SubMenu.TicketUI;

public class Menu {

    private final Scanner sc;
    private PartnerUI partnerUI;
    private ContractUI contractUI;
    private SpecialOfferUI specialOfferUI;
    private TicketUI ticketsUI;
    private int choice;

    public Menu() throws SQLException {
        this.sc = new Scanner(System.in);
        this.partnerUI = new PartnerUI();
        this.contractUI = new ContractUI();
        this.specialOfferUI = new SpecialOfferUI();
        this.ticketsUI = new TicketUI();
    }

    public Menu start(Menu menu) throws SQLException{
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
