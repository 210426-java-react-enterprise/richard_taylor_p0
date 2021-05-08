package com.revature.util;

import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.screens.*;
import com.revature.services.UserService;

public class AppState {

    private Console console;
    private ScreenRouter screenRouter;
    private boolean appRunning;
    private User loggedInUser;
    private Account activeAccount;
    private UserService userService;

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


    public Console getConsole() {
        return console;
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
