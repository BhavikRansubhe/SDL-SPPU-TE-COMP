package com.company;

import javax.swing.*;
import java.awt.*;

public class LoggedInMenuPage extends JFrame {
    //creating instance of JSwing Components
    JLabel bankInfo=new JLabel("BR14X BANK SYSTEM - LOGGED IN PAGE ");

    JLabel accountNumberLabel=new JLabel("ACCOUNT NUMBER :");
    JLabel accountNumberValue=new JLabel();

    JLabel menu=new JLabel("DO YOU WANT TO :");
    JLabel details=new JLabel("ACCOUNT DETAILS");

    JLabel userNameLabel=new JLabel("User Name :");
    JLabel userNameValue=new JLabel();

    JLabel userPhoneNumberLabel=new JLabel("Phone Number :");
    JLabel userPhoneNumberValue=new JLabel();

    JLabel userAadharLabel=new JLabel("Aadhar Number :");
    JLabel userAadharValue=new JLabel();

    JLabel userPANLabel=new JLabel("PAN Number :");
    JLabel userPANValue=new JLabel();

    JLabel userDOBLabel=new JLabel("DOB :");
    JLabel userDOBValue=new JLabel();

    JLabel userAddressLabel=new JLabel("Address :");
    JLabel userAddressValue=new JLabel();

    JLabel accountNomineeLabel=new JLabel("Account Nominee :");
    JLabel accountNomineeValue=new JLabel();

    JLabel accountBalanceLabel=new JLabel("Account Balance :");
    JLabel accountBalanceValue=new JLabel();

    JRadioButton withdrawAmount=new JRadioButton("Withdraw Amount");
    JRadioButton depositAmount=new JRadioButton("Deposit Amount");
    JRadioButton transferAmount=new JRadioButton("Transfer Amount");
    JRadioButton updateAccountDetails=new JRadioButton("Update Account Details");
    JRadioButton showStatement=new JRadioButton("Show Statement");
    Container container=getContentPane();

    JButton proceed=new JButton("Proceed");
    JButton logOut=new JButton("LOGOUT");

    Accounts accountInformation;


    LoggedInMenuPage(Accounts accountInformation){

        this.accountInformation=accountInformation;

        container.setLayout(null);
        bankInfo.setBounds(120,20,270,30);

        accountNumberLabel.setBounds(20,90,150,25);
        accountNumberValue.setBounds(170,90,170,25);

        logOut.setBounds(340,290,90,30);
        details.setBounds(20,60,150,20);

        userNameLabel.setBounds(40,110,160,25);
        userNameValue.setBounds(150,110,160,25);

        userPhoneNumberLabel.setBounds(40,140,160,25);
        userPhoneNumberValue.setBounds(150,140,160,25);

        userAadharLabel.setBounds(40,170,160,25);
        userAadharValue.setBounds(150,170,160,25);

        userPANLabel.setBounds(40,200,160,25);
        userPANValue.setBounds(150,200,160,25);

        userDOBLabel.setBounds(40,230,160,25);
        userDOBValue.setBounds(150,230,160,25);

        userAddressLabel.setBounds(40,260,160,25);
        userAddressValue.setBounds(150,260,160,25);

        accountNomineeLabel.setBounds(40,290,160,25);
        accountNomineeValue.setBounds(150,290,160,25);

        accountBalanceLabel.setBounds(40,320,160,25);
        accountBalanceValue.setBounds(150,320,160,25);

        menu.setBounds(340,60,160,25);

        withdrawAmount.setBounds(340,90,180,25);
        depositAmount.setBounds(340,120,180,25);
        transferAmount.setBounds(340,150,180,25);
        updateAccountDetails.setBounds(340,180,180,25);
        showStatement.setBounds(340,210,180,25);
        proceed.setBounds(340,240,100,30);

        container.add(bankInfo);

        container.add(userNameLabel);
        container.add(userNameValue);
        container.add(userPhoneNumberLabel);
        container.add(userPhoneNumberValue);

        container.add(userAadharLabel);
        container.add(userAadharValue);
        container.add(userPANLabel);
        container.add(userPANValue);

        container.add(userDOBLabel);
        container.add(userDOBValue);
        container.add(userAddressLabel);
        container.add(userAddressValue);

        container.add(accountNomineeLabel);
        container.add(accountNomineeValue);
        container.add(accountBalanceLabel);
        container.add(accountBalanceValue);


        container.add(accountNumberLabel);
        container.add(accountNumberValue);

        container.add(menu);
        container.add(details);

        ButtonGroup loggedInGroup=new ButtonGroup();
        loggedInGroup.add(withdrawAmount);
        loggedInGroup.add(depositAmount);
        loggedInGroup.add(transferAmount);
        loggedInGroup.add(updateAccountDetails);
        loggedInGroup.add(showStatement);

        container.add(withdrawAmount);
        container.add(depositAmount);
        container.add(transferAmount);
        container.add(updateAccountDetails);
        container.add(showStatement);

        container.add(proceed);
        container.add(logOut);

        setData();

        proceed.addActionListener(e -> {
            if (withdrawAmount.isSelected()) {
                setVisible(false);
               new TransactionPage(1,accountInformation);
            } else if (depositAmount.isSelected()) {
                setVisible(false);
                new TransactionPage(2,accountInformation);
            } else if (transferAmount.isSelected()) {
               new TransferAmountPage(accountInformation);
            } else if (updateAccountDetails.isSelected()) {
              new UpdateAccountDetailsPage(accountInformation);
            }else{
                new showStatements(accountInformation.transactionsArrayList);
            }
        });

        logOut.addActionListener(e -> {
            setVisible(false);
            new LoginPage();
        });

        setVisible(true);
        setSize(590,420);
        setTitle("BR14X BANK MANAGEMENT SYSTEM - LOGIN");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setData(){
        accountNumberValue.setText(String.valueOf(accountInformation.getAccountNumber()));
        userNameValue.setText(accountInformation.getUserName());
        userPhoneNumberValue.setText(accountInformation.getUserPhoneNumber());
        userAadharValue.setText(accountInformation.getUserAadharNumber());
        userPANValue.setText(accountInformation.getUserPAN());
        userDOBValue.setText(String.valueOf(accountInformation.getUserDOB()));
        userAddressValue.setText(accountInformation.getUserAddress());
        accountNomineeValue.setText(accountInformation.getAccountNominee());
        accountBalanceValue.setText(String.valueOf(accountInformation.getAccountBalance()));
    }
}
