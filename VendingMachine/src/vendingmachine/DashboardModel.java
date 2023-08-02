/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import javax.swing.JOptionPane;

/**
 *
 * @author Darryl
 */
public class DashboardModel {

    Owner authenticatedOwner;

    public DashboardModel(Owner owner) {
        authenticatedOwner = owner;
    }

    public Owner getAuthOwner() {
        return this.authenticatedOwner;
    }

    public boolean createRegularVM(String machineName, int slotsQt, int itemSlotsQt) {
        boolean regularMachineExists = authenticatedOwner.getRegularMachine(machineName) != null;

        // informs the user that creation of machine is unsuccessful if the vending machine already exists in the system
        if (regularMachineExists) {
            return false;
        }

        /*
            If the number of slots entered by the user is less than eight, this will inform 
            the user that they entered an invalid slots capacity. 
         */
        if (slotsQt < 8) {
            return false;
        }

        /*
            If the number of item per slot entered by the user is less than 10, this will inform 
            the user that they entered an invalid item per slot capacity. 
         */
        if (itemSlotsQt < 10) {
            return false;
        }

        RegularVendingMachine newVendingMachine = new RegularVendingMachine(authenticatedOwner, machineName, slotsQt, itemSlotsQt);
        authenticatedOwner.addMachine(newVendingMachine);
        return true;
    }
}
