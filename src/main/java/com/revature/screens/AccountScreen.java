package com.revature.screens;

import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.main.Driver;
import com.revature.models.Account;
import com.revature.util.Console;
import com.revature.util.ScreenRouter;

/**
 * Class for a user to interact with one specific account at a time
 */
public class AccountScreen extends Screen {

    private Console console;
    private UserDAO userDAO;
    private ScreenRouter screenRouter;
    private AccountDAO accountDAO;

    public AccountScreen(Console console, UserDAO userDAO, ScreenRouter screenRouter, AccountDAO accountDAO) {
        super("AccountScreen", "/account");
        this.console = console;
        this.userDAO = userDAO;
        this.screenRouter = screenRouter;
        this.accountDAO = accountDAO;
    }

    @Override
    public void render() {

        Account activeAccount = Driver.getAppState().getActiveAccount();

        System.out.println(activeAccount.getName());
        System.out.println("=============================");
        System.out.println("Please choose an option");
        System.out.println("1) Deposit funds");
        System.out.println("2) Withdraw funds");
        System.out.println("3) Back to dashboard");
        String choice = console.getString("> ");
        double amount = 0.0;

        switch (choice) {
            case "1":
                amount = console.getDouble("Enter an amount: ", 0, Double.MAX_VALUE);
                activeAccount.addBalance(amount);
                accountDAO.saveBalance(activeAccount.getBalance(), activeAccount.getAccountID());
                System.out.printf("Your new balance is: %f\n", activeAccount.getBalance());
                screenRouter.navigate("/dashboard");
                break;
            case "2":
                amount = console.getDouble("Enter an amount: ", 0, activeAccount.getBalance());
                activeAccount.subtractBalance(amount);
                accountDAO.saveBalance(activeAccount.getBalance(), activeAccount.getAccountID());
                System.out.printf("Your new balance is: %f\n", activeAccount.getBalance());
                screenRouter.navigate("/dashboard");
                break;
            case "3":
                screenRouter.navigate("/dashboard");
            default:
                System.out.println("Your choice was invalid");
        }

    }

}

