package com.revature.util;

import com.revature.models.*;

/**
 * Cache
 *
 * This Class is used to store a cache of information to prevent needless calls to the database.
 */
public class Cache { //Idea shamelessly stolen from Wezley

    private User loggedInUser;
    private Account activeAccount;
    private List<Transaction> transactions;

    public Cache() {

    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
