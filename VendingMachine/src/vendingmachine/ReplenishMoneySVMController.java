/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Controller class for the ReplenishMoneySVMView, responsible for handling user
 * interactions and updating the view.
 */
public class ReplenishMoneySVMController {

    final private ReplenishMoneySVMView replenishMoneySVMView;
    final private MaintenanceModel maintenanceModel;

    /**
     * Constructor for the ReplenishMoneySVMController class.
     * Initializes the controller with the provided owner, regular vending machine,
     * and special vending machine.
     *
     * @param owner          The owner of the vending machine.
     * @param regularMachine The regular vending machine object.
     * @param specialMachine The special vending machine object.
     */
    public ReplenishMoneySVMController(Owner owner, RegularVendingMachine regularMachine,
            SpecialVendingMachine specialMachine) {
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

    /**
     * ActionListener implementation for handling the "Replenish" button in the
     * ReplenishMoneySVMView.
     * This method is triggered when the "Replenish" button is clicked in the
     * ReplenishMoneySVMView.
     * It retrieves the selected money value and the replenishment amount from the
     * view and calls the replenishSpecialMoney() method
     * from the MaintenanceModel to replenish the selected money type with the
     * specified amount.
     * The result of the replenishment is displayed in the view through a message
     * dialog.
     * After replenishment, it calls the TestSpecialMaintenanceController to return
     * to the maintenance menu.
     */
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

            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner,
                    authRegular, authSpecial);
            replenishMoneySVMView.dispose();
        }
    }

    /**
     * ActionListener implementation for handling the change in the selected money
     * value in the ReplenishMoneySVMView.
     * This method is triggered when the selected money value in the moneyComboBox
     * is changed in the ReplenishMoneySVMView.
     * It retrieves the selected money value from the view and updates the displayed
     * amount of the selected money type.
     * The amount is fetched from the MaintenanceModel based on the selected money
     * value and displayed in the view.
     */
    class MoneyChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int selectedValue = Integer.parseInt(replenishMoneySVMView.getMoneyComboBox().getSelectedItem().toString());
            Money money = maintenanceModel.getAuthSpecialMachine().getMoney(selectedValue);
            replenishMoneySVMView.setMoneyAmountLabel(money.getAmount());
        }
    }

    /**
     * ActionListener implementation for handling the "Back" button in the
     * ReplenishMoneySVMView.
     * This method is triggered when the "Back" button is clicked in the
     * ReplenishMoneySVMView.
     * It returns to the TestSpecialMaintenanceController and disposes of the
     * ReplenishMoneySVMView.
     */
    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            TestSpecialMaintenanceController testSpecialMaintenance = new TestSpecialMaintenanceController(authOwner,
                    authRegular, authSpecial);
            replenishMoneySVMView.dispose();
        }
    }

}
