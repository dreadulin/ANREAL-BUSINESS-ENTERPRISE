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
public class RegularVMController {

    String[] chosenItemColumnNames = {"Name", "Price", "Quantity", "Total"};
    DefaultTableModel chosenItemTableModel = new DefaultTableModel(chosenItemColumnNames, 0);
    String[] availableItemColumnNames = {"Name", "Price", "Calories", "Stock"};
    DefaultTableModel availableItemTableModel = new DefaultTableModel(availableItemColumnNames, 0);

    final private RegularVMView rvmView;
    final private RVMModel rvmModel;

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
                Object[] row = {slotItem.getName(), slotItem.getPrice(), slotItem.getCalories(), slot.getItemQuantity()};
                availableItemTableModel.addRow(row);
            }
        }

        rvmView.setLocationRelativeTo(null);
        rvmView.setVisible(true);
    }

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
            String itemName = rvmView.getInputItemName();
            int dispenseQuantity = rvmView.getInputQuantity();

            Response addItemResponse = rvmModel.addItem(itemName, dispenseQuantity);
            boolean responseSuccess = addItemResponse.getStatus();
            String responseMessage = addItemResponse.getMessage();

            if (responseSuccess) {
                String[] columnNames = {"Name", "Price", "Quantity", "Total"};
                DefaultTableModel chosenItemTableModel = new DefaultTableModel(columnNames, 0);

                rvmView.getChosenItemsTable().setModel(chosenItemTableModel);

                Item selectedItem = rvmModel.getSelectedItem();
                int totalCost = selectedItem.getPrice() * dispenseQuantity;

                Object[] row = {selectedItem.getName(), selectedItem.getPrice(), dispenseQuantity, totalCost};
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

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authenticatedOwner = rvmModel.getAuthOwner();
            DashboardController dashboard = new DashboardController(authenticatedOwner);
            rvmView.dispose();
        }
    }

}
