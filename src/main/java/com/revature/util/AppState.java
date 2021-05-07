package com.revature.util;

import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.User;
import com.revature.screens.Dashboard;
import com.revature.screens.LoginScreen;
import com.revature.screens.RegisterScreen;
import com.revature.screens.WelcomeScreen;

public class AppState {

    private Console console;
    private ScreenRouter screenRouter;
    private boolean appRunning;
    private User loggedInUser;

    public AppState() {
        System.out.println("Initializing application");

        setAppRunning(true);
        console = new Console();
        UserDAO userDAO = new UserDAO();
        AccountDAO accountDAO = new AccountDAO();

        screenRouter = new ScreenRouter();
        screenRouter.addScreen(new WelcomeScreen(console, userDAO, screenRouter))
                .addScreen(new LoginScreen(console, userDAO, screenRouter))
                .addScreen(new Dashboard(console, screenRouter, userDAO, accountDAO))
                .addScreen(new RegisterScreen(console, userDAO));

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
}
