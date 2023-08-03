/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

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

    public TransactionSummaryRVMController(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        maintenanceModel = new MaintenanceModel(owner, regularMachine, specialMachine);
        transactionSummaryView = new TransactionSummaryRVMView();

        String[] columnNames = {"Item Name", "Quantity Purchase", "Total Sale", "Date"};
        String[] stockColumnNames = {"Name", "Calories", "Price", "Stock"};
        DefaultTableModel lastRestockTableModel = new DefaultTableModel(stockColumnNames, 0);
        DefaultTableModel currStockTableModel = new DefaultTableModel(stockColumnNames, 0);
        DefaultTableModel transactionTableModel = new DefaultTableModel(columnNames, 0);

        transactionSummaryView.getTransactionTable().setModel(transactionTableModel);
        transactionSummaryView.getLastRestockTable().setModel(lastRestockTableModel);
        transactionSummaryView.getCurrStockTable().setModel(currStockTableModel);
        List<Transaction> transactionsToSummarize = maintenanceModel.getRegularTransactions();
        for (Transaction transaction : transactionsToSummarize) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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

        transactionSummaryView.setVisible(true);
    }
}
