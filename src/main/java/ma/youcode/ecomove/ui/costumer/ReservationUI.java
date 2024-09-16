package ma.youcode.ecomove.ui.costumer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import ma.youcode.ecomove.entity.Passenger;
import ma.youcode.ecomove.entity.Preference;
import ma.youcode.ecomove.entity.Reservation;
import ma.youcode.ecomove.entity.Ticket;
import ma.youcode.ecomove.enums.TransportationType;
import ma.youcode.ecomove.service.PreferenceService;
import ma.youcode.ecomove.service.ReservationService;
import ma.youcode.ecomove.service.TicketService;
import ma.youcode.ecomove.service.implementations.PreferenceServiceImp;
import ma.youcode.ecomove.service.implementations.ReservationServiceImp;
import ma.youcode.ecomove.service.implementations.TicketServiceImp;
import ma.youcode.ecomove.ui.Menu;
import ma.youcode.ecomove.utils.Session;
import ma.youcode.ecomove.validator.Validator;

public class ReservationUI {

    private static final Validator validator;
    private static final TicketService  ticketService;
    private static final ReservationService reservationService;
    private static final PreferenceService preferenceService;

    private static final Scanner sc = new Scanner(System.in);
    private static int choice;

    static {
        validator = new Validator();
        ticketService = new TicketServiceImp();
        reservationService = new ReservationServiceImp();
        preferenceService = new PreferenceServiceImp();
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
                    System.out.println("You have chosen the 'Make Reservation' option.\n");

                    System.out.println("Would you like to search using your saved preferences? (yes/no): ");
                    String option = sc.next();
                    List<List<Ticket>> availableRoutes = new ArrayList<>();
                    String departure , destination;
                    LocalDateTime departureTime;
                    TransportationType type;
                    Preference preference;
                    if (option.equals("yes")) {
                        List<Preference> preferences = preferenceService.getMyPreferences();
                        if (preferences.size() > 0) {
                            displayPreferences(preferences);
                            System.out.println("Choose your preference: ");
                            int preferenceIndex = sc.nextInt();
                            while (preferenceIndex < 1 || preferenceIndex > preferences.size()) {
                                System.out.println("Please enter a valid preference number: ");
                                preferenceIndex = sc.nextInt();
                            }
                            Preference selectedPreference = preferences.get(preferenceIndex - 1);
                            departure = selectedPreference.getPreferredDeparture();
                            destination = selectedPreference.getPreferredDestination();
                            departureTime = selectedPreference.getPreferredDepartureTime();
                            type = selectedPreference.getPreferredTransportationType();
                            availableRoutes = ticketService.availbeJourneys(departure, destination, departureTime, type);
                        } else {
                            System.out.println("No saved preferences found. Switching to manual search.");

                            departure = validator.getValidStringInput("Enter your departure: ", "Please enter a valid departure, try again: ");
                            destination = validator.getValidStringInput("Enter your destination: ", "Please enter a valid destination, try again: ");
                            departureTime = validator.getValidLocalDateTime("Departure Time for journey: ");
                            type = validator.choiceOption(TransportationType.class);
                            availableRoutes = ticketService.availbeJourneys(departure, destination, departureTime ,type);
                            preference = new Preference();
                            preference.setPreferenceId(UUID.randomUUID().toString());
                            preference.setPreferredDeparture(departure);
                            preference.setPreferredDepartureTime(departureTime);
                            preference.setPreferredDestination(destination);
                            preference.setPreferredTransportationType(type);
                            Passenger passenger = new Passenger();
                            passenger.setEmail(Session.getLoggedEmail());
                            preference.setPassenger(passenger);
                            System.out.println("Do you would to save this like a preference? (yes/no): ");
                            String request = sc.next();
                            if (request.equals("yes")) {
                                preferenceService.addPreference(preference);
                            }
                        }
                    } else {
                        departure = validator.getValidStringInput("Enter your departure: ", "Please enter a valid departure, try again: ");
                        destination = validator.getValidStringInput("Enter your destination: ", "Please enter a valid destination, try again: ");
                        departureTime = validator.getValidLocalDateTime("Departure Time for journey: ");
                        type = validator.choiceOption(TransportationType.class);
                        availableRoutes = ticketService.availbeJourneys(departure, destination, departureTime , type);
                    }



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
                            sc.nextLine();
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
        Double price = 0.00;
        for (Ticket ticket : journey) {
            price += ticket.getSellingPrice();
        }
        sb.append("┌──────────────────────────────────────────────┐\n")
                .append("│  Journey ").append(journeyNumber)
                .append(String.format("%" + (45 - (String.valueOf(journeyNumber).length() + 10)) + "s", "Price -->" + price + "$" )).append(" \n")
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


    public static void displayPreferences(List<Preference> preferences) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d, MMMM yyyy, HH'h'mm'min'", Locale.ENGLISH);
        int BOX_WIDTH = 50;
        // Check if preferences are available
        if (preferences.size() > 0) {
            for (int i = 0; i < preferences.size(); i++) {
                Preference preference = preferences.get(i);
                // Print each preference in a box-like format with a header
                System.out.println(" _______________________________________________ ");
                System.out.println("| Preference #" + (i + 1) + "                                |");
                System.out.println("| Departure City: " + formatString(preference.getPreferredDeparture(), BOX_WIDTH - 21) + " |");
                System.out.println("| Arrival City: " + formatString(preference.getPreferredDestination(), BOX_WIDTH - 16) + " |");
                System.out.println("| Departure Time: " + formatString(preference.getPreferredDepartureTime().format(formatter), BOX_WIDTH - 22) + " |");
                System.out.println("| Transportation Type: " + formatString(preference.getPreferredTransportationType().toString(), BOX_WIDTH - 28) + " |");
                System.out.println("|_______________________________________________|");
                System.out.println(); // Add a blank line between preferences
            }
        } else {
            System.out.println("No saved preferences found.");
        }
    }

    // Helper method to format strings with a fixed width
    private static String formatString(String input, int width) {
        if (input.length() > width) {
            return input.substring(0, width - 3) + "..."; // Truncate long strings
        } else {
            return String.format("%-" + width + "s", input); // Pad strings to the specified width
        }
    }



}
