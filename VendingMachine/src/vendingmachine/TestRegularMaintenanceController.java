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
public class TestRegularMaintenanceController {

    final private TestRegularMaintenanceView testRegularMaintenanceView;
    final private MaintenanceModel maintenanceModel;

    public TestRegularMaintenanceController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        testRegularMaintenanceView = new TestRegularMaintenanceView();

        for (RegularVendingMachine vendingMachine : maintenanceModel.getAuthOwner().getRegularMachines()) {
            testRegularMaintenanceView.getRegularMachineComboBox().addItem(vendingMachine.getName());
        }

        this.testRegularMaintenanceView.addCollectMoneyListener(new CollectMoneyListener());
        this.testRegularMaintenanceView.addReplenishListener(new ReplenishListener());
        this.testRegularMaintenanceView.addChangePriceListener(new ChangePriceListener());
        this.testRegularMaintenanceView.addStockListener(new StockListener());
        this.testRegularMaintenanceView.addRestockListener(new RestockListener());
        this.testRegularMaintenanceView.addTransactionSummaryListener(new TransactionSummaryListener());
        this.testRegularMaintenanceView.addRegularMachineChangeListener(new RegularMachineChangeListener());
        this.testRegularMaintenanceView.addBackButtonListener(new BackButtonListener());

        testRegularMaintenanceView.setVisible(true);
    }

    class CollectMoneyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            CollectMoneyRVMController collectMoney = new CollectMoneyRVMController(authOwner, authRegular, authSpecial);
            testRegularMaintenanceView.dispose();
        }
    }

    class ReplenishListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            ReplenishMoneyRVMController replenishMoney = new ReplenishMoneyRVMController(authOwner, authRegular, authSpecial);
            testRegularMaintenanceView.dispose();
        }
    }

    class ChangePriceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            ChangePriceRVMController changePrice = new ChangePriceRVMController(authOwner, authRegular, authSpecial);
            testRegularMaintenanceView.dispose();
        }
    }

    class StockListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            AddStockRVMController addStock = new AddStockRVMController(authOwner, authRegular, authSpecial);
            testRegularMaintenanceView.dispose();
        }
    }

    class RestockListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            RestockRVMController collectMoney = new RestockRVMController(authOwner, authRegular, authSpecial);
            testRegularMaintenanceView.dispose();
        }
    }

    class TransactionSummaryListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            TransactionSummaryRVMController collectMoney = new TransactionSummaryRVMController(authOwner, authRegular, authSpecial);
            testRegularMaintenanceView.dispose();
        }
    }

    class RegularMachineChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String selectedMachine = testRegularMaintenanceView.getRegularMachineComboBox().getSelectedItem().toString();
            maintenanceModel.authenticateRegularMachine(selectedMachine);
            testRegularMaintenanceView.revealOptions();
        }
    }

    class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            TestMaintenanceMenuController testMaintenanceMenu = new TestMaintenanceMenuController(authOwner, null, null);
            testRegularMaintenanceView.dispose();
        }
    }
}
