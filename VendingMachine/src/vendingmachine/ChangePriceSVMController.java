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
public class ChangePriceSVMController {

    final private ChangePriceSVMView changePriceSVMView;
    final private MaintenanceModel maintenanceModel;

    public ChangePriceSVMController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        changePriceSVMView = new ChangePriceSVMView();

        for (Slot slot : maintenanceModel.getAuthSpecialMachine().getItemSlots()) {
            if (slot.getSlotItemType() != null) {
                changePriceSVMView.getItemComboBox().addItem(slot.getSlotItemType().getName());
            }
        }

        this.changePriceSVMView.addChangePriceListener(new ChangePriceListener());
        this.changePriceSVMView.addItemChangeListener(new ItemChangeListener());
        changePriceSVMView.setVisible(true);
    }

    class ChangePriceListener implements ActionListener {

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

            TestMaintenanceMenuController testMaintenanceMenu = new TestMaintenanceMenuController(authOwner, authRegular, authSpecial);
            changePriceSVMView.dispose();
        }
    }

    class ItemChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String selectedItemName = changePriceSVMView.getItemComboBox().getSelectedItem().toString();
            Item selectedItem = maintenanceModel.getSpecialVMItem(selectedItemName);
            changePriceSVMView.setCurrentPrice(selectedItem.getPrice());
        }
    }

}
