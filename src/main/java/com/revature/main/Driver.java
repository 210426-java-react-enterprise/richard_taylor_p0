package com.revature.main;

import com.revature.util.AppState;

/**
 * Main class, runs the application via a while loop that checks the state of the application on every iteration.
 * When the state is false, falls through to exit the application gracefully.
 */
public class Driver {

    private static final AppState appState = new AppState();

    public static void main(String[] args) {
        AppState app = getAppState();

        while (app.isAppRunning()) {
            app.getScreenRouter().navigate("/welcome");
        }
    }

    /**
     * Used to get the appState object for a few purposes.
     *
     * @return the appState object.
     */
    public static AppState getAppState() {
        return appState;
    }

}
