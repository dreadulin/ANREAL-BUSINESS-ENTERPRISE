/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class represents a controller for the DashboardView, which displays the
 * main dashboard of the Vending Machine application.
 * It manages the interactions between the DashboardView and the DashboardModel.
 * The DashboardController is responsible for handling user actions on the
 * dashboard, such as purchasing a vending machine,
 * testing vending functionality, and testing maintenance functionality.
 * 
 * The DashboardController listens to events triggered by buttons in the
 * DashboardView and responds accordingly.
 * It creates instances of other controllers (e.g., VMShopController,
 * TestVendingMenuController, TestMaintenanceMenuController)
 * to handle specific tasks and operations related to purchasing, testing
 * vending, and testing maintenance.
 * 
 * The DashboardModel is used to keep track of the currently authenticated owner
 * and other relevant data related to the dashboard.
 * 
 * The PurchaseMachineListener is triggered when the user clicks the "Purchase
 * Machine" button in the DashboardView.
 * It creates an instance of the VMShopController to handle the process of
 * purchasing a vending machine.
 * 
 * The TestVendingListener is triggered when the user clicks the "Test Vending"
 * button in the DashboardView.
 * It creates an instance of the TestVendingMenuController to provide access to
 * various testing options related to vending functionality.
 * 
 * The TestMaintainListener is triggered when the user clicks the "Test
 * Maintenance" button in the DashboardView.
 * It creates an instance of the TestMaintenanceMenuController to provide access
 * to various testing options related to maintenance functionality.
 * 
 * Note: The actual functionality and parameters of the methods may vary
 * depending on the implementation of the DashboardModel and DashboardView.
 * 
 * @author Darryl
 */
public class DashboardController {

    final private DashboardView dashboardView;
    final private DashboardModel dashboardModel;

    /**
     * Constructs a new DashboardController with the provided owner.
     * Initializes the DashboardModel and DashboardView.
     *
     * @param owner The authenticated owner using the dashboard.
     */
    public DashboardController(Owner owner) {
        dashboardModel = new DashboardModel(owner);
        dashboardView = new DashboardView();

        this.dashboardView.addPurchaseMachineListener(new PurchaseMachineListener());
        this.dashboardView.addTestVendingListener(new TestVendingListener());
        this.dashboardView.addTestMaintainListener(new TestMaintainListener());

        dashboardView.setLocationRelativeTo(null);
        dashboardView.setVisible(true);
    }

    /**
     * ActionListener implementation for handling the "Purchase Machine" button in
     * the DashboardView.
     * This method is triggered when the "Purchase Machine" button is clicked in the
     * DashboardView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class PurchaseMachineListener implements ActionListener {

        /**
         * Performs the action when the "Purchase Machine" button is clicked.
         * Gets the authenticated owner from the DashboardModel,
         * opens the VMShopController for purchasing a vending machine, and closes the
         * DashboardView.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = dashboardModel.getAuthOwner();
            VMShopController VMShop = new VMShopController(authOwner);

            dashboardView.dispose();
        }
    }

    /**
     * * ActionListener implementation for handling the "Test Vending" button in the
     * DashboardView.
     * This method is triggered when the "Test Vending" button is clicked in the
     * DashboardView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class TestVendingListener implements ActionListener {

        /**
         * Performs the action when the "Test Vending" button is clicked.
         * Gets the authenticated owner from the DashboardModel,
         * opens the TestVendingMenuController for testing vending functionality, and
         * closes the DashboardView.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = dashboardModel.getAuthOwner();
            TestVendingMenuController testVendingMenu = new TestVendingMenuController(authOwner, null, null);
            dashboardView.dispose();
        }
    }

    /**
     * ActionListener implementation for handling the "Test Maintenance" button in
     * the DashboardView.
     * This method is triggered when the "Test Maintenance" button is clicked in the
     * DashboardView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class TestMaintainListener implements ActionListener {

        /**
         * Performs the action when the "Test Maintenance" button is clicked.
         * Gets the authenticated owner from the DashboardModel,
         * opens the TestMaintenanceMenuController for testing maintenance
         * functionality, and closes the DashboardView.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = dashboardModel.getAuthOwner();
            TestMaintenanceMenuController testMaintenance = new TestMaintenanceMenuController(authOwner, null, null);
            dashboardView.dispose();
        }
    }
}
