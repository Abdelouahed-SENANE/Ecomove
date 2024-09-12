package ma.youcode.transport.validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * A generic validator class for various input types.
 */
public class Validator {
    private Scanner sc;
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    public Validator() {
        sc = new Scanner(System.in);
    }

    /**
     * Gets a valid string input from the user.
     * @param prompt The input prompt message.
     * @param errorMessage The error message for invalid input.
     * @return A valid non-empty string.
     */
    public String getValidStringInput(String prompt, String errorMessage) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (isString(input)) {
                return input;
            } else {
                System.out.println(errorMessage);
            }
        }
    }

    /**
     * Gets a valid timestamp input from the user.
     * @param entity The input prompt message.
     * @return A valid timestamp.
     */
    public LocalDate getValidLocalDate(String entity) {
        while (true) {
            int year;
            while (true) {
                System.out.print("Please enter the year for the " + entity + " (e.g., 2024): ");
                if (sc.hasNextInt()) {
                    year = sc.nextInt();
                    int currentYear = LocalDate.now().getYear();
                    if (year < currentYear) {
                        System.out.println("The year cannot be in the past. Please try again.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid year.");
                    sc.next();
                }
            }

            int month;
            while (true) {
                System.out.print("Please enter the month for the " + entity + " (1-12): ");
                if (sc.hasNextInt()) {
                    month = sc.nextInt();
                    if (month < 1 || month > 12) {
                        System.out.println("Invalid month. Please enter a value between 1 and 12.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid month (1-12).");
                    sc.next();
                }
            }

            int day;
            while (true) {
                System.out.print("Please enter the day for the " + entity + " (1-31): ");
                if (sc.hasNextInt()) {
                    day = sc.nextInt();
                    if (day < 1 || day > 31) {
                        System.out.println("Invalid day. Please enter a value between 1 and 31.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid day (1-31).");
                    sc.next();
                }
            }
            try {
                return LocalDate.of(year, month, day);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd HH:mm.");
            }catch (Exception e) {
                System.out.println("Invalid input. Please enter numeric values.");
                sc.next();
            }
        }
    }
    public LocalDateTime getValidLocalDateTime(String entity) {
        while (true) {
            int year;
            while (true) {
                System.out.print("Please enter the year for the " + entity + " (e.g., 2024): ");
                if (sc.hasNextInt()) {
                    year = sc.nextInt();
                    int currentYear = LocalDate.now().getYear();
                    if (year < currentYear) {
                        System.out.println("The year cannot be in the past. Please try again.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid year.");
                    sc.next();
                }
            }

            int month;
            while (true) {
                System.out.print("Please enter the month for the " + entity + " (1-12): ");
                if (sc.hasNextInt()) {
                    month = sc.nextInt();
                    if (month < 1 || month > 12) {
                        System.out.println("Invalid month. Please enter a value between 1 and 12.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid month (1-12).");
                    sc.next();
                }
            }

            int day;
            while (true) {
                System.out.print("Please enter the day for the " + entity + " (1-31): ");
                if (sc.hasNextInt()) {
                    day = sc.nextInt();
                    if (day < 1 || day > 31) {
                        System.out.println("Invalid day. Please enter a value between 1 and 31.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid day (1-31).");
                    sc.next();
                }
            }

            int hour;
            while (true) {
                System.out.print("Please enter the hour for the " + entity + " (0-23): ");
                if (sc.hasNextInt()) {
                    hour = sc.nextInt();
                    if (hour < 0 || hour > 23) {
                        System.out.println("Invalid hour. Please enter a value between 0 and 23.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid hour (0-23).");
                    sc.next();
                }
            }

            int min;
            while (true) {
                System.out.print("Please enter the minute for the " + entity + " (0-59): ");
                if (sc.hasNextInt()) {
                    min = sc.nextInt();
                    if (min < 0 || min > 59) {
                        System.out.println("Invalid minute. Please enter a value between 0 and 59.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid minute (0-59).");
                    sc.next();  // clear invalid input
                }
            }
            try {
                return LocalDateTime.of(year, month, day, hour, min);
            } catch (DateTimeException e) {
                System.out.println("Invalid date or time combination. Please try again.");
            }
        }
    }


    /**
     * Gets a valid double input from the user.
     * @param prompt The input prompt message.
     * @param errorMessage The error message for invalid input.
     * @return A valid double value.
     */
    public double getValidDoubleInput(String prompt, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }

    /**
     * Gets a valid boolean input from the user.
     * @param prompt The input prompt message.
     * @param errorMessage The error message for invalid input.
     * @return A valid boolean value.
     */
    public boolean getValidBooleanInput(String prompt, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            } else {
                System.out.println(errorMessage);
            }
        }
    }

    public Integer convertToMinutes(String input) {

        int totalMinutes = 0;
        String[] hourPart = input.split("h");
        if (hourPart.length > 0) {
            int hours = Integer.parseInt(hourPart[0].trim());
            totalMinutes += hours * 60;
        }
        if (hourPart.length > 1) {
            String minutePart = hourPart[1].replace("min" , "").trim();
            int minutes = Integer.parseInt(minutePart);
            totalMinutes += minutes;
        }
        return totalMinutes;
    }
    public String getValidDuration() {
        String duration;
        String REGEX_DURATION = "(\\d+)h(\\d+)min";
        while (true) {
            System.out.print("Enter duration e.g(2h15min): ");
            duration = sc.nextLine();
            if (duration.matches(REGEX_DURATION)){
                return duration;
            }else {
                System.out.println("Invalid duration. Please try again.");
            }
        }




    }

    /**
     * Validates if a string is non-null and non-empty.
     * @param str The string to validate.
     * @return true if the string is valid; false otherwise.
     */
    public static boolean isString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Validates if an input string matches any value in a given enum.
     * @param input The input string.
     * @param enumClass The enum class to check against.
     * @return true if the input is a valid enum value; false otherwise.
     */
    public static <E extends Enum<E>> boolean isValidEnum(String input, Class<E> enumClass) {
        try {
            Enum.valueOf(enumClass, input.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Gets a valid enum value from the user.
     * @param prompt The input prompt message.
     * @param enumClass The enum class to check against.
     * @param <E> The type of the enum.
     * @return A valid enum value.
     */
    public <E extends Enum<E>> E getValidEnumInput(String prompt, Class<E> enumClass) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().toUpperCase();
            if (isValidEnum(input, enumClass)) {
                return Enum.valueOf(enumClass, input);
            } else {
                System.out.println("Invalid input. Please enter a valid value from the list.");
            }
        }
    }

    public <E extends Enum<E>> E choiceOption(Class<E> enumClass) {
        E[] enumConstants = enumClass.getEnumConstants();
        if (enumConstants == null || enumConstants.length == 0) {
            throw new IllegalArgumentException("Enum class must have at least one enum constant");
        }
        System.out.println("Choose an option:");
        for (int i = 0; i < enumConstants.length; i++) {
            System.out.println((i + 1) + " - " + enumConstants[i].name());
        }
        while (true) {
            try {
                int choice = sc.nextInt();
                if (choice < 1 || choice > enumConstants.length) {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + enumConstants.length);
                    continue;
                }
                sc.nextLine();
                return enumConstants[choice - 1];
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            sc.nextLine();
        }
    }


    public  String isValidEmail(String label , String errorMessage) {
        String input;
        String  EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        while (true) {
            System.out.print(label);
            input = sc.nextLine().trim();
            if (input.matches(EMAIL_REGEX)) {
                return input;
            }else {
                System.out.println(errorMessage);
            }
        }
    }
    public String isValidPhoneNumber(String label , String errorMessage) {
        String input;
        String  PHONE_REGEX = "^0[5-7]\\d{8}$";
        while (true) {
            System.out.print(label);
            input = sc.nextLine().trim();
            if (input.matches(PHONE_REGEX)) {
                return input;
            }else {
                System.out.println(errorMessage);
            }
        }
    }

}
