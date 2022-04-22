package logic;

import database.IDBLayer;
import database.DatabaseManager;
import database.TransactionPersistence;
import objects.IDSO;
import objects.Transaction;

public class TransactionAccessor {
    private TransactionPersistence TransactionDB;

    // default constructor
    public TransactionAccessor() {
        this.TransactionDB = (TransactionPersistence) DatabaseManager.getTransactionPersistence();
    }

    // constructor
    public TransactionAccessor(IDBLayer db) {
        this.TransactionDB = (TransactionPersistence) db;
    }

    // method that returns the report of all the transactions for a specific account
    // found using the given account id
    public String getAccountTransactions(int accountID) {
        Transaction[] transactions = TransactionDB.getAccountTransactions(accountID);
        String report = getReport(transactions);
        return report;
    }

    // method that returns the report of all the transactions for a specific item
    // found using the given item id
    public String getItemTransactions(int itemID) {
        Transaction[] transactions = TransactionDB.getItemTransactions(itemID);
        String report = getReport(transactions);
        return report;
    }

    // method that returns the report of all the transactions for a specific
    // inventory found using the given inventory id
    public String getInventoryTransactions(int inventoryID) {
        Transaction[] transactions = TransactionDB.getInventoryTransactions(inventoryID);
        String report = getReport(transactions);
        return report;
    }

    // method that returns the report of all the transactions
    public String getAllTransactions() {
        IDSO[] transactionsAsIDSO = TransactionDB.getDB();
        Transaction[] transactions = new Transaction[transactionsAsIDSO.length];

        for (int i = 0; i < transactionsAsIDSO.length; i++) {
            transactions[i] = (Transaction) transactionsAsIDSO[i];
        }

        String report = getReport(transactions);
        return report;
    }

    // method that returns a report string which is made from given transactions array
    private String getReport(Transaction[] transactions) {
        String report = "";

        for (Transaction transaction : transactions) {
            if (transaction != null) {
                report += transaction.toString() + "\n\n";
            }
        }

        return report;
    }
}
