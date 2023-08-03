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

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner, authRegular, authSpecial);
            restockRVMView.dispose();
        }
    }

}
