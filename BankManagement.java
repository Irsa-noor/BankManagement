
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bankmanagement;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

class Account implements Serializable {
    String name;
    int accountNumber;
    int pin;
    double amount;

    Account() {
        name = null;
        accountNumber = 0;
        pin = 0;
        amount = 0;
    }

    Account(String name, int acc, int pin, double amount) {
        this.name = name;
        this.accountNumber = acc;
        this.pin = pin;
        this.amount = 1000 + amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getPIN() {
        return pin;
    }

    public void setPIN(int pin) {
        this.pin = pin;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

public class BankManagement {
    ArrayList<Account> accounts = new ArrayList<>();

    public void addNewRecord(String name, double initialAmount) {
        Random random = new Random();
        int accountNumber = 10000000 + random.nextInt(90000000);
        int pin = 1000 + random.nextInt(9000);

        Account account = new Account(name, accountNumber, pin, initialAmount);
        accounts.add(account);
        JOptionPane.showMessageDialog(null, "Account created successfully.\nAccount Number: " + accountNumber + "\nPIN: " + pin);
    }

    public void deposit(int pin, double amount) {
        Account account = findAccount(pin);
        if (account != null) {
            account.setAmount(account.getAmount() + amount);
            JOptionPane.showMessageDialog(null, "Deposit of " + amount + " successful!");
        } else {
            JOptionPane.showMessageDialog(null, "Account not found!");
        }
    }

    public void withdraw(int pin, double amount) {
        Account account = findAccount(pin);
        if (account != null) {
            if (amount <= account.getAmount()) {
                account.setAmount(account.getAmount() - amount);
                JOptionPane.showMessageDialog(null, "Withdrawal of " + amount + " successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient balance!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Account not found!");
        }
    }

    public void transfer(int senderPin, int receiverPin, double amount) {
        Account sender = findAccount(senderPin);
        if (sender != null) {
            Account receiver = findAccount(receiverPin);
            if (receiver != null) {
                if (amount <= sender.getAmount()) {
                    sender.setAmount(sender.getAmount() - amount);
                    receiver.setAmount(receiver.getAmount() + amount);
                    JOptionPane.showMessageDialog(null, "Transfer of " + amount + " successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient balance!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Recipient's account not found!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Sender's account not found!");
        }
    }

    public void deleteAccount(int pin) {
        Account account = findAccount(pin);
        if (account != null) {
            accounts.remove(account);
            JOptionPane.showMessageDialog(null, "Account deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Account not found!");
        }
    }

    public void searchAccount(int pin) {
        Account account = findAccount(pin);
        if (account != null) {
            JOptionPane.showMessageDialog(null, "Account found:\nName: " + account.getName() + "\nAccount Number: " + account.getAccountNumber() + "\nBalance: " + account.getAmount() + "\nPIN: " + account.getPIN());
        } else {
            JOptionPane.showMessageDialog(null, "Account not found!");
        }
    }

    public void print() {
        if (accounts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No accounts found!");
        } else {
            StringBuilder accountsInfo = new StringBuilder();
            for (Account account : accounts) {
                accountsInfo.append("\nName: ").append(account.getName());
                accountsInfo.append("\nAccount Number: ").append(account.getAccountNumber());
                accountsInfo.append("\nBalance: ").append(account.getAmount());
                accountsInfo.append("\nPIN: ").append(account.getPIN()).append("\n");
            }
            JOptionPane.showMessageDialog(null, accountsInfo.toString());
        }
    }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream("BankRecord.ser");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(accounts);
            out.close();
            fos.close();
            JOptionPane.showMessageDialog(null, "Accounts saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving accounts: " + e.getMessage());
        }
    }

    public void load() {
        try {
            FileInputStream fis = new FileInputStream("BankRecord.ser");
            ObjectInputStream in = new ObjectInputStream(fis);
            accounts = (ArrayList<Account>) in.readObject();
            in.close();
            fis.close();
            JOptionPane.showMessageDialog(null, "Accounts loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error loading accounts: " + e.getMessage());
        }
    }

    private Account findAccount(int pin) {
        for (Account account : accounts) {
            if (account.getPIN() == pin) {
                return account;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        BankManagement  bank = new BankManagement ();
        JFrame frame = new JFrame("Bank Management System");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton addNewRecordBtn = new JButton("Add New Account");
        addNewRecordBtn.setBounds(50, 50, 200, 30);
        addNewRecordBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter name of Account Holder:").toUpperCase();
            double initialAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter the initial amount to be deposited:"));
            bank.addNewRecord(name, initialAmount);
        });
        frame.add(addNewRecordBtn);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(50, 100, 200, 30);
        depositBtn.addActionListener(e -> {
            int pin = Integer.parseInt(JOptionPane.showInputDialog("Enter your 4-digit PIN:"));
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount to be deposited:"));
            bank.deposit(pin, amount);
        });
        frame.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(50, 150, 200, 30);
        withdrawBtn.addActionListener(e -> {
            int pin = Integer.parseInt(JOptionPane.showInputDialog("Enter your 4-digit PIN:"));
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount to be withdrawn:"));
            bank.withdraw(pin, amount);
        });
        frame.add(withdrawBtn);

        JButton transferBtn = new JButton("Transfer");
        transferBtn.setBounds(50, 200, 200, 30);
        transferBtn.addActionListener(e -> {
            int senderPin = Integer.parseInt(JOptionPane.showInputDialog("Enter your 4-digit PIN:"));
            int receiverPin = Integer.parseInt(JOptionPane.showInputDialog("Enter the recipient's 4-digit PIN:"));
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount to transfer:"));
            bank.transfer(senderPin, receiverPin, amount);
        });
        frame.add(transferBtn);

        JButton deleteAccountBtn = new JButton("Delete Account");
        deleteAccountBtn.setBounds(50, 250, 200, 30);
        deleteAccountBtn.addActionListener(e -> {
            int pin = Integer.parseInt(JOptionPane.showInputDialog("Enter your 4-digit PIN:"));
            bank.deleteAccount(pin);
        });
        frame.add(deleteAccountBtn);

        JButton searchAccountBtn = new JButton("Search Account");
        searchAccountBtn.setBounds(50, 300, 200, 30);
        searchAccountBtn.addActionListener(e -> {
            int pin = Integer.parseInt(JOptionPane.showInputDialog("Enter your 4-digit PIN:"));
            bank.searchAccount(pin);
        });
        frame.add(searchAccountBtn);

        JButton printAccountsBtn = new JButton("Print All Accounts");
        printAccountsBtn.setBounds(50, 350, 200, 30);
        printAccountsBtn.addActionListener(e -> bank.print());
        frame.add(printAccountsBtn);

        JButton saveAccountsBtn = new JButton("Save Accounts");
        saveAccountsBtn.setBounds(50, 400, 200, 30);
        saveAccountsBtn.addActionListener(e -> bank.save());
        frame.add(saveAccountsBtn);

        JButton loadAccountsBtn = new JButton("Load Accounts");
        loadAccountsBtn.setBounds(50, 450, 200, 30);
        loadAccountsBtn.addActionListener(e -> bank.load());
        frame.add(loadAccountsBtn);

        frame.setVisible(true);
    }
}

