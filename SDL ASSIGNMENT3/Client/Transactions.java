package com.company;

import java.io.Serializable;
import java.util.Date;

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

    public void showStatement(){
        System.out.format("%1$-25s%2$-10s%3$-12s%4$-10s%5$-21s",
                getTransactionDateAndTime(),
                getTransactionAmount(),
                getAccountBalance(),
                getTransactionType(),
                getTransactionDescription()
        );
        System.out.println();
    }

    public String getTransactionDateAndTime() {
        return transactionDateAndTime;
    }

    public void setTransactionDateAndTime(String transactionDateAndTime) {
        this.transactionDateAndTime = transactionDateAndTime;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
}
