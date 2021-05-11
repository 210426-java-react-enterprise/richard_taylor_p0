package com.revature.models;

/**
 * Account
 * <p>
 * POJO to represent a bank account within the application.
 */
public class Account {

    private int accountID;
    private double balance;
    private String name;

    public Account() {

    }

    public Account(int accountID, double balance, String name) {
        this.accountID = accountID;
        this.balance = balance;
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addBalance(double amount) {
        balance = getBalance() + amount;
    }

    public void subtractBalance(double amount) {
        balance = getBalance() - amount;
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
