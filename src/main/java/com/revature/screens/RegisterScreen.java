package com.revature.screens;

import com.revature.daos.UserDAO;
import com.revature.models.User;
import com.revature.util.Console;

import java.util.Scanner;

public class RegisterScreen extends Screen {

    private Console console;
    private  UserDAO userDAO;

    public RegisterScreen(Console console, UserDAO userDAO) {
        super("RegisterScreen", "/register");
        this.console = console;
        this.userDAO = userDAO;
    }

    @Override
    public void render() {
        System.out.println("Registration");
        User user = new User();
        user.setUserName(console.getString("Enter a username: "));
        user.setPassword(console.getString("Enter a password: "));
        user.setEmail(console.getEmail("Enter an email address: "));
        user.setFirstName(console.getString("Enter your first name: "));
        user.setLastName(console.getString("Enter your last name: "));
        user.setBirthday(console.getDate("Enter your birthday (yyyy-MM-dd): "));
        user.setAge(console.getInt("Enter your age: "));
        userDAO.saveUserToDataBase(user);
    }
}
