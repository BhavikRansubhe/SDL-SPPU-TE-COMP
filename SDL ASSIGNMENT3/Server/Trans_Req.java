package com.company;

import java.io.Serializable;

public class Trans_Req implements Serializable {  //Transfer Amount Class
    private static final long serialVersionUID = 6128016096756071380L;
    long accountNumberSender,accountNumberReceiver; // fields
    double amount;

    public Trans_Req(long accountNumberSender, long accountNumberReceiver, double amount) { //Constructor
        this.accountNumberSender = accountNumberSender;
        this.accountNumberReceiver = accountNumberReceiver;
        this.amount = amount;
    }
    public long getAccountNumberSender() {
        return accountNumberSender;
    }
    public double getAmount() {
        return amount;
    }
}
