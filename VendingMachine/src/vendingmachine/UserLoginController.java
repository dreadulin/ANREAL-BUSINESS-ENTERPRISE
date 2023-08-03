/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

/**
 *
 * @author Darryl
 */
public class UserLoginController {

    private final UserLoginView loginView;
    private final StartModel startModel;

    public UserLoginController(ArrayList<Owner> owners) {
        startModel = new StartModel(owners);
        loginView = new UserLoginView();

        this.loginView.addLoginListener(new LoginListener());
        loginView.setVisible(true);
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String userName = loginView.getInputUsername();
            String password = loginView.getInputPassword();

            Owner authenticatedOwner = startModel.loginOwner(userName, password);

            if (authenticatedOwner != null) {
                loginView.showSuccessMessage();
                DashboardController dashboardController = new DashboardController(authenticatedOwner);
                loginView.dispose();
            } else {
                ArrayList<Owner> owners = startModel.getOwnersList();
                loginView.showErrorMessage();
                StartController startController = new StartController(owners);
                loginView.dispose();
            }
        }
    }
}
