/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents a controller for changing prices of items in a Special
 * Vending Machine.
 * It manages interactions between the ChangePriceSVMView and the
 * MaintenanceModel.
 * 
 * @author Darryl
 */
public class ChangePriceSVMController {

    final private ChangePriceSVMView changePriceSVMView;
    final private MaintenanceModel maintenanceModel;

    /**
     * Constructs a new ChangePriceSVMController with the provided parameters.
     *
     * @param owner          The owner of the vending machine.
     * @param regularMachine The RegularVendingMachine object.
     * @param specialMachine The SpecialVendingMachine object.
     */
    public ChangePriceSVMController(Owner owner, RegularVendingMachine regularMachine,
            SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        changePriceSVMView = new ChangePriceSVMView();

        for (Slot slot : maintenanceModel.getAuthSpecialMachine().getItemSlots()) {
            if (slot.getSlotItemType() != null) {
                changePriceSVMView.getItemComboBox().addItem(slot.getSlotItemType().getName());
            }
        }

        this.changePriceSVMView.addBackListener(new BackListener());
        this.changePriceSVMView.addChangePriceListener(new ChangePriceListener());
        this.changePriceSVMView.addItemChangeListener(new ItemChangeListener());
        changePriceSVMView.setLocationRelativeTo(null);
        changePriceSVMView.setVisible(true);
    }

    /**
     * ActionListener implementation for handling the "Change Price" button in the
     * ChangePriceSVMView.
     * This method is triggered when the "Change Price" button is clicked in the
     * ChangePriceSVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class ChangePriceListener implements ActionListener {

        /**
         * Performs the action when the "Change Price" button is clicked.
         * Gets the selected item name and new price from the ChangePriceSVMView,
         * attempts to change the price of the selected item in the Special Vending
         * Machine,
         * and displays a success or error message accordingly.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            String selected = changePriceSVMView.getItemComboBox().getSelectedItem().toString();
            int newPrice = changePriceSVMView.getInputPrice();

            boolean success = maintenanceModel.setSpecialPrice(selected, newPrice);

            if (success) {
                changePriceSVMView.showSuccessMessage();
            } else {
                changePriceSVMView.showErrorMessage();
            }

            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner,
                    authRegular, authSpecial);
            changePriceSVMView.dispose();
        }
    }

    /**
     * ActionListener implementation for handling the item selection change in the
     * ChangePriceSVMView.
     * This method is triggered when the user selects an item from the item combo
     * box in the ChangePriceSVMView.
     *
     * @param arg0 The ActionEvent object representing the item selection change
     *             event.
     */
    class ItemChangeListener implements ActionListener {

        /**
         * Performs the action when the user selects an item from the item combo box.
         * Gets the selected item name, retrieves the corresponding item from the
         * Special Vending Machine,
         * and updates the current price displayed in the ChangePriceSVMView
         * accordingly.
         *
         * @param arg0 The ActionEvent object representing the item selection change
         *             event.
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String selectedItemName = changePriceSVMView.getItemComboBox().getSelectedItem().toString();
            Item selectedItem = maintenanceModel.getSpecialVMItem(selectedItemName);
            changePriceSVMView.setCurrentPrice(selectedItem.getPrice());
        }
    }

    /**
     * ActionListener implementation for handling the "Back" button in the
     * ChangePriceSVMView.
     * This method is triggered when the "Back" button is clicked in the
     * ChangePriceSVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class BackListener implements ActionListener {

        /**
         * Performs the action when the "Back" button is clicked.
         * Gets the authenticated owner, regular vending machine, and special vending
         * machine,
         * opens the TestSpecialMaintenanceController, and closes the
         * ChangePriceSVMView.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner,
                    authRegular, authSpecial);
            changePriceSVMView.dispose();
        }
    }
}
