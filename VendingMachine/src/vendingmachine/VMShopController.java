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
public class VMShopController {

    private final VMShopView vmShopView;
    private final DashboardModel dashboardModel;

    public VMShopController(Owner authorizedOwner) {
        dashboardModel = new DashboardModel(authorizedOwner);

        vmShopView = new VMShopView();

        this.vmShopView.addCreateRegularListener(new CreateRegularListener());
        this.vmShopView.addCreateSpecialListener(new CreateSpecialListener());
        vmShopView.setVisible(true);
    }

    class CreateRegularListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authenticatedOwner = dashboardModel.getAuthOwner();
            CreateRegularVMController regularVM = new CreateRegularVMController(authenticatedOwner);
            vmShopView.dispose();
        }
    }

    class CreateSpecialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authenticatedOwner = dashboardModel.getAuthOwner();
            CreateSpecialVMController specialVM = new CreateSpecialVMController(authenticatedOwner);
            vmShopView.dispose();
        }
    }
}
