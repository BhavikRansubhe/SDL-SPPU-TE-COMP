package com.company;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class AccountInformation implements Serializable { //Account Information Class
    private static final long serialVersionUID = 6128016096756071380L;
    int accountNumber; //Class Fields
    String userName;
    String userDOB;
    String userPAN;
    String userAddress;
    String accountNominee;
    String userPhoneNumber;
    String userAadharNumber;
    double accountBalance;
    String accountPassword;
    public ArrayList<Transactions> transactionStatement;
    public AccountInformation() {
    }
    public void showData(){ //Show Data Function
        for (int i = 0; i < 155; i++)
            System.out.print('_');
        System.out.println(); //Format
        System.out.format("%1$-13s%2$-22s%3$-15s%4$-15s%5$-12s%6$-12s%7$-30s%8$-20s%9$-17s", "Account No", "Name", "Phone Number", "Aadhar No", "PAN No", "DOB", "Address", "Nominee", "Account Balance");
        System.out.println();
        for (int i = 0; i < 155; i++)
            System.out.print('_');
        System.out.println();
        System.out.format("%1$-13s%2$-22s%3$-15s%4$-15s%5$-12s%6$-12s%7$-30s%8$-20s%9$-17s", accountNumber, userName, userPhoneNumber, userAadharNumber, userPAN, userDOB, userAddress, accountNominee, accountBalance);
        System.out.println();
        for (int i = 0; i < 155; i++)
            System.out.print('_');
    }
    public void getData(){ //Get Data Function
        Scanner scanner=new Scanner(System.in);
        boolean stat = false; //Ask data to User
        System.out.println("Please Fill Out Following Details :\n");
                System.out.print("Enter Full Name : ");
        userName=scanner.nextLine();
        System.out.print("Enter Date Of Birth : ");
        userDOB= scanner.nextLine();
        System.out.print("Enter Address : ");
        userAddress=scanner.nextLine();
        System.out.print("Enter Nominee : ");
        accountNominee = scanner.nextLine();
        System.out.print("Enter PAN :");
        userPAN = scanner.nextLine();
        do { //Take Appropriate Phone Number
            System.out.print("Enter Phone Number : ");
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
                    if(userPhoneNumber.charAt(i) >='0' &&
                            userPhoneNumber.charAt(i)<='9'){
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
        do { //Take Appropriate Aadhar Number
            System.out.print("Enter Aadhar : ");
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
                    if(userAadharNumber.charAt(i) >='0' &&
                            userAadharNumber.charAt(i)<='9'){
                        stat=false;
                    }
                    else{
                        System.out.print("Aadhar Should Not Contain Any Letters");
                                System.out.println();
                        userAadharNumber=null;
                        stat=true;
                    }
                }
            }
        }while(stat);
        do { //Take Balance Above 3000
            System.out.print("Enter Initial Balance (Must Be Above Rs.10,000) : ");
                    accountBalance = scanner.nextDouble();
        }while(accountBalance<10000);
        System.out.print("Creating An Account.....");
        scanner.nextLine();
        boolean stat1;
        String pass1;
        do {
            System.out.print("\nPlease Enter New And Unique Password : ");
            pass1 = scanner.nextLine();
            if (pass1.length() >= 8) {
                Pattern letter = Pattern.compile("[a-zA-z]");
                Pattern digit = Pattern.compile("[0-9]");
                Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
                Matcher hasLetter = letter.matcher(pass1);
                Matcher hasDigit = digit.matcher(pass1);
                Matcher hasSpecial = special.matcher(pass1);
                if (hasDigit.find() && hasLetter.find() && hasSpecial.find())
                {
                    stat1=false;
                } else {
                    System.out.println("Should be of minimum size 8 with Letter, Digit And Special Characters"); //Take Password In Format
                            pass1 = null;
                    stat1=true;
                }
            } else {
                System.out.println("Invalid Pass. Should be of minimum size 8 ");
                        stat1=true;
            }
        }while (stat1);
        String pass2;
        do {
            System.out.print("Please Enter Password Again To Confirm :");
            pass2 = scanner.nextLine();
            if (pass2.isEmpty()) {
                System.out.println("Password cant be empty");
            } else {
                if (pass1.equals(pass2)) {
                    accountPassword = pass1;
                } else {
                    System.out.println("Password Do Not Match");
                }
            }
        }while (!pass1.equals(pass2));
        transactionStatement=new ArrayList<>();
    }
    //getter Setter Function
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserDOB(String userDOB) {
        this.userDOB = userDOB;
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
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    public String getUserName() {
        return userName;
    }
    public String getUserDOB() {
        return userDOB;
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
}