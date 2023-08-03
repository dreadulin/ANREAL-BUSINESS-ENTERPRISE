/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents a controller for adding stock to a Regular Vending Machine.
 * It manages interactions between the AddStockRVMView and the MaintenanceModel.
 * 
 * @author Darryl
 */
public class AddStockRVMController {

    final private AddStockRVMView addStockView;
    final private MaintenanceModel maintenanceModel;

     /**
     * Constructs a new AddStockRVMController with the provided parameters.
     *
     * @param owner The owner of the vending machine.
     * @param regularMachine The RegularVendingMachine object.
     * @param specialMachine The SpecialVendingMachine object.
     */
    public AddStockRVMController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        addStockView = new AddStockRVMView();

        this.addStockView.addBackListener(new BackListener());
        this.addStockView.addStockListener(new StockListener());

        addStockView.setLocationRelativeTo(null);
        addStockView.setVisible(true);
    }

     /**
     * ActionListener implementation for adding stock to the Regular Vending Machine.
     * This method is triggered when the "Add Stock" button is clicked in the AddStockRVMView.
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

            boolean success = maintenanceModel.addRegularStock(itemName, itemPrice, itemCalories, itemAmount);

            if (success) {
                addStockView.showSuccessMessage();
            } else {
                addStockView.showErrorMessage();
            }

            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner, authRegular, authSpecial);
            addStockView.dispose();
        }
    }

     /**
     * ActionListener implementation for handling the "Back" button in the AddStockRVMView.
     * This method is triggered when the "Back" button is clicked in the AddStockRVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            TestRegularMaintenanceController regularMaintenance = new TestRegularMaintenanceController(authOwner, authRegular, authSpecial);
            addStockView.dispose();
        }
    }
}
