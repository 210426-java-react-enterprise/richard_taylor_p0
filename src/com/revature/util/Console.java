package com.revature.util;

import java.util.Scanner;

/**
 * Console
 *
 * This class is used to get input from the user, and in the case of numbers, perform basic validation.
 */
public class Console {

    /**
     * Gets a string from the user. Does not perform any form of validation.
     * @param scanner The scanner to be passed to this method.
     * @param prompt The prompt to be shown to the user
     * @return the string the user enters.
     */
    public static String getString(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    /**
     * Prompts the user to provide an Integer, and keeps re-prompting them until one is provided.
     *
     * @param scanner The scanner passed into this method.
     * @param prompt The prompt to be shown to the user.
     * @return
     */
    public static int getInt(Scanner scanner, String prompt) {
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
     * @param scanner The scanner passed into this method.
     * @param prompt The prompt to be shown to the user.
     * @return
     */
    public static double getDouble(Scanner scanner, String prompt) {
        double number = 0.0;
        boolean valid = false;
        while(!valid) {
            System.out.print(prompt);
            if(scanner.hasNextInt()) {
                number = scanner.nextInt();
                valid = true;
            } else {
                System.out.print("Error! Input must be a number.");
            }
            scanner.nextLine();
        }
        return number;
    }

}
