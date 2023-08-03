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
public class RestockSVMController {

    final private RestockSVMView restockSVMView;
    final private MaintenanceModel maintenanceModel;

    public RestockSVMController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        restockSVMView = new RestockSVMView();

        this.restockSVMView.addRestockListener(new RestockListener());
        this.restockSVMView.addItemChangeListener(new ItemChangeListener());

        for (Slot slot : maintenanceModel.getAuthSpecialMachine().getItemSlots()) {
            if (slot.getSlotItemType() != null) {
                restockSVMView.getItemComboBox().addItem(slot.getSlotItemType().getName());
            }
        }

        restockSVMView.setVisible(true);
    }

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

            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner, authRegular, authSpecial);
            restockSVMView.dispose();
        }
    }

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

            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner, authRegular, authSpecial);
            restockSVMView.dispose();
        }
    }
}
