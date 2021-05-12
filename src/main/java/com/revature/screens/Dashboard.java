package com.revature.screens;

import com.revature.Exceptions.InvalidRequestException;
import com.revature.daos.UserDAO;
import com.revature.main.Driver;
import com.revature.models.*;
import com.revature.services.UserService;
import com.revature.util.Cache;
import com.revature.util.Console;
import com.revature.util.List;
import com.revature.util.ScreenRouter;


/**
 * Dashboard
 *
 * Allows the user to create a new account or access an existing one.
 */
public class Dashboard extends Screen {

    private Console console;
    private ScreenRouter screenRouter;
    private UserService userService;
    private Cache cache;

    public Dashboard(Console console, ScreenRouter screenRouter, UserService userService, Cache cache) {
        super("Dashboard", "/dashboard");
        this.console = console;
        this.screenRouter = screenRouter;
        this.userService = userService;
        this.cache = cache;
    }

    /**
     * Presents the user with dialogue and performs various functions based on user input.
     */
    @Override
    public void render() {
        User loggedInUser = cache.getLoggedInUser();
        List<Account> accounts;
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
                accounts = userService.getAccounts(loggedInUser);
                Account accountOfChoice;
                if (accounts.isEmpty()) {
                    System.out.println("You do not have any registered accounts");
                    break;
                }

                for (Account account : accounts) {
                    System.out.printf("Account ID: %s\n", account.getAccountID());
                    System.out.printf("Name: %s\n", account.getName());
                    System.out.printf("Balance: $%.2f\n==========================\n", account.getBalance());
                }

                String name = console.getString("Specify the account name: ");

                try {
                    accountOfChoice = userService.getAccountByName(accounts, name);
                    cache.setActiveAccount(accountOfChoice);
                } catch (InvalidRequestException e) {
                    System.out.println(e.getMessage());
                    screenRouter.navigate("/dashboard");
                    break;
                }
                screenRouter.navigate("/account");
                break;
            case "2":
                String accountName = "";
                //keep re-prompting user for a non-empty string.
                while (accountName.trim().isEmpty()) accountName = console.getString("Please empty a non-empty account name: ");
                double initialBalance = console.getDouble("Enter an initial deposit: ", 0, Double.MAX_VALUE);

                try {
                    userService.openUserAccount(loggedInUser, accountName, initialBalance);
                    System.out.println("Account created successfully!");
                    cache.setActiveAccount(userService.getAccountByName(
                            userService.getAccounts(loggedInUser), accountName)); //This has to be the most cursed statement I have ever written.
                    screenRouter.navigate("/account");
                } catch (InvalidRequestException e) {
                    System.err.println(e.getMessage());
                }
                break;
            case "3":
                screenRouter.navigate("/welcome");
                break;
            default:
                System.out.println("The choice you entered was invalid");
        }
    }
}
