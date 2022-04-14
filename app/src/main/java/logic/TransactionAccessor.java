package logic;

import database.IDBLayer;
import database.DatabaseManager;
import database.TransactionPersistence;
import objects.IDSO;
import objects.Transaction;

public class TransactionAccessor {
    private IDBLayer TransactionDB;

    // default constructor
    public InventoryAccessor(){
        this.TransactionDB = DatabaseManager.getTransactionPersistence();
    }

    // constructor
    public InventoryAccessor(IDBLayer db) {
        this.TransactionDB = (TransactionPersistence) db;
    }

    public String getAccountTransactions(int accountID) {
        Transaction[] transactions = TransactionDB.getAccountTransactions(accountID);
        String report = getReport(transactions);
        return report;
    }

    public String getItemTransactions(int itemID) {
        Transaction[] transactions = TransactionDB.getItemTransactions(itemID);
        String report = getReport(transactions);
        return report;
    }

    public String getInventoryTransactions(int inventoryID) {
        Transaction[] transactions = TransactionDB.getInventoryTransactions(inventoryID);
        String report = getReport(transactions);
        return report;
    }

    public String getAllTransactions() {
        IDSO[] transactionsAsIDSO = TransactionDB.getItemTransactions(itemID);
        Transaction[] transactions = new Transaction[transactionsAsIDSO.length];

        for (int i = 0; i < transactionsAsIDSO.length; i++) {
            transactions[i] = (Transaction) transactionsAsIDSO[i];
        }

        String report = getReport(transactions);
        return report;
    }

    private String getReport(Transaction[] transactions) {
        String report = "";

        for (Transaction transaction : transactions) {
            if (transaction != null) {
                report += transaction.toString() + "\n";
            }
        }

        return report;
    }
}
