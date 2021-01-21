package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class showStatements extends JFrame {

    JTable table = new JTable();

    JScrollPane scroll;

    String headers[] = { "Date", "Amount", "Balance", "Type", "Description" };

    showStatements(ArrayList<Transactions> transactionsArrayList){
        DefaultTableModel model = new DefaultTableModel(new String[]{"Date", "Amount", "Balance", "Type", "Description"},0);

        for (Transactions transactions : transactionsArrayList) {
            model.addRow(new Object[]
                    {
                            transactions.getTransactionDateAndTime(),
                            transactions.getTransactionAmount(),
                            transactions.getAccountBalance(),
                            transactions.getTransactionType(),
                            transactions.getTransactionDescription()
                    });
        }

        table.setModel(model);
        table.setBounds(100,100,1000,400);
        scroll = new JScrollPane(table);;
        add(scroll);
        setSize(1010, 400);
        setVisible(true);
    }
}
