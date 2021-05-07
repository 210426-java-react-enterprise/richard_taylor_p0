package com.revature.screens;

import com.revature.daos.UserDAO;
import com.revature.main.Driver;
import com.revature.models.User;
import com.revature.util.Console;
import com.revature.util.ScreenRouter;

import java.util.Scanner;

public class LoginScreen extends Screen {

    private Console console;
    private UserDAO userDAO;
    private ScreenRouter screenRouter;

    public LoginScreen(Console console, UserDAO userDAO, ScreenRouter screenRouter) {
        super("LoginScreen", "/login");
        this.console = console;
        this.userDAO = userDAO;
        this.screenRouter = screenRouter;
    }

    @Override
    public void render() {
        System.out.println("Login Screen");
        String username = console.getString("Enter your username: ");
        String password =  console.getString("Enter your password: ");
        User user = userDAO.getUserByUserNameAndPassword(username, password);
        if(user != null) {
            System.out.println("Login Successful!");
            System.out.println(user.toString());
            Driver.getAppState().setLoggedInUser(user);
            screenRouter.navigate("/dashboard");
        } else {
            System.out.println("Login Failed :(");
        }
    }
}
