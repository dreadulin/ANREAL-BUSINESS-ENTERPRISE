/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.util.ArrayList;
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

    public boolean addStock(String stockName, int stockPrice, double stockCalories, int stockItemAmount) {
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

}
