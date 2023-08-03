/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

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
        this.testVendingMenuView.addBackButtonListener(new BackButtonListener());

        Owner owner = vendingModel.getAuthOwner();
        for (RegularVendingMachine regularMachine : owner.getRegularMachines()) {
            testVendingMenuView.getRegularComboBox().addItem(regularMachine.getName());
        }

        for (SpecialVendingMachine specialMachine : owner.getSpecialMachines()) {
            testVendingMenuView.getSpecialComboBox().addItem(specialMachine.getName());
        }

        // String machineName = specialComboBox.getSelectedItem().toString();
        //   authenticatedSpecialMachine = authorizedOwner.getSpecialMachine(machineName);
        // testSpecialBtn.setEnabled(true);
        testVendingMenuView.setVisible(true);
    }

    class TestSpecialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = vendingModel.getAuthOwner();
            RegularVendingMachine authRegular = vendingModel.getAuthRegularMachine();
            RegularVendingMachine authSpecial = vendingModel.getAuthSpecialMachine();
            //TestSpecialController testSpecial = new TestSpecialController(authOwner, authRegular, authSpecial);
            testVendingMenuView.dispose();
        }
    }

    class TestRegularListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = vendingModel.getAuthOwner();
            RegularVendingMachine authRegular = vendingModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = vendingModel.getAuthSpecialMachine();
            TestRegularVendingController testRegular = new TestRegularVendingController(authOwner, authRegular, authSpecial);
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
            String selectedItemName = testVendingMenuView.getRegularComboBox().getSelectedItem().toString();
            RegularVendingMachine authenticatedRegularMachine = vendingModel.getAuthOwner().getRegularMachine(machineName);
            vendingModel.authenticateRegularMachine(authenticatedRegularMachine);
            testVendingMenuView.enableRegularButton();
        }
    }

    class SpecialChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String selectedItemName = testVendingMenuView.getSpecialComboBox().getSelectedItem().toString();
            SpecialVendingMachine authenticatedSpecialMachine = vendingModel.getAuthOwner().getSpecialMachine(machineName);
            vendingModel.authenticateSpecialMachine(authenticatedSpecialMachine);
            testVendingMenuView.enableSpecialButton();
        }
    }

}
