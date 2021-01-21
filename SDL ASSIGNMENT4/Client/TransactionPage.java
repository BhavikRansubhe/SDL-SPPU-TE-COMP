package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import static com.company.LoginPage.objectInputStream;
import static com.company.LoginPage.objectOutputStream;

public class TransactionPage extends JFrame {

    int type;

    JLabel bankInfo=new JLabel("BR14X BANK MANAGEMENT SYSTEM");

    JLabel accountNumberLabel=new JLabel("ACCOUNT NUMBER :");
    JLabel accountNumberValue=new JLabel();


    JLabel accountBalanceLabel=new JLabel("ACCOUNT BALANCE :");
    JLabel accountBalanceValue=new JLabel();

    JLabel amountTransferLabel=new JLabel("AMOUNT:");
    JTextField amountTransferInput=new JTextField();

    JButton proceed=new JButton("Proceed");
    JButton cancel=new JButton("EXIT");

    static JButton returnBack=new JButton("Exit");

    Container container=getContentPane();

    Accounts accountInformation;

    TransactionPage(int type, Accounts loggedInAccount){
        this.type=type;
        this.accountInformation =loggedInAccount;

        container.setLayout(null);

        bankInfo.setBounds(50,5,270,30);
        accountNumberLabel.setBounds(20,55,160,25);
        accountNumberValue.setBounds(200,55,70,25);
        accountBalanceLabel.setBounds(20,90,160,25);
        accountBalanceValue.setBounds(200,90,70,25);

        accountNumberValue.setText(String.valueOf(accountInformation.getAccountNumber()));
        accountBalanceValue.setText(String.valueOf(accountInformation.getAccountBalance()));


        amountTransferLabel.setBounds(70,120,160,25);
        amountTransferInput.setBounds(180,120,160,25);

        proceed.setBounds(20,170,100,25);
        cancel.setBounds(20,230,90,25);

        container.add(accountNumberLabel);
        container.add(accountNumberValue);
        container.add(accountBalanceLabel);
        container.add(accountBalanceValue);

        container.add(amountTransferLabel);
        container.add(amountTransferInput);
        container.add(proceed);
        container.add(cancel);


        proceed.addActionListener(e -> {
            String amount=amountTransferInput.getText();
            if(!amount.isEmpty()){
                switch (type){
                    case 1:withdrawAmount(amount);
                    break;
                    case 2:depositAmount(amount);
                }
            }
        });

        cancel.addActionListener(e -> {
            setVisible(false);
            new LoggedInMenuPage(loggedInAccount);
        });

        returnBack.addActionListener(e -> {
            setVisible(false);
            new LoggedInMenuPage(loggedInAccount);
        });


        setVisible(true);
        setSize(430,320);
        setTitle("BR14X BANK MANAGEMENT SYSTEM");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    private void withdrawAmount(String amt) {

        double amount = accountInformation.getAccountBalance();
        double withdrawAmount=Double.parseDouble(amt);

        if (withdrawAmount > amount) {
            sendErrorMessage("Account Balance is low to withdraw amount " + withdrawAmount);
        } else {
            try {
                objectOutputStream.writeInt(5);
                objectOutputStream.flush();
                Trans_Req request = new Trans_Req(accountInformation.accountNumber, accountInformation.accountNumber, withdrawAmount);
                objectOutputStream.writeObject(request);
                objectOutputStream.flush();
                String msg = (String) objectInputStream.readObject();
                if (msg.equals("Successful")) {
                    amount -= withdrawAmount;
                    accountInformation.setAccountBalance(amount);
                    sendMessage("Amount Withdrawn \nAccount Balance : " + amount);
                    addTransactionStatement(accountInformation, withdrawAmount, amount, "Self", "Self Withdraw");
                }else{
                    sendErrorMessage("Unsuccessful | Try Again");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void depositAmount(String amt){
        double amount = accountInformation.getAccountBalance();
        double depositAmount=Double.parseDouble(amt);
        try {
            objectOutputStream.writeInt(6);
            objectOutputStream.flush();
            Trans_Req request=new Trans_Req(accountInformation.accountNumber, accountInformation.accountNumber,depositAmount);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            String msg=(String)objectInputStream.readObject();
            if(msg.equals("Successful")){
                amount +=  depositAmount;
                accountInformation.setAccountBalance(amount);
               sendMessage("Amount Deposited \nAccount Balance : " + amount);
                addTransactionStatement(accountInformation,depositAmount,amount,"Self","Self Deposit");
            }else{
                sendErrorMessage("Unsuccessful | Try Again");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void addTransactionStatement(Accounts loggedInAccount, double amount, double balance, String type, String description){

        SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String date = formattedDate.format(System.currentTimeMillis());

        Transactions transactions=new Transactions(date,amount,balance,type,description);
        loggedInAccount.transactionsArrayList.add(transactions);

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
