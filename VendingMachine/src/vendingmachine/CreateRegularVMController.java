/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents a controller for creating a new RegularVendingMachine.
 * It manages interactions between the CreateRegularVMView and the
 * DashboardModel.
 * The information for creating the RegularVendingMachine (machine name, item
 * slots, item slots quantity)
 * will be entered in the CreateRegularVMView.
 * It allows the creation of a new RegularVendingMachine and displays the
 * success or error message accordingly.
 * 
 * The CreateRegularVMController listens to the "Create" button click in the
 * CreateRegularVMView,
 * and the "Back" button click to go back to the DashboardController.
 * 
 * The RegularVendingMachine information is stored in the DashboardModel.
 * 
 * The controller creates the RegularVendingMachine and updates the dashboard
 * after the creation is done.
 * It displays the success or error message in the CreateRegularVMView based on
 * the creation result.
 * 
 * The controller uses the DashboardModel to perform the RegularVendingMachine
 * creation process.
 * It also uses the DashboardController to navigate back after the
 * RegularVendingMachine creation is done.
 * 
 * The CreateRegularListener is triggered when the "Create" button is clicked.
 * It collects the input machine name, item slots, and item slots quantity from
 * the CreateRegularVMView,
 * attempts to create a new RegularVendingMachine using the DashboardModel,
 * and displays a message about the creation result.
 * 
 * The BackListener is triggered when the "Back" button is clicked.
 * It navigates back to the DashboardController when the user decides not to
 * create a RegularVendingMachine.
 * 
 * Note: The actual functionality and parameters of the methods may vary
 * depending on the implementation of the DashboardModel and
 * CreateRegularVMView.
 * 
 * @author Darryl
 */
public class CreateRegularVMController {

    private final CreateRegularVMView createRegularVMView;
    private final DashboardModel dashboardModel;

    /**
     * Constructs a new CreateRegularVMController with the provided parameters.
     *
     * @param authorizedOwner The authorized owner creating the
     *                        RegularVendingMachine.
     */
    public CreateRegularVMController(Owner authorizedOwner) {
        dashboardModel = new DashboardModel(authorizedOwner);
        createRegularVMView = new CreateRegularVMView();

        this.createRegularVMView.addBackListener(new BackListener());
        this.createRegularVMView.addCreateRegularListener(new CreateRegularListener());
        createRegularVMView.setLocationRelativeTo(null);
        createRegularVMView.setVisible(true);
    }

    /**
     * ActionListener implementation for handling the "Create" button in the
     * CreateRegularVMView.
     * This method is triggered when the "Create" button is clicked in the
     * CreateRegularVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class CreateRegularListener implements ActionListener {

        /**
         * Performs the action when the "Create" button is clicked.
         * Gets the input machine name, item slots, and item slots quantity from the
         * CreateRegularVMView,
         * attempts to create a new RegularVendingMachine using the DashboardModel,
         * and displays a message about the creation result.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authenticatedOwner = dashboardModel.getAuthOwner();
            String inputName = createRegularVMView.getInputMachineName();
            int itemSlots = createRegularVMView.getInputSlotsQt();
            int itemSlotsQt = createRegularVMView.getInputItemSlotsQt();

            boolean success = dashboardModel.createRegularVM(inputName, itemSlots, itemSlotsQt);

            if (success) {
                createRegularVMView.showSuccessMessage();
            } else {
                createRegularVMView.showErrorMessage();
            }

            DashboardController dashboardController = new DashboardController(authenticatedOwner);
            createRegularVMView.dispose();
        }
    }

    /**
     * ActionListener implementation for handling the "Back" button in the
     * CreateRegularVMView.
     * This method is triggered when the "Back" button is clicked in the
     * CreateRegularVMView.
     *
     * @param arg0 The ActionEvent object representing the button click event.
     */
    class BackListener implements ActionListener {

        /**
         * Performs the action when the "Back" button is clicked.
         * Gets the authenticated owner from the DashboardModel,
         * opens the DashboardController, and closes the CreateRegularVMView.
         *
         * @param arg0 The ActionEvent object representing the button click event.
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authenticatedOwner = dashboardModel.getAuthOwner();
            DashboardController dashboardController = new DashboardController(authenticatedOwner);
            createRegularVMView.dispose();
        }
    }

}
