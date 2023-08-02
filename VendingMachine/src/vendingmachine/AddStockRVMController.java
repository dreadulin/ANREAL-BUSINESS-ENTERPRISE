/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Darryl
 */
public class AddStockRVMController {

    final private AddStockRVMView addStockView;
    final private MaintenanceModel maintenanceModel;

    public AddStockRVMController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        addStockView = new AddStockRVMView();

        this.addStockView.addStockListener(new StockListener());

        addStockView.setVisible(true);
    }

    class StockListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegular = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecial = maintenanceModel.getAuthSpecialMachine();

            //CollectMoneyRVMController collectMoney = new CollectMoneyRVMController();
            addStockView.dispose();
        }
    }
}
