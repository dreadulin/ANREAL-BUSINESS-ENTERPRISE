/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Controller class for the ReplenishMoneyRVMView, responsible for handling user
 * interactions and updating the view.
 * 
 * @author Darryl
 */
public class ReplenishMoneyRVMController {

    final private ReplenishMoneyRVMView replenishMoneyRVMView;
    final private MaintenanceModel maintenanceModel;

    /**
     * Constructor for the ReplenishMoneyRVMController class.
     * Initializes the controller with the provided owner, regular vending machine,
     * and special vending machine.
     *
     * @param owner          The owner of the vending machine.
     * @param regularMachine The regular vending machine object.
     * @param specialMachine The special vending machine object.
     */
    public ReplenishMoneyRVMController(Owner owner, RegularVendingMachine regularMachine,
            SpecialVendingMachine specialMachine) {
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

    /**
     * ActionListener implementation for handling the "Replenish" button in the
     * ReplenishMoneyRVMView.
     * This method is triggered when the "Replenish" button is clicked in the
     * ReplenishMoneyRVMView.
     * It retrieves the selected money value and the replenishment amount from the
     * view and calls the replenishRegularMoney() method
     * from the MaintenanceModel to replenish the selected money type with the
     * specified amount.
     * The result of the replenishment is displayed in the view through a message
     * dialog.
     * After replenishment, it calls the TestRegularMaintenanceController to return
     * to the maintenance menu.
     */
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

            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner,
                    authRegular, authSpecial);
            replenishMoneyRVMView.dispose();
        }
    }

    /**
     * ActionListener implementation for handling the change in the selected money
     * value in the ReplenishMoneyRVMView.
     * This method is triggered when the selected money value in the moneyComboBox
     * is changed in the ReplenishMoneyRVMView.
     * It retrieves the selected money value from the view and updates the displayed
     * amount of the selected money type.
     * The amount is fetched from the MaintenanceModel based on the selected money
     * value and displayed in the view.
     */
    class MoneyChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int selectedValue = Integer.parseInt(replenishMoneyRVMView.getMoneyComboBox().getSelectedItem().toString());
            Money money = maintenanceModel.getAuthRegularMachine().getMoney(selectedValue);
            replenishMoneyRVMView.setMoneyAmountLabel(money.getAmount());
        }
    }

    /**
     * ActionListener implementation for handling the "Back" button in the
     * ReplenishMoneyRVMView.
     * This method is triggered when the "Back" button is clicked in the
     * ReplenishMoneyRVMView.
     * It returns to the TestRegularMaintenanceController and disposes of the
     * ReplenishMoneyRVMView.
     */
    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authOwner,
                    authRegular, authSpecial);
            replenishMoneyRVMView.dispose();
        }
    }

}
