/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

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
        DefaultTableModel transactionTableModel = new DefaultTableModel(columnNames, 0);

        transactionSummaryView.getTransactionTable().setModel(transactionTableModel);
        List<Transaction> transactionsToSummarize = maintenanceModel.getRegularTransactions();
        for (Transaction transaction : transactionsToSummarize) {
            Object[] row = {transaction.getItemPurchased().getName(), transaction.getPurchaseAmount(), transaction.getTotalSales(), transaction.getTransactionDate()};
            transactionTableModel.addRow(row);
        }

        transactionSummaryView.setVisible(true);
    }
}
