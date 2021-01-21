package com.company;

import java.io.Serializable;

public class Transactions implements Serializable {
    private static final long serialVersionUID = 6128016096756071380L;
    private  String transactionDateAndTime;
    private  double transactionAmount;
    private double accountBalance;
    private  String transactionType;
    private  String transactionDescription;

    public Transactions() {
    }
    public void setTransactionDateAndTime(String transactionDateAndTime) {
        this.transactionDateAndTime = transactionDateAndTime;
    }
    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
}
