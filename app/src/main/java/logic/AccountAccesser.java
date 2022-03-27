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
        return (Account) AccountDB.get(id);
    }

    public void deleteAccount(int id) {
        AccountDB.delete(id);
    }

    public Account[] getAllAccounts() {
        IDSO[] accountsAsIDSO = AccountDB.getDB();
        Account[] accounts = new Account[accountsAsIDSO.length];

        for (int i = 0; i < accountsAsIDSO.length; i++) {
            accounts[i] = (Account) accountsAsIDSO[i];
        }

        return accounts;
    }

    public void deleteAllAccounts() {
        AccountDB.clearDB();
    }
}
