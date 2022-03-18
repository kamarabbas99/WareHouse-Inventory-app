package logic;

import database.IDBLayer;
import objects.Account;

public class AccountManager {

    private IDBLayer AccountDB;

    public AccountManager(IDBLayer db) {
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
        return (Account[]) AccountDB.getDB();
    }

    public void deleteAllAccounts() {
        AccountDB.clearDB();
    }
}
