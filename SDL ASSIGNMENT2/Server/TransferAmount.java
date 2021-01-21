package com.company;
import java.io.Serializable;
public class TransferAmount implements Serializable { //Transfer Amount Class
    private static final long serialVersionUID = 6128016096756071380L;
    int accountNumberSender,accountNumberReceiver; // fields
    double amount;
    public TransferAmount(int accountNumberSender, int accountNumberReceiver, double amount) {
        this.accountNumberSender = accountNumberSender;
        this.accountNumberReceiver = accountNumberReceiver;
        this.amount = amount;
    }
}
