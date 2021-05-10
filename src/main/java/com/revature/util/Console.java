package com.revature.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.*;

/**
 * Console
 * <p>
 * This class is used to get input from the user while performing basic type validation.
 * A scanner object is used for its convenient methods such as hasNextInt(), hasNextDouble(), etc.
 */
public class Console {

    private Scanner scanner;
    private Pattern emailRegex;

    public Console() {
        this.scanner = new Scanner(System.in);
        this.emailRegex = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"); //shamelessly copied from the internet
    }

    /**
     * Gets a string from the user. Does not perform any form of validation.
     *
     * @param prompt The prompt to be shown to the user
     * @return the string the user enters.
     */
    public String getString(String prompt) {
        System.out.print(prompt);

        return scanner.nextLine();
    }

    /**
     * Prompts the user to provide an Integer, and keeps re-prompting them until one is provided.
     *
     * @param prompt The prompt to be shown to the user.
     * @return The number the user enters, so long as it is a whole number
     */
    public int getInt(String prompt) {
        int number = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
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
     * Prompts the user to enter a decimal value.
     *
     * @param prompt The prompt to be shown to the user
     * @param min    The minimum acceptable value
     * @param max    The maximum acceptable value
     * @return The valid number entered by the user
     */
    public double getDouble(String prompt, double min, double max) {
        double number = 0.0;
        boolean valid = false;
        System.out.print(prompt);
        while (!valid) {
            if (scanner.hasNextDouble()) {
                number = scanner.nextDouble();
                if (number >= min && number <= max) {
                    valid = true;
                } else {
                    System.err.printf("Please input a number between %.2f and %.2f: ", min, max);
                }
            }
            scanner.nextLine();
        }
        return number;
    }

    /**
     * Prompts the user for a valid Date, adds time on the end of the string for parsing.
     *
     * @param prompt The prompt shown to the user
     * @return The valid date
     */
    public LocalDateTime getDate(String prompt) {
        boolean valid = false;
        String input = "";
        LocalDateTime Date = null;
        System.out.print(prompt);

        while (!valid) {
            try {
                input = scanner.next();
                input = input.concat("T00:00:00.000");
                Date = LocalDateTime.parse(input);
            } catch (DateTimeParseException e) {
                System.err.print("Invalid date format. Please try again\n" + e.getMessage() + "\nValid format: yyyy-MM-dd");
                continue;
            }
            scanner.nextLine();
            valid = true;
        }
        return Date;
    }

    /**
     * Uses regex to get a valid email from the user. Valid here is used loosely as "a@a" would be a valid email
     * according to the regex pattern. Can always make the pattern better if need be.
     *
     * @param prompt The prompt shown to the user
     * @return The validated email
     */
    public String getEmail(String prompt) {
        String email = "";
        System.out.print(prompt);

        while (true) {
            email = scanner.nextLine();
            Matcher matcher = emailRegex.matcher(email);
            boolean valid = matcher.find();
            if (!valid) {
                System.err.print("Error: Email was in an incorrect format. Please enter a valid email: ");
            } else {
                return email;
            }
        }
    }

    public void nextLine() {
        scanner.nextLine();
    }
}
