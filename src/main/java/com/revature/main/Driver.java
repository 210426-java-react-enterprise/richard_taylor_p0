package com.revature.main;


import com.revature.util.AppState;
import com.revature.util.Console;

import java.time.LocalDateTime;

public class Driver {


    private static AppState appState = new AppState();

    public static void main(String[] args) {
        AppState app = getAppState();

        while (app.isAppRunning()) {
            app.getScreenRouter().navigate("/welcome");
        }
    }

    public static AppState getAppState() {
        return appState;
    }

//    public static void main(String[] args) {
//        Console console = new Console();
//        LocalDateTime date = console.getDate(">");
//        System.out.println(date);
//    }
}
