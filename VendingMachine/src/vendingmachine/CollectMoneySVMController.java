/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 * This class represents a controller for collecting money from a Special
 * Vending Machine.
 * It manages interactions between the CollectMoneySVMView and the
 * MaintenanceModel.
 * The collected money information will be displayed in a table in the
 * CollectMoneySVMView.
 * It also allows the authorized owner to collect money from the Special Vending
 * Machine.
 * The collected money information will be updated in the Special Vending
 * Machine.
 * It also provides an option to go back to the
 * TestSpecialMaintenanceController.
 * 
 * The money table in CollectMoneySVMView displays the denominations of bills
 * (Value)
 * and the quantity of each bill (Amount).
 * 
 * The CollectMoneySVMController listens to the "Collect Money" button click in
 * the CollectMoneySVMView,
 * and the "Back" button click to go back to the
 * TestSpecialMaintenanceController.
 * 
 * The Special Vending Machine money information is stored in the
 * MaintenanceModel.
 * 
 * The controller initializes the table with the money denominations and
 * quantities,
 * and updates the Special Vending Machine money information after the
 * collection is done.
 * It displays the success or error message in the CollectMoneySVMView based on
 * the collection result.
 * 
 * The controller uses the MaintenanceModel to perform the collection process.
 * It also uses the TestSpecialMaintenanceController to navigate back after the
 * collection is done.
 * 
 * The CollectMoneyListener is triggered when the "Collect Money" button is
 * clicked.
 * It collects money from the Special Vending Machine, updates the table in the
 * view,
 * and displays a message about the collection result.
 * 
 * The BackListener is triggered when the "Back" button is clicked.
 * It navigates back to the TestSpecialMaintenanceController when the owner
 * decides not to collect money.
 * 
 * Note: The actual functionality and parameters of the methods may vary
 * depending on the implementation of the MaintenanceModel and
 * CollectMoneySVMView.
 * 
 * @author Darryl
 */
public class CollectMoneySVMController {

    final private CollectMoneySVMView collectMoneySVMView;
    final private MaintenanceModel maintenanceModel;

    /**
     * Constructs a new CollectMoneySVMController with the provided parameters.
     *
     * @param owner          The owner of the vending machine.
     * @param regularMachine The RegularVendingMachine object.
     * @param specialMachine The SpecialVendingMachine object.
     */
    public CollectMoneySVMController(Owner owner, RegularVendingMachine regularMachine,
            SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        collectMoneySVMView = new CollectMoneySVMView();

        // Sets the column titles of the table
        String[] columnNames = { "Value", "Amount" };
        DefaultTableModel moneyTableModel = new DefaultTableModel(columnNames, 0);

        collectMoneySVMView.getMoneyTable().setModel(moneyTableModel);
        Money[] bills = maintenanceModel.getAuthSpecialMachine().getMoneyArray();

        // Gets the denomination of the bill and the quantity of each bill and will be
        // displayed to the table row
        for (Money bill : bills) {
            Object[] moneyRow = { bill.getValue(), bill.getAmount() };
            moneyTableModel.addRow(moneyRow);
        }

        this.collectMoneySVMView.addBackListener(new BackListener());
        this.collectMoneySVMView.addCollectMoneyListener(new CollectMoneyListener());

        collectMoneySVMView.setLocationRelativeTo(null);
        collectMoneySVMView.setVisible(true);
    }

    /**
     * ActionListener implementation for handling the "Collect Money" button in the
     * CollectMoneySVMView.
     * This method is triggered when the "Collect Money" button is clicked in the
     * CollectMoneySVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class CollectMoneyListener implements ActionListener {

        @Override
        /**
         * This initializes the authorizedOwner and authenticatedSpecialMachine
         * to be used all throughout the program
         *
         * @param owner          which is the name of the owner of the vending machine
         * @param vendingMachine which is the type of vending machine to be used
         */

        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            String resultString = maintenanceModel.collectSpecialMoney();
            collectMoneySVMView.showMessage(resultString);

            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner,
                    authRegular, authSpecial);
            collectMoneySVMView.dispose();
        }
    }

    /**
     * ActionListener implementation for handling the "Back" button in the
     * CollectMoneySVMView.
     * This method is triggered when the "Back" button is clicked in the
     * CollectMoneySVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner,
                    authRegular, authSpecial);
            collectMoneySVMView.dispose();
        }
    }

}
