package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import static com.company.LoginPage.objectInputStream;
import static com.company.LoginPage.objectOutputStream;

public class TransferAmountPage extends JFrame {

    JLabel bankInfo=new JLabel("BR14X BANK MANAGEMNT SYSTEM");

    JLabel accountNumberLabel=new JLabel("ACCOUNT NUMBER :");
    JLabel accountNumberValue=new JLabel();

    JLabel accountBalanceLabel=new JLabel("ACCOUNT BALANCE :");
    JLabel accountBalanceValue=new JLabel();

    JLabel transferAccountNumberLabel=new JLabel("Transfer Account Number:");
    JTextField transferAccountNumberInput=new JTextField();

    JLabel amountTransferLabel=new JLabel("AMOUNT :");
    JTextField amountTransferInput=new JTextField();

    JButton proceed=new JButton("Proceed");
    JButton cancel=new JButton("EXIT");

    static JButton returnBack=new JButton("Exit");

    Container container=getContentPane();

    Accounts accountInformation;

    TransferAmountPage(Accounts information){
        this.accountInformation =information;

        container.setLayout(null);

        bankInfo.setBounds(70,5,270,30);
        accountNumberLabel.setBounds(20,55,150,25);
        accountNumberValue.setBounds(170,55,70,25);
        accountBalanceLabel.setBounds(20,90,150,25);
        accountBalanceValue.setBounds(170,90,70,25);

        transferAccountNumberLabel.setBounds(20,120,160,25);
        transferAccountNumberInput.setBounds(180,120,160,25);

        amountTransferLabel.setBounds(20,160,160,25);
        amountTransferInput.setBounds(180,160,160,25);

        proceed.setBounds(20,190,100,25);
        cancel.setBounds(20,230,90,20);

        returnBack.setBounds(120,360,100,25);

        accountNumberValue.setText(String.valueOf(accountInformation.getAccountNumber()));
        accountBalanceValue.setText(String.valueOf(accountInformation.getAccountBalance()));

        container.add(bankInfo);

        container.add(accountNumberLabel);
        container.add(accountNumberValue);
        container.add(accountBalanceLabel);
        container.add(accountBalanceValue);

        container.add(amountTransferLabel);
        container.add(amountTransferInput);
        container.add(transferAccountNumberLabel);
        container.add(transferAccountNumberInput);
        container.add(proceed);
        container.add(cancel);

        proceed.addActionListener(e -> {
            String accNo=transferAccountNumberInput.getText();
            String amt=amountTransferInput.getText();

            if(!accNo.isEmpty() && !amt.isEmpty()){
                transferAmount(accNo,amt);
            }
        });

        cancel.addActionListener(e -> {
            setVisible(false);
            new LoggedInMenuPage(information);
        });

        returnBack.addActionListener(e -> {
            setVisible(false);
            new LoggedInMenuPage(information);
        });
        setVisible(true);
        setSize(430,300);
        setTitle("BR14X BANK MANAGEMENT SYSTEM");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void transferAmount(String accNo,String amt){

        int transAccNo=Integer.parseInt(accNo);
        double amount=Double.parseDouble(amt);
        String msg="@";
        System.out.println("TRANSFER MONEY PORTAL");

        try {
            objectOutputStream.writeInt(4);
            objectOutputStream.flush();

            Trans_Req request=new Trans_Req(accountInformation.accountNumber,transAccNo,amount); //Send Transfer Request
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            msg=(String)objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.print(msg);
        if(msg.equals("Transfer Successful")){
            accountInformation.setAccountBalance((accountInformation.accountBalance-amount));
            addTransactionStatement(accountInformation,amount,(accountInformation.accountBalance-amount),"Transfer","Transferred To "+transAccNo);
            sendMessage();
        }else{
            sendErrorMessage();
        }
    }

    public static void addTransactionStatement(Accounts loggedInAccount, double amount, double balance, String type, String description){

        SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String date = formattedDate.format(System.currentTimeMillis());

        Transactions transactions=new Transactions(date,amount,balance,type,description);
        loggedInAccount.transactionsArrayList.add(transactions);
    }

    void sendErrorMessage(){
        JOptionPane.showMessageDialog(null, "Unsuccessful | Try Again", "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    void sendMessage(){
        JOptionPane.showMessageDialog(null,
                "Transfer Amount Successful",
                "Success",
                JOptionPane.PLAIN_MESSAGE);
    }
}
