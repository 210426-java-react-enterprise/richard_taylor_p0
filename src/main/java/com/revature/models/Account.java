package com.revature.models;

public class Account {
    private User user;
    private double balance;

    public Account(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance (double amount) {
        balance = getBalance() + amount;
    }

    public User getUser () {
        return this.user;
    }
}
