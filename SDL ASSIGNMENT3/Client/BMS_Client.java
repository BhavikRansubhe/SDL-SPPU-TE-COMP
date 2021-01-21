package com.company;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class BMS_Client {
    Socket socket;   //socket
    ObjectOutputStream objectOutputStream;  //objectOutputStream
    OutputStream outputStream;
    InputStream inputStream;
    ObjectInputStream objectInputStream;
    Scanner scanner=new Scanner(System.in);
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    InetAddress inetAddress;

    BMS_Client(){   //Constructor
        try {
            inetAddress=InetAddress.getLocalHost();
            socket=new Socket(inetAddress,1401);   //connecting to localhost
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());

            outputStream=socket.getOutputStream();
            inputStream=socket.getInputStream();

            this.objectOutputStream = new ObjectOutputStream(outputStream);
            this.objectInputStream = new ObjectInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        menu(); //Call Menu
    }


    public void menu(){
        int ch;
        do{
            System.out.print("\n|  WELCOME TO BR14x BANK SYSTEM  |\n1)Login User \n2)User Registration\n3)Exit : ");
            ch=scanner.nextInt();
            switch (ch){
                case 1: loginUser();
                    break;
                case 2:registerUser();
                    break;
            }
        }while (ch<3);
    }
//
    public void registerUser(){  //register New Account
        Accounts account= new Accounts();  //create object
        account.getAccountDetails();    //getData
        try {
            int send=1;
            this.objectOutputStream.writeInt(send);
            this.objectOutputStream.flush();

            this.objectOutputStream.writeObject(account);
            this.objectOutputStream.flush();

            String msg=(String)this.objectInputStream.readObject(); //Print Message From Server
            System.out.print(msg);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loginUser(){  //login User
        String accountNumber;
        String accountPassword;
        scanner.nextLine();
        System.out.print("Enter Account Number : ");
        accountNumber=scanner.nextLine();
        System.out.print("Enter Account Password : ");
        accountPassword=scanner.nextLine();

        try { //Transfer Details
            objectOutputStream.writeInt(2);
            objectOutputStream.flush();
            objectOutputStream.writeObject(accountNumber);
            objectOutputStream.flush();
            objectOutputStream.writeObject(accountPassword);
            objectOutputStream.flush();

            int type=objectInputStream.readInt();
            if(type==1){  //if Account Exist in System
                Accounts rAccount= (Accounts) objectInputStream.readObject();
                System.out.print("\nWELCOME !\n");
                int ch;
                do{  //Menu
                    System.out.print("\n1)Show Account Details \n2)Withdraw Amount\n3)Deposit Amount\n4)Update Account Details\n5)Transfer Amount\n6)Show Account Statement\n7)Exit : ");
                    ch = scanner.nextInt();
                    switch (ch){
                        case 1:rAccount.showAccountDetails(); //Show Data
                            break;
                        case 2:withdrawAmount(rAccount); //Withdraw Amount
                            break;
                        case 3:depositAmount(rAccount); //Deposit Amount
                            break;
                        case 4:updateAccountInformation(rAccount); //Update Data
                            break;
                        case 5:transferAmount(rAccount); //Transfer Amount
                            break;
                        case 6:showStatement(rAccount); //Show Statement
                            break;
                        case 7:rAccount=null; //Logout/Exit
                            break;
                    }
                }while(ch<7);
            } else if(type==2) {
                System.out.print("Invalid Credentials");  //If account doesnt Exist
            }
        }catch(EOFException e){
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void withdrawAmount(Accounts loggedInAccount) {
        double amount = loggedInAccount.getAccountBalance();
        double withdrawAmount;
        System.out.print("Enter The Amount You Withdraw : ");
        withdrawAmount = scanner.nextDouble();
        if (withdrawAmount > amount) {
            System.out.println("Account Balance is low to withdraw amount " + withdrawAmount);
        } else {
            try {
                objectOutputStream.writeInt(5);
                objectOutputStream.flush();
                Trans_Req request=new Trans_Req(loggedInAccount.accountNumber,loggedInAccount.accountNumber,withdrawAmount);
                objectOutputStream.writeObject(request);
                objectOutputStream.flush();
                String msg=(String)objectInputStream.readObject();
                if(msg.equals("Successful")){
                    amount -= withdrawAmount;
                    loggedInAccount.setAccountBalance(amount);
                    System.out.println("Amount Withdrawn \nAccount Balance : " + amount);
                    addTransactionStatement(loggedInAccount,withdrawAmount,amount,"Self","Self Withdraw");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void addTransactionStatement(Accounts loggedInAccount, double amount, double balance, String type, String description){
        java.util.Date dateT=null;
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String date = formattedDate.format(System.currentTimeMillis());

        try {
            dateT = formattedDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Transactions transactions=new Transactions(date,amount,balance,type,description);
        loggedInAccount.transactionsArrayList.add(transactions);

    }

    private void depositAmount(Accounts loggedInAccount){
        double amount = loggedInAccount.getAccountBalance();
        double depositAmount;
        System.out.print("Enter The Amount You Deposit : ");
        depositAmount = scanner.nextDouble();
        try {
            objectOutputStream.writeInt(6);
            objectOutputStream.flush();
            Trans_Req request=new Trans_Req(loggedInAccount.accountNumber,loggedInAccount.accountNumber,depositAmount);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            String msg=(String)objectInputStream.readObject();
            if(msg.equals("Successful")){
                amount +=  depositAmount;
                loggedInAccount.setAccountBalance(amount);
                System.out.println("Amount Deposited \nAccount Balance : " + amount);
                addTransactionStatement(loggedInAccount,depositAmount,amount,"Self","Self Deposit");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showStatement(Accounts loggedInAccount){
        for (int i = 0; i < 110; i++)
            System.out.print('_');
        Transactions transactions;
        System.out.println();
        System.out.format("%1$-25s%2$-10s%3$-12s%4$-10s%5$-21s",
                "Date", "Amount", "Balance", "Type", "Description");
        System.out.println();
        for (int i = 0; i < 110; i++)
            System.out.print('_');
        System.out.println();
        for(int i=0;i<loggedInAccount.transactionsArrayList.size();i++){
            transactions=loggedInAccount.transactionsArrayList.get(i);
            transactions.showStatement();
        }
        System.out.println();
        for (int i = 0; i < 110; i++)
            System.out.print('_');
    }


    private void transferAmount(Accounts information){
        Scanner scanner=new Scanner(System.in);
        int transAccNo;
        double amount;
        String msg="@";
        System.out.print("Enter The Account Number Of User Whom You Want To Transfer Amount :"); //Ask Acc Number
        transAccNo= scanner.nextInt();

        System.out.print("Enter Amount  :"); //Ask amount
        amount = scanner.nextDouble();

        try {
            objectOutputStream.writeInt(4);
            objectOutputStream.flush();

            Trans_Req request=new Trans_Req(information.accountNumber,transAccNo,amount); //Send Transfer Request
                objectOutputStream.writeObject(request);
                objectOutputStream.flush();
                msg=(String)objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.print(msg);
        if(msg.equals("Transfer Successful")){
            information.setAccountBalance((information.accountBalance-amount));
            addTransactionStatement(information,amount,(information.accountBalance-amount),"Transfer","Transferred To "+transAccNo);
        }
    }

    public void updateAccountInformation(Accounts information) {  //Update Data
        Scanner scanner = new Scanner(System.in);
        int ch;
        String field2;
        int ch2;
        boolean process=true;
        do {  //Ask Field To Update
            System.out.print("\nSelect Field You Want To Update/Change\n1)Name\n2)DOB\n3)PAN\n4)Address\n5)Nominee\n6)PhoneNumber\n7)Aadhar No\n8)SAVE & EXIT : ");
            ch = scanner.nextInt();
            switch (ch) {
                case 1:   //Change Name
                    scanner.nextLine();
                    System.out.println("Current Name :" + information.getUserName());
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
                case 2:  //Change DOB
                    scanner.nextLine();
                    System.out.println("Current DOB :" + information.getUserDOB());
                    System.out.print("Enter Change DOB To :");
                    String date = scanner.nextLine();
                    System.out.print("Change DOB [1/0]? :");
                    ch2 = scanner.nextInt();
                    if (ch2 == 1) {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date userDOB;
                        try {
                            userDOB = dateFormat.parse(date);
                            information.setUserDOB(userDOB);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.print("DOB Unchanged");
                    }
                    break;
                case 3:  //Change PAN
                    scanner.nextLine();
                    System.out.println("Current PAN :" + information.getUserPAN());
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
                    System.out.println("Current Address :" + information.getUserAddress());
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
                case 5:  //Change Nominee
                    scanner.nextLine();
                    System.out.println("Current Nominee :" + information.getAccountNominee());
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
                case 6: scanner.nextLine();
                System.out.println("Current Phone Number :" + information.getUserPhoneNumber());
                boolean stat = false;
                do {

                    System.out.print("Enter Change Phone Number To : ");
                    String phoneNum = scanner.nextLine();

                    if (phoneNum.length() != 10) {
                        System.out.print("Phone Number Is Not Valid");
                        System.out.println();
                        stat = true;
                    } else {
                        for (int i = 0; i < 10; i++) {
                            assert phoneNum != null;
                            if (phoneNum.charAt(i) >= '0' && phoneNum.charAt(i) <= '9') {
                                System.out.print("Change Phone [1/0]?");
                                ch2 = scanner.nextInt();
                                if (ch2 == 1) {
                                    information.setUserPhoneNumber(phoneNum);
                                    break;
                                } else {
                                    System.out.print("Phone Number Unchanged");
                                }
                                stat=false;
                                break;
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

                case 7:
                    scanner.nextLine();
                    System.out.println("Current Aadhar :" + information.getUserAadharNumber());
                    stat = false;
                    do {
                        System.out.print("Enter Aadhar : ");
                        String userAadhar = scanner.nextLine();

                        if (userAadhar.length() != 12) {
                            System.out.print("Aadhar Number Is Not Valid.");
                            System.out.println();
                            stat = true;
                        } else {
                            for (int i = 0; i < 12; i++) {
                                assert userAadhar != null;
                                if (userAadhar.charAt(i) >= '0' && userAadhar.charAt(i) <= '9') {
                                    System.out.print("Change Aadhar [1/0]?");
                                    ch2 = scanner.nextInt();
                                    if (ch2 == 1) {
                                        information.setUserAadharNumber(userAadhar);
                                        break;
                                    } else {
                                        System.out.print("Aadhar Number Unchanged");
                                    }
                                    stat = false;
                                    break;
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
                    try {  //Update in Server
                        objectOutputStream.writeInt(3);
                        objectOutputStream.writeObject(information);
                        objectOutputStream.flush();
                        String message=(String)objectInputStream.readObject();
                        System.out.print(message);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    System.out.println();
                    information.showAccountDetails();
                    process=false;
            }
        } while (process);
    }

    public static void main(String[] args){
        new BMS_Client();  //Initialize Object
    }

}
