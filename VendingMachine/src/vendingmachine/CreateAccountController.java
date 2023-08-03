/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class represents a controller for creating a new account for an owner.
 * It manages interactions between the CreateAccountView and the StartModel.
 * The account information (username, password, balance) will be entered in the
 * CreateAccountView.
 * It allows the creation of a new owner account and displays the success or
 * error message accordingly.
 * 
 * The CreateAccountController listens to the "Create Account" button click in
 * the CreateAccountView,
 * and the "Back" button click to go back to the StartController.
 * 
 * The owner account information is stored in the StartModel.
 * 
 * The controller creates the owner account and updates the list of owners in
 * the StartModel after the creation is done.
 * It displays the success or error message in the CreateAccountView based on
 * the creation result.
 * 
 * The controller uses the StartModel to perform the account creation process.
 * It also uses the StartController to navigate back after the account creation
 * is done.
 * 
 * The CreateAccountListener is triggered when the "Create Account" button is
 * clicked.
 * It collects the input username, password, and balance from the
 * CreateAccountView,
 * attempts to create a new owner account using the StartModel, and displays a
 * message about the creation result.
 * 
 * The BackListener is triggered when the "Back" button is clicked.
 * It navigates back to the StartController when the user decides not to create
 * an account.
 * 
 * Note: The actual functionality and parameters of the methods may vary
 * depending on the implementation of the StartModel and CreateAccountView.
 * 
 * @author Darryl
 */
public class CreateAccountController {

    private final CreateAccountView createAccountView;
    private final StartModel startModel;

    /**
     * Constructs a new CreateAccountController with the provided parameters.
     *
     * @param owners The list of existing owners.
     */
    public CreateAccountController(ArrayList<Owner> owners) {
        startModel = new StartModel(owners);
        createAccountView = new CreateAccountView();

        this.createAccountView.addBackListener(new BackListener());
        this.createAccountView.addCreateAccountListener(new CreateAccountListener());
        createAccountView.setLocationRelativeTo(null);
        createAccountView.setVisible(true);
    }

    /**
     * ActionListener implementation for handling the "Create Account" button in the
     * CreateAccountView.
     * This method is triggered when the "Create Account" button is clicked in the
     * CreateAccountView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class CreateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String userName = createAccountView.getInputUsername();
            String password = createAccountView.getInputPassword();
            int balance = createAccountView.getInputBalance();

            boolean success = startModel.createOwner(userName, password, balance);

            if (success) {
                createAccountView.showSuccessMessage();
            } else {
                createAccountView.showErrorMessage();
            }

            ArrayList<Owner> owners = startModel.getOwnersList();
            StartController startController = new StartController(owners);
            createAccountView.dispose();
        }
    }

    /**
     * ActionListener implementation for handling the "Back" button in the
     * CreateAccountView.
     * This method is triggered when the "Back" button is clicked in the
     * CreateAccountView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            ArrayList<Owner> owners = startModel.getOwnersList();
            StartController startController = new StartController(owners);
        }
    }

}
