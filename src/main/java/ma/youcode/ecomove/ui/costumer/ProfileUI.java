package ma.youcode.ecomove.ui.costumer;

import java.util.Scanner;

import ma.youcode.ecomove.entity.Passenger;
import ma.youcode.ecomove.enums.AuthStatus;
import ma.youcode.ecomove.service.AuthService;
import ma.youcode.ecomove.service.implementations.AuthServiceImp;
import ma.youcode.ecomove.ui.Menu;
import ma.youcode.ecomove.validator.Validator;

public class ProfileUI {

    private static  final Scanner sc = new Scanner(System.in);
    private static int choice;
    private static AuthService   authService;
    private static Validator  validator;


    static {
        authService = new AuthServiceImp();
        validator = new Validator();
    }
    public static Menu start(Menu menu) {
        do {
            System.out.println("\n================== Profile Menu ==================");
            System.out.println("1. Continue");
            System.out.println("0. Return to main menu");
            System.out.println("================== Profile Menu ==================\n");
            System.out.println("Choose an option :");
            choice = sc.nextInt();
            choice = getChoice();

            switch (choice) {
                case 1:
                    Passenger editPassenger = new Passenger();
                    editPassenger.setFirstName(validator.getValidStringInput("Enter your first name: ", "Please enter a valid first name."));
                    editPassenger.setFamilyName(validator.getValidStringInput("Enter your family name: ", "Please enter a valid family name."));
                    editPassenger.setPhone(validator.isValidPhoneNumber("Enter your phone number: ", "Please enter a valid phone number."));
                    AuthStatus status = authService.updateCustomer(editPassenger);
                    if (status.equals(AuthStatus.SUCCESS_UPDATE)) {
                        System.out.println("\n" + "\033[32m" + status.getMessage() + "\033[0m");
                        System.out.println("Your information after update: " +
                                "First Name: " + editPassenger.getFirstName() + ", " +
                                "Family Name: " + editPassenger.getFamilyName() + ", " +
                                "Email: " + editPassenger.getEmail() + ", " +
                                "Phone Number: " + editPassenger.getPhone());
                        break;
                    }

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
        while (choice < 0 || choice > 2) {
            System.out.println("Enter Number Between 0 - 2");
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
