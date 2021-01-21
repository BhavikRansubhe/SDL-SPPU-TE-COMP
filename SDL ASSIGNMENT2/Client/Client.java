package com.company;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Scanner;
public class Client { //Class Client
    Socket socket; //socket
    ObjectOutputStream objectOutputStream; //objectOutputStream
    OutputStream outputStream;
    InputStream inputStream;
    ObjectInputStream objectInputStream;
    Scanner scanner=new Scanner(System.in);
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Client(){ //Constructor
        try {
            socket=new Socket("localhost",1401); //connecting to localhost
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu(); //Call Menu
    }
    public void menu(){
        int ch;
        do{
            System.out.print("\n|  WELCOME TO BR14x BANK SYSTEM  |\nEnter Following Choice\n1)Register The User \n2)Login User \n3)Exit : "); //Portal
                    ch=scanner.nextInt();
            switch (ch){
                case 1: registerUser();
                    break;
                case 2:loginUser();
                    break;
            }
        }while (ch<3);
    }
    public void registerUser(){
        AccountInformation account= new AccountInformation(); //create object
        account.getData(); //getData
        try {
            dataOutputStream.write(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try { //Send Object To Server
            outputStream=socket.getOutputStream();
            objectOutputStream=new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(account);
            String msg=dataInputStream.readUTF(); //Print Message From Server
            System.out.print(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loginUser(){ //login User
        String accountNumber;
        String accountPassword;
        scanner.nextLine();
        System.out.print("Enter Account Number :- ");
        accountNumber=scanner.nextLine();
        System.out.print("Enter Account Password :- ");
        accountPassword=scanner.nextLine();
        try { //Transfer Details
            dataOutputStream.write(2);
            dataOutputStream.writeUTF(accountNumber);
            dataOutputStream.writeUTF(accountPassword);
            int type=dataInputStream.read();
            if(type==1){ //if Account Exist in System
                inputStream = socket.getInputStream(); //Receive Account
                objectInputStream = new ObjectInputStream(inputStream);
                AccountInformation rAccount= (AccountInformation)
                        objectInputStream.readObject();
                System.out.print("\nWelcome User !\n");
                int ch;
                do{ //Menu
                    System.out.print("\n\n1)Show Account Details \n2)Withdraw Amount\n3)Deposit Amount\n4)Update Account Details\n5)Transfer Amount\n6)Show Account Statement\n7)LogOut : ");
                            ch = scanner.nextInt();
                    switch (ch){
                        case 1:rAccount.showData(); //Show Data
                            break;
                        case 2:withdraw(rAccount); //Withdraw Amount
                            break;
                        case 3:deposit(rAccount); //Deposit Amount
                            break;
                        case 4:updateData(rAccount); //Update Data
                            break;
                        case 5:transferAmountF(rAccount); //Transfer Amount
                            break;
                        case 6:showStatement(rAccount); //Show Statement
                            break;
                        case 7:rAccount=null; //Logout
                            break;
                    }
                }while(ch<7);
            }
            else{
                System.out.print("Invalid Credentials"); //If account doesnt Exist
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void transferAmountF(AccountInformation information) { //TransferAmount
        Scanner scanner=new Scanner(System.in);
        int accNo;
        double amount;
        String msg="@";
        System.out.print("\nEnter Account Number Of User Whom You Want To Transfer Amount :"); //Ask Acc Number
        accNo = scanner.nextInt();
        System.out.print("Enter Amount :"); //Ask amount
        amount = scanner.nextDouble();
        TransferAmount amount1=new
                TransferAmount(information.accountNumber,accNo,amount); //Send TransferRequest
        try { //Send Object
            dataOutputStream.write(4);
            outputStream=socket.getOutputStream();
            objectOutputStream=new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(amount1);
            msg=dataInputStream.readUTF(); //Get Status From Server
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(msg);
    }
    public void withdraw(AccountInformation information) { //Ask Amount To Withdraw
        Scanner scanner=new Scanner(System.in);
        double amount = information.accountBalance;
        double withdrawAmount;
        System.out.print("Enter The Amount You Withdraw : "); //Ask Amount
        withdrawAmount = scanner.nextDouble();
        if (withdrawAmount > amount+3000) { //Check Limit
            System.out.println("Account Balance is low to withdraw amount " +
                    withdrawAmount);
        } else {
            amount -= withdrawAmount;
            System.out.println("Amount Withdrawn \nAccount Balance : " +
                    amount);
            information.setAccountBalance(amount); //Update Balance
            SimpleDateFormat formattedDate = new SimpleDateFormat("dd:MM:yyyy hh:mm a");
            String date = formattedDate.format(System.currentTimeMillis());
            Transactions Trans = new Transactions(date, withdrawAmount,
                    "Withdraw", amount, "Withdrawn Self"); //Create Statement
            information.transactionStatement.add(Trans);
            try { //Send Object
                dataOutputStream.write(3);
                outputStream=socket.getOutputStream();
                objectOutputStream=new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(information);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void showStatement(AccountInformation information) { //Show Account Statement
        for (int i = 0; i < 110; i++)
            System.out.print('_');
        System.out.println();
        System.out.format("%1$-21s%2$-10s%3$-12s%4$-10s%5$-21s", "Date", "Amount", "Type", "Balance", "Description");
        System.out.println();
        for (int i = 0; i < 110; i++)
            System.out.print('_');
        System.out.println();
        for (int i = 0; i < information.transactionStatement.size(); i++) {
            if(i!=0) {
                System.out.println();
            }
            Transactions trans = information.transactionStatement.get(i);
            System.out.format("%1$-21s%2$-10s%3$-12s%4$-10s%5$-21s",
                    trans.getDate(),
                    trans.getAmount(),
                    trans.getType(),
                    trans.getTotalBalance(),
                    trans.getDescription());
        }
        System.out.println();
        for (int i = 0; i < 110; i++)
            System.out.print('_');
    }
    public void deposit(AccountInformation information) { //Deposit Amount
        Scanner scanner=new Scanner(System.in);
        double amount = information.accountBalance;
        double withdrawAmount;
        System.out.print("Enter The Amount You Deposit : "); //Ask amount
        withdrawAmount = scanner.nextDouble();
        amount += withdrawAmount;
        System.out.println("Amount Deposited \nAccount Balance : " +
                amount); //Ask Balance
        information.setAccountBalance(amount);
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd:MM:yyyy hh:mm a");
        String date = formattedDate.format(System.currentTimeMillis());
        Transactions Trans = new Transactions(date, withdrawAmount,
                "Deposit", amount, "Deposited Self"); //Create Statement
        information.transactionStatement.add(Trans);
        try { //Send Object
            dataOutputStream.write(3);
            outputStream=socket.getOutputStream();
            objectOutputStream=new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(information);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateData(AccountInformation information) { //Update Data
        Scanner scanner = new Scanner(System.in);
        int ch;
        String field2;
        int ch2;
        boolean process=true;
        do { //Ask Field To Update
            System.out.print("\nEnter Field To Change\n1)Name\n2)DOB\n3)PAN\n4)Address\n5)Nominee\n6)PhoneNumber\n7)Aadhar No\n8)EXIT : ");
                    ch = scanner.nextInt();
            switch (ch) {
                case 1: //Change Name
                    scanner.nextLine();
                    System.out.println("Current Name :" +
                            information.getUserName());
                    System.out.print("Enter Change Name To :");
                    field2 = scanner.nextLine();
                    System.out.print("Change Name [1/0]? :");
                    ch2 = scanner.nextInt();
                    if (ch2 == 1) {
                        information.setUserName(field2);
                    } else {
                        System.out.print("Name Unchanged");
                    }
                    break;
                case 2: //Change DOB
                    scanner.nextLine();
                    System.out.println("Current DOB :" +
                            information.getUserDOB());
                    System.out.print("Enter Change DOB To :");
                    field2 = scanner.nextLine();
                    System.out.print("Change DOB [1/0]? :");
                    ch2 = scanner.nextInt();
                    if (ch2 == 1) {
                        information.setUserDOB(field2);
                    } else {
                        System.out.print("DOB Unchanged");
                    }
                    break;
                case 3: //Change PAN
                    scanner.nextLine();
                    System.out.println("Current PAN :" +
                            information.getUserPAN());
                    System.out.print("Enter Change PAN To :");
                    field2 = scanner.nextLine();
                    System.out.print("Change PAN [1/0]? :");
                    ch2 = scanner.nextInt();
                    if (ch2 == 1) {
                        information.setUserPAN(field2);
                    } else {
                        System.out.print("PAN Unchanged");
                    }
                    break;
                case 4: //Change Address
                    scanner.nextLine();
                    System.out.println("Current Address :" +
                            information.getUserAddress());
                    System.out.print("Enter Change Address :");
                    field2 = scanner.nextLine();
                    System.out.print("Change Address [1/0]? :");
                    ch2 = scanner.nextInt();
                    if (ch2 == 1) {
                        information.setUserAddress(field2);
                    } else {
                        System.out.print("Address Unchanged");
                    }
                    break;
                case 5: //Change Nominee
                    scanner.nextLine();
                    System.out.println("Current Nominee :" +
                            information.getAccountNominee());
                    System.out.print("Enter Change Nominee :");
                    field2 = scanner.nextLine();
                    System.out.print("Change Nominee [1/0]? :");
                    ch2 = scanner.nextInt();
                    if (ch2 == 1) {
                        information.setAccountNominee(field2);
                    } else {
                        System.out.print("Nominee Unchanged");
                    }
                    break;
                case 6: //Change Phone Number
                    scanner.nextLine();
                    System.out.println("Current Phone Number :" +
                            information.getUserPhoneNumber());
                    boolean stat = false;
                    do {
                        System.out.print("Enter Change Phone Number To : ");
                        String phoneNum = scanner.nextLine();
                        if (phoneNum.length() != 10) {
                            System.out.print("Phone Number Is Not Valid");
                            System.out.println();
                            stat = true;
                        } else {
                            for (int i = 0; i < 10; i++) { //take proper phonenumber
                                assert phoneNum != null;
                                if (phoneNum.charAt(i) >= '0' &&
                                        phoneNum.charAt(i) <= '9') {
                                    System.out.print("Change Phone [1/0]? :");
                                    ch2 = scanner.nextInt();
                                    if (ch2 == 1) {

                                        information.setUserPhoneNumber(phoneNum);
                                    } else {
                                        System.out.print("Phone Number Unchanged");
                                    }
                                    stat = false;
                                } else {
                                    System.out.print("Phone Number Should Not Contain Any Letters");
                                            System.out.println();
                                    phoneNum = null;
                                    stat = true;
                                }
                            }
                        }
                    } while (stat);
                    break;
                case 7: //Change Aadhar Number
                    scanner.nextLine();
                    System.out.println("Current Aadhar :" +
                            information.getUserAadharNumber());
                    stat = false;
                    do {
                        System.out.print("Enter Aadhar : ");
                        String userAadhar = scanner.nextLine();
                        if (userAadhar.length() != 12) {
                            System.out.print("Aadhar Number Is Not Valid.");
                            System.out.println();
                            stat = true;
                        } else {
                            for (int i = 0; i < 12; i++) { //take proper aadhar number
                                assert userAadhar != null;
                                if (userAadhar.charAt(i) >= '0' &&
                                        userAadhar.charAt(i) <= '9') {
                                    System.out.print("Change Aadhar [1/0]?:");
                                    ch2 = scanner.nextInt();
                                    if (ch2 == 1) {

                                        information.setUserAadharNumber(userAadhar);
                                    } else {
                                        System.out.print("Aadhar Number Unchanged");
                                    }
                                    stat = false;
                                } else {
                                    System.out.print("Aadhar Should Not Contain Any Letters");
                                            System.out.println();
                                    userAadhar = null;
                                    stat = true;
                                }
                            }
                        }
                    } while (stat);
                    break;
                case 8:
                    try { //Update in Server
                        dataOutputStream.write(3);
                        outputStream=socket.getOutputStream();
                        objectOutputStream=new
                                ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(information);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.print("Updation Process Completed !\n");
                    information.showData();
                    process=false;
            }
        } while (process);
    }
    public static void main(String[] args){
        new Client(); //Initialize Object
    }
}
