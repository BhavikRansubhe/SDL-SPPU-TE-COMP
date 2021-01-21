package com.company;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Accounts implements Serializable {
    private static final long serialVersionUID = 6128016096756071380L;
    long accountNumber;
    String userName,userPAN,userAddress,accountNominee,userPhoneNumber,userAadharNumber,accountPassword;
    Date userDOB;
    double accountBalance;
    ArrayList<Transactions> transactionsArrayList;


    public void getAccountDetails(){
        Scanner scanner=new Scanner(System.in);
        boolean stat = false;
        System.out.println("Please Fill Out Following Details :\n");
        System.out.print("Enter Your Full Name :");
        userName=scanner.nextLine();

        System.out.print("Enter Date Of Birth :");

        String date = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        userDOB=null;
        try {
            userDOB = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.print("Enter Address :");
        userAddress=scanner.nextLine();

        System.out.print("Enter Nominee For Your Account  :");
        accountNominee = scanner.nextLine();

        System.out.print("Enter Your PAN :");
        userPAN = scanner.nextLine();

        do {

            System.out.print("Enter Your Phone Number :");
            userPhoneNumber = scanner.nextLine();

            if(userPhoneNumber.length()!=10){
                System.out.print("Phone Number Is Not Valid");
                System.out.println();
                stat=true;
                userPhoneNumber=null;
            }
            else{
                for(int i=0;i<10;i++){
                    assert userPhoneNumber != null;
                    if(userPhoneNumber.charAt(i) >='0' && userPhoneNumber.charAt(i)<='9'){
                        stat=false;
                    }
                    else{
                        System.out.print("Phone Number Should Not Contain Any Letters");
                        System.out.println();
                        userPhoneNumber=null;
                        stat=true;
                    }
                }
            }

        }while(stat);

        do {

            System.out.print("Enter Your Aadhar Number :");
            userAadharNumber=scanner.nextLine();

            if(userAadharNumber.length()!=12){
                System.out.print("Aadhar Number Is Not Valid.");
                System.out.println();
                stat=true;
                userAadharNumber=null;
            }
            else{
                for(int i=0;i<12;i++){
                    assert userAadharNumber != null;
                    if(userAadharNumber.charAt(i) >='0' && userAadharNumber.charAt(i)<='9'){
                        stat=false;
                    }
                    else{
                        System.out.print("Aadhar Number Should Not Contain Any Letters");
                        System.out.println();
                        userAadharNumber=null;
                        stat=true;
                    }
                }
            }


        }while(stat);

        do {
            System.out.print("Enter Initial Balance (Must Be Above Rs.10000) : ");
            accountBalance = scanner.nextDouble();
        }while(accountBalance<10000);
        scanner.nextLine();
        boolean stat1;
        String pass1;
        do {
            System.out.print("\nPlease Enter A New & Unique Password  : ");
            pass1 = scanner.nextLine();
            if (pass1.length() >= 8) {
                Pattern letter = Pattern.compile("[a-zA-z]");
                Pattern digit = Pattern.compile("[0-9]");
                Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

                Matcher hasLetter = letter.matcher(pass1);
                Matcher hasDigit = digit.matcher(pass1);
                Matcher hasSpecial = special.matcher(pass1);
                if (hasDigit.find() && hasLetter.find() && hasSpecial.find()) {
                    stat1=false;

                } else {
                    System.out.println("Password must contain Letters, minimum 1 Digit And 1 Special Characters");
                    pass1 = null;
                    stat1=true;
                }

            } else {
                System.out.println("Invalid Password Should be of minimum size 8.");
                stat1=true;
            }
        }while (stat1);

        System.out.print("Please Enter Password Again To Confirm :");
        String pass2 = scanner.nextLine();

        if (pass2.isEmpty()) {
            System.out.println("Password cant be empty.");
        } else {
            if (pass1.equals(pass2)) {
                accountPassword = pass1;
            } else {
                System.out.println("Password Do Not Match.");
            }
        }
        transactionsArrayList=new ArrayList<>();
    }

    public void showAccountDetails(){
        for (int i = 0; i < 155; i++)
            System.out.print('_');

        System.out.println(); //Format
        System.out.format("%1$-13s%2$-22s%3$-15s%4$-15s%5$-12s%6$-12s%7$-30s%8$-20s%9$-17s",
                "Account No", "Name", "Phone Number", "Aadhar No", "PAN No", "DOB", "Address", "Nominee", "Account Balance");
        System.out.println();
        for (int i = 0; i < 155; i++)
            System.out.print('_');

        System.out.println();
        System.out.format("%1$-13s%2$-22s%3$-15s%4$-15s%5$-12s%6$-12s%7$-30s%8$-20s%9$-17s",
                getAccountNumber(), getUserName(), getUserPhoneNumber(), getUserAadharNumber(), getUserPAN(),
                getUserDOB(),getUserAddress(), getAccountNominee(), getAccountBalance());
        System.out.println();
        for (int i = 0; i < 155; i++)
            System.out.print('_');
    }

    public long getAccountNumber() {
        return accountNumber;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPAN() {
        return userPAN;
    }

    public void setUserPAN(String userPAN) {
        this.userPAN = userPAN;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getAccountNominee() {
        return accountNominee;
    }

    public void setAccountNominee(String accountNominee) {
        this.accountNominee = accountNominee;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserAadharNumber() {
        return userAadharNumber;
    }

    public void setUserAadharNumber(String userAadharNumber) {
        this.userAadharNumber = userAadharNumber;
    }


    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Date getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(Date userDOB) {
        this.userDOB = userDOB;
    }
}
