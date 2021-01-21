package com.company;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BMS extends Thread{
    Socket socket ;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    Connection connect;
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public BMS(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, Connection connect, Statement statement, PreparedStatement preparedStatement, ResultSet resultSet) {
        this.socket = socket;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.connect=connect;
        this.statement=statement;
        this.preparedStatement=preparedStatement;
        this.resultSet=resultSet;
    }
    private void receive() throws IOException, ClassNotFoundException, SQLException {
        int ch=this.objectInputStream.readInt();
        switch (ch){
            case 1: createAccount();
                    break;
            case 2: authenticateAccount();
                    break;
            case 3: updateAccountInfo();
                    break;
            case 4: transferAmount();
                    break;
            case 5: withdrawAmount();
                break;
            case 6: depositAmount();
                  break;
        }
    }

    private void updateAccountInfo() throws IOException, ClassNotFoundException {
        Accounts information=(Accounts)objectInputStream.readObject();

        String query ="UPDATE customers SET userName = '"+information.getUserName()+
                "' , userDOB = '"+information.getUserDOB()+
                "' , userPAN =  '"+information.getUserPAN()+
                "' , userAddress ='"+information.getUserAddress()+
                "' , accountNominee ='"+information.getAccountNominee()+
                "' , userPhoneNumber ='"+information.getUserPhoneNumber()+
                "' , userAadharNumber ='"+information.getUserAadharNumber()+
                "' , accountBalance = "+information.getAccountBalance()+ " WHERE accountNumber ="+information.getAccountNumber()+";";
        try {
            int stat=this.statement.executeUpdate(query);
            if(stat==1) {
                objectOutputStream.writeObject("Account Details Updated !");
            }else{
                objectOutputStream.writeObject("Account Details Not Updated !");
            }
            objectOutputStream.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void createAccount() throws IOException, ClassNotFoundException {  //Create Account
        Accounts newAccount=(Accounts) this.objectInputStream.readObject(); //Get Object from Client
        long newAccountNumber=0;
        String query0="SELECT * FROM customers WHERE accountNumber=(SELECT MAX(accountNumber) FROM customers)";
        try {
            this.resultSet=this.statement.executeQuery(query0);
            if (resultSet.next()){
                newAccountNumber=resultSet.getInt("accountNumber");
                newAccountNumber+=1;
            }else{
                newAccountNumber=100;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        newAccount.accountNumber=newAccountNumber;
        String query = "INSERT INTO customers (accountNumber, userName, userDOB, userPAN, userAddress, accountNominee, userPhoneNumber, userAadharNumber, accountBalance, accountPassword)"
                +"VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            this.preparedStatement = connect.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            this.preparedStatement.setLong(1, newAccountNumber);
            this.preparedStatement.setString(2, newAccount.getUserName());
            this.preparedStatement.setDate(3, convertUtilToSql(newAccount.userDOB));
            this.preparedStatement.setString(4, newAccount.getUserPAN());
            this.preparedStatement.setString(5, newAccount.getUserAddress());
            this.preparedStatement.setString(6, newAccount.getAccountNominee());
            this.preparedStatement.setString(7, newAccount.getUserPhoneNumber());
            this.preparedStatement.setString(8, newAccount.getUserAadharNumber());
            this.preparedStatement.setDouble(9, newAccount.getAccountBalance());
            this.preparedStatement.setString(10, newAccount.getAccountPassword());

            int rowAffected = this.preparedStatement.executeUpdate();
            if(rowAffected > 0) {
                System.out.print("\nAccount Created Successfully\n");
//                this.objectOutputStream.writeObject("Account Generated With Account number : " +"\033[0;31m"+newAccountNumber+"\033[0m");
                this.objectOutputStream.writeObject("Account Generated With Account number : " +newAccountNumber);

                String query1="INSERT INTO logincredentials(accountNumber,password,userId) VALUES ("+newAccountNumber+" , '"
                        +newAccount.getAccountPassword()+"' , (SELECT userId from customers WHERE accountNumber ="+newAccountNumber +" ))";
               int result=statement.executeUpdate(query1);
                if(result>0){
                    this.objectOutputStream.writeObject("\nLogin To Proceed !");
                    this.objectOutputStream.flush();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        newAccount.showData();
    }

    private void authenticateAccount() throws IOException, ClassNotFoundException {  //Authenticate Client

        Accounts account;
        String accNum=(String)objectInputStream.readObject(); //Receive AccountNumber
        String pass=(String)objectInputStream.readObject();   // Receive Password
        int flag = 0;
        long accNo=Long.parseLong(accNum);  //Check if account Exist

        String query1="SELECT * FROM logincredentials WHERE accountNumber = "+accNo;
        try {
            resultSet = statement.executeQuery(query1);
            while (resultSet.next()) {
                String password = resultSet.getString("password");
                if (pass.equals(password)) {
                    account=getAccountData(accNo);
                    this.objectOutputStream.writeInt(1);
                    this.objectOutputStream.flush();
                    this.objectOutputStream.writeObject(account);
                    this.objectOutputStream.flush();
                    flag=2;
                }else{
                    flag=-1;
                }
            }
            if(flag==-1) {
                this.objectOutputStream.writeInt(2);
                this.objectOutputStream.flush();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Accounts getAccountData(long accNo){
          Accounts accountData=new Accounts();
          accountData.transactionsArrayList=new ArrayList<>();
          try {
              String query = "SELECT * FROM customers WHERE accountNumber = " + accNo;
              resultSet = statement.executeQuery(query);
              while (resultSet.next()) {
                accountData.setUserName(resultSet.getString("userName"));
                accountData.setUserDOB(convertSqlToUtil(resultSet.getDate("userDOB")));
                accountData.setUserPAN(resultSet.getString("userPAN"));
                accountData.setUserAddress(resultSet.getString("userAddress"));
                accountData.setAccountNominee(resultSet.getString("accountNominee"));
                accountData.setUserPhoneNumber(resultSet.getString("userPhoneNumber"));
                accountData.setUserAadharNumber(resultSet.getString("userAadharNumber"));
                accountData.setAccountBalance(resultSet.getDouble("accountBalance"));
                accountData.setAccountNumber(accNo);
              }
              String query1="SELECT * FROM transactionstatements WHERE accountNumber ="+accNo;
              resultSet=statement.executeQuery(query1);
              while (resultSet.next()) {
                  Transactions transactions=new Transactions();
                  transactions.setTransactionDateAndTime(resultSet.getString("dateAndTime"));
                  transactions.setTransactionAmount(resultSet.getDouble("amount"));
                  transactions.setAccountBalance(resultSet.getDouble("accountBalance"));
                  transactions.setTransactionType(resultSet.getString("type"));
                  transactions.setTransactionDescription(resultSet.getString("description"));
                  accountData.transactionsArrayList.add(transactions);
              }
              return accountData;
          }catch (SQLException e) {
              e.printStackTrace();
          }
          return accountData;
    }
    private void withdrawAmount() throws IOException, ClassNotFoundException, SQLException {
        Trans_Req request=(Trans_Req)objectInputStream.readObject();
        long fromAcc=request.getAccountNumberSender();
        double amount=request.getAmount();
        String query ="UPDATE customers SET accountBalance = (SELECT accountBalance FROM customers where accountNumber ="+fromAcc+") - "+amount+" WHERE accountNumber ="+fromAcc;
        int result=statement.executeUpdate(query);
        if(result>0){
            updateTransaction(fromAcc,fromAcc,amount,"Self","Self Withdraw");
            objectOutputStream.writeObject("Successful");
        }else{
            objectOutputStream.writeObject("Unsuccessful");
        }

    }
    private void depositAmount() throws IOException, ClassNotFoundException, SQLException {
        Trans_Req request=(Trans_Req)objectInputStream.readObject();
        long fromAcc=request.getAccountNumberSender();
        double amount=request.getAmount();
        String query ="UPDATE customers SET accountBalance = (SELECT accountBalance FROM customers where accountNumber ="+fromAcc+") + "+amount+" WHERE accountNumber ="+fromAcc;
        int result=statement.executeUpdate(query);
        if(result>0){
            updateTransaction(fromAcc,fromAcc,amount,"Self","Self Deposit");
            objectOutputStream.writeObject("Successful");
        }else{
            objectOutputStream.writeObject("Unsuccessful");
        }
    }
    public void transferAmount() throws IOException, ClassNotFoundException { //transfer Account
        Trans_Req amountT=(Trans_Req)objectInputStream.readObject(); //get Transaction Information
        long transAccNo=amountT.accountNumberSender;
        long receiverAccNo=amountT.accountNumberReceiver;
        double transferAmount=amountT.amount;

        String query=" SELECT * FROM customers WHERE accountNumber = "+transAccNo;
        try {
            resultSet=statement.executeQuery(query);
            if(resultSet.next()){
                double balance=resultSet.getDouble("accountBalance");
                if ( balance > transferAmount) {
                    String query1="UPDATE customers SET accountBalance = (SELECT accountBalance FROM customers where accountNumber ="+transAccNo+") - "+transferAmount+" WHERE accountNumber ="+transAccNo;
                    statement.executeUpdate(query1);
                    String query2="UPDATE customers SET accountBalance = (SELECT accountBalance FROM customers where accountNumber ="+receiverAccNo+") + "+transferAmount+" WHERE accountNumber ="+receiverAccNo;
                    statement.executeUpdate(query2);
                    updateTransaction(transAccNo,receiverAccNo,transferAmount,"Transfer","Transferred To "+receiverAccNo);
                    updateTransaction(receiverAccNo,transAccNo,transferAmount,"Transfer","Transferred From "+transAccNo);
                    objectOutputStream.writeObject("Transfer Successful");
                }
            }else{
                objectOutputStream.writeObject("Transfer Unsuccessful");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTransaction(long senderAccNo,long receiverAccNo,double amount,String type,String description) throws SQLException {

        double accBalance=0;
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String date = formattedDate.format(System.currentTimeMillis());

        String query0 ="SELECT accountBalance FROM customers where accountNumber ="+senderAccNo;
        resultSet = statement.executeQuery(query0);
        if(resultSet.next()){
            accBalance=resultSet.getDouble("accountBalance");
        }

        String query1 = "INSERT INTO transactionstatements (accountNumber, toAccountNumber, dateAndTime,accountBalance,type,amount,description)"
                +"VALUES (?,?,?,?,?,?,?)";
        try {
            this.preparedStatement = this.connect.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
            this.preparedStatement.setLong(1, senderAccNo);
            this.preparedStatement.setLong(2, receiverAccNo);
            this. preparedStatement.setString(3, date);
            this.preparedStatement.setDouble(4,accBalance);
            this.preparedStatement.setString(5, type);
            this.preparedStatement.setDouble(6, amount);
            this.preparedStatement.setString(7, description);
            this.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    private static java.util.Date convertSqlToUtil(java.sql.Date sDate) {
        java.util.Date uDate = new java.sql.Date(sDate.getTime());
        return uDate;
    }

    @Override
    public void run() {
        while (true){
            try {
                receive();
            }catch(EOFException | SocketException ignored){
            } catch (IOException | ClassNotFoundException | SQLException e ) {
                e.printStackTrace();
            }
        }
    }
}
