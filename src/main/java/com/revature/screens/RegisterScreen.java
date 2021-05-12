package com.revature.screens;

import com.revature.Exceptions.InvalidRequestException;
import com.revature.Exceptions.ResourcePersistenceException;
import com.revature.daos.UserDAO;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.Console;

import java.util.Scanner;

public class RegisterScreen extends Screen {

    private Console console;
    private UserService userService;

    public RegisterScreen(Console console, UserService userService) {
        super("RegisterScreen", "/register");
        this.console = console;
        this.userService = userService;
    }

    /**
     * Presents the user with dialogue and performs various functions based on user input.
     */
    @Override
    public void render() {
        try {
            System.out.println("Registration");

            User user = new User();

            user.setUserName(console.getString("Enter a username(limit 50 characters): "));
            user.setPassword(console.getString("Enter a password: "));
            user.setEmail(console.getEmail("Enter an email address: "));
            user.setFirstName(console.getString("Enter your first name: "));
            user.setLastName(console.getString("Enter your last name: "));
            user.setBirthday(console.getDate("Enter your birthday (yyyy-MM-dd): "));
            user.setAge(console.getInt("Enter your age: "));

            userService.registerUser(user);
        } catch (InvalidRequestException | ResourcePersistenceException e) {
            System.err.println(e.getMessage());
        }
    }
}
