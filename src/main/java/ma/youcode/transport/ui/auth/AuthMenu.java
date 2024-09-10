package ma.youcode.transport.ui.auth;

import java.util.Scanner;

import ma.youcode.transport.entity.Passenger;
import ma.youcode.transport.enums.AuthStatus;
import ma.youcode.transport.service.AuthService;
import ma.youcode.transport.service.implementations.AuthServiceImp;
import ma.youcode.transport.ui.Menu;
import ma.youcode.transport.ui.costumer.CostumerMenu;
import ma.youcode.transport.utils.Session;
import ma.youcode.transport.validator.Validator;

public class AuthMenu {

    private static final Scanner sc = new Scanner(System.in);
    private static final Validator validator = new Validator();
    private static final AuthService authService;
    static {
        authService = new AuthServiceImp();
    }

    public static Menu start(Menu menu) {
        int choice;
        do {
            System.out.println("\n================== Authentication Costumers ==================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Back to main menu");
            System.out.println("================== Authentication Costumers ==================\n");
            System.out.print("Choose an option: ");

            choice = getChoice();

            switch (choice) {
                case 1:
                    handleLogin(menu);
                    break;
                case 2:
                    handleRegistration(menu);
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);

        return menu;
    }

    private static void handleLogin(Menu menu) {
        while (true) {
            String email = validator.isValidEmail("Enter your email address: ", "Please enter a valid email address.");
            AuthStatus status = authService.login(email);
            if (status.equals(AuthStatus.SUCCESS_LOGIN)) {
                System.out.println("\n" + "\033[32m" + status.getMessage() + "\033[0m");
                CostumerMenu.start(menu);
                break;
            } else {
                System.out.println("\n" + "\033[41m" + status.getMessage() + "\033[0m");
                String answer = validator.getValidStringInput("Would you like to register? (yes/no): ", "Please enter 'yes' or 'no'");
                if (answer.equalsIgnoreCase("yes")) {
                    handleRegistration(menu);
                    break;
                } else {
                    System.out.println("Returning to the authentication menu...");
                    break;
                }
            }
        }
    }

    private static void handleRegistration(Menu menu) {
        while (true) {
            Passenger newPassenger = new Passenger();
            newPassenger.setFirstName(validator.getValidStringInput("Enter your first name: ", "Please enter a valid first name."));
            newPassenger.setFamilyName(validator.getValidStringInput("Enter your family name: ", "Please enter a valid family name."));
            newPassenger.setEmail(validator.isValidEmail("Enter your email address: ", "Please enter a valid email address."));
            newPassenger.setPhone(validator.isValidPhoneNumber("Enter your phone number: ", "Please enter a valid phone number."));

            AuthStatus status = authService.register(newPassenger);

            if (status.equals(AuthStatus.SUCCESS_REGISTRATION)) {
                System.out.println("\n" + "\033[32m" + status.getMessage() + "\033[0m");
                CostumerMenu.start(menu);
                break;
            } else if (status.equals(AuthStatus.EMAIL_ALREADY_REGISTERED)) {
                System.out.println("\n" + "\033[33m" + status.getMessage() + "\033[0m");
                System.out.println("\033[44m Welcome back " + Session.getLoggedEmail() + " \033[0m");
                CostumerMenu.start(menu);
                break;
            } else {
                String retry = validator.getValidStringInput("Would you like to try again? (yes/no): ", "Please enter 'yes' or 'no'");
                if (retry.equalsIgnoreCase("no")) {
                    System.out.println("Returning to the authentication menu...");
                    break;
                }
            }
        }
    }

    private static int getChoice() {
        int choice = -1;
        while (choice < 0 || choice > 2) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (choice < 0 || choice > 2) {
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
