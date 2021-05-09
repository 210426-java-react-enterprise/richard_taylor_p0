package com.revature.screens;

import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.main.Driver;
import com.revature.models.Account;
import com.revature.util.Console;
import com.revature.util.ScreenRouter;

/**
 * AccountScreen
 * <p>
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

    /**
     * Presents the user with dialogue and performs various functions based on user input.
     */
    @Override
    public void render() {

        Account activeAccount = Driver.getAppState().getActiveAccount();

        System.out.println(activeAccount.getName());
        System.out.println("=============================");
        System.out.printf("Current account: %s\n", activeAccount.getName());
        System.out.printf("Current Balance: $%.2f\n", activeAccount.getBalance());
        System.out.println("Please choose an option");
        System.out.println("1) Deposit funds");
        System.out.println("2) Withdraw funds");
        System.out.println("3) Make a transfer");
        System.out.println("4) Back to dashboard");

        String choice = console.getString("> ");
        double amount = 0.0;

        switch (choice) {
            case "1":
                amount = console.getDouble("Enter an amount: ", 0, Double.MAX_VALUE);

                accountDAO.addBalance(amount, activeAccount.getAccountID());

                System.out.printf("Your new balance is: $%.2f\n", activeAccount.getBalance() + amount);

                screenRouter.navigate("/dashboard");
                break;
            case "2":
                amount = console.getDouble("Enter an amount: ", 0, activeAccount.getBalance());

                accountDAO.subtractBalance(amount, activeAccount.getAccountID());

                System.out.printf("Your new balance is: $%.2f\n", activeAccount.getBalance() - amount);

                screenRouter.navigate("/dashboard");
                break;
            case "3":
                amount = console.getDouble("Enter an amount: ", 0, activeAccount.getBalance());
                int recipient = console.getInt("Enter the account id of the recipient: ");

                if (accountDAO.accountExists(recipient)) {
                    accountDAO.subtractBalance(amount, activeAccount.getAccountID());
                    accountDAO.addBalance(amount, recipient);
                    System.out.println("Transfer successful!");
                } else {
                    System.out.println("Transfer failed. The account specified does not exist.");
                }
                break;
            case "4":
                screenRouter.navigate("/dashboard");
                break;
            default:
                System.out.println("Your choice was invalid");
        }
    }
}

