/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents a controller for adding stock to a Special Vending Machine.
 * It manages interactions between the AddStockSVMView and the MaintenanceModel.
 * 
 * @author Darryl
 */
public class AddStockSVMController {

    final private AddStockSVMView addStockView;
    final private MaintenanceModel maintenanceModel;

    /**
     * Constructs a new AddStockSVMController with the provided parameters.
     *
     * @param owner The owner of the vending machine.
     * @param regularMachine The RegularVendingMachine object.
     * @param specialMachine The SpecialVendingMachine object.
     */
    public AddStockSVMController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        addStockView = new AddStockSVMView();

        this.addStockView.addStockListener(new StockListener());
        this.addStockView.addBackListener(new BackListener());

        addStockView.setLocationRelativeTo(null);
        addStockView.setVisible(true);
    }

    /**
     * ActionListener implementation for adding stock to the Special Vending Machine.
     * This method is triggered when the "Add Stock" button is clicked in the AddStockSVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class StockListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            String itemName = addStockView.getStockName();
            int itemPrice = addStockView.getStockPrice();
            double itemCalories = addStockView.getStockCalories();
            int itemAmount = addStockView.getStockItemAmount();

            boolean success = maintenanceModel.addSpecialStock(itemName, itemPrice, itemCalories, itemAmount);

            if (success) {
                addStockView.showSuccessMessage();
            } else {
                addStockView.showErrorMessage();
            }

            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner, authRegular, authSpecial);
            addStockView.dispose();
        }
    }

    /**
     * ActionListener implementation for adding stock to the Special Vending Machine.
     * This method is triggered when the "Add Stock" button is clicked in the AddStockSVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner, authRegular, authSpecial);
            addStockView.dispose();
        }
    }

}
