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
public class CreateAccountController {

    private final CreateAccountView createAccountView;
    private final StartModel startModel;

    public CreateAccountController(ArrayList<Owner> owners) {
        startModel = new StartModel(owners);
        createAccountView = new CreateAccountView();

        this.createAccountView.addCreateAccountListener(new CreateAccountListener());
        createAccountView.setVisible(true);
    }

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
}
