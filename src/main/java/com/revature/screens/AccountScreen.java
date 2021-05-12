package com.revature.screens;

import com.revature.Exceptions.ResourcePersistenceException;
import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.Cache;
import com.revature.util.Console;
import com.revature.util.List;
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
    private Cache cache;
    private UserService userService;

    public AccountScreen(Console console, UserDAO userDAO, ScreenRouter screenRouter, AccountDAO accountDAO, Cache cache, UserService userService) {
        super("AccountScreen", "/account");
        this.console = console;
        this.userDAO = userDAO;
        this.screenRouter = screenRouter;
        this.accountDAO = accountDAO;
        this.cache = cache;
        this.userService = userService;
    }

    /**
     * Presents the user with dialogue and performs various functions based on user input.
     */
    @Override
    public void render() {

        Account activeAccount = cache.getActiveAccount();
        User loggedInUser = cache.getLoggedInUser();
        Transaction transaction;
        System.out.println(activeAccount.getName());
        System.out.println("=============================");
        System.out.printf("Current account: %s\n", activeAccount.getName());
        System.out.printf("Current Balance: $%.2f\n", activeAccount.getBalance());
        System.out.println("Please choose an option");
        System.out.println("1) Deposit funds");
        System.out.println("2) Withdraw funds");
        System.out.println("3) Make a transfer");
        System.out.println("4) View transaction history for this account");
        System.out.println("5) Back to dashboard");

        String choice = console.getString("> ");
        double amount = 0.0;

        switch (choice) {
            case "1":
                amount = console.getDouble("Enter an amount: ", 0, Double.MAX_VALUE);
                accountDAO.addBalance(amount, activeAccount.getAccountID());
                try {
                    transaction = userService.recordTransaction(loggedInUser.getUserName(), activeAccount.getAccountID(), activeAccount.getAccountID(), "deposit", amount);
                    cache.getTransactions().add(transaction);
                } catch (ResourcePersistenceException e) {
                    System.err.println(e.getMessage());
                }
                System.out.printf("Your new balance is: $%.2f\n", activeAccount.getBalance() + amount);
                screenRouter.navigate("/dashboard");
                break;
            case "2":
                amount = console.getDouble("Enter an amount: ", 0, activeAccount.getBalance());
                accountDAO.subtractBalance(amount, activeAccount.getAccountID());
                try {
                    transaction = userService.recordTransaction(loggedInUser.getUserName(), activeAccount.getAccountID(), activeAccount.getAccountID(), "withdrawal", amount);
                    cache.getTransactions().add(transaction);
                } catch (ResourcePersistenceException e) {
                    System.err.println(e.getMessage());
                }
                System.out.printf("Your new balance is: $%.2f\n", activeAccount.getBalance() - amount);
                screenRouter.navigate("/dashboard");
                break;
            case "3":
                amount = console.getDouble("Enter an amount: ", 0, activeAccount.getBalance());
                int recipient = console.getInt("Enter the account id of the recipient: ");

                if (accountDAO.accountExists(recipient)) {
                    accountDAO.subtractBalance(amount, activeAccount.getAccountID());
                    accountDAO.addBalance(amount, recipient);
                    try {
                        transaction = userService.recordTransaction(loggedInUser.getUserName(), activeAccount.getAccountID(), recipient, "transfer", amount);
                        cache.getTransactions().add(transaction);
                    } catch (ResourcePersistenceException e) {
                        System.err.println(e.getMessage());
                    }
                    System.out.println("Transfer successful!");
                } else {
                    System.out.println("Transfer failed. The account specified does not exist.");
                }
                break;
            case "4":
                List<Transaction> transactions = cache.getTransactions();

                if(transactions == null) {
                    System.out.println("This account has no transaction history.");
                    this.render(); //Again, gross. I know.
                } else {
                    for(Transaction trans: transactions) {
                        System.out.printf("Sender: %s\n", trans.getSender());
                        System.out.printf("Sender ID: %d\n", trans.getSenderAccount());
                        System.out.printf("Recipient: %s\n", trans.getRecipient());
                        System.out.printf("Recipient ID: %d\n", trans.getRecipientAccount());
                        System.out.printf("Type: %s\n: ", trans.getTransactionType());
                        System.out.printf("Amount: $%.2f\n", trans.getAmount());
                        System.out.println("Date: " + trans.getDate());
                        System.out.println("===============================");
                    }
                }
                this.render();
                break;
            case "5":
                screenRouter.navigate("/dashboard");
                break;
            default:
                System.out.println("Your choice was invalid");
        }
    }
}

