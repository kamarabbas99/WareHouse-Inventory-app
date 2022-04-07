package logic;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import database.AccountPersistence;

import org.junit.Before;
import org.junit.Test;

import objects.Account;


public class AccountAccesserTest {

    AccountAccesser aa;
    Account acc;
    AccountPersistence accPerMock;

    @Before
    public void setUp() {


        acc=new Account(1,"test1","test1",1);
        accPerMock=mock(AccountPersistence.class);
        aa=new AccountAccesser(accPerMock);

    }


    @Test
    public void testCreateAccount() {
        when(accPerMock.create(acc)).thenReturn(acc.getID());
        assertEquals(1,aa.createAccount(acc));

    }

    @Test
    public void testGetAccount() {
        when(accPerMock.get(1)).thenReturn(acc);
        assertEquals(1,aa.getAccount(1).getID());
        assertNull(aa.getAccount(2));
    }

    @Test
    public void testDeleteAccount() {
        int del=2;
        aa.deleteAccount(del);
        verify(accPerMock).delete(del);
    }


    @Test
    public void testGetAllAccounts() {
       Account[] toReturn={new Account(1,"test1","test1",1)};
       when(accPerMock.getDB()).thenReturn(toReturn);
       assertArrayEquals(toReturn,aa.getAllAccounts());
    }


    @Test
    public void testDeleteAllAccounts() {
        aa.deleteAllAccounts();
        verify(accPerMock).clearDB();

    }



}