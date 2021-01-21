package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.company.LoginPage.objectInputStream;
import static com.company.LoginPage.objectOutputStream;

public class UpdateAccountDetailsPage extends JFrame {

    Container container=getContentPane();

    JLabel bankInfo=new JLabel("BR14X BANK MANAGEMENT SYSTEM - UPDATE DETAILS PAGE");
    JLabel accountNumberLabel=new JLabel("ACCOUNT NUMBER :");
    JLabel accountNumberValue=new JLabel();

    JLabel userNameLabel=new JLabel("Name");
    JLabel userNameValue=new JLabel();

    JLabel userPhoneNumberLabel=new JLabel("Phone Number ");
    JTextField userPhoneNumberInput=new JTextField();

    JLabel userAadharLabel=new JLabel("Aadhar Number");
    JTextField userAadharInput=new JTextField();

    JLabel userPANLabel=new JLabel("PAN Number");
    JTextField userPANInput=new JTextField();

    JLabel userDOBLabel=new JLabel("DOB");
    JLabel userDOBInput=new JLabel();

    JLabel userAddressLabel=new JLabel("Address");
    JTextField userAddressInput=new JTextField();

    JLabel accountNomineeLabel=new JLabel("Account Nominee");
    JTextField accountNomineeInput=new JTextField();

    JLabel accountBalanceLabel=new JLabel("Account Balance");
    JLabel accountBalanceValue=new JLabel();


    JButton submitButton=new JButton("PROCEED");
    JButton cancel=new JButton("Cancel");

    private String userPhoneNumber;
    private String userName;
    private String userAadharNumber;
    private String accountNominee;
    private String postalAddress;
    private String userPANNumber;

    Accounts accountInformation;

    UpdateAccountDetailsPage(Accounts information){

        this.accountInformation=information;

        container.setLayout(null);

        bankInfo.setBounds(40,20,380,30);

        accountNumberLabel.setBounds(40,60,150,25);
        accountNumberValue.setBounds(200,60,70,25);

        userNameLabel.setBounds(40,110,160,25);
        userNameValue.setBounds(150,110,160,25);

        userPhoneNumberLabel.setBounds(40,140,160,25);
        userPhoneNumberInput.setBounds(150,140,160,25);

        userAadharLabel.setBounds(40,170,160,25);
        userAadharInput.setBounds(150,170,160,25);

        userPANLabel.setBounds(40,200,160,25);
        userPANInput.setBounds(150,200,160,25);

        userDOBLabel.setBounds(40,230,160,25);
        userDOBInput.setBounds(150,230,160,25);

        userAddressLabel.setBounds(40,260,160,25);
        userAddressInput.setBounds(150,260,160,25);

        accountNomineeLabel.setBounds(40,290,160,25);
        accountNomineeInput.setBounds(150,290,160,25);

        accountBalanceLabel.setBounds(40,320,160,25);
        accountBalanceValue.setBounds(150,320,160,25);

        submitButton.setBounds(145,350,100,25);

        cancel.setBounds(145,400,90,20);

        accountNumberValue.setText(String.valueOf(accountInformation.getAccountNumber()));
        container.add(cancel);
        container.add(bankInfo);
        container.add(userNameLabel);
        container.add(userNameValue);
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

        container.add(accountBalanceLabel);
        container.add(accountBalanceValue);

        container.add(accountNumberLabel);
        container.add(accountNumberValue);

        container.add(submitButton);

        setData();

        submitButton.addActionListener(e -> validateData());

        cancel.addActionListener(e -> {
            setVisible(false);
            new LoggedInMenuPage(information);
        });

        setVisible(true);
        setSize(500,500);
        setTitle("BANK MANAGEMENT SYSTEM");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void validateData(){

        boolean stat=false;

        userPhoneNumber=userPhoneNumberInput.getText();
        userAadharNumber=userAadharInput.getText();
        userPANNumber = userPANInput.getText();
        String userDOB = userDOBInput.getText();
        postalAddress = userAddressInput.getText();
        accountNominee = accountNomineeInput.getText();
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
                            if (postalAddress.isEmpty()) {
                                sendErrorMessage("Empty Postal Address");
                            } else {
                                if (accountNominee.isEmpty()) {
                                    sendErrorMessage("Empty Account Nominee");
                                } else {
                                    updateDatabase();

                                }
                            }
                        }
                    }
                }
        }
    }

        private void updateDatabase(){
         accountInformation.setUserPhoneNumber(userPhoneNumber);
         accountInformation.setUserAadharNumber(userAadharNumber);
         accountInformation.setAccountNominee(accountNominee);
         accountInformation.setUserAddress(postalAddress);
        // accountInformation.setUserDOB(userD);
         accountInformation.setUserPAN(userPANNumber);

        try {  //Update in Server
                objectOutputStream.writeInt(3);
                objectOutputStream.writeObject(accountInformation);
                objectOutputStream.flush();
                String message=(String)objectInputStream.readObject();
                sendMessage(message);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    private void setData(){
        
        accountNumberValue.setText(String.valueOf(accountInformation.getAccountNumber()));

        userPhoneNumberInput.setText(accountInformation.getUserPhoneNumber());
        userAadharInput.setText(accountInformation.getUserAadharNumber());
        userPANInput.setText(accountInformation.getUserPAN());

        String[] arr;

        arr=String.valueOf(accountInformation.getUserDOB()).split("-");

        String newDOB=arr[2]+"/"+arr[1]+"/"+arr[0];

        userDOBInput.setText(newDOB);

        userAddressInput.setText(accountInformation.getUserAddress());
        accountNomineeInput.setText(accountInformation.getAccountNominee());
        accountBalanceValue.setText(String.valueOf(accountInformation.getAccountBalance()));
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
