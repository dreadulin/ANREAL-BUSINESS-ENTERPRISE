/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.util.ArrayList;

/**
 *
 * @author Darryl
 */
public class RVMModel {

    Owner authorizedOwner;
    RegularVendingMachine authenticatedRegularMachine;
    Item selectedItem = null;
    Slot selectedItemSlot = null;
    int dispenseQuantity = 0;
    int payment = 0;

    public RVMModel(Owner owner, RegularVendingMachine authenticatedRegularMachine) {
        this.authorizedOwner = owner;
        this.authenticatedRegularMachine = authenticatedRegularMachine;
    }

    public Item getSelectedItem() {
        return this.selectedItem;
    }

    public Slot getSelectedItemSlot() {
        return this.selectedItemSlot;
    }

    public RegularVendingMachine getAuthRegularMachine() {
        return authenticatedRegularMachine;
    }

    public Owner getAuthOwner() {
        return authorizedOwner;
    }

    public void resetInfo() {
        selectedItem = null;
        selectedItemSlot = null;
    }

    public Response dispenseItem(int totalCost, int totalChange) {
        Response response;

        boolean dispenseChangeSuccess = authenticatedRegularMachine.dispenseChange(payment, totalCost);

        if (!dispenseChangeSuccess) {
            response = new Response(false, "Vending machine money is not enough to dispense change for your transaction.", null);
            return response;
        }

        authenticatedRegularMachine.receivePayment(payment);
        Transaction newTransaction = new Transaction(selectedItem, dispenseQuantity, totalCost);
        authenticatedRegularMachine.addTransaction(newTransaction);

        /*
         *  If everything checks out and everything is done, return the Item
         *  Remove the item(s) from their slot
         */
        selectedItemSlot.removeStock(dispenseQuantity);

        String summaryString = String.format("""
                                             ==================
                                             DISPENSE SUMMARY
                                             ==================
                                             Item: %s
                                             Quantity: %d
                                             Total Cost: P %d
                                             Total Paid: P %d
                                             Change: P %d
                                             ==================
                                             Thank you for shopping!
                                             """,
                selectedItem.getName(), dispenseQuantity, totalCost, payment, totalChange);

        this.resetInfo();
        response = new Response(true, summaryString, null);
        return response;
    }

    public Response receivePayment(int payment, int totalCost) {
        Response response;
        if (payment < (totalCost)) {
            response = new Response(false, "Your payment is not enough for this item.", null);
            return response;
        }

        this.payment = payment;

        response = new Response(true, null, null);

        return response;
    }

    public Response cancelDispense() {
        Response response;
        authenticatedRegularMachine.dispenseChange(payment, 0);
        String summaryString = String.format("""
                                             ==================
                                             DISPENSE CANCELLED
                                             ==================
                                             Change: P %d
                                             ==================
                                             Come again!
                                             """,
                payment);

        response = new Response(true, summaryString, null);
        this.resetInfo();
        return response;
    }

    public Response addItem(String itemName, int dispenseQuantity) {
        Response response;
        this.dispenseQuantity = dispenseQuantity;

        // finds the slot the item belongs to 
        for (Slot slot : authenticatedRegularMachine.getItemSlots()) {
            if (slot.getSlotItemType() != null) {
                if (slot.getSlotItemType().getName().equals(itemName)) {
                    selectedItemSlot = slot;
                    selectedItem = slot.getSlotItemType();
                    break;
                }
            }
        }

        if (selectedItemSlot == null || selectedItem == null) {
            response = new Response(false, "Item does not exist in the vending machine.", null);
            return response;
        }

        if (selectedItemSlot.getItemQuantity() < dispenseQuantity) {
            response = new Response(false, "Quantity exceeds the stock for the item.", null);
            return response;
        }

        response = new Response(true, null, null);
        return response;
    }

}
