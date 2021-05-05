package com.revature.screens;

import com.revature.util.Console;

import java.util.Scanner;

public class WelcomeScreen extends Screen {

    private Console console;

    public WelcomeScreen(Console console) {
        super("WelcomeScreen", "/welcome");
        this.console = console;
    }

    @Override
    public void render() {

    }
}
