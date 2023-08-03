/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Darryl
 */
public class CreateSpecialVMController {

    private final CreateSpecialVMView createSpecialVMView;
    private final DashboardModel dashboardModel;

    public CreateSpecialVMController(Owner authorizedOwner) {
        dashboardModel = new DashboardModel(authorizedOwner);
        createSpecialVMView = new CreateSpecialVMView();

        this.createSpecialVMView.addBackListener(new BackListener());
        this.createSpecialVMView.addCreateSpecialListener(new CreateSpecialListener());
        createSpecialVMView.setLocationRelativeTo(null);
        createSpecialVMView.setVisible(true);
    }

    class CreateSpecialListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authenticatedOwner = dashboardModel.getAuthOwner();
            String inputName = createSpecialVMView.getInputMachineName();
            int itemSlots = createSpecialVMView.getInputSlotsQt();
            int itemSlotsQt = createSpecialVMView.getInputItemSlotsQt();

            boolean success = dashboardModel.createSpecialVM(inputName, itemSlots, itemSlotsQt);

            if (success) {
                createSpecialVMView.showSuccessMessage();
            } else {
                createSpecialVMView.showErrorMessage();
            }

            DashboardController dashboardController = new DashboardController(authenticatedOwner);
            createSpecialVMView.dispose();
        }
    }

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authenticatedOwner = dashboardModel.getAuthOwner();
            DashboardController dashboardController = new DashboardController(authenticatedOwner);
            createSpecialVMView.dispose();
        }
    }

}
