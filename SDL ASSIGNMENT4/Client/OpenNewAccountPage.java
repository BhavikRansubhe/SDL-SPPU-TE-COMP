package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenNewAccountPage extends JFrame {

    Container container=getContentPane();

    JLabel bankInfo=new JLabel("WELCOME , PLEASE FILL OUT FOLLOWING DETAILS !");

    JLabel userNameLabel=new JLabel("ENTER FULL NAME");
    JTextField userNameInput=new JTextField();

    JLabel userPhoneNumberLabel=new JLabel("Provide PhoneNumber ");
    JTextField userPhoneNumberInput=new JTextField();

    JLabel userAadharLabel=new JLabel("Provide AadharCard Number");
    JTextField userAadharInput=new JTextField();

    JLabel userPANLabel=new JLabel("Provide PANCARD Number");
    JTextField userPANInput=new JTextField();

    JLabel userDOBLabel=new JLabel("ENTER Date OF Birth");
    JTextField userDOBInput=new JTextField();

    JLabel userAddressLabel=new JLabel("Provide Address");
    JTextField userAddressInput=new JTextField();

    JLabel accountNomineeLabel=new JLabel("Give Nominee For Account");
    JTextField accountNomineeInput=new JTextField();

    JLabel accountPasswordLabel=new JLabel("Create Unique Password");
    JPasswordField accountPasswordInput=new JPasswordField();

    JLabel accountCPasswordLabel=new JLabel("Confirm Password Again");
    JPasswordField accountCPasswordInput=new JPasswordField();

    JLabel accountBalanceLabel=new JLabel("Enter Account Balance");
    JTextField accountBalanceInput=new JTextField();

    JButton submitButton=new JButton("CREATE ACCOUNT");
    JButton backToLoginButton=new JButton("<-- Go BACK TO LOGIN");

    String userName,userPhoneNumber;
    String userAadharNumber;
    String accountBalance;
    String accountPassword;
    String accountNominee;
    String postalAddress;
    String userDOB;
    String userPANNumber;
    double accountBalanceDbl;
    Date userD;

    OpenNewAccountPage(){
        container.setLayout(null);

        bankInfo.setBounds(70,20,380,30);

        userNameLabel.setBounds(40,60,160,25);
        userNameInput.setBounds(220,60,160,25);

        userPhoneNumberLabel.setBounds(40,90,160,25);
        userPhoneNumberInput.setBounds(220,90,160,25);

        userAadharLabel.setBounds(40,120,160,25);
        userAadharInput.setBounds(220,120,160,25);

        userPANLabel.setBounds(40,150,160,25);
        userPANInput.setBounds(220,150,160,25);

        userDOBLabel.setBounds(40,180,160,25);
        userDOBInput.setBounds(220,180,160,25);

        userAddressLabel.setBounds(40,210,160,25);
        userAddressInput.setBounds(220,210,160,25);

        accountNomineeLabel.setBounds(40,240,160,25);
        accountNomineeInput.setBounds(220,240,160,25);

        accountBalanceLabel.setBounds(40,270,160,25);
        accountBalanceInput.setBounds(220,270,160,25);

        accountPasswordLabel.setBounds(40,300,160,25);
        accountPasswordInput.setBounds(220,300,160,25);

        accountCPasswordLabel.setBounds(40,330,160,25);
        accountCPasswordInput.setBounds(220,330,160,25);

        submitButton.setBounds(125,400,150,30);

        backToLoginButton.setBounds(105,450,180,30);

        container.add(bankInfo);
        container.add(userNameLabel);
        container.add(userNameInput);
        container.add(userPhoneNumberLabel);
        container.add(userPhoneNumberInput);

        container.add(userAadharLabel);
        container.add(userAadharInput);
        container.add(userPANLabel);
        container.add(userPANInput);

        container.add(userDOBLabel);
        container.add(userDOBInput);
        container.add(userAddressLabel);
        container.add(userAddressInput);

        container.add(accountNomineeLabel);
        container.add(accountNomineeInput);
        container.add(accountPasswordLabel);
        container.add(accountPasswordInput);

        container.add(accountBalanceLabel);
        container.add(accountBalanceInput);

        container.add(accountPasswordLabel);
        container.add(accountPasswordInput);
        container.add(accountCPasswordLabel);
        container.add(accountCPasswordInput);

        container.add(submitButton);
        container.add(backToLoginButton);

        submitButton.addActionListener(e -> {

            boolean stat=false;
            userName=userNameInput.getText();
            userPhoneNumber=userPhoneNumberInput.getText();
            userAadharNumber=userAadharInput.getText();
            userPANNumber = userPANInput.getText();
            userDOB = userDOBInput.getText();
            postalAddress = userAddressInput.getText();
            accountNominee = accountNomineeInput.getText();
            accountPassword =accountPasswordInput.getText();
            accountBalance=accountBalanceInput.getText();

            if(userName.isEmpty()){
               sendErrorMessage("Empty User Name");
            }else {
                if (userPhoneNumber.isEmpty()) {
                    sendErrorMessage("Empty Phone Number");
                }else {
                    do {
                        if(userPhoneNumber.length()!=10){
                            sendErrorMessage("Phone Number Is Not Valid");
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
                                    sendErrorMessage("Phone Number Should Not Contain Any Letters");
                                    System.out.println();
                                    userPhoneNumber=null;
                                    stat=true;
                                }
                            }
                        }

                    }while(stat);
                    if (userAadharNumber.isEmpty()) {
                        sendErrorMessage("Empty Aadhar Number");
                    } else {
                        do {
                            if(userAadharNumber.length()!=12){
                                sendErrorMessage("Aadhar Number Is Not Valid.");
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
                                        sendErrorMessage("Aadhar Number Should Not Contain Any Letters");
                                        System.out.println();
                                        userAadharNumber=null;
                                        stat=true;
                                    }
                                }
                            }
                        }while(stat);

                        if (userPANNumber.isEmpty()) {
                            sendErrorMessage("Empty PAN Number");
                        } else {
                            if (userDOB.isEmpty()) {
                                sendErrorMessage("Empty DOB");
                            } else {
                                String date =userDOB;

                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                userD=null;
                                try {
                                    userD = dateFormat.parse(date);
                                } catch (ParseException e2) {
                                   sendErrorMessage("Enter in DD-MM-YYYY");
                                }
                                if (postalAddress.isEmpty()) {
                                    sendErrorMessage("Empty Postal Address");
                                } else {
                                    if (accountNominee.isEmpty()) {
                                        sendErrorMessage("Empty Account Nominee");
                                    } else {
                                        if (accountPassword.isEmpty()) {
                                            sendErrorMessage("Empty Password");
                                        }
                                        else {
                                            boolean stat1=false;
                                            String pass1=accountPassword;
                                            do {
                                                //System.out.print("\nPlease Enter New Password \nShould be of minimum size 8 with Letter, Digit And Special Characters\n(Remember For Next Login) : ");
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
                                                        sendErrorMessage("Password must contain Letters, minimum 1 Digit And 1 Special Characters");
                                                        pass1 = null;
                                                        stat1=true;
                                                    }

                                                } else {
                                                    sendErrorMessage("Invalid Password Should be of minimum size 8.");
                                                    stat1=true;
                                                }
                                            }while (stat1);

                                            if (accountBalance.isEmpty()) {
                                                sendErrorMessage("Empty Account Balance");
                                            }else{
                                                 accountBalanceDbl = Double.parseDouble(accountBalance);
                                                if(accountBalanceDbl<3000){
                                                    sendErrorMessage("Balance Should Be Above 3000");
                                                }else{
                                                    addNewUser();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        backToLoginButton.addActionListener(e -> {
            new LoginPage();
            setVisible(false);
        });
        setVisible(true);
        setSize(500,550);
        setTitle("BR14X BANK SYSTEM -OPEN ACCOUNT");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void addNewUser(){
        ArrayList<Transactions> transactions=new ArrayList<>();
        Accounts account=new Accounts(0,userName,userPANNumber,postalAddress,accountNominee,userPhoneNumber,userAadharNumber,accountPassword,userD,accountBalanceDbl,transactions);
        try {
            int send=1;
            LoginPage.objectOutputStream.writeInt(send);
            LoginPage.objectOutputStream.flush();

            LoginPage.objectOutputStream.writeObject(account);
            LoginPage.objectOutputStream.flush();

            String msg=(String) LoginPage.objectInputStream.readObject();//Print Message From Server
            sendMessage(msg);
            new LoginPage();
            setVisible(false);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    void sendErrorMessage(String msg){
        JOptionPane.showMessageDialog(null, msg, "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    void sendMessage(String msg){
        JOptionPane.showMessageDialog(null,
                msg,
                "Success",
                JOptionPane.PLAIN_MESSAGE);
    }
}
