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
public class SVMModel {

    Owner authorizedOwner;
    SpecialVendingMachine authenticatedSpecialMachine;
    ArrayList<Item> selectedItems = new ArrayList<>();
    ArrayList<Integer> selectedItemsQuantities = new ArrayList<>();
    int payment = 0;

    public SVMModel(Owner owner, SpecialVendingMachine authenticatedSpecialMachine) {
        this.authorizedOwner = owner;
        this.authenticatedSpecialMachine = authenticatedSpecialMachine;
    }

    public ArrayList<Item> getSelectedItems() {
        return this.selectedItems;
    }

    public ArrayList<Integer> getSelectedItemsQuantities() {
        return this.selectedItemsQuantities;
    }

    public Item getSelectedItem(String itemName) {
        for (Item item : selectedItems) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void setSelectedItems(ArrayList<Item> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public void setSelectedItemsQuantities(ArrayList<Integer> selectedItemsQuantities) {
        this.selectedItemsQuantities = selectedItemsQuantities;
    }

    public RegularVendingMachine getAuthSpecialMachine() {
        return authenticatedSpecialMachine;
    }

    public Owner getAuthOwner() {
        return authorizedOwner;
    }

    public void resetInfo() {
        selectedItems = new ArrayList<>();
        selectedItemsQuantities = new ArrayList<>();
    }

    public Response dispenseItem(int totalCost, int totalChange) {
        Response response;

        boolean dispenseChangeSuccess = authenticatedSpecialMachine.dispenseChange(payment, totalCost);

        if (!dispenseChangeSuccess) {
            response = new Response(false, "Vending machine money is not enough to dispense change for your transaction.", null);
            return response;
        }

        SpecialItem specialCombination = null;

        // Loops through Items array and checks if quantity is valid 
        if (selectedItems.size() > 1) {
            for (SpecialItem special : authenticatedSpecialMachine.getSpecialItems()) {
                if (special.compareIngredients(selectedItems)) {
                    specialCombination = special;
                    break;
                }
            }

            if (specialCombination == null) {
                response = new Response(false, "The special item could not be made.", null);
                return response;
            }
        }

        // ADD TRANSACTION FOR EACH ITEM PURCHASED
        for (int i = 0; i < selectedItems.size(); ++i) {
            int totalItemCost = selectedItems.get(i).getPrice() * selectedItemsQuantities.get(i);
            Transaction newTransaction = new Transaction(selectedItems.get(i), selectedItemsQuantities.get(i), totalItemCost);
            authenticatedSpecialMachine.addTransaction(newTransaction);
        }

        // REMOVE STOCK FOR EACH ITEM CHOSEN
        for (int i = 0; i < selectedItems.size(); ++i) {
            Slot itemSlot = null;
            String itemName = selectedItems.get(i).getName();
            for (Slot slot : authenticatedSpecialMachine.getItemSlots()) {
                if (slot.getSlotItemType() != null) {
                    if (slot.getSlotItemType().getName().equals(itemName)) {
                        itemSlot = slot;
                        break;
                    }
                }
            }

            if (itemSlot == null) {
                response = new Response(false, "No such item exists in the vending machine.", null);
                return response;
            }
            itemSlot.removeStock(selectedItemsQuantities.get(i));
        }

        authenticatedSpecialMachine.receivePayment(payment);

        String summaryString;
        if (specialCombination != null) {
            summaryString = String.format("""
                                             ==================
                                             DISPENSE SUMMARY
                                             ==================
                                             Item: %s
                                             Total Cost: P %d
                                             Total Paid: P %d
                                             Change: P %d
                                             ==================
                                             Thank you for shopping!
                                             """,
                    specialCombination.getName(), totalCost, payment, totalChange);
        } else {
            summaryString = String.format("""
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
                    selectedItems.get(0).getName(), selectedItemsQuantities.get(0), totalCost, payment, totalChange);
        }

        response = new Response(true, summaryString, null);
        this.resetInfo();
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
        authenticatedSpecialMachine.dispenseChange(payment, 0);
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

    public Response addItem(String itemName, int dispenseQuantity, int currentTotalCost) {
        Response response;

        Item selectedItem = null;
        Slot selectedItemSlot = null;

        // finds the slot the item belongs to 
        for (Slot slot : authenticatedSpecialMachine.getItemSlots()) {
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

        // IF ITEM ALREADY EXISTS, UPDATE THE ITEM IN SELECTED ITEMS AND IN THE CART.
        if (selectedItems.indexOf(selectedItem) >= 0) {
            int existingItemIndex = selectedItems.indexOf(selectedItem);
            int newQt = selectedItemsQuantities.get(existingItemIndex);
            newQt += dispenseQuantity;
            selectedItemsQuantities.set(existingItemIndex, newQt);

            response = new Response(true, null, selectedItem);
            return response;
        }

        selectedItems.add(selectedItem);
        selectedItemsQuantities.add(dispenseQuantity);

        response = new Response(true, null, null);
        return response;
    }
}
