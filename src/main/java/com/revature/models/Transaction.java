package com.revature.models;

import java.time.LocalDateTime;

public class Transaction {

    private int transactionID;
    private String sender;
    private int senderAccount;
    private String recipient;
    private int recipientAccount;
    private String transactionType;
    private double amount;
    private LocalDateTime date;

    public Transaction () {

    }

    public Transaction (int transactionID, String sender, int senderAccount, String recipient,
                        int recipientAccount, double amount, LocalDateTime date, String transactionType) {
        this.transactionID = transactionID;
        this.sender = sender;
        this.senderAccount = senderAccount;
        this.recipient = recipient;
        this.recipientAccount = recipientAccount;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.transactionType = transactionType;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(int senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public int getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(int recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}