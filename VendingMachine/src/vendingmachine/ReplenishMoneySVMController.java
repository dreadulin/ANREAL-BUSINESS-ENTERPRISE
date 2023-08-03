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
public class ReplenishMoneySVMController {

    final private ReplenishMoneySVMView replenishMoneySVMView;
    final private MaintenanceModel maintenanceModel;

    public ReplenishMoneySVMController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        replenishMoneySVMView = new ReplenishMoneySVMView();

        this.replenishMoneySVMView.addBackListener(new BackListener());
        this.replenishMoneySVMView.addReplenishListener(new ReplenishListener());
        this.replenishMoneySVMView.addMoneyChangeListener(new MoneyChangeListener());

        for (Money money : maintenanceModel.getAuthSpecialMachine().getMoneyArray()) {
            replenishMoneySVMView.getMoneyComboBox().addItem(Integer.toString(money.getValue()));
        }

        replenishMoneySVMView.setLocationRelativeTo(null);
        replenishMoneySVMView.setVisible(true);
    }

    class ReplenishListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            String selectedValue = replenishMoneySVMView.getMoneyComboBox().getSelectedItem().toString();
            int amount = replenishMoneySVMView.getInputAmount();

            String result = maintenanceModel.replenishSpecialMoney(amount, Integer.parseInt(selectedValue));

            replenishMoneySVMView.showMessage(result);

            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner, authRegular, authSpecial);
            replenishMoneySVMView.dispose();
        }
    }

    class MoneyChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int selectedValue = Integer.parseInt(replenishMoneySVMView.getMoneyComboBox().getSelectedItem().toString());
            Money money = maintenanceModel.getAuthSpecialMachine().getMoney(selectedValue);
            replenishMoneySVMView.setMoneyAmountLabel(money.getAmount());
        }
    }

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner, authRegular, authSpecial);
            replenishMoneySVMView.dispose();
        }
    }

}
