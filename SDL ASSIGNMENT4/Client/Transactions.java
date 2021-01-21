package com.company;

import java.io.Serializable;

public class Transactions implements Serializable {
    private static final long serialVersionUID = 6128016096756071380L;

    private  String transactionDateAndTime;
    private  double transactionAmount;
    private double accountBalance;
    private  String transactionType;
    private  String transactionDescription;

    public Transactions(String transactionDateAndTime, double transactionAmount, double accountBalance, String transactionType, String transactionDescription) {
        this.transactionDateAndTime = transactionDateAndTime;
        this.transactionAmount = transactionAmount;
        this.accountBalance = accountBalance;
        this.transactionType = transactionType;
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionDateAndTime() {
        return transactionDateAndTime;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }
}
