package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Accounts implements Serializable {
    private static final long serialVersionUID = 6128016096756071380L;
    long accountNumber;
    String userName,userPAN,userAddress,accountNominee,userPhoneNumber,userAadharNumber,accountPassword;
    Date userDOB;
    double accountBalance;
    ArrayList<Transactions> transactionsArrayList;


    public Accounts(long accountNumber, String userName, String userPAN, String userAddress, String accountNominee, String userPhoneNumber, String userAadharNumber, String accountPassword, Date userDOB, double accountBalance, ArrayList<Transactions> transactionsArrayList) {
        this.accountNumber = accountNumber;
        this.userName = userName;
        this.userPAN = userPAN;
        this.userAddress = userAddress;
        this.accountNominee = accountNominee;
        this.userPhoneNumber = userPhoneNumber;
        this.userAadharNumber = userAadharNumber;
        this.accountPassword = accountPassword;
        this.userDOB = userDOB;
        this.accountBalance = accountBalance;
        this.transactionsArrayList = transactionsArrayList;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPAN() {
        return userPAN;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getAccountNominee() {
        return accountNominee;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserAadharNumber() {
        return userAadharNumber;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public Date getUserDOB() {
        return userDOB;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setUserPAN(String userPAN) {
        this.userPAN = userPAN;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setAccountNominee(String accountNominee) {
        this.accountNominee = accountNominee;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setUserAadharNumber(String userAadharNumber) {
        this.userAadharNumber = userAadharNumber;
    }

    public void setUserDOB(Date userDOB) {
        this.userDOB = userDOB;
    }
}
