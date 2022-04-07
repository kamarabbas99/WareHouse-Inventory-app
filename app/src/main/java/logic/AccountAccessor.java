package logic;

import database.IDBLayer;
import database.DatabaseManager;
import database.PersistenceException;
import objects.IDSO;
import objects.Account;

public class AccountAccessor {

    private IDBLayer AccountDB;

    // default constructor
    public AccountAccessor() {
        this.AccountDB = DatabaseManager.getAccountPersistence();
    }

    // constructor
    public AccountAccessor(IDBLayer db) {
        this.AccountDB = db;
    }

    public int getCurrentPrivilege() {
        int id = DatabaseManager.getActiveAccount();
        Account account = (Account) AccountDB.get(id);
        if (account != null) {
            return account.getPrivilege();
        }
        return 0;
    }

    // method that creates the account
    public Account createAccount(String username, String password, int privilege) {
        Account newAccount = new Account(-1, username, password, privilege);
        int id = AccountDB.create(newAccount);
        DatabaseManager.setActiveAccount(id);
        newAccount = (Account) AccountDB.get(id);
        if (newAccount == null) {
            System.out.println("Null account");
        }
        return newAccount;
    }

    // Method that deletes the account with parameter passed
    public void deleteAccount(int id) {
        AccountDB.delete(id);
    }

    // method that verifies an account with given username and password exists in
    // the database or not
    public boolean verifyLogin(String username, String password) {
        Account[] accounts = getAllAccounts();
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.verifyPassword(password)) {
                DatabaseManager.setActiveAccount(account.getID());
                return true;
            }
        }

        return false;
    }

    // method that return an array of all account existed
    private Account[] getAllAccounts() {
        IDSO[] accountsAsIDSO = AccountDB.getDB();
        Account[] accounts = new Account[accountsAsIDSO.length];

        for (int i = 0; i < accountsAsIDSO.length; i++) {
            accounts[i] = (Account) accountsAsIDSO[i];
        }

        return accounts;
    }

    // method that delete all account
    public void deleteAllAccounts() {
        AccountDB.clearDB();
    }
}
