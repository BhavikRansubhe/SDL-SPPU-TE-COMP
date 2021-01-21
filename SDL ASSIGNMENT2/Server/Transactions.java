package com.company;
import java.io.Serializable;
public class Transactions implements Serializable { //Transaction Class
    private static final long serialVersionUID = 6128016096756071380L;
    // Data fields
    private String date;
    private double amount;
    private String type;
    private double totalBalance;
    private String description;
    public Transactions(String date, double amount, String type, double
            totalBalance, String description) {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.totalBalance = totalBalance;
        this.description = description;
    }
}