/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents a controller for changing prices of items in a Regular
 * Vending Machine.
 * It manages interactions between the ChangePriceRVMView and the
 * MaintenanceModel.
 * 
 * @author Darryl
 */
public class ChangePriceRVMController {

    final private ChangePriceRVMView changePriceRVMView;
    final private MaintenanceModel maintenanceModel;

    /**
     * Constructs a new ChangePriceRVMController with the provided parameters.
     *
     * @param owner          The owner of the vending machine.
     * @param regularMachine The RegularVendingMachine object.
     * @param specialMachine The SpecialVendingMachine object.
     */
    public ChangePriceRVMController(Owner owner, RegularVendingMachine regularMachine,
            SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        changePriceRVMView = new ChangePriceRVMView();

        for (Slot slot : maintenanceModel.getAuthRegularMachine().getItemSlots()) {
            if (slot.getSlotItemType() != null) {
                changePriceRVMView.getItemComboBox().addItem(slot.getSlotItemType().getName());
            }
        }

        this.changePriceRVMView.addBackListener(new BackListener());
        this.changePriceRVMView.addChangePriceListener(new ChangePriceListener());
        this.changePriceRVMView.addItemChangeListener(new ItemChangeListener());
        changePriceRVMView.setLocationRelativeTo(null);
        changePriceRVMView.setVisible(true);
    }

    /**
     * ActionListener implementation for handling the "Change Price" button in the
     * ChangePriceRVMView.
     * This method is triggered when the "Change Price" button is clicked in the
     * ChangePriceRVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */

    class ChangePriceListener implements ActionListener {

        /**
         * Performs the action when the "Change Price" button is clicked.
         * Gets the selected item name and new price from the ChangePriceRVMView,
         * attempts to change the price of the selected item in the Regular Vending
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
            String selected = changePriceRVMView.getItemComboBox().getSelectedItem().toString();
            int newPrice = changePriceRVMView.getInputPrice();

            boolean success = maintenanceModel.setRegularPrice(selected, newPrice);

            if (success) {
                changePriceRVMView.showSuccessMessage();
            } else {
                changePriceRVMView.showErrorMessage();
            }

            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner,
                    authRegular, authSpecial);
            changePriceRVMView.dispose();
        }
    }

    /**
     * ActionListener implementation for handling the item selection change in the
     * ChangePriceRVMView.
     * This method is triggered when the user selects an item from the item combo
     * box in the ChangePriceRVMView.
     *
     * @param arg0 The ActionEvent object representing the item selection change
     *             event.
     */

    class ItemChangeListener implements ActionListener {

        /**
         * Performs the action when the user selects an item from the item combo box.
         * Gets the selected item name, retrieves the corresponding item from the
         * Regular Vending Machine,
         * and updates the current price displayed in the ChangePriceRVMView
         * accordingly.
         *
         * @param arg0 The ActionEvent object representing the item selection change
         *             event.
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String selectedItemName = changePriceRVMView.getItemComboBox().getSelectedItem().toString();
            Item selectedItem = maintenanceModel.getRegularVMItem(selectedItemName);
            changePriceRVMView.setCurrentPrice(selectedItem.getPrice());
        }
    }

    /**
     * ActionListener implementation for handling the "Back" button in the
     * ChangePriceRVMView.
     * This method is triggered when the "Back" button is clicked in the
     * ChangePriceRVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class BackListener implements ActionListener {

        /**
         * Performs the action when the "Back" button is clicked.
         * Gets the authenticated owner, regular vending machine, and special vending
         * machine,
         * opens the TestRegularMaintenanceController, and closes the
         * ChangePriceRVMView.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner,
                    authRegular, authSpecial);
            changePriceRVMView.dispose();
        }
    }
}
