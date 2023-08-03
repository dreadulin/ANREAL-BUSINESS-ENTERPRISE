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
 * Controller class for the SpecialVMView, responsible for handling user
 * interactions and updating the view.
 */
public class SpecialVMController {

    final private SpecialVMView svmView;
    final private SVMModel svmModel;
    private int currentTotalCost = 0;

    String[] columnNames = { "Name", "Price", "Quantity", "Total" };
    String[] columnNames2 = { "Name", "Price", "Calories", "Stock" };
    DefaultTableModel cartItemTableModel = new DefaultTableModel(columnNames, 0);
    DefaultTableModel availableItemTableModel = new DefaultTableModel(columnNames2, 0);

    /**
     * Constructor for the SpecialVMController class.
     * Initializes the controller with the provided owner and authenticated special
     * vending machine.
     *
     * @param owner                       The owner of the vending machine.
     * @param authenticatedSpecialMachine The authenticated special vending machine
     *                                    object.
     */
    public SpecialVMController(Owner owner, SpecialVendingMachine authenticatedSpecialMachine) {
        svmModel = new SVMModel(owner, authenticatedSpecialMachine);
        svmView = new SpecialVMView();

        svmView.getCartTable().setModel(cartItemTableModel);
        svmView.getAvailableItemsTable().setModel(availableItemTableModel);
        ArrayList<Slot> machineSlots = svmModel.getAuthSpecialMachine().getItemSlots();

        this.svmView.addBackListener(new BackListener());
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
                Object[] row = { slotItem.getName(), slotItem.getPrice(), slotItem.getCalories(),
                        slot.getItemQuantity() };
                availableItemTableModel.addRow(row);
            }
        }

        svmView.getSpecialComboBox().addItem("Select Combo Item");
        for (SpecialItem item : authenticatedSpecialMachine.getSpecialItems()) {
            svmView.getSpecialComboBox().addItem(item.getName());
        }

        svmView.setLocationRelativeTo(null);
        svmView.setVisible(true);
    }

    /**
     * Resets the information in the view and updates the available items table.
     * Called after completing a transaction or when the "Reset" button is clicked.
     */
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
                Object[] row = { slotItem.getName(), slotItem.getPrice(), slotItem.getCalories(),
                        slot.getItemQuantity() };
                availableItemTableModel.addRow(row);
            }
        }
    }

    /**
     * ActionListener implementation for handling the "Reset" button in the
     * SpecialVMView.
     * This method is triggered when the "Reset" button is clicked in the
     * SpecialVMView.
     * It resets the information in the view and updates the available items table.
     */
    class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            resetInfo();
        }
    }

    /**
     * ActionListener implementation for handling the "Add Item" button in the
     * SpecialVMView.
     * This method is triggered when the "Add Item" button is clicked in the
     * SpecialVMView.
     * It adds the selected item to the cart, updates the cart table, and calculates
     * the total cost.
     * If the item is already in the cart, it updates the quantity and total cost.
     * Displays a message if the item cannot be added due to insufficient stock or
     * other reasons.
     */

    class AddItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            String itemName = svmView.getInputItemName();
            int dispenseQuantity = svmView.getInputQuantity();

            Response addItemResponse = svmModel.addItem(itemName, dispenseQuantity);
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
                    Object[] row = { currItem.getName(), currItem.getPrice(), currItemQt,
                            currItem.getPrice() * currItemQt };
                    cartItemTableModel.addRow(row);
                }

                svmView.setTotalCost(Integer.toString(currentTotalCost));
                svmView.setInputItemName("");
                svmView.setInputQuantity("");
                svmView.togglePay(true);
            } else if (responseSuccess) {
                selectedItem = svmModel.getSelectedItem(itemName);
                int totalCost = selectedItem.getPrice() * dispenseQuantity;

                Object[] row = { selectedItem.getName(), selectedItem.getPrice(), dispenseQuantity, totalCost };
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

    /**
     * ActionListener implementation for handling the "Pay" button in the
     * SpecialVMView.
     * This method is triggered when the "Pay" button is clicked in the
     * SpecialVMView.
     * It processes the payment entered by the user and updates the total change.
     * Calls the SVMModel's receivePayment method to handle the payment and
     * calculate the change.
     * Displays a message if the payment is insufficient or if there are other
     * payment-related issues.
     */
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
                svmView.toggleAddItem(false);
            } else {
                svmView.setPayment("");
                svmView.togglePay(true);
            }

            if (paymentResponse.getMessage() != null) {
                svmView.showMessage(responseMessage);
            }
        }
    }

    /**
     * ActionListener implementation for handling the "Dispense" button in the
     * SpecialVMView.
     * This method is triggered when the "Dispense" button is clicked in the
     * SpecialVMView.
     * It dispenses the items from the cart and updates the stock quantity in the
     * special vending machine.
     * Displays a message if the dispense operation fails or if there are any issues
     * during the process.
     */
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

    /**
     * ActionListener implementation for handling the "Cancel Dispense" button in
     * the SpecialVMView.
     * This method is triggered when the "Cancel Dispense" button is clicked in the
     * SpecialVMView.
     * It cancels the ongoing dispense operation and returns the items to the stock
     * in the vending machine.
     */
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

    /**
     * ActionListener implementation for handling the change in the selected special
     * combo in the SpecialVMView.
     * This method is triggered when the selected special combo in the
     * specialComboBox changes in the SpecialVMView.
     * It updates the cart and total cost based on the selected special combo.
     * Adds the ingredients of the selected special combo to the cart and updates
     * the total cost accordingly.
     */
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
                    cartItemTableModel.setRowCount(0);
                }
                case "Crema De Leche" -> {
                    CremaDeLeche cremaDeLeche = new CremaDeLeche();
                    ingredients = cremaDeLeche.getIngredients();
                    cartItemTableModel.setRowCount(0);
                }
                case "Halo Halo" -> {
                    CustomHaloHalo customHaloHalo = new CustomHaloHalo();
                    ingredients = customHaloHalo.getIngredients();
                    cartItemTableModel.setRowCount(0);
                }
                default -> {
                    return;
                }
            }

            int totalCost = 0;
            for (String ingredient : ingredients) {
                Item item = svmModel.getAuthSpecialMachine().getItem(ingredient);
                if (item != null) {
                    Object[] row = { item.getName(), item.getPrice(), 1, item.getPrice() };
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

    /**
     * ActionListener implementation for handling the "Back" button in the
     * SpecialVMView.
     * This method is triggered when the "Back" button is clicked in the
     * SpecialVMView.
     * Returns to the DashboardController and disposes of the SpecialVMView.
     */

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authenticatedOwner = svmModel.getAuthOwner();
            DashboardController dashboard = new DashboardController(authenticatedOwner);
            svmView.dispose();
        }
    }

}
