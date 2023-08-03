/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

/**
 *
 * @author Darryl
 */
public class TestSpecialMaintenanceController {

    final private TestSpecialMaintenanceView testSpecialMaintenanceView;
    final private MaintenanceModel maintenanceModel;

    public TestSpecialMaintenanceController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        testSpecialMaintenanceView = new TestSpecialMaintenanceView();

        for (SpecialVendingMachine vendingMachine : maintenanceModel.getAuthOwner().getSpecialMachines()) {
            testSpecialMaintenanceView.getSpecialMachineComboBox().addItem(vendingMachine.getName());
        }

        this.testSpecialMaintenanceView.addCollectMoneyListener(new CollectMoneyListener());
        this.testSpecialMaintenanceView.addReplenishListener(new ReplenishListener());
        this.testSpecialMaintenanceView.addChangePriceListener(new ChangePriceListener());
        this.testSpecialMaintenanceView.addStockListener(new StockListener());
        this.testSpecialMaintenanceView.addRestockListener(new RestockListener());
        this.testSpecialMaintenanceView.addTransactionSummaryListener(new TransactionSummaryListener());
        this.testSpecialMaintenanceView.addSpecialMachineChangeListener(new SpecialMachineChangeListener());
        this.testSpecialMaintenanceView.addBackButtonListener(new BackButtonListener());

        testSpecialMaintenanceView.setVisible(true);
    }

    class CollectMoneyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            //CollectMoneyRVMController collectMoney = new CollectMoneyRVMController();
            testSpecialMaintenanceView.dispose();
        }
    }

    class ReplenishListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            //CollectMoneyRVMController collectMoney = new CollectMoneyRVMController();
            testSpecialMaintenanceView.dispose();
        }
    }

    class ChangePriceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            //CollectMoneyRVMController collectMoney = new CollectMoneyRVMController();
            testSpecialMaintenanceView.dispose();
        }
    }

    class StockListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            //CollectMoneyRVMController collectMoney = new CollectMoneyRVMController();
            testSpecialMaintenanceView.dispose();
        }
    }

    class RestockListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            //CollectMoneyRVMController collectMoney = new CollectMoneyRVMController();
            testSpecialMaintenanceView.dispose();
        }
    }

    class TransactionSummaryListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            //CollectMoneyRVMController collectMoney = new CollectMoneyRVMController();
            testSpecialMaintenanceView.dispose();
        }
    }

    class SpecialMachineChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String selectedMachine = testSpecialMaintenanceView.getSpecialMachineComboBox().getSelectedItem().toString();
            maintenanceModel.authenticateSpecialMachine(selectedMachine);
            //CollectMoneyRVMController collectMoney = new CollectMoneyRVMController();
            testSpecialMaintenanceView.dispose();
        }
    }

    class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            TestMaintenanceMenuController testMaintenanceMenu = new TestMaintenanceMenuController(authOwner, null, null);
            testSpecialMaintenanceView.dispose();
        }
    }
}
