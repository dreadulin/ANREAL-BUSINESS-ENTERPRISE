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
public class ReplenishMoneyRVMController {

    final private ReplenishMoneyRVMView replenishMoneyRVMView;
    final private MaintenanceModel maintenanceModel;

    public ReplenishMoneyRVMController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        replenishMoneyRVMView = new ReplenishMoneyRVMView();

        this.replenishMoneyRVMView.addBackListener(new BackListener());
        this.replenishMoneyRVMView.addReplenishListener(new ReplenishListener());
        this.replenishMoneyRVMView.addMoneyChangeListener(new MoneyChangeListener());

        for (Money money : maintenanceModel.getAuthRegularMachine().getMoneyArray()) {
            replenishMoneyRVMView.getMoneyComboBox().addItem(Integer.toString(money.getValue()));
        }

        replenishMoneyRVMView.setLocationRelativeTo(null);
        replenishMoneyRVMView.setVisible(true);
    }

    class ReplenishListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            String selectedValue = replenishMoneyRVMView.getMoneyComboBox().getSelectedItem().toString();
            int amount = replenishMoneyRVMView.getInputAmount();

            String result = maintenanceModel.replenishRegularMoney(amount, Integer.parseInt(selectedValue));

            replenishMoneyRVMView.showMessage(result);

            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner, authRegular, authSpecial);
            replenishMoneyRVMView.dispose();
        }
    }

    class MoneyChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int selectedValue = Integer.parseInt(replenishMoneyRVMView.getMoneyComboBox().getSelectedItem().toString());
            Money money = maintenanceModel.getAuthRegularMachine().getMoney(selectedValue);
            replenishMoneyRVMView.setMoneyAmountLabel(money.getAmount());
        }
    }

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner, authRegular, authSpecial);
            replenishMoneyRVMView.dispose();
        }
    }

}
