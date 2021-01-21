package com.company;
import java.io.Serializable;
public class TransferAmount implements Serializable { //Class to Transfer Amount
    private static final long serialVersionUID = 6128016096756071380L;
    int accountNumberSender,accountNumberReceiver;
    double amount;
    public TransferAmount(int accountNumberSender, int accountNumberReceiver,
                          double senderAmount) { //Constructor
        this.accountNumberSender = accountNumberSender;
        this.accountNumberReceiver = accountNumberReceiver;
        this.amount = senderAmount;
    }
}
