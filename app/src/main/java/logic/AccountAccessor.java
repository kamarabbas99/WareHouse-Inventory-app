package logic;

import database.IDBLayer;
import database.DatabaseManager;
import database.PersistenceException;
import objects.IDSO;
import objects.Account;

public class AccountAccessor {

    private IDBLayer accountDB;

    // default constructor
    public AccountAccessor() { this.accountDB = DatabaseManager.getAccountPersistence(); }

    // constructor
    public AccountAccessor(IDBLayer db) {
        this.accountDB = db;
    }

    // method that returns the privilege value of the current active account
    public int getCurrentPrivilege() {
        int id = DatabaseManager.getActiveAccount();
        Account account = (Account) accountDB.get(id);
        if (account != null) {
            return account.getPrivilege();
        }
        return 0;
    }

    // method that creates the account with the given information
    public Account createAccount(String username, String password, int privilege) {
        Account newAccount = new Account(-1, username, password, privilege);
        assert(accountDB != null);
        int id = accountDB.create(newAccount);
        // case A: account already exists with same name
        if (id < 0)
        {
            newAccount = null;
        }
        // case B: account did not already exist
        else{
            DatabaseManager.setActiveAccount(id);
            newAccount = (Account) accountDB.get(id);
        }

        if (newAccount == null)
        {
            System.out.println("Null account");
        }
        return newAccount;
    }

    // Method that deletes the account with given id
    public void deleteAccount(int id)
    {
        // case A: account you're trying to delete is not the active account
        if (DatabaseManager.getActiveAccount() != id)
        {
            accountDB.delete(id);
        }
        // case B: account you're trying to delete is the active account
        else
        {
            System.out.println("Cannot delete the active account. Please sign into another account to delete this one.");
        }
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
        IDSO[] accountsAsIDSO = accountDB.getDB();
        Account[] accounts = new Account[accountsAsIDSO.length];

        // stores every element of accountsAsIDSO in accounts after typecasting to Account
        for (int i = 0; i < accountsAsIDSO.length; i++)
        {
            accounts[i] = (Account) accountsAsIDSO[i];
        }

        return accounts;
    }

    // method that deletes all accounts
    public void deleteAllAccounts() {
        try
        {
            Account[] accounts = getAllAccounts();
            for (Account account : accounts)
            {
                // if the account id is not the active account's id, then delete the account
                if (account.getID() != DatabaseManager.getActiveAccount())
                {
                    accountDB.delete(account.getID());
                }
            }
        }
        catch (PersistenceException exception)
        {
            System.out.println("Cannot delete all accounts");
        }
    }
}
