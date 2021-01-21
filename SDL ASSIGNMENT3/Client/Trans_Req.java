package com.company;

import java.io.Serializable;

public class Trans_Req implements Serializable { //Class to Transfer Amount
    private static final long serialVersionUID = 6128016096756071380L;
    long accountNumberSender,accountNumberReceiver;
    double amount;

    public Trans_Req(long accountNumberSender, long accountNumberReceiver, double senderAmount) {  //Constructor
        this.accountNumberSender = accountNumberSender;
        this.accountNumberReceiver = accountNumberReceiver;
        this.amount = senderAmount;
    }
}
