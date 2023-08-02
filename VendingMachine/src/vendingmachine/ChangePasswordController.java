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
public class ChangePasswordController {

    private final ChangePasswordView changePasswordView;
    private final StartModel startModel;

    public ChangePasswordController(ArrayList<Owner> owners) {
        startModel = new StartModel(owners);
        changePasswordView = new ChangePasswordView();

        this.changePasswordView.addChangePassListener(new ChangePassListener());
        changePasswordView.setVisible(true);

    }

    class ChangePassListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String userName = changePasswordView.getInputUsername();
            String newPassword = changePasswordView.getInputNewPassword();

            boolean success = startModel.changeOwnerPassword(userName, newPassword);

            if (success) {
                changePasswordView.showSuccessMessage();
            } else {
                changePasswordView.showErrorMessage();
            }

            ArrayList<Owner> owners = startModel.getOwnersList();
            StartController startController = new StartController(owners);
            changePasswordView.dispose();
        }
    }
}
