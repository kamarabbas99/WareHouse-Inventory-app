package logic;

import database.IDBLayer;
import database.DatabaseManager;
import database.PersistenceException;
import objects.IDSO;
import objects.Account;

public class AccountAccessor {

    private IDBLayer accountDB;

    // default constructor
    public AccountAccessor() {
        this.accountDB = DatabaseManager.getAccountPersistence();
    }

    // constructor
    public AccountAccessor(IDBLayer db) {
        this.accountDB = db;
    }

    // method that returns the privilege value of the current active account
    public int getCurrentPrivilege() {
        try {
            int id = DatabaseManager.getActiveAccount();
            Account account = (Account) accountDB.get(id);
            if (account != null) {
                return account.getPrivilege();
            }
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }

        return 0;
    }

    // method that creates the account with the given information
    public Account createAccount(String username, String password, int privilege) {
        try {
            Account newAccount = new Account(-1, username, password, privilege);
            int id = accountDB.create(newAccount);
            // case A: account already exists with same name
            if (id < 0) {
                newAccount = null;
            }
            // case B: account did not already exist
            else {
                DatabaseManager.setActiveAccount(id);
                newAccount = (Account) accountDB.get(id);
            }

            if (newAccount == null) {
                System.out.println("Null account");
            }
            return newAccount;
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    // Method that deletes the account with given id
    public void deleteAccount(int id) {
        try {
            // case A: account you're trying to delete is not the active account
            if (DatabaseManager.getActiveAccount() != id) {
                accountDB.delete(id);
            }
            // case B: account you're trying to delete is the active account
            else {
                System.out.println(
                        "Cannot delete the active account. Please sign into another account to delete this one.");
            }
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }

    }

    // method that verifies an account with given username and password exists in
    // the database or not
    public boolean verifyLogin(String username, String password) {
        try {
            Account[] accounts = getAllAccounts();
            for (int i = 0; i < accounts.length; i++) {
                if (accounts[i].getUsername().equals(username) && accounts[i].verifyPassword(password)) {
                    DatabaseManager.setActiveAccount(accounts[i].getID());
                    return true;
                }
            }
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    // method that return an array of all account existed
    private Account[] getAllAccounts() {
        try {
            IDSO[] accountsAsIDSO = accountDB.getDB();
            Account[] accounts = new Account[accountsAsIDSO.length];

            // stores every element of accountsAsIDSO in accounts after typecasting to
            // Account
            for (int i = 0; i < accountsAsIDSO.length; i++) {
                accounts[i] = (Account) accountsAsIDSO[i];
            }

            return accounts;
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
            return null;
        }

    }

    // method that deletes all accounts
    public void deleteAllAccounts() {
        try {
            Account[] accounts = getAllAccounts();
            for (int i = 0; i < accounts.length; i++) {
                // if the account id is not the active account's id, then delete the account
                if (accounts[i].getID() != DatabaseManager.getActiveAccount()) {
                    accountDB.delete(accounts[i].getID());
                }
            }
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
            System.out.println("Cannot delete all accounts");
        }
    }
}
