/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * This class represents a controller for creating a new SpecialVendingMachine.
 * It manages interactions between the CreateSpecialVMView and the
 * DashboardModel.
 * The information for creating the SpecialVendingMachine (machine name, item
 * slots, item slots quantity)
 * will be entered in the CreateSpecialVMView.
 * It allows the creation of a new SpecialVendingMachine and displays the
 * success or error message accordingly.
 * 
 * The CreateSpecialVMController listens to the "Create" button click in the
 * CreateSpecialVMView,
 * and the "Back" button click to go back to the DashboardController.
 * 
 * The SpecialVendingMachine information is stored in the DashboardModel.
 * 
 * The controller creates the SpecialVendingMachine and updates the dashboard
 * after the creation is done.
 * It displays the success or error message in the CreateSpecialVMView based on
 * the creation result.
 * 
 * The controller uses the DashboardModel to perform the SpecialVendingMachine
 * creation process.
 * It also uses the DashboardController to navigate back after the
 * SpecialVendingMachine creation is done.
 * 
 * The CreateSpecialListener is triggered when the "Create" button is clicked.
 * It collects the input machine name, item slots, and item slots quantity from
 * the CreateSpecialVMView,
 * attempts to create a new SpecialVendingMachine using the DashboardModel,
 * and displays a message about the creation result.
 * 
 * The BackListener is triggered when the "Back" button is clicked.
 * It navigates back to the DashboardController when the user decides not to
 * create a SpecialVendingMachine.
 * 
 * Note: The actual functionality and parameters of the methods may vary
 * depending on the implementation of the DashboardModel and
 * CreateSpecialVMView.
 * 
 * @author Darryl
 */
public class CreateSpecialVMController {

    private final CreateSpecialVMView createSpecialVMView;
    private final DashboardModel dashboardModel;

    /**
     * Constructs a new CreateSpecialVMController with the provided parameters.
     *
     * @param authorizedOwner The authorized owner creating the
     *                        SpecialVendingMachine.
     */
    public CreateSpecialVMController(Owner authorizedOwner) {
        dashboardModel = new DashboardModel(authorizedOwner);
        createSpecialVMView = new CreateSpecialVMView();

        this.createSpecialVMView.addBackListener(new BackListener());
        this.createSpecialVMView.addCreateSpecialListener(new CreateSpecialListener());
        createSpecialVMView.setLocationRelativeTo(null);
        createSpecialVMView.setVisible(true);
    }

    /**
     * ActionListener implementation for handling the "Create" button in the
     * CreateSpecialVMView.
     * This method is triggered when the "Create" button is clicked in the
     * CreateSpecialVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class CreateSpecialListener implements ActionListener {

        /**
         * Performs the action when the "Create" button is clicked.
         * Gets the input machine name, item slots, and item slots quantity from the
         * CreateSpecialVMView,
         * attempts to create a new SpecialVendingMachine using the DashboardModel,
         * and displays a message about the creation result.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */
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

    /**
     * ActionListener implementation for handling the "Back" button in the
     * CreateSpecialVMView.
     * This method is triggered when the "Back" button is clicked in the
     * CreateSpecialVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class BackListener implements ActionListener {

        /**
         * Performs the action when the "Back" button is clicked.
         * Gets the authenticated owner from the DashboardModel,
         * opens the DashboardController, and closes the CreateSpecialVMView.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authenticatedOwner = dashboardModel.getAuthOwner();
            DashboardController dashboardController = new DashboardController(authenticatedOwner);
            createSpecialVMView.dispose();
        }
    }

}
