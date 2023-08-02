/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Darryl
 */
public class DashboardController {

    final private DashboardView dashboardView;
    final private DashboardModel dashboardModel;

    public DashboardController(Owner owner) {
        dashboardModel = new DashboardModel(owner);
        dashboardView = new DashboardView();

        this.dashboardView.addPurchaseMachineListener(new PurchaseMachineListener());
        this.dashboardView.addTestVendingListener(new TestVendingListener());
        this.dashboardView.addTestMaintainListener(new TestMaintainListener());

        dashboardView.setVisible(true);
    }

    class PurchaseMachineListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = dashboardModel.getAuthOwner();
            VMShopController VMShop = new VMShopController(authOwner);

            dashboardView.dispose();
        }
    }

    class TestVendingListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = dashboardModel.getAuthOwner();
            // TestVendingMenuController VMShop = new TestVendingMenuController(authOwner);

            dashboardView.dispose();
        }
    }

    class TestMaintainListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = dashboardModel.getAuthOwner();
            TestMaintenanceMenuController testMaintenance = new TestMaintenanceMenuController(authOwner, null, null);
            dashboardView.dispose();
        }
    }
}
