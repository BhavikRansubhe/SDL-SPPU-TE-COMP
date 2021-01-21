package com.company;
import java.io.Serializable;
public class Transactions implements Serializable { //Class For Transaction
    private static final long serialVersionUID = 6128016096756071380L;
    private String date; //Statement Fields
    private double amount;
    private String type;
    private double totalBalance;
    private String description;
    public Transactions(String date, double amount, String type, double
            totalBalance, String description) { // Transaction Constructor
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.totalBalance = totalBalance;
        this.description = description;
    }
    //getter Setter Functions
    public String getDate() {
        return date;
    }
    public double getAmount() {
        return amount;
    }
    public String getType() {
        return type;
    }
    public double getTotalBalance() {
        return totalBalance;
    }
    public String getDescription() {
        return description;
    }
}