/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class represents a controller for changing passwords for owners in the
 * vending machine system.
 * It manages interactions between the ChangePasswordView and the StartModel.
 * 
 * @author Darryl
 */
public class ChangePasswordController {

    private final ChangePasswordView changePasswordView;
    private final StartModel startModel;

    /**
     * Constructs a new ChangePasswordController with the provided list of owners.
     *
     * @param owners The list of owners in the vending machine system.
     */
    public ChangePasswordController(ArrayList<Owner> owners) {
        startModel = new StartModel(owners);
        changePasswordView = new ChangePasswordView();

        this.changePasswordView.addBackListener(new BackListener());
        this.changePasswordView.addChangePassListener(new ChangePassListener());
        changePasswordView.setLocationRelativeTo(null);
        changePasswordView.setVisible(true);

    }

    /**
     * ActionListener implementation for handling the "Change Password" button in
     * the ChangePasswordView.
     * This method is triggered when the "Change Password" button is clicked in the
     * ChangePasswordView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class ChangePassListener implements ActionListener {

        /**
         * Performs the action when the "Change Password" button is clicked.
         * Gets the username and new password entered by the user in the
         * ChangePasswordView,
         * attempts to change the owner's password in the StartModel, and displays a
         * success or error message accordingly.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */
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

    /**
     * ActionListener implementation for handling the "Back" button in the
     * ChangePasswordView.
     * This method is triggered when the "Back" button is clicked in the
     * ChangePasswordView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class BackListener implements ActionListener {

        /**
         * ActionListener implementation for handling the "Back" button in the
         * ChangePasswordView.
         * This method is triggered when the "Back" button is clicked in the
         * ChangePasswordView.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            ArrayList<Owner> owners = startModel.getOwnersList();
            StartController startController = new StartController(owners);
            changePasswordView.dispose();
        }
    }

}
