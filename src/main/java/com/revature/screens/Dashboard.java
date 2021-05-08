package com.revature.screens;

import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.main.Driver;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.Console;
import com.revature.util.List;
import com.revature.util.ScreenRouter;


public class Dashboard extends Screen {

    private Console console;
    private ScreenRouter screenRouter;
    private UserDAO userDAO;
    private UserService userService;

    public Dashboard(Console console, ScreenRouter screenRouter, UserDAO userDAO, UserService userService) {
        super("Dashboard", "/dashboard");
        this.console = console;
        this.screenRouter = screenRouter;
        this.userDAO = userDAO;
        this.userService = userService;
    }

    @Override
    public void render() {
        User loggedInUser = Driver.getAppState().getLoggedInUser();
        System.out.println("Dashboard");
        System.out.println("================");
        System.out.printf("Welcome, %s !\n", loggedInUser.getUserName());
        System.out.println("What would you like to do today?");
        System.out.println("1) Access an account");
        System.out.println("2) Open a new account");
        System.out.println("3) Logout");

        String choice = (console.getString(">"));

        switch (choice) {
            case "1":
                List<Account> accounts = userService.getAccounts(loggedInUser);
                if(accounts == null) {
                    System.out.println("You do not have any registered accounts"); //prevent NPE
                    break;
                }
                for (Account account: accounts) {
                    System.out.printf("Name: %s\n", account.getName());
                    System.out.printf("Balance: %f\n==========================\n", account.getBalance());
                }
                String name = console.getString("Specify the account name: ");
                Account accountOfChoice = userService.getAccountByName(accounts, name);
                Driver.getAppState().setActiveAccount(accountOfChoice);
                screenRouter.navigate("/account");
                break;
            case "2":
                String accountName = console.getString("Enter a Name: ");
                double initialBalance = console.getDouble("Enter an initial deposit: ", 0, Double.MAX_VALUE);
                userService.openUserAccount(loggedInUser, accountName, initialBalance);
                break;
            case "3":
                break;
            default:
                System.out.println("The choice you entered was invalid");
        }
    }
}
