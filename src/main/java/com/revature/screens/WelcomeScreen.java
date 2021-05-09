package com.revature.screens;

import com.revature.main.Driver;
import com.revature.util.Console;
import com.revature.util.ScreenRouter;

/**
 * WelcomeScreen
 *
 * The default home screen. Allows user to choose if they want to log in, or register for a new user account.
 */
public class WelcomeScreen extends Screen {

    private Console console;
    private ScreenRouter router;

    public WelcomeScreen(Console console, ScreenRouter router) {
        super("WelcomeScreen", "/welcome");
        this.console = console;
        this.router = router;
    }

    /**
     * Presents dialog options and allows the user to make a choice.
     */
    @Override
    public void render() {
        String choice = "";
        System.out.println("Welcome to the Bank of Pepega!");
        System.out.println("Please choose an option");
        System.out.println("1) Login");
        System.out.println("2) Register");
        System.out.println("3) Exit");
        choice = console.getString("> ");

        switch (choice) {
            case "1":
                router.navigate("/login");
                break;
            case "2":
                router.navigate("/register");
                break;
            case "3":
                Driver.getAppState().setAppRunning(false);
                break;
            default:
                System.out.println("The choice you entered was invalid");
        }
    }
}
