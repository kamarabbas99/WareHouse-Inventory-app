package logic;


import static org.junit.Assert.*;

import database.PersistenceException;

import org.junit.Before;
import org.junit.Test;
import objects.Account;
import database.Database;


public class AccountAccesserTest {

    AccountAccesser aa;
    Account acc;

    @Before
    public void setUp() {
        aa=new AccountAccesser(new Database());
        assertNotNull(aa);

        acc=new Account();
        assertNotNull(acc);

        aa.createAccount(acc);
        assertNotEquals(1,aa.createAccount(acc));
    }

    @Test
    public void testCreateAccount() {
        assertEquals(-1,aa.createAccount(acc));
    }

    @Test
    public void testGetAccount() {
        assertEquals(-1,aa.getAccount(-1));
    }

    @Test (expected = PersistenceException.class)
    public void testDeleteAccount() {
        aa.deleteAccount(0);

    }


    @Test
    public void testGetAllAccounts() {
        Account bc[]=aa.getAllAccounts();
        assertEquals(-1,bc[0].getID());
    }

    @Test (expected = PersistenceException.class)
    public void testDeleteAllAccounts() {
        aa.deleteAllAccounts();
        aa.deleteAllAccounts();
    }
}