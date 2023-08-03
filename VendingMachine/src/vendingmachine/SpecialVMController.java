/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Darryl
 */
public class SpecialVMController {

    final private SpecialVMView svmView;
    final private SVMModel svmModel;

    String[] columnNames = {"Name", "Price", "Quantity", "Total"};
    String[] columnNames2 = {"Name", "Price", "Calories", "Stock"};
    DefaultTableModel cartItemTableModel = new DefaultTableModel(columnNames, 0);
    DefaultTableModel availableItemTableModel = new DefaultTableModel(columnNames2, 0);

    public SpecialVMController(Owner owner, SpecialVendingMachine authenticatedSpecialMachine) {
        svmModel = new SVMModel(owner, authenticatedSpecialMachine);
        svmView = new SpecialVMView();

        svmView.getCartTable().setModel(cartItemTableModel);
        svmView.getAvailableItemsTable().setModel(availableItemTableModel);
        ArrayList<Slot> machineSlots = svmModel.getAuthSpecialMachine().getItemSlots();

        this.svmView.addResetListener(new ResetListener());
        this.svmView.addItemListener(new AddItemListener());
        this.svmView.addPaymentListener(new PaymentListener());
        this.svmView.addDispenseListener(new DispenseListener());
        this.svmView.addCancelDispenseListener(new CancelDispenseListener());
        this.svmView.addSpecialComboChangeListener(new SpecialComboChangeListener());

        // This adds the details of the items to the table and display it
        for (Slot slot : machineSlots) {
            if (slot.getSlotItemType() != null) {
                Item slotItem = slot.getSlotItemType();
                Object[] row = {slotItem.getName(), slotItem.getPrice(), slotItem.getCalories(), slot.getItemQuantity()};
                availableItemTableModel.addRow(row);
            }
        }

        svmView.getSpecialComboBox().addItem("");
        for (SpecialItem item : authenticatedSpecialMachine.getSpecialItems()){
            svmView.getSpecialComboBox().addItem(item.getName());
        }

        svmView.setVisible(true);
    }

    private void resetInfo() {

        svmModel.resetInfo();
        svmView.setInputItemName("");
        svmView.setInputQuantity("");
        svmView.setTotalCost("");
        svmView.setTotalChange("");

        svmView.toggleAddItem(true);
        svmView.toggleDispense(false);
        svmView.toggleCancelDispense(false);
        svmView.togglePay(false);

        cartItemTableModel.setRowCount(0);
        availableItemTableModel.setRowCount(0);

        ArrayList<Slot> machineSlots = svmModel.getAuthSpecialMachine().getItemSlots();

        for (Slot slot : machineSlots) {
            if (slot.getSlotItemType() != null) {
                Item slotItem = slot.getSlotItemType();
                Object[] row = {slotItem.getName(), slotItem.getPrice(), slotItem.getCalories(), slot.getItemQuantity()};
                availableItemTableModel.addRow(row);
            }
        }
    }


    class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            resetInfo();
        }
    }

    class AddItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            String itemName = svmView.getInputItemName();
            int dispenseQuantity = svmView.getInputQuantity();
            int currentTotalCost = svmView.getTotalCost();

            Response addItemResponse = svmModel.addItem(itemName, dispenseQuantity, currentTotalCost);
            boolean responseSuccess = addItemResponse.getStatus();
            String responseMessage = addItemResponse.getMessage();
            Item selectedItem = (Item) addItemResponse.getResponse();

            if (responseSuccess && selectedItem != null) {
                ArrayList<Item> selectedItems = svmModel.getSelectedItems();
                ArrayList<Integer> selectedItemsQuantities = svmModel.getSelectedItemsQuantities();
                int totalCost = selectedItem.getPrice() * dispenseQuantity;

                currentTotalCost += totalCost;

                // RECREATE THE CART TABLE
                cartItemTableModel.setRowCount(0);
                for (int i = 0; i < selectedItems.size(); ++i) {
                    Item currItem = selectedItems.get(i);
                    int currItemQt = selectedItemsQuantities.get(i);
                    Object[] row = {currItem.getName(), currItem.getPrice(), currItemQt, currItem.getPrice() * currItemQt};
                    cartItemTableModel.addRow(row);
                }

                svmView.setTotalCost(Integer.toString(currentTotalCost));
                svmView.setInputItemName("");
                svmView.setInputQuantity("");
                svmView.togglePay(true);
            } else if (responseSuccess && selectedItem == null) {
                selectedItem = svmModel.getSelectedItem(itemName);
                int totalCost = selectedItem.getPrice() * dispenseQuantity;

                Object[] row = {selectedItem.getName(), selectedItem.getPrice(), dispenseQuantity, totalCost};
                cartItemTableModel.addRow(row);

                currentTotalCost += totalCost;
                svmView.setTotalCost(Integer.toString(currentTotalCost));
                svmView.setInputItemName("");
                svmView.setInputQuantity("");
                svmView.togglePay(true);
            } else {
                svmView.setInputItemName("");
                svmView.setInputQuantity("");
            }

            if (addItemResponse.getMessage() != null) {
                svmView.showMessage(responseMessage);
            }
        }
    }

    class PaymentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Response paymentResponse;
            svmView.togglePay(false);

            int payment = svmView.getPayment();
            int totalCost = svmView.getTotalCost();

            paymentResponse = svmModel.receivePayment(payment, totalCost);
            boolean responseSuccess = paymentResponse.getStatus();
            String responseMessage = paymentResponse.getMessage();

            if (responseSuccess) {
                int change = payment - totalCost;
                svmView.setTotalChange(Integer.toString(change));

                svmView.setPayment("");
                svmView.toggleDispense(true);
                svmView.toggleCancelDispense(true);
            } else {
                svmView.setPayment("");
                svmView.togglePay(true);
            }

            if (paymentResponse.getMessage() != null) {
                svmView.showMessage(responseMessage);
            }
        }
    }

    class DispenseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int totalCost = svmView.getTotalCost();
            int totalChange = svmView.getTotalChange();

            Response dispenseResponse = svmModel.dispenseItem(totalCost, totalChange);
            boolean responseSuccess = dispenseResponse.getStatus();
            String responseMessage = dispenseResponse.getMessage();

            if (!responseSuccess) {
                svmView.toggleDispense(false);
                svmView.toggleCancelDispense(true);
                svmView.showMessage(responseMessage);
                return;
            }

            if (dispenseResponse.getMessage() != null) {
                svmView.showMessage(responseMessage);
            }

            resetInfo();
        }
    }

    class CancelDispenseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Response dispenseResponse = svmModel.cancelDispense();
            boolean responseSuccess = dispenseResponse.getStatus();
            String responseMessage = dispenseResponse.getMessage();

            if (responseSuccess && responseMessage != null) {
                svmView.showMessage(responseMessage);
                resetInfo();
            }

        }
    }

    class SpecialComboChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            svmModel.resetInfo();

            String[] ingredients = new String[0];
            switch (svmView.getSpecialComboBox().getSelectedItem().toString()) {
                case "Select Combo Item" -> {
                    svmModel.resetInfo();
                    svmView.setInputItemName("");
                    svmView.setInputQuantity("");
                    svmView.setTotalCost("");
                    svmView.setTotalChange("");

                    svmView.toggleAddItem(true);
                    svmView.toggleDispense(false);
                    svmView.toggleCancelDispense(false);
                    svmView.togglePay(false);

                    cartItemTableModel.setRowCount(0);
                    return;
                }
                case "Ultimate Halo Halo" -> {
                    UltimateHaloHalo ultimateHaloHalo = new UltimateHaloHalo();
                    ingredients = ultimateHaloHalo.getIngredients();
                }
                case "Crema De Leche" -> {
                    CremaDeLeche cremaDeLeche = new CremaDeLeche();
                    ingredients = cremaDeLeche.getIngredients();
                }
                case "Halo Halo" -> {
                    CustomHaloHalo customHaloHalo = new CustomHaloHalo();
                    ingredients = customHaloHalo.getIngredients();
                }
                default -> {
                    return;
                }
            }

            int totalCost = 0;
            for (String ingredient : ingredients) {
                Item item = svmModel.getAuthSpecialMachine().getItem(ingredient);
                if (item != null) {
                    Object[] row = {item.getName(), item.getPrice(), item.getCalories(), 1};
                    totalCost += item.getPrice();
                    cartItemTableModel.addRow(row);
                    svmModel.getSelectedItems().add(item);
                    svmModel.getSelectedItemsQuantities().add(1);
                }
            }

            svmView.setTotalCost(Integer.toString(totalCost));
            svmView.setInputItemName("");
            svmView.setInputQuantity("");
            svmView.togglePay(true);
        }
    }
}
