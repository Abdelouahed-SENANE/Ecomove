package ma.youcode.transport.ui.costumer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import ma.youcode.transport.entity.Ticket;
import ma.youcode.transport.service.TicketService;
import ma.youcode.transport.service.implementations.TicketServiceImp;
import ma.youcode.transport.ui.Menu;
import ma.youcode.transport.validator.Validator;

public class ReservationUI {

    private static final Validator validator;
    private static final TicketService  ticketService;
    private static final Scanner sc = new Scanner(System.in);
    private static int choice;

    static {
        validator = new Validator();
        ticketService = new TicketServiceImp();
    }
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
                    LocalDateTime departureTime = validator.getValidLocalDateTime("Departure Time for journey : ");

                    List<List<Ticket>> AvailableRoutes = ticketService.availbeJourneys(departure , destination , departureTime);
                   System.out.println(displayJourneysInCards(AvailableRoutes));

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

    public static String displayJourneysInCards(List<List<Ticket>> allJourneys) {
        StringBuilder sb = new StringBuilder();
        int journeyNumber = 1;
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("d, MMMM yyyy, HH'h'mm'min'", Locale.ENGLISH);

        for (List<Ticket> journey : allJourneys) {
            sb.append("┌──────────────────────────────────────────────┐\n")
                    .append("│  Journey ").append(journeyNumber)
                    .append(String.format("%" + (45 - (String.valueOf(journeyNumber).length() + 10)) + "s", "Ticket N* " + journeyNumber)).append(" │\n")
                    .append("├──────────────────────────────────────────────┤\n");

            for (Ticket ticket : journey) {
                String departure = ticket.getRoute().getDeparture().toUpperCase();
                String destination = ticket.getRoute().getDestination().toUpperCase();
                String departureTime = ticket.getDepartureTime().format(customFormatter);
                String arrivalTime = ticket.getDepartureTime().plusMinutes(ticket.getDuration()).format(customFormatter);

                sb.append("│ ➤ ").append(String.format("%-" + (departure.length() + 1) + "" +"s", departure)).append("--------→ ").append(String.format("%-10s", destination)).append("\n")
                        .append("│    Departure: ").append(String.format("%-22s", departureTime)).append("\n")
                        .append("│    Arrival:   ").append(String.format("%-22s", arrivalTime)).append("\n")
                        .append("├──────────────────────────────────────────────┤\n");
            }

            // End card with border
            sb.append("└──────────────────────────────────────────────┘\n");

            journeyNumber++;
        }

        return sb.toString();
    }




}
