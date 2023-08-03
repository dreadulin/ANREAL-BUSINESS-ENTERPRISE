/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Darryl
 */
public class RestockRVMController {

    final private RestockRVMView restockRVMView;
    final private MaintenanceModel maintenanceModel;

    public RestockRVMController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        restockRVMView = new RestockRVMView();

        this.restockRVMView.addRestockListener(new RestockListener());
        this.restockRVMView.addItemChangeListener(new ItemChangeListener());

        restockRVMView.setVisible(true);
    }

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

            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner, authRegular, authSpecial);
            restockRVMView.dispose();
        }
    }

    class ItemChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            String selectedItemName = restockRVMView.getItemComboBox().getSelectedItem().toString();

            for (Slot slot : maintenanceModel.getAuthRegularMachine().getItemSlots()) {
                if (slot.getSlotItemType() != null) {
                    if (slot.getSlotItemType().getName().equals(selectedItemName)) {
                        restockRVMView.setStockQuantity(slot.getItemQuantity());
                        return;
                    }
                }
            }

            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner, authRegular, authSpecial);
            restockRVMView.dispose();
        }
    }
}
