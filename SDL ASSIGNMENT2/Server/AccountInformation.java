package com.company;
import java.io.Serializable;
import java.util.ArrayList;
public class AccountInformation implements Serializable { //AccountInformation Class
    private static final long serialVersionUID = 6128016096756071380L;
    //Data Fields
    int accountNumber;
    String
            userName,userDOB,userPAN,userAddress,accountNominee,userPhoneNumber,userAadharNumber,accountPassword;
    double accountBalance;
    ArrayList<Transactions> transactionStatement;
    //getter steer Functions
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    public String getUserName() {
        return userName;
    }
}



