/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Darryl
 */
public class MaintenanceModel {

    Owner authorizedOwner;
    RegularVendingMachine authenticatedRegularMachine;
    SpecialVendingMachine authenticatedSpecialMachine;

    public MaintenanceModel(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        authorizedOwner = owner;
        authenticatedRegularMachine = regularMachine;
        authenticatedSpecialMachine = specialMachine;
    }

    public Owner getAuthOwner() {
        return this.authorizedOwner;
    }

    public RegularVendingMachine getAuthRegularMachine() {
        return this.authenticatedRegularMachine;
    }

    public SpecialVendingMachine getAuthSpecialMachine() {
        return this.authenticatedSpecialMachine;
    }

    public void authenticateRegularMachine(String machineName) {
        authorizedOwner.getRegularMachine(machineName);
    }

    public void authenticateSpecialMachine(String machineName) {
        authorizedOwner.getRegularMachine(machineName);
    }

    public Item getRegularVMItem(String machineName) {
        return authenticatedRegularMachine.getItem(machineName);
    }

    public Item getSpecialVMItem(String machineName) {
        return authenticatedSpecialMachine.getItem(machineName);
    }

    public boolean addRegularStock(String stockName, int stockPrice, double stockCalories, int stockItemAmount) {
        Item existingItem;
        existingItem = authenticatedRegularMachine.getItem(stockName);

        // If there is already an existing item, instead of adding a stock, it will be restocked instead.
        if (existingItem != null) {
            boolean successOp = authorizedOwner.restock(authenticatedRegularMachine, stockName, stockItemAmount);
            return successOp;
        }

        Item newItem = new Item(stockName, stockPrice, stockCalories);

        boolean success = authorizedOwner.stock(authenticatedRegularMachine, newItem, stockItemAmount);
        return success;
    }

    public boolean addSpecialStock(String stockName, int stockPrice, double stockCalories, int stockItemAmount) {
        Item existingItem;
        existingItem = authenticatedSpecialMachine.getItem(stockName);

        // If there is already an existing item, instead of adding a stock, it will be restocked instead.
        if (existingItem != null) {
            boolean successOp = authorizedOwner.restock(authenticatedSpecialMachine, stockName, stockItemAmount);
            return successOp;
        }

        Item newItem = new Item(stockName, stockPrice, stockCalories);

        boolean success = authorizedOwner.stock(authenticatedSpecialMachine, newItem, stockItemAmount);
        return success;
    }

    public boolean setRegularPrice(String selectedItem, int newPrice) {
        return authorizedOwner.setPrice(authenticatedRegularMachine, selectedItem, newPrice);
    }

    public boolean setSpecialPrice(String selectedItem, int newPrice) {
        return authorizedOwner.setPrice(authenticatedSpecialMachine, selectedItem, newPrice);
    }

    public String collectRegularMoney() {
        int balanceBefore = authorizedOwner.getBalance();
        int collectedMoney = authenticatedRegularMachine.getStockMoney();
        int balanceAfter = balanceBefore + collectedMoney;
        authorizedOwner.collectMoney(authenticatedRegularMachine);
        String resultMessage = "Balance before collecting money: " + balanceBefore + "\n"
                + "Collected money: " + collectedMoney + "\n"
                + "Balance after collecting money: " + balanceAfter + "\n";

        /*
            Informs the user of their balance before money was collected, the amount of collected money 
            and the balance after the money was collected 
         */
        return resultMessage;
    }

    public String collectSpecialMoney() {
        int balanceBefore = authorizedOwner.getBalance();
        int collectedMoney = authenticatedSpecialMachine.getStockMoney();
        int balanceAfter = balanceBefore + collectedMoney;
        authorizedOwner.collectMoney(authenticatedSpecialMachine);
        String resultMessage = "Balance before collecting money: " + balanceBefore + "\n"
                + "Collected money: " + collectedMoney + "\n"
                + "Balance after collecting money: " + balanceAfter + "\n";

        /*
            Informs the user of their balance before money was collected, the amount of collected money 
            and the balance after the money was collected 
         */
        return resultMessage;
    }

    public String replenishRegularMoney(int amount, int selectedValue) {
        Money money = authenticatedRegularMachine.getMoney(selectedValue);
        int beforeAmount = money.getAmount();

        if (authorizedOwner.getBalance() - (selectedValue * amount) < 0) {
            return "You do not have enough money to replenish this amount.";
        }

        authorizedOwner.replenishMoney(authenticatedRegularMachine, amount, selectedValue);

        /**
         * Informs the user the before replenish amount and after replenish
         * amount of a denomination
         */
        String message = "Before Replenish Amount: " + beforeAmount + "\n" + "After Replenish Amount: " + money.getAmount();
        return message;
    }

    public String replenishSpecialMoney(int amount, int selectedValue) {
        Money money = authenticatedSpecialMachine.getMoney(selectedValue);
        int beforeAmount = money.getAmount();

        if (authorizedOwner.getBalance() - (selectedValue * amount) < 0) {
            return "You do not have enough money to replenish this amount.";
        }

        authorizedOwner.replenishMoney(authenticatedSpecialMachine, amount, selectedValue);

        /**
         * Informs the user the before replenish amount and after replenish
         * amount of a denomination
         */
        String message = "Before Replenish Amount: " + beforeAmount + "\n" + "After Replenish Amount: " + money.getAmount();
        return message;
    }

    public boolean restockRegular(int itemAmount, String selectedItem) {
        boolean operationSuccessful;
        operationSuccessful = authorizedOwner.restock(authenticatedRegularMachine, selectedItem, itemAmount);

        return operationSuccessful;
    }

    public boolean restockSpecial(int itemAmount, String selectedItem) {
        boolean operationSuccessful;
        operationSuccessful = authorizedOwner.restock(authenticatedSpecialMachine, selectedItem, itemAmount);

        return operationSuccessful;
    }

    public List<Transaction> getRegularTransactions() {
        ArrayList<Transaction> transactions = authenticatedRegularMachine.getTransactions();
        List<Transaction> transactionsToSummarize = transactions.stream().filter(t -> t.getTransactionDate() > authenticatedRegularMachine.lastRestockDate).toList();
        return transactionsToSummarize;
    }

    public List<Transaction> getSpecialTransactions() {
        ArrayList<Transaction> transactions = authenticatedSpecialMachine.getTransactions();
        List<Transaction> transactionsToSummarize = transactions.stream().filter(t -> t.getTransactionDate() > authenticatedSpecialMachine.lastRestockDate).toList();
        return transactionsToSummarize;
    }
}
