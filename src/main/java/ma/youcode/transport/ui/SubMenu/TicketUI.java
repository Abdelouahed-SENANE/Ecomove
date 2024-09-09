package ma.youcode.transport.ui.SubMenu;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import ma.youcode.transport.entity.Contract;
import ma.youcode.transport.entity.Ticket;
import ma.youcode.transport.enums.TicketStatus;
import ma.youcode.transport.enums.TransportationType;
import ma.youcode.transport.service.implementations.ContractServiceImp;
import ma.youcode.transport.service.implementations.TicketServiceImp;
import ma.youcode.transport.ui.Menu;
import ma.youcode.transport.utils.DisplayUtil;
import ma.youcode.transport.validator.Validator;

public class TicketUI {
    private int choice;
    private final TicketServiceImp ticketServiceImp;
    private final ContractServiceImp contractsServiceImp;
    private final Scanner sc;
    private final Validator validator;
    private final ContractUI contractUI;
    public TicketUI() throws SQLException {
        this.sc = new Scanner(System.in);
        this.ticketServiceImp = new TicketServiceImp();
        this.validator = new Validator();
        this.contractsServiceImp = new ContractServiceImp();
        this.contractUI = new ContractUI();
    }

    public int getChoice() {
        while (true) {
            if (sc.hasNextInt()) {
                this.choice = sc.nextInt();
                if (this.choice >= 0 && this.choice <= 4) {
                    return this.choice;  
                } else {
                    System.out.println("Number out of range. Please enter a number between 0 - 4.");
                }
            } else {
                System.out.println("Invalid input. Please enter a correct number.");
                sc.next();  
            }
        }
    }

    public Menu start(Menu menu) throws SQLException {
        do {
            System.out.println("\n================ Manage Tickets ================");
            System.out.println("1. Add new ticket");
            System.out.println("2. Edit ticket");
            System.out.println("3. Delete ticket");
            System.out.println("4. Show Tickets");
            System.out.println("0. Go back");
            System.out.println("================ Manage Tickets ================\n");
            System.out.print("Choose an option: ");

            this.choice = getChoice();

            switch (this.choice) {
                case 1: // Add new ticket
                    System.out.println("You chose option 1: Add new ticket\n");
                    Ticket newTicket = new Ticket();
                    newTicket.setTicketId(UUID.randomUUID().toString());
                    newTicket.setTransportationType(validator.choiceOption(TransportationType.class));
                    newTicket.setBoughtFor(validator.getValidDoubleInput("Enter bought price: ", "Please enter a valid bought price."));
                    newTicket.setSellingPrice(validator.getValidDoubleInput("Enter selling price: ", "Please enter a valid selling price."));
                    newTicket.setSoldAt(Timestamp.valueOf(LocalDateTime.now()));
                    newTicket.setTicketStatus(validator.choiceOption(TicketStatus.class));
                    this.contractUI.displayValidatedContract();

                    String contractId = validator.getValidStringInput("Enter Contract ID: ", "Please enter a valid Contract ID.");
                    Contract contract = contractsServiceImp.getContractWithSpecialOffers(contractId);
                    if (contract != null) {
                        newTicket.setContract(contract);
                        Ticket addedTicket = ticketServiceImp.addTicket(newTicket);
                        if (addedTicket != null) {
                            System.out.println("New ticket added successfully with ID " + addedTicket.getTicketId());
                        } else {
                            System.out.println("Failed to add the ticket.");
                        }
                    } else {
                        System.out.println("Contract with ID " + contractId + " does not exist.");
                    }
                    break;

                case 2:
                    System.out.println("You chose option 2: Edit ticket\n");
                    this.displayTickets(); 
                    sc.nextLine();
                    Ticket existingTicket = null;
                    String editTicketId = "";
                
                    while (true) {
                        System.out.println("Enter the Ticket ID you wish to update: ");
                        editTicketId = sc.nextLine().trim();
                
                        if (editTicketId.isEmpty()) {
                            System.out.println("Ticket ID cannot be empty. Please enter a valid Ticket ID.");
                            continue; 
                        }
                
                        existingTicket = ticketServiceImp.getTicket(editTicketId);
                
                        if (existingTicket != null) {
                            break;
                        } else {
                            System.out.println("Ticket with ID " + editTicketId + " does not exist. Please check the Ticket ID and try again.");
                        }
                    }
                
                    // Collect updated details for the ticket
                    existingTicket.setTransportationType(validator.choiceOption(TransportationType.class));
                    existingTicket.setBoughtFor(validator.getValidDoubleInput("Enter bought price: ", "Please enter a valid bought price."));
                    existingTicket.setSellingPrice(validator.getValidDoubleInput("Enter selling price: ", "Please enter a valid selling price."));
                    existingTicket.setSoldAt(Timestamp.valueOf(LocalDateTime.now()));
                    existingTicket.setTicketStatus(validator.choiceOption(TicketStatus.class));
                
                    Ticket updatedTicket = ticketServiceImp.updateTicket(existingTicket);
                
                    if (updatedTicket != null) {
                        System.out.println("Ticket with ID " + updatedTicket.getTicketId() + " updated successfully.");
                    } else {
                        System.out.println("Cannot update this ticket.");
                    }
                    
                    break;

                case 3: // Delete ticket
                    System.out.println("You chose option 3: Delete ticket\n");
                    String ticketId;
                    while (true) {
                        this.displayTickets();
                        System.out.println("Enter ticket ID you want to delete: ");
                        ticketId = sc.nextLine().trim();
                        if (ticketId.isEmpty()) {
                            System.out.println("Ticket ID cannot be empty. Please check the Ticket ID and try again.");
                            continue;
                        }
                        existingTicket = ticketServiceImp.getTicket(ticketId);
                        if (existingTicket != null) {
                            break;
                        } else {
                            System.out.println("Ticket with ID " + ticketId + " does not exist. Please check the Ticket ID and try again.");
                        }
                    }

                    Ticket ticket = ticketServiceImp.deleteTicket(ticketId);
                    if (ticket != null) {
                        System.out.println("Ticket with ID " + ticket.getTicketId() + " deleted successfully.");
                    } else {
                        System.out.println("Cannot delete this ticket.");
                    }
                    break;

                case 4: // Show tickets
                    System.out.println("You chose option 4: Show tickets\n");
                    this.displayTickets();
                    break;

                case 0: // Go back
                    System.out.println("Back to main menu \n");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (this.choice != 0);

        return menu;
    }

    private void displayTickets()  {
        List<Ticket> ticketsList = ticketServiceImp.getAllTickets();
        
        String[] headers = {
            "ticketId", "transportationType", "boughtFor", "sellingPrice", "soldAt", "ticketStatus", "contract.contractId"
        };

        DisplayUtil.displayTable(headers, ticketsList);
    }
}
