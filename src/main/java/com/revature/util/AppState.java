package com.revature.util;

import com.revature.daos.UserDAO;
import com.revature.screens.Dashboard;
import com.revature.screens.LoginScreen;
import com.revature.screens.RegisterScreen;
import com.revature.screens.WelcomeScreen;

public class AppState {

    private Console console;
    private ScreenRouter screenRouter;
    private boolean appRunning;

    public AppState() {
        System.out.println("Initializing application");

        setAppRunning(true);
        console = new Console();
        UserDAO userDAO = new UserDAO();

        screenRouter = new ScreenRouter();
        screenRouter.addScreen(new WelcomeScreen(console, userDAO, screenRouter))
                .addScreen(new LoginScreen(console))
                .addScreen(new Dashboard(console))
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
}
