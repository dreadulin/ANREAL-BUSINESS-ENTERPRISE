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
public class TestVendingMenuController {

    final private TestVendingMenuView testVendingMenuView;
    final private VendingModel vendingModel;

    public TestVendingMenuController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        vendingModel = new VendingModel(owner, regularMachine, specialMachine);
        testVendingMenuView = new TestVendingMenuView();

        this.testVendingMenuView.addTestSpecialListener(new TestSpecialListener());
        this.testVendingMenuView.addTestRegularListener(new TestRegularListener());
        this.testVendingMenuView.addRegularChangeListener(new RegularChangeListener());
        this.testVendingMenuView.addSpecialChangeListener(new SpecialChangeListener());

        for (RegularVendingMachine machine : owner.getRegularMachines()) {
            testVendingMenuView.getRegularComboBox().addItem(machine.getName());
        }

        for (SpecialVendingMachine machine : owner.getSpecialMachines()) {
            testVendingMenuView.getSpecialComboBox().addItem(machine.getName());
        }

        testVendingMenuView.setVisible(true);
    }

    class TestSpecialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = vendingModel.getAuthOwner();
            SpecialVendingMachine authSpecial = vendingModel.getAuthSpecialMachine();
            SpecialVMController testSpecial = new SpecialVMController(authOwner, authSpecial);
            testVendingMenuView.dispose();
        }
    }

    class TestRegularListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = vendingModel.getAuthOwner();
            RegularVendingMachine authRegular = vendingModel.getAuthRegularMachine();
            RegularVMController testRegular = new RegularVMController(authOwner, authRegular);
            testVendingMenuView.dispose();
        }
    }

    class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = vendingModel.getAuthOwner();
            DashboardController dashboard = new DashboardController(authOwner);
            testVendingMenuView.dispose();
        }
    }

    class RegularChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String selectedMachine = testVendingMenuView.getRegularComboBox().getSelectedItem().toString();
            vendingModel.authenticateRegularMachine(selectedMachine);
            testVendingMenuView.enableRegularButton();
        }
    }

    class SpecialChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String selectedMachine = testVendingMenuView.getSpecialComboBox().getSelectedItem().toString();
            vendingModel.authenticateSpecialMachine(selectedMachine);
            testVendingMenuView.enableSpecialButton();
        }
    }

}
