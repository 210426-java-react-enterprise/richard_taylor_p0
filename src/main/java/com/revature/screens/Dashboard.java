package com.revature.screens;

import com.revature.util.Console;


public class Dashboard extends Screen {

    private Console console;

    public Dashboard(Console console) {
        super("Dashboard", "/dashboard");
        this.console = console;
    }

    @Override
    public void render() {

    }
}
