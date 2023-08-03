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
public class TestMaintenanceMenuController {

    final private TestMaintenanceMenuView testMaintenanceMenuView;
    final private MaintenanceModel maintenanceModel;

    public TestMaintenanceMenuController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        testMaintenanceMenuView = new TestMaintenanceMenuView();

        this.testMaintenanceMenuView.addTestSpecialListener(new TestSpecialListener());
        this.testMaintenanceMenuView.addTestRegularListener(new TestRegularListener());
        this.testMaintenanceMenuView.addBackButtonListener(new BackButtonListener());

        testMaintenanceMenuView.setVisible(true);
    }

    class TestSpecialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            RegularVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            //TestSpecialController testSpecial = new TestSpecialController(authOwner, authRegular, authSpecial);
            testMaintenanceMenuView.dispose();
        }
    }

    class TestRegularListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();
            TestRegularMaintenanceController testRegular = new TestRegularMaintenanceController(authOwner, authRegular, authSpecial);
            testMaintenanceMenuView.dispose();
        }
    }

    class BackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            DashboardController dashboard = new DashboardController(authOwner);
            testMaintenanceMenuView.dispose();
        }
    }
}
