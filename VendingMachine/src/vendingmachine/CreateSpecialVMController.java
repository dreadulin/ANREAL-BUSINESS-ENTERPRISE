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
public class CreateRegularVMController {

    private final CreateRegularVMView createRegularVMView;
    private final DashboardModel dashboardModel;

    public CreateRegularVMController(Owner authorizedOwner) {
        dashboardModel = new DashboardModel(authorizedOwner);
        createRegularVMView = new CreateRegularVMView();

        this.createRegularVMView.addCreateRegularListener(new CreateRegularListener());
        createRegularVMView.setVisible(true);
    }

    class CreateRegularListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authenticatedOwner = dashboardModel.getAuthOwner();
            String inputName = createRegularVMView.getInputMachineName();
            int itemSlots = createRegularVMView.getInputSlotsQt();
            int itemSlotsQt = createRegularVMView.getInputItemSlotsQt();

            boolean success = dashboardModel.createRegularVM(inputName, itemSlots, itemSlotsQt);

            if (success) {
                JOptionPane.showMessageDialog(null, "Created Regular Machine Successfully. Going back to the start menu...", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to added machine. Going back to the start menu...", "Message", JOptionPane.INFORMATION_MESSAGE);
            }

            DashboardController dashboardController = new DashboardController(authenticatedOwner);
            createRegularVMView.dispose();
        }
    }
}
