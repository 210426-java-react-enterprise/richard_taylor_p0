package com.revature.screens;

import com.revature.util.Console;

import java.util.Scanner;

public class LoginScreen extends Screen {

    private Console console;


    public LoginScreen(Console console) {
        super("LoginScreen", "/login");
        this.console = console;
    }

    @Override
    public void render() {

    }
}
