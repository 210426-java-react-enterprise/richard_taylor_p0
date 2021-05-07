package com.revature.screens;

import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.main.Driver;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.Console;
import com.revature.util.List;
import com.revature.util.ScreenRouter;


public class Dashboard extends Screen {

    private Console console;
    private ScreenRouter screenRouter;
    private UserDAO userDAO;
    private AccountDAO accountDAO;

    public Dashboard(Console console, ScreenRouter screenRouter, UserDAO userDAO, AccountDAO accountDAO) {
        super("Dashboard", "/dashboard");
        this.console = console;
        this.screenRouter = screenRouter;
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
    }

    @Override
    public void render() {
        User loggedInUser = Driver.getAppState().getLoggedInUser();
        System.out.println("Dashboard");
        System.out.println("================");
        System.out.printf("Welcome, %s !\n", loggedInUser.getUserName());
        System.out.println("What would you like to do today?");
        System.out.println("1) Access your accounts");
        System.out.println("2) Open a new account");
        System.out.println("3) Logout");

        String choice = (console.getString(">"));

        switch (choice) {
            case "1":
                List<Account> accounts = accountDAO.getAccountsByUserID(loggedInUser);
                for (Account account: accounts) {
                    System.out.printf("Name: %s\n", account.getName());
                    System.out.printf("Balance: %f\n", account.getBalance());
                }
                break;
            case "2":
                String accountName = console.getString("Enter a Name: ");
                double initialBalance = console.getDouble("Enter an initial deposit: ");
                accountDAO.openAccount(loggedInUser.getUserID(), accountName, initialBalance);
                break;
            case "3":
                break;
            default:
                System.out.println("The choice you entered was invalid");
        }
    }
}
