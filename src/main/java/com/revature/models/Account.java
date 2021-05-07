package com.revature.models;

public class Account {
    //private User user;
    private int accountID;
    private double balance;
    private String name;
    //private boolean isActive;

//    public Account(User user) {
//        this.user = user;
//    }

    public Account () {

    }

    public double getBalance() {
        return balance;
    }

    public void setBalance (double balance) {
        this.balance = balance;
    }

    public void addBalance (double amount) {
        balance = getBalance() + amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

//    public User getUser () {
//        return this.user;
//    }
}
