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
public class ChangePriceRVMController {

    final private ChangePriceRVMView changePriceRVMView;
    final private MaintenanceModel maintenanceModel;

    public ChangePriceRVMController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        changePriceRVMView = new ChangePriceRVMView();

        for (Slot slot : maintenanceModel.getAuthRegularMachine().getItemSlots()) {
            if (slot.getSlotItemType() != null) {
                changePriceRVMView.getItemComboBox().addItem(slot.getSlotItemType().getName());
            }
        }

        this.changePriceRVMView.addChangePriceListener(new ChangePriceListener());
        this.changePriceRVMView.addItemChangeListener(new ItemChangeListener());
        changePriceRVMView.setVisible(true);
    }

    class ChangePriceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            String selected = changePriceRVMView.getItemComboBox().getSelectedItem().toString();
            int newPrice = changePriceRVMView.getInputPrice();

            boolean success = maintenanceModel.setPrice(selected, newPrice);

            if (success) {
                changePriceRVMView.showSuccessMessage();
            } else {
                changePriceRVMView.showErrorMessage();
            }

            TestMaintenanceMenuController testMaintenanceMenu = new TestMaintenanceMenuController(authOwner, authRegular, authSpecial);
            changePriceRVMView.dispose();
        }
    }

    class ItemChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String selectedItemName = changePriceRVMView.getItemComboBox().getSelectedItem().toString();
            Item selectedItem = maintenanceModel.getRegularVMItem(selectedItemName);
            changePriceRVMView.setCurrentPrice(selectedItem.getPrice());
        }
    }

}
