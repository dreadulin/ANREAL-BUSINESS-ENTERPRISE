/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 * This class represents a controller for collecting money from a Regular
 * Vending Machine.
 * It manages interactions between the CollectMoneyRVMView and the
 * MaintenanceModel.
 * The collected money information will be displayed in a table in the
 * CollectMoneyRVMView.
 * It also allows the authorized owner to collect money from the Regular Vending
 * Machine.
 * The collected money information will be updated in the Regular Vending
 * Machine.
 * It also provides an option to go back to the
 * TestRegularMaintenanceController.
 * 
 * The money table in CollectMoneyRVMView displays the denominations of bills
 * (Value)
 * and the quantity of each bill (Amount).
 * 
 * The CollectMoneyRVMController listens to the "Collect Money" button click in
 * the CollectMoneyRVMView,
 * and the "Back" button click to go back to the
 * TestRegularMaintenanceController.
 * 
 * The Regular Vending Machine money information is stored in the
 * MaintenanceModel.
 * 
 * The controller initializes the table with the money denominations and
 * quantities,
 * and updates the Regular Vending Machine money information after the
 * collection is done.
 * It displays the success or error message in the CollectMoneyRVMView based on
 * the collection result.
 * 
 * The controller uses the MaintenanceModel to perform the collection process.
 * It also uses the TestRegularMaintenanceController to navigate back after the
 * collection is done.
 * 
 * The CollectMoneyListener is triggered when the "Collect Money" button is
 * clicked.
 * It collects money from the Regular Vending Machine, updates the table in the
 * view,
 * and displays a message about the collection result.
 * 
 * The BackListener is triggered when the "Back" button is clicked.
 * It navigates back to the TestRegularMaintenanceController when the owner
 * decides not to collect money.
 * 
 * 
 * @author Darryl
 */
public class CollectMoneyRVMController {

    final private CollectMoneyRVMView collectMoneyRVMView;
    final private MaintenanceModel maintenanceModel;

    /**
     * Constructs a new CollectMoneyRVMController with the provided parameters.
     *
     * @param owner          The owner of the vending machine.
     * @param regularMachine The RegularVendingMachine object.
     * @param specialMachine The SpecialVendingMachine object.
     */
    public CollectMoneyRVMController(Owner owner, RegularVendingMachine regularMachine,
            SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        collectMoneyRVMView = new CollectMoneyRVMView();

        // Sets the column titles of the table
        String[] columnNames = { "Value", "Amount" };
        DefaultTableModel moneyTableModel = new DefaultTableModel(columnNames, 0);

        collectMoneyRVMView.getMoneyTable().setModel(moneyTableModel);
        Money[] bills = maintenanceModel.getAuthRegularMachine().getMoneyArray();

        // Gets the denomination of the bill and the quantity of each bill and will be
        // displayed to the table row
        for (Money bill : bills) {
            Object[] moneyRow = { bill.getValue(), bill.getAmount() };
            moneyTableModel.addRow(moneyRow);
        }

        this.collectMoneyRVMView.addBackListener(new BackListener());
        this.collectMoneyRVMView.addCollectMoneyListener(new CollectMoneyListener());

        collectMoneyRVMView.setLocationRelativeTo(null);
        collectMoneyRVMView.setVisible(true);
    }

    /**
     * ActionListener implementation for handling the "Collect Money" button in the
     * CollectMoneyRVMView.
     * This method is triggered when the "Collect Money" button is clicked in the
     * CollectMoneyRVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class CollectMoneyListener implements ActionListener {

        /**
         * Performs the action when the "Collect Money" button is clicked.
         * Gets the authenticated owner and regular vending machine,
         * attempts to collect money from the Regular Vending Machine,
         * updates the money table in the view, and displays a message about the
         * collection result.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            String resultString = maintenanceModel.collectRegularMoney();
            collectMoneyRVMView.showMessage(resultString);

            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner,
                    authRegular, authSpecial);
            collectMoneyRVMView.dispose();
        }
    }

    /**
     * This initializes the authorizedOwner and authenticatedRegularMachine
     * to be used all throughout the program
     *
     * @param owner          which is the name of the owner of the vending machine
     * @param vendingMachine which is the type of vending machine to be used
     */
    class BackListener implements ActionListener {

        /**
         * Performs the action when the "Back" button is clicked.
         * Gets the authenticated owner, regular vending machine, and special vending
         * machine,
         * opens the TestRegularMaintenanceController, and closes the
         * CollectMoneyRVMView.
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
            collectMoneyRVMView.dispose();
        }
    }

}
