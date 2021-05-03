package com.revature.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.*;

/**
 * Console
 *
 * This class is used to get input from the user while performing basic validation.
 */
public class Console {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"); //shamelessly copied from the internet

    /**
     * Gets a string from the user. Does not perform any form of validation.
     * @param prompt The prompt to be shown to the user
     * @return the string the user enters.
     */
    public static String getString(String prompt) {
        System.out.print(prompt);

        return scanner.nextLine();
    }

    /**
     * Prompts the user to provide an Integer, and keeps re-prompting them until one is provided.
     *
     * @param prompt The prompt to be shown to the user.
     * @return The number the user enters, so long as it is a whole number
     */
    public static int getInt(String prompt) {
        int number = 0;
        boolean valid = false;
        while(!valid) {
            System.out.print(prompt);
            if(scanner.hasNextInt()) {
                number = scanner.nextInt();
                valid = true;
            } else {
                System.err.print("Error! Input must be a whole number.");
            }
            scanner.nextLine();
        }
        return number;
    }

    /**
     * Prompts the user to provide a number, and keeps re-prompting them until one is provided.
     *
     * @param prompt The prompt to be shown to the user.
     * @return The number the user enters.
     */
    public static double getDouble(String prompt) {
        double number = 0.0;
        boolean valid = false;
        while(!valid) {
            System.out.print(prompt);
            if(scanner.hasNextInt()) {
                number = scanner.nextInt();
                valid = true;
            } else {
                System.err.print("Error! Input must be a number.");
            }
            scanner.nextLine();
        }
        return number;
    }

    /**
     * TODO create documentation, and finish implementation
     */
    public static LocalDate getDate(String prompt) {
        boolean valid = false;
        String input = "";
        LocalDate Date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.print(prompt);

        while (!valid) {
            try {
                input = scanner.next();
                Date = LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("Invalid date format.");
                continue;
            }
            scanner.nextLine();
            valid = true;
        }
        return Date;
    }

    //TODO create javadoc
    public static String getEmail (String prompt) {
        String email = "";
        System.out.print(prompt);

        while (true) {
            email = scanner.nextLine();
            Matcher matcher = EMAIL_REGEX.matcher(email);
            boolean valid = matcher.find();
            if (!valid) {
                System.err.print("Error: Email was in an incorrect format. Please enter a valid email: ");
            } else {
                return email;
            }
        }
    }

}
