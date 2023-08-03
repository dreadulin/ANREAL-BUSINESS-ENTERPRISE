/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for the RestockSVMView, responsible for handling user
 * interactions and updating the view.
 */
public class RestockSVMController {

    final private RestockSVMView restockSVMView;
    final private MaintenanceModel maintenanceModel;

    /**
     * Constructor for the RestockSVMController class.
     * Initializes the controller with the provided owner, regular vending machine,
     * and special vending machine.
     *
     * @param owner          The owner of the vending machine.
     * @param regularMachine The regular vending machine object.
     * @param specialMachine The special vending machine object.
     */
    public RestockSVMController(Owner owner, RegularVendingMachine regularMachine,
            SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        restockSVMView = new RestockSVMView();

        this.restockSVMView.addBackListener(new BackListener());
        this.restockSVMView.addRestockListener(new RestockListener());
        this.restockSVMView.addItemChangeListener(new ItemChangeListener());

        for (Slot slot : maintenanceModel.getAuthSpecialMachine().getItemSlots()) {
            if (slot.getSlotItemType() != null) {
                restockSVMView.getItemComboBox().addItem(slot.getSlotItemType().getName());
            }
        }

        restockSVMView.setLocationRelativeTo(null);
        restockSVMView.setVisible(true);
    }

    /**
     * ActionListener implementation for handling the "Restock" button in the
     * RestockSVMView.
     * This method is triggered when the "Restock" button is clicked in the
     * RestockSVMView.
     * It retrieves the selected item name and the restock amount from the view and
     * calls the restockSpecial() method
     * from the MaintenanceModel to restock the selected item in the special vending
     * machine with the specified amount.
     * The success of the restocking operation is displayed in the view through a
     * message dialog.
     * After restocking, it calls the TestSpecialMaintenanceController to return to
     * the special maintenance menu.
     */
    class RestockListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            int amount = restockSVMView.getInputAmount();
            String selectedItemName = restockSVMView.getItemComboBox().getSelectedItem().toString();

            boolean success = maintenanceModel.restockSpecial(amount, selectedItemName);

            if (success) {
                restockSVMView.showSuccessMessage();
            } else {
                restockSVMView.showErrorMessage();
            }

            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner,
                    authRegular, authSpecial);
            restockSVMView.dispose();
        }
    }

    /**
     * ActionListener implementation for handling the change in the selected item in
     * the RestockSVMView.
     * This method is triggered when the selected item in the itemComboBox is
     * changed in the RestockSVMView.
     * It retrieves the selected item name from the view and updates the displayed
     * stock quantity of the selected item.
     * The stock quantity is fetched from the MaintenanceModel based on the selected
     * item name and displayed in the view.
     * If the selected item is not found in the item slots, it calls the
     * TestSpecialMaintenanceController to return to
     * the special maintenance menu.
     */

    class ItemChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            String selectedItemName = restockSVMView.getItemComboBox().getSelectedItem().toString();

            for (Slot slot : maintenanceModel.getAuthSpecialMachine().getItemSlots()) {
                if (slot.getSlotItemType() != null) {
                    if (slot.getSlotItemType().getName().equals(selectedItemName)) {
                        restockSVMView.setStockQuantity(slot.getItemQuantity());
                        return;
                    }
                }
            }

            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner,
                    authRegular, authSpecial);
            restockSVMView.dispose();
        }
    }

    /**
     * ActionListener implementation for handling the "Back" button in the
     * RestockSVMView.
     * This method is triggered when the "Back" button is clicked in the
     * RestockSVMView.
     * It returns to the TestSpecialMaintenanceController and disposes of the
     * RestockSVMView.
     */
    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner,
                    authRegular, authSpecial);
            restockSVMView.dispose();
        }
    }

}
