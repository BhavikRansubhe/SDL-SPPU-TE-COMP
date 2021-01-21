package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {

    ServerSocket serverSocket;
    Socket socket;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    Connection connect;
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    static int clientNo=0;

    Server(){
        try {
            serverSocket=new ServerSocket(1401);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://localhost:3307/bms", "root", "");
                statement = connect.createStatement();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                assert serverSocket != null;
                socket=serverSocket.accept();

                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.flush();
                objectInputStream = new ObjectInputStream(socket.getInputStream());

                Thread thread=new BMS(socket,objectOutputStream,objectInputStream,connect,statement,preparedStatement,resultSet);
                thread.start();

                String name="Client "+getClientNo();
                thread.setName(name);

                if(thread.isAlive()){
                    System.out.print("\n"+name+" Is Connected");
                }
                if(socket.isClosed()){
                    removeClient();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public int getClientNo(){
        return ++clientNo;
    }
    public void removeClient(){
       --clientNo;
    }
    public static void main(String[] args){
        new Server();
    }
}

