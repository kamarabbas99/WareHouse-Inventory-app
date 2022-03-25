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

    //method that creates the account
    public int createAccount(Account toCreate) {
        return AccountDB.create(toCreate);
    }

    //method that return the account: Getter
    public Account getAccount(int id) {
        return (Account) AccountDB.get(id);
    }

    //Method that deletes the account with parameter passed
    public void deleteAccount(int id) {
        AccountDB.delete(id);
    }

    //method that return an array of all account existed
    public Account[] getAllAccounts() {
        IDSO[] accountsAsIDSO = AccountDB.getDB();
        Account[] accounts = new Account[accountsAsIDSO.length];

        for (int i = 0; i < accountsAsIDSO.length; i++) {
            accounts[i] = (Account) accountsAsIDSO[i];
        }

        return accounts;
    }

    //method that delete all account 
    public void deleteAllAccounts() {
        AccountDB.clearDB();
    }
}
