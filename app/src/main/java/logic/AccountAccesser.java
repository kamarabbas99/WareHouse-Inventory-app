package logic;

import database.IDBLayer;
import objects.IDSO;
import objects.Account;

public class AccountAccesser {

    private IDBLayer AccountDB;

    // default constructor needs to be updated after the singleton class for getting AccountPersistence instance is complete

    // constructor
    public AccountAccesser(IDBLayer db) {
        this.AccountDB = db;
    }

    public int createAccount(Account toCreate) {
        return AccountDB.create(toCreate);
    }

    public Account getAccount(int id) {
        Account toGet = null;

        if (AccountDB.verifyID(id)) {
            toGet = (Account) AccountDB.get(id);
        }

        return toGet;
    }

    public Account deleteAccount(int id) {
        Account deleted = null;

        if (AccountDB.verifyID(id)) {
            deleted = (Account) AccountDB.delete(id);
        }

        return deleted;
    }

    public Account[] getAllAccounts() {
        IDSO[] accountsAsIDSO = AccountDB.getDB();
        Account[] accounts = new Item[accountsAsIDSO.length];

        for (int i = 0; i < accountsAsIDSO.length; i++) {
            accounts[i] = (Account) accountsAsIDSO[i];
        }

        return accounts;
    }

    public void deleteAllAccounts() {
        AccountDB.clearDB();
    }
}
