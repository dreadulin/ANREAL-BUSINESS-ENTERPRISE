/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for the RestockRVMView, responsible for handling user
 * interactions and updating the view.
 */
public class RestockRVMController {

    final private RestockRVMView restockRVMView;
    final private MaintenanceModel maintenanceModel;

    /**
     * Constructor for the RestockRVMController class.
     * Initializes the controller with the provided owner, regular vending machine,
     * and special vending machine.
     *
     * @param owner          The owner of the vending machine.
     * @param regularMachine The regular vending machine object.
     * @param specialMachine The special vending machine object.
     */
    public RestockRVMController(Owner owner, RegularVendingMachine regularMachine,
            SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        restockRVMView = new RestockRVMView();

        this.restockRVMView.addBackListener(new BackListener());
        this.restockRVMView.addRestockListener(new RestockListener());
        this.restockRVMView.addItemChangeListener(new ItemChangeListener());

        for (Slot slot : maintenanceModel.getAuthRegularMachine().getItemSlots()) {
            if (slot.getSlotItemType() != null) {
                restockRVMView.getItemComboBox().addItem(slot.getSlotItemType().getName());
            }
        }

        restockRVMView.setLocationRelativeTo(null);
        restockRVMView.setVisible(true);
    }

    /**
     * ActionListener implementation for handling the "Restock" button in the
     * RestockRVMView.
     * This method is triggered when the "Restock" button is clicked in the
     * RestockRVMView.
     * It retrieves the selected item name and the restock amount from the view and
     * calls the restockRegular() method
     * from the MaintenanceModel to restock the selected item in the regular vending
     * machine with the specified amount.
     * The success of the restocking operation is displayed in the view through a
     * message dialog.
     * After restocking, it calls the TestRegularMaintenanceController to return to
     * the maintenance menu.
     */
    class RestockListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            int amount = restockRVMView.getInputAmount();
            String selectedItemName = restockRVMView.getItemComboBox().getSelectedItem().toString();

            boolean success = maintenanceModel.restockRegular(amount, selectedItemName);

            if (success) {
                restockRVMView.showSuccessMessage();
            } else {
                restockRVMView.showErrorMessage();
            }

            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner,
                    authRegular, authSpecial);
            restockRVMView.dispose();
        }
    }

    /**
     * ActionListener implementation for handling the change in the selected item in
     * the RestockRVMView.
     * This method is triggered when the selected item in the itemComboBox is
     * changed in the RestockRVMView.
     * It retrieves the selected item name from the view and updates the displayed
     * stock quantity of the selected item.
     * The stock quantity is fetched from the MaintenanceModel based on the selected
     * item name and displayed in the view.
     */
    class ItemChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String selectedItemName = restockRVMView.getItemComboBox().getSelectedItem().toString();

            for (Slot slot : maintenanceModel.getAuthRegularMachine().getItemSlots()) {
                System.out.println(slot.getItemQuantity());
                if (slot.getSlotItemType() != null) {
                    if (slot.getSlotItemType().getName().equals(selectedItemName)) {
                        restockRVMView.setStockQuantity(slot.getItemQuantity());
                        break;
                    }
                }
            }
        }
    }

    /**
     * ActionListener implementation for handling the "Back" button in the
     * RestockRVMView.
     * This method is triggered when the "Back" button is clicked in the
     * RestockRVMView.
     * It returns to the TestRegularMaintenanceController and disposes of the
     * RestockRVMView.
     */
    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner,
                    authRegular, authSpecial);
            restockRVMView.dispose();
        }
    }

}
