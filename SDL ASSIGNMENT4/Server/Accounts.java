package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Accounts implements Serializable { //AccountInformation Class
    private static final long serialVersionUID = 6128016096756071380L;
   //Data Fields
    long accountNumber;
    String userName,userPAN,userAddress,accountNominee,userPhoneNumber,userAadharNumber,accountPassword;
    Date userDOB;
    double accountBalance;
    ArrayList<Transactions> transactionsArrayList;

    //getter steer Functions
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getUserName() {
        return userName;
    }

    public void showData(){  //Show Data Function
        for (int i = 0; i < 155; i++)
            System.out.print('_');

        System.out.println(); //Format
        System.out.format("%1$-13s%2$-22s%3$-15s%4$-15s%5$-12s%6$-22s%7$-30s%8$-20s%9$-17s",
                "Account No", "Name", "Phone Number", "Aadhar No", "PAN No", "DOB", "Address", "Nominee", "Account Balance");
        System.out.println();
        for (int i = 0; i < 155; i++)
            System.out.print('_');

        System.out.println();
        System.out.format("%1$-13s%2$-22s%3$-15s%4$-15s%5$-12s%6$-22s%7$-30s%8$-20s%9$-17s",  //Print Data
                accountNumber, userName, userPhoneNumber, userAadharNumber,
                userPAN, userDOB, userAddress, accountNominee, accountBalance);
        System.out.println();
        for (int i = 0; i < 155; i++)
            System.out.print('_');
    }

    public long getAccountNumber() {
        return accountNumber;
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
    public double getAccountBalance() {
        return accountBalance;
    }
    public Date getUserDOB() {
        return userDOB;
    }
    public String getUserPAN() {
        return userPAN;
    }
    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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

