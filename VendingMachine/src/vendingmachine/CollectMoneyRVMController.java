/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Darryl
 */
public class CollectMoneyRVMController {

    final private CollectMoneyRVMView collectMoneyRVMView;
    final private MaintenanceModel maintenanceModel;

    public CollectMoneyRVMController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        collectMoneyRVMView = new CollectMoneyRVMView();

        // Sets the column titles of the table
        String[] columnNames = {"Value", "Amount"};
        DefaultTableModel moneyTableModel = new DefaultTableModel(columnNames, 0);

        collectMoneyRVMView.getMoneyTable().setModel(moneyTableModel);
        Money[] bills = maintenanceModel.getAuthRegularMachine().getMoneyArray();

        // Gets the denomination of the bill and the quantity of each bill and will be displayed to the table row 
        for (Money bill : bills) {
            Object[] moneyRow = {bill.getValue(), bill.getAmount()};
            moneyTableModel.addRow(moneyRow);
        }

        this.collectMoneyRVMView.addCollectMoneyListener(new CollectMoneyListener());

        collectMoneyRVMView.setVisible(true);
    }

    class CollectMoneyListener implements ActionListener {

        @Override
        /**
         * This initializes the authorizedOwner and authenticatedRegularMachine
         * to be used all throughout the program
         *
         * @param owner which is the name of the owner of the vending machine
         * @param vendingMachine which is the type of vending machine to be used
         */

        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            String resultString = maintenanceModel.collectMoney();
            collectMoneyRVMView.showMessage(resultString);

            TestMaintenanceMenuController testMaintenanceMenu = new TestMaintenanceMenuController(authOwner, authRegular, authSpecial);
            collectMoneyRVMView.dispose();
        }
    }
}
