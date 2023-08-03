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
 * This class represents a controller for the RegularVMView, which displays the
 * user interface for a regular vending machine.
 * It manages the interactions between the RegularVMView and the RVMModel.
 * The RegularVMController is responsible for handling user actions on the
 * regular vending machine, such as adding items,
 * processing payment, dispensing items, canceling transactions, and resetting
 * the machine.
 * 
 * The RegularVMController listens to events triggered by buttons and input
 * fields in the RegularVMView and responds accordingly.
 * It uses the RVMModel to manage the state and logic of the regular vending
 * machine, including adding items, calculating total cost,
 * processing payment, and dispensing items.
 * 
 * The RegularVMView displays two tables, one for available items and the other
 * for chosen items. The controller updates these tables
 * based on the actions performed by the user.
 * 
 * The ResetListener is triggered when the user clicks the "Reset" button in the
 * RegularVMView.
 * It resets the regular vending machine to its initial state, clearing the
 * chosen items, payment information, and messages.
 * 
 * The AddItemListener is triggered when the user clicks the "Add Item" button
 * in the RegularVMView.
 * It adds the selected item to the chosen items table and updates the total
 * cost accordingly.
 * 
 * The PaymentListener is triggered when the user clicks the "Pay" button in the
 * RegularVMView.
 * It processes the payment, checks if the payment is sufficient, and calculates
 * the change.
 * 
 * The DispenseListener is triggered when the user clicks the "Dispense" button
 * in the RegularVMView.
 * It dispenses the chosen items if the payment is sufficient and updates the
 * available items table accordingly.
 * 
 * The CancelDispenseListener is triggered when the user clicks the "Cancel
 * Dispense" button in the RegularVMView.
 * It cancels the current transaction and resets the regular vending machine to
 * its initial state.
 * 
 * The BackListener is triggered when the user clicks the "Back" button in the
 * RegularVMView.
 * It navigates back to the main dashboard (DashboardController) while passing
 * the authenticated owner.
 * 
 * 
 * @author Darryl
 */
public class RegularVMController {

    String[] chosenItemColumnNames = { "Name", "Price", "Quantity", "Total" };
    DefaultTableModel chosenItemTableModel = new DefaultTableModel(chosenItemColumnNames, 0);
    String[] availableItemColumnNames = { "Name", "Price", "Calories", "Stock" };
    DefaultTableModel availableItemTableModel = new DefaultTableModel(availableItemColumnNames, 0);

    final private RegularVMView rvmView;
    final private RVMModel rvmModel;

    /**
     * Constructs a new RegularVMController with the provided owner and
     * authenticatedRegularMachine.
     * Initializes the RVMModel and RegularVMView.
     *
     * @param owner                       The owner of the vending machine.
     * @param authenticatedRegularMachine The authenticated regular vending machine.
     */
    public RegularVMController(Owner owner, RegularVendingMachine authenticatedRegularMachine) {
        rvmModel = new RVMModel(owner, authenticatedRegularMachine);
        rvmView = new RegularVMView();

        rvmView.getAvailableItemsTable().setModel(availableItemTableModel);
        rvmView.getChosenItemsTable().setModel(chosenItemTableModel);
        ArrayList<Slot> machineSlots = rvmModel.getAuthRegularMachine().getItemSlots();

        this.rvmView.addBackListener(new BackListener());
        this.rvmView.addResetListener(new ResetListener());
        this.rvmView.addItemListener(new AddItemListener());
        this.rvmView.addPaymentListener(new PaymentListener());
        this.rvmView.addDispenseListener(new DispenseListener());
        this.rvmView.addCancelDispenseListener(new CancelDispenseListener());

        // This adds the details of the items to the table and display it
        for (Slot slot : machineSlots) {
            if (slot.getSlotItemType() != null) {
                Item slotItem = slot.getSlotItemType();
                Object[] row = { slotItem.getName(), slotItem.getPrice(), slotItem.getCalories(),
                        slot.getItemQuantity() };
                availableItemTableModel.addRow(row);
            }
        }

        rvmView.setLocationRelativeTo(null);
        rvmView.setVisible(true);
    }

    /**
     * Resets the information displayed in the RegularVMView and updates the
     * available items table.
     * Called when the "Reset" button is clicked or after a transaction is
     * completed.
     */
    private void resetInfo() {

        rvmModel.resetInfo();

        rvmView.setInputItemName("");
        rvmView.setInputQuantity("");
        rvmView.setTotalCost("0");
        rvmView.setTotalChange("0");

        rvmView.toggleAddItem(true);
        rvmView.toggleDispense(false);
        rvmView.toggleCancelDispense(false);
        rvmView.togglePay(false);

        chosenItemTableModel.setRowCount(0);
        availableItemTableModel.setRowCount(0);

        ArrayList<Slot> machineSlots = rvmModel.getAuthRegularMachine().getItemSlots();

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
     * RegularVMView.
     * This method is triggered when the "Reset" button is clicked in the
     * RegularVMView.
     */
    class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            resetInfo();
        }
    }

    /**
     * ActionListener implementation for handling the "Add Item" button in the
     * RegularVMView.
     * This method is triggered when the "Add Item" button is clicked in the
     * RegularVMView.
     */
    class AddItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String itemName = rvmView.getInputItemName();
            int dispenseQuantity = rvmView.getInputQuantity();

            Response addItemResponse = rvmModel.addItem(itemName, dispenseQuantity);
            boolean responseSuccess = addItemResponse.getStatus();
            String responseMessage = addItemResponse.getMessage();

            if (responseSuccess) {
                String[] columnNames = { "Name", "Price", "Quantity", "Total" };
                DefaultTableModel chosenItemTableModel = new DefaultTableModel(columnNames, 0);

                rvmView.getChosenItemsTable().setModel(chosenItemTableModel);

                Item selectedItem = rvmModel.getSelectedItem();
                int totalCost = selectedItem.getPrice() * dispenseQuantity;

                Object[] row = { selectedItem.getName(), selectedItem.getPrice(), dispenseQuantity, totalCost };
                chosenItemTableModel.addRow(row);

                rvmView.setTotalCost(Integer.toString(totalCost));
                rvmView.setInputItemName("");
                rvmView.setInputQuantity("");
                rvmView.toggleAddItem(false);
                rvmView.togglePay(true);
            } else {
                rvmView.setInputItemName("");
                rvmView.setInputQuantity("");
            }

            if (addItemResponse.getMessage() != null) {
                rvmView.showMessage(responseMessage);
            }
        }
    }

    /**
     * ActionListener implementation for handling the "Pay" button in the
     * RegularVMView.
     * This method is triggered when the "Pay" button is clicked in the
     * RegularVMView.
     */
    class PaymentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Response paymentResponse;
            rvmView.togglePay(false);

            int payment = rvmView.getPayment();
            int totalCost = rvmView.getTotalCost();

            paymentResponse = rvmModel.receivePayment(payment, totalCost);
            boolean responseSuccess = paymentResponse.getStatus();
            String responseMessage = paymentResponse.getMessage();

            if (responseSuccess) {
                int change = payment - totalCost;
                rvmView.setTotalChange(Integer.toString(change));

                rvmView.setPayment("");
                rvmView.toggleDispense(true);
                rvmView.toggleCancelDispense(true);
            } else {
                rvmView.setPayment("");
                rvmView.togglePay(true);
            }

            if (paymentResponse.getMessage() != null) {
                rvmView.showMessage(responseMessage);
            }
        }
    }

    /**
     * ActionListener implementation for handling the "Dispense" button in the
     * RegularVMView.
     * This method is triggered when the "Dispense" button is clicked in the
     * RegularVMView.
     * It retrieves the total cost and total change from the view and calls the
     * dispenseItem() method from the RVMModel
     * to dispense the chosen items if the payment is sufficient.
     * If the transaction is successful, it displays a success message.
     * If the transaction fails (e.g., insufficient payment or item out of stock),
     * it displays an error message,
     * disables the "Dispense" button, and enables the "Cancel Dispense" button.
     * After dispense or failure, it calls the resetInfo() method to reset the
     * regular vending machine's state.
     */
    class DispenseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // For the total
            int totalCost = rvmView.getTotalCost(); // gets the total cost of the items
            int totalChange = rvmView.getTotalChange(); // gets the total change
            Response dispenseResponse = rvmModel.dispenseItem(totalCost, totalChange);
            boolean responseSuccess = dispenseResponse.getStatus();
            String responseMessage = dispenseResponse.getMessage();

            if (!responseSuccess) {
                rvmView.toggleDispense(false);
                rvmView.toggleCancelDispense(true);
                rvmView.showMessage(responseMessage);
                return;
            }

            if (responseMessage != null) {
                rvmView.showMessage(responseMessage);
            }
            resetInfo();
        }
    }

    /**
     * ActionListener implementation for handling the "Cancel Dispense" button in
     * the RegularVMView.
     * This method is triggered when the "Cancel Dispense" button is clicked in the
     * RegularVMView.
     * It calls the cancelDispense() method from the RVMModel to cancel the current
     * transaction and reset the regular vending machine.
     * If the transaction was canceled successfully, it displays a success message
     * and calls the resetInfo() method
     * to reset the regular vending machine's state.
     */
    class CancelDispenseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Response dispenseResponse = rvmModel.cancelDispense();
            boolean responseSuccess = dispenseResponse.getStatus();
            String responseMessage = dispenseResponse.getMessage();

            if (responseMessage != null && responseSuccess) {
                rvmView.showMessage(responseMessage);
                resetInfo();
            }
        }
    }

    /**
     * ActionListener implementation for handling the "Back" button in the
     * RegularVMView.
     * This method is triggered when the "Back" button is clicked in the
     * RegularVMView.
     * It navigates back to the main dashboard (DashboardController) while passing
     * the authenticated owner.
     */
    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authenticatedOwner = rvmModel.getAuthOwner();
            DashboardController dashboard = new DashboardController(authenticatedOwner);
            rvmView.dispose();
        }
    }

}
