package com.revature.util;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;

public class Cache { //Idea shamelessly plundered from Wezley

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
