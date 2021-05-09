package com.revature.util;

import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.screens.*;
import com.revature.services.UserService;

/**
 * AppState
 * <p>
 * This class encapsulates the state of the entire application. All important objects
 * live here, including the boolean to tell the driver if the application is running.The application is written in a way
 * that only one instance of this class will ever be instantiated at one time. This also holds the current logged in user
 * and their active account.
 */
public class AppState {

    private Console console;
    private ScreenRouter screenRouter;
    private boolean appRunning;
    private User loggedInUser;
    private Account activeAccount;
    private UserService userService;

    /**
     * All persistence, console, screens, and business logic is instantiated here. Therefore, only one of each of these
     * objects can exist at any given time. They are used throughout the application through dependency injection.
     */
    public AppState() {
        System.out.println("Initializing application");

        setAppRunning(true);
        console = new Console();
        UserDAO userDAO = new UserDAO();
        AccountDAO accountDAO = new AccountDAO();
        userService = new UserService(userDAO, accountDAO);


        screenRouter = new ScreenRouter();
        screenRouter.addScreen(new WelcomeScreen(console, screenRouter))
                .addScreen(new LoginScreen(console, userDAO, screenRouter))
                .addScreen(new Dashboard(console, screenRouter, userDAO, userService))
                .addScreen(new RegisterScreen(console, userService))
                .addScreen(new AccountScreen(console, userDAO, screenRouter, accountDAO));

        System.out.println("Application ready");

    }


    public boolean isAppRunning() {
        return appRunning;
    }

    public void setAppRunning(boolean appRunning) {
        this.appRunning = appRunning;
    }

    public ScreenRouter getScreenRouter() {
        return screenRouter;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }
}
