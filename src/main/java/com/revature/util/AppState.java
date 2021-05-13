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

    private final Console console;
    private final ScreenRouter screenRouter;
    private final UserService userService;
    private final UserDAO userDAO;
    private final AccountDAO accountDAO;
    private boolean appRunning;
    private Cache cache;


    /**
     * All persistence, console, screens, and business logic is instantiated here. Therefore, only one of each of these
     * objects can exist at any given time. They are used throughout the application through dependency injection.
     */
    public AppState() {
        System.out.println("Initializing application");

        setAppRunning(true);
        console = new Console();
        userDAO = new UserDAO();
        accountDAO = new AccountDAO();
        userService = new UserService(userDAO, accountDAO);
        cache = new Cache();


        screenRouter = new ScreenRouter();
        screenRouter.addScreen(new WelcomeScreen(console, screenRouter))
                .addScreen(new LoginScreen(console, userDAO, screenRouter, cache, userService))
                .addScreen(new Dashboard(console, screenRouter, userService, cache))
                .addScreen(new RegisterScreen(console, userService, screenRouter))
                .addScreen(new AccountScreen(console, userDAO, screenRouter, accountDAO, cache, userService));

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

}
