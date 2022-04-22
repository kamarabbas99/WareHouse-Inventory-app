package logic;

import database.IDBLayer;
import database.DatabaseManager;
import database.TransactionPersistence;
import database.PersistenceException;
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
        String report = "";

        try {
            Transaction[] transactions = TransactionDB.getAccountTransactions(accountID);
            report = getReport(transactions);
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }

        return report;
    }

    // method that returns the report of all the transactions for a specific item
    // found using the given item id
    public String getItemTransactions(int itemID) {
        String report = "";

        try {
            Transaction[] transactions = TransactionDB.getItemTransactions(itemID);
            report = getReport(transactions);
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }

        return report;
    }

    // method that returns the report of all the transactions for a specific
    // inventory found using the given inventory id
    public String getInventoryTransactions(int inventoryID) {
        String report = "";

        try {
            Transaction[] transactions = TransactionDB.getInventoryTransactions(inventoryID);
            report = getReport(transactions);
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }

        return report;
    }

    // method that returns the report of all the transactions
    public String getAllTransactions() {
        String report = "";

        try {
            IDSO[] transactionsAsIDSO = TransactionDB.getDB();
            Transaction[] transactions = new Transaction[transactionsAsIDSO.length];

            // stores all elements of transactionsAsIDSO in transactions after converting to
            // Transaction
            for (int i = 0; i < transactionsAsIDSO.length; i++) {
                transactions[i] = (Transaction) transactionsAsIDSO[i];
            }

            report = getReport(transactions);
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }

        return report;
    }

    // method that returns a report string which is made from given transactions
    // array
    private String getReport(Transaction[] transactions) {
        String report = "";

        for (int i = 0; i < transactions.length; i++) {
            if (transactions[i] != null) {
                report += transactions[i].toString() + "\n\n";
            }
        }

        return report;
    }
}
