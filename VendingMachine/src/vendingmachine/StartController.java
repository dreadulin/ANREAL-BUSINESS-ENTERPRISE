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
public class StartController {

    final private StartView startView;
    final private StartModel startModel;

    public StartController(ArrayList<Owner> owners) {
        startModel = new StartModel(owners);
        startView = new StartView();

        this.startView.addChangePassListener(new ChangePassListener());
        this.startView.addCreateAccountListener(new CreateAccountListener());
        this.startView.addLoginListener(new LoginListener());

        startView.setVisible(true);
    }

    class ChangePassListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            ArrayList<Owner> owners = startModel.getOwnersList();
            ChangePasswordController changePass = new ChangePasswordController(owners);
            startView.dispose();
        }
    }

    class CreateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            ArrayList<Owner> owners = startModel.getOwnersList();
            CreateAccountController createAccount = new CreateAccountController(owners);
            startView.dispose();
        }
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            ArrayList<Owner> owners = startModel.getOwnersList();
            UserLoginController login = new UserLoginController(owners);
            startView.dispose();
        }
    }

}
