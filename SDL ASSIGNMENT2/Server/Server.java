package com.company;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.lang.System.exit;
public class Server {
    private static int latestAccNo = 14010;
    private ArrayList<AccountInformation> accountData;
    ServerSocket serverSocket;
    Socket socket;
    InputStream inputStream;
    OutputStream outputStream;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Server(){ //Constructor
        accountData=new ArrayList<>();
        System.out.print("");
        try {
            serverSocket = new ServerSocket(1401); //Create Socket
            socket = serverSocket.accept();
            if(socket.isConnected()){ //Check If Client is Connected
                System.out.print("\nClient is SuccessfullyConnected");
            }
            //initialize input and output streams
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
        } catch (SocketException s){ //catch exceptions
            System.out.print("Client Disconnected !");
            showData(1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            menu(); //call Menu
        } catch (SocketException s){ //Handle Exception
            System.out.println();
            System.out.print("Accounts :\n");
            showData(1);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void showData(int type){ //Show Data
        for (int i = 0; i < 155; i++)
            System.out.print('_');
        System.out.println();
        System.out.format("%1$-13s%2$-22s%3$-15s%4$-15s%5$-12s%6$-12s%7$-30s%8$-20s%9$-17s", //format
                "Account No", "Name", "Phone Number", "Aadhar No", "PAN No",
                "DOB", "Address", "Nominee", "Account Balance");
        System.out.println();
        for (int i = 0; i < 155; i++)
            System.out.print('_');
        System.out.println();
        for (AccountInformation accountDatum : accountData) {
            System.out.format("%1$-13s%2$-22s%3$-15s%4$-15s%5$-12s%6$-12s%7$-30s%8$-20s%9$-17s",
                    accountDatum.accountNumber,
                    accountDatum.userName,
                    accountDatum.userPhoneNumber,
                    accountDatum.userAadharNumber,
                    accountDatum.userPAN,
                    accountDatum.userDOB,
                    accountDatum.userAddress,
                    accountDatum.accountNominee,
                    accountDatum.accountBalance);
            System.out.println();
        }
        System.out.println();
        System.out.println();
        for (int i = 0; i < 155; i++)
            System.out.print('_');
        if(type==1){
            exitProgram();
        }
    }
    void exitProgram(){ //Exit Program
        exit(0);
    }
    void menu() throws IOException, ClassNotFoundException { //Menu
        int ch;
        do{
            ch = dataInputStream.read();
            switch (ch) {
                case 1: createAccount();
                    break;
                case 2:authenticateAccount();
                    break;
                case 3: updateAccount();
                    break;
                case 4: transferAmount();
                    break;
            }
        }while (true);
    }
    public void createAccount() throws IOException, ClassNotFoundException {
//Create Account
        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
        AccountInformation account=(AccountInformation)
                objectInputStream.readObject(); //Get Object from Client
        int accNo = generateAccNo(); //assign new Account Number
        account.accountNumber = accNo;
        accountData.add(account);
        System.out.print("\nAccount Created Successfully");
        dataOutputStream.writeUTF("Account Created !\n Account Number : "+"\033[0;31m"+accNo+"\033[0m"+" And Password : "+"\033[0;31m"+account.accountPassword+"\033[0m");
        //convey User Login Details
    }
    public void authenticateAccount() throws IOException { //AuthenticateClient
        String accNum=dataInputStream.readUTF(); //Receive AccountNumber
        String pass=dataInputStream.readUTF(); // Receive Password
        int index ;
        int accNo=Integer.parseInt(accNum); //Check if account Exist
        index=getAccountIndex(accNo);
        if(index!=-1){ //of account Exist
            dataOutputStream.write(1);
            AccountInformation information;
            if(pass.equals(accountData.get(index).accountPassword)){ //isPassword Matches
                information=accountData.get(index);
                outputStream=socket.getOutputStream();
                objectOutputStream=new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(information); //Send The User'sAccount Info
            }
            else{
                dataOutputStream.write(2);//if Password Dont match , sendError
            }
        }else{
            dataOutputStream.write(2); //if Account Does Not Exist , sendError
        }
    }
    public void updateAccount() throws IOException, ClassNotFoundException {
//Update Information
        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
        AccountInformation account=(AccountInformation)
                objectInputStream.readObject(); //Receive updated object
        int accNo=account.accountNumber;
        int index=getAccountIndex(accNo); //get Account Index
        accountData.set(index,account); //update account on Server side
    }
    public void transferAmount() throws IOException, ClassNotFoundException {
//transfer Account
        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
        TransferAmount amountT=(TransferAmount)objectInputStream.readObject();
//get Transaction Information
        int indexS=getAccountIndex(amountT.accountNumberSender);
        int indexR=getAccountIndex(amountT.accountNumberReceiver);
        double amountTransfer=amountT.amount;
        double Amt1=accountData.get(indexS).accountBalance;
        double Amt2=accountData.get(indexR).accountBalance;
        if (Amt1 > amountTransfer + 3000.0) { //check minimum balance limit
            Amt1 -= amountTransfer;
            Amt2 += amountTransfer;
            accountData.get(indexS).setAccountBalance(Amt1);//update amount inaccount1
            accountData.get(indexR).setAccountBalance(Amt2);//update amount inaccount2
            SimpleDateFormat formattedDate = new SimpleDateFormat("dd:MM:yyyy hh:mm a");
            String date = formattedDate.format(System.currentTimeMillis());
            Transactions Trans = new Transactions(date, amountTransfer,
                    "Withdraw", Amt1, "Transferred To " + accountData.get(indexR).getUserName());
            accountData.get(indexS).transactionStatement.add(Trans); //updateTransaction Statement
            Transactions Trans1 = new Transactions(date, amountTransfer,
                    "Deposit", Amt2, "Transferred From " + accountData.get(indexS).getUserName());
            accountData.get(indexR).transactionStatement.add(Trans1); //updateTransaction Statement
            dataOutputStream.writeUTF("Transferred The Money"); //Convey UserThat Money is Transferred
        } else {
            dataOutputStream.writeUTF("You Dont Have Enough Balance To Transfer The Money"); //Low Balance Error
        }
    }
    public int getAccountIndex(int accNo){ // function to search Account inArray List
        int index=-1;
        boolean stat=false;
        for (int i=0;i<accountData.size();i++) {
            if (accountData.get(i).accountNumber == accNo) { //if accountExist
                        index=i;
                stat = true;
                break;
            }
        }
        if(stat){
            return index; //return index
        }
        return -1; //else return error
    }
    public static int generateAccNo() { //generate Account Number
        return ++latestAccNo;
    }
    public static void main(String[] args){
        new Server(); //Initialize Object
    }
}