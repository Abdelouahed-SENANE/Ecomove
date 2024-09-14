package ma.youcode.transport.ui.costumer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

import ma.youcode.transport.entity.Passenger;
import ma.youcode.transport.entity.Reservation;
import ma.youcode.transport.entity.Ticket;
import ma.youcode.transport.service.ReservationService;
import ma.youcode.transport.service.TicketService;
import ma.youcode.transport.service.implementations.ReservationServiceImp;
import ma.youcode.transport.service.implementations.TicketServiceImp;
import ma.youcode.transport.ui.Menu;
import ma.youcode.transport.utils.Session;
import ma.youcode.transport.validator.Validator;

public class ReservationUI {

    private static final Validator validator;
    private static final TicketService  ticketService;
    private static final ReservationService reservationService;
    private static final Scanner sc = new Scanner(System.in);
    private static int choice;

    static {
        validator = new Validator();
        ticketService = new TicketServiceImp();
        reservationService = new ReservationServiceImp();
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
                    sc.nextLine();
                    String departure = validator.getValidStringInput("Enter your departure : ", "Please Enter valid departure , try again : " );
                    String destination = validator.getValidStringInput("Enter your destination : ", "Please Enter valid destination , try again : " );;
                    LocalDateTime departureTime = validator.getValidLocalDateTime("Departure Time for journey  ");

                    List<List<Ticket>> availableRoutes = ticketService.availbeJourneys(departure , destination , departureTime);
                    if (availableRoutes.size() > 0) {
                        System.out.println(displayJourneysInCards(availableRoutes));
                        int choiceJourney;
                        while(true) {
                            System.out.println("Choice your journey : ");
                            choiceJourney = sc.nextInt() ;
                            if (choiceJourney > 0 && choiceJourney <= availableRoutes.size()) {
                                break;
                            }
                            System.out.println("Please enter number between { 1 and " + (availableRoutes.size() +" }" ));
                        }
                        Reservation newReservation = new Reservation();
                        newReservation.setReservationId(UUID.randomUUID().toString());
                        Passenger passenger = new Passenger();
                        passenger.setEmail(Session.getLoggedEmail());
                        newReservation.setPassenger(passenger);
                        newReservation.setTickets(availableRoutes.get(choiceJourney - 1));
                        Reservation savedReservation = reservationService.addReservation(newReservation);
                        if (savedReservation != null) {
                            System.out.println("Reservation made successfully ");
                        }else {
                            System.out.println("Reservation not made");
                        }
                    }else {
                        System.out.println("\n _______________________________________________ ");
                        System.out.println("|  There are no available routes for departure  |");
                        System.out.println("|_______________________________________________|");
                    }

                    break;
                case 2:

                    System.out.println("You are choice to view all your reservation \n");
                    List<Reservation> myReservations = reservationService.getAllReservations();
                    if (myReservations.size() > 0) {
                        System.out.println(displayMyReservations(myReservations));
                    }else {
                    System.out.println("\n _______________________________________________ ");
                    System.out.println("|  There are no available routes for departure  |");
                    System.out.println("|_______________________________________________|");
                     }

                    break;
                    case 3:
                        System.out.println("You are choice to cancel the reservation \n");
                        List<Reservation> reservationsToCancel = reservationService.getAllReservations();

                        if (reservationsToCancel.size() > 0) {
                            System.out.println(displayMyReservations(reservationsToCancel));


                            while (true) {
                                System.out.println("Choice Number of the reservation you want to cancel: ");
                                int reservationNumber = sc.nextInt() - 1;
                                if (reservationNumber >= 0 && reservationsToCancel.size() > reservationNumber){
                                    Reservation selectedReservation = reservationsToCancel.get(reservationNumber);
                                    System.out.println("Are you sure you want to cancel this  reservation ? (yes/no)");
                                    String confirmation = sc.next();
                                    if (confirmation.equals("yes")) {
                                        boolean isCancelled  = reservationService.markedAsCancelled(selectedReservation);
                                        if (isCancelled) {
                                            System.out.println("Cancelled reservation");
                                        }
                                        break;
                                    }
                                }
                                System.out.println("Invalid choice, Please Enter number between { 1 and " + (reservationsToCancel.size() +" }" ));
                            }


                        }else {
                            System.out.println("\n _______________________________________________ ");
                            System.out.println("|  There are no available routes for departure  |");
                            System.out.println("|_______________________________________________|");
                        }




                        break;


                    case 0:
                    System.out.println("See you later!");
                    break;
                    default:
                    System.out.println("You are choice incorrect option, Try Again... ");
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
                .append(String.format("%" + (45 - (String.valueOf(journeyNumber).length() + 10)) + "s", "Ticket N* " + journeyNumber)).append(" \n")
                .append("├──────────────────────────────────────────────┤\n");

        for (Ticket ticket : journey) {
            String departure = ticket.getRoute().getDeparture().toUpperCase();
            String destination = ticket.getRoute().getDestination().toUpperCase();
            String departureTime = ticket.getDepartureTime().format(customFormatter);
            String arrivalTime = ticket.getDepartureTime().plusMinutes(ticket.getDuration()).format(customFormatter);

            sb.append("│ ➤ ").append(String.format("%-" + (departure.length() + 1) + "" +"s", departure)).append("--------→ ").append(String.format("%-10s", destination)).append("\n")
                    .append("│    Departure: ").append(String.format("%-22s", departureTime)).append("\n")
                    .append("│    Arrival:   ").append(String.format("%-22s", arrivalTime)).append("\n");
        }

        sb.append("└──────────────────────────────────────────────┘\n");

        journeyNumber++;
    }

    return sb.toString();
}

    public static String displayMyReservations(List<Reservation> myReservations) {
        StringBuilder sb = new StringBuilder();
        int journeyNumber = 1;
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("d, MMMM yyyy, HH'h'mm'min'", Locale.ENGLISH);

        for (Reservation reservation : myReservations) {

            sb.append("┌──────────────────────────────────────────────┐\n")
                    .append("|  Reservation ").append(journeyNumber)
                    .append(String.format("%" + (40 - (String.valueOf(journeyNumber).length() + 12)) + "s", "Ticket N* " + journeyNumber)).append("    |\n");

            for (Ticket ticket : reservation.getTickets()) {
                String departure = ticket.getRoute().getDeparture().toUpperCase();
                String destination = ticket.getRoute().getDestination().toUpperCase();
                String departureTime = ticket.getDepartureTime().format(customFormatter);
                String arrivalTime = ticket.getDepartureTime().plusMinutes(ticket.getDuration()).format(customFormatter);
                String companyName = ticket.getContract().getPartner().getCompanyName();

                sb.append("├──────────────────────────────────────────────┤\n")
                        .append("|  Company: ").append(companyName)
                        .append(String.format("%" + (40 - companyName.length() - 10) + "s", " ")).append("\n")
                        .append("| ➤ ").append(String.format("%-" + (departure.length() + 1) + "s", departure))
                        .append("--------→ ").append(String.format("%-10s", destination)).append("\n")
                        .append("|    Departure: ").append(String.format("%-22s", departureTime)).append("\n")
                        .append("|    Arrival:   ").append(String.format("%-22s", arrivalTime)).append("\n");
            }

            sb.append("└──────────────────────────────────────────────┘\n\n");

            journeyNumber++;
        }

        return sb.toString();
    }





}
