/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Darryl
 */
public class TransactionSummaryRVMController {

    final private TransactionSummaryRVMView transactionSummaryView;
    final private MaintenanceModel maintenanceModel;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public TransactionSummaryRVMController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        transactionSummaryView = new TransactionSummaryRVMView();

        this.transactionSummaryView.addBackListener(new BackListener());
        String[] columnNames = {"Item Name", "Quantity Purchase", "Total Sale", "Date"};
        String[] stockColumnNames = {"Name", "Calories", "Price", "Stock"};
        DefaultTableModel lastRestockTableModel = new DefaultTableModel(stockColumnNames, 0);
        DefaultTableModel currStockTableModel = new DefaultTableModel(stockColumnNames, 0);
        DefaultTableModel transactionTableModel = new DefaultTableModel(columnNames, 0);

        transactionSummaryView.getTransactionTable().setModel(transactionTableModel);
        transactionSummaryView.getLastRestockTable().setModel(lastRestockTableModel);
        transactionSummaryView.getCurrStockTable().setModel(currStockTableModel);

        String lastRestockDate = formatter.format(maintenanceModel.getAuthRegularMachine().getLastRestockDate());
        transactionSummaryView.setLastRestockDate(lastRestockDate);
        List<Transaction> transactionsToSummarize = maintenanceModel.getRegularTransactions();
        for (Transaction transaction : transactionsToSummarize) {

            String formattedDate = formatter.format(transaction.getTransactionDate());
            Object[] row = {transaction.getItemPurchased().getName(), transaction.getPurchaseAmount(), transaction.getTotalSales(), formattedDate};
            transactionTableModel.addRow(row);
        }

        for (Slot slot : maintenanceModel.getRegularLastRestockSlots()) {
            if (slot.getSlotItemType() != null) {
                Object[] row = {slot.getSlotItemType().getName(), slot.getSlotItemType().getCalories(), slot.getSlotItemType().getPrice(), slot.getItemQuantity()};
                lastRestockTableModel.addRow(row);
            }
        }

        for (Slot slot : maintenanceModel.getRegularSlots()) {
            if (slot.getSlotItemType() != null) {
                Object[] row = {slot.getSlotItemType().getName(), slot.getSlotItemType().getCalories(), slot.getSlotItemType().getPrice(), slot.getItemQuantity()};
                currStockTableModel.addRow(row);
            }
        }

        transactionSummaryView.setLocationRelativeTo(null);
        transactionSummaryView.setVisible(true);
    }

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Owner authenticatedOwner = maintenanceModel.getAuthOwner();
            RegularVendingMachine authRegularMachine = maintenanceModel.getAuthRegularMachine();
            SpecialVendingMachine authSpecialMachine = maintenanceModel.getAuthSpecialMachine();
            TestRegularMaintenanceController testRegularMaintenance = new TestRegularMaintenanceController(authenticatedOwner, authRegularMachine, authSpecialMachine);
        }
    }
}
