package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class LoginPage extends JFrame {
    static Socket socket;   //socket
    static ObjectOutputStream objectOutputStream;  //objectOutputStream
    static OutputStream outputStream;
    static InputStream inputStream;
    static ObjectInputStream objectInputStream;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static InetAddress inetAddress;

    //creating instance of JSwing Components
    JLabel bankInfo=new JLabel("WELCOME  TO  BR14X  BANK  MANAGEMENT  SYSTEM"); //Creates a JLabel instance with no image and with an empty string for the title
    JLabel accNoLabel=new JLabel("Type Account Number");
    JTextField accNoInput=new JTextField();
    JLabel passLabel=new JLabel("Type Your Password");
    JPasswordField passwordInput=new JPasswordField();
    JButton loginButton=new JButton("LOGIN");
    JTextArea area=new JTextArea("--------------------------------OR--------------------------------");
    JButton openAccountButton=new JButton("CREATE NEW ACCOUNT");

    JLabel errorLabel=new JLabel("");

    Container container=getContentPane(); //provides a space where a component can be located

    LoginPage(){

        try {
            inetAddress=InetAddress.getLocalHost();
            socket=new Socket(inetAddress,1401);   //connecting to localhost
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());

            outputStream=socket.getOutputStream();
            inputStream=socket.getInputStream();

            objectOutputStream = new ObjectOutputStream(outputStream);
            objectInputStream = new ObjectInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        startGUI();
    }

    private void startGUI(){

        container.setLayout(null);
        //x axis, y axis, width, height
        bankInfo.setBounds(180,20,340,30);
        accNoLabel.setBounds(20,90,250,25);
        passLabel.setBounds(340,90,150,25);
        accNoInput.setBounds(150,90,150,25);
        passwordInput.setBounds(470,90,150,25);

        loginButton.setBounds(220,150,150,35);


        area.setBounds(175,190,270,25);
        openAccountButton.setBounds(220,230,190,35);

        errorLabel.setBounds(90,305,200,25);
        errorLabel.setVisible(false);

        container.add(bankInfo);
        container.add(accNoLabel);
        container.add(accNoInput);
        container.add(passLabel);
        container.add(passwordInput);
        container.add(loginButton);
        container.add(area);
        container.add(openAccountButton);
        container.add(errorLabel);

        openAccountButton.addActionListener(e -> {
            setVisible(false);
            new OpenNewAccountPage();
        });

        loginButton.addActionListener(e -> getLoginCredentials());

        setVisible(true);    //making the frame visible
        setSize(680,320);   //setting width and height
        setTitle("BR14x BANK SYSTEM - LOGIN");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void getLoginCredentials(){

        String accountNumber= accNoInput.getText();
        String accountPassword=passwordInput.getText();

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
                new LoggedInMenuPage(rAccount);
                setVisible(false);
            } else if(type==2) {
                sendErrorMessage();
            }
        }catch(EOFException ignored){
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void sendErrorMessage(){
        JOptionPane.showMessageDialog(null, "INVALID INPUTS | TRY AGAIN", "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) { new LoginPage();
    }
}
