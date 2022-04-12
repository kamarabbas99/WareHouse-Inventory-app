package logic;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import database.AccountPersistence;

import org.junit.Before;
import org.junit.Test;

import database.DatabaseManager;
import objects.Account;

public class AccountAccessorTest {

    AccountAccessor aa;
    Account acc;
    AccountPersistence accPerMock;
    DatabaseManager dmMock;

    @Before
    public void setUp() {


        acc=new Account(1,"username1","password1",1);
        accPerMock=mock(AccountPersistence.class);
        dmMock=mock(DatabaseManager.class);
        aa=new AccountAccessor(accPerMock);

    }


    @Test
    public void testCreateAccount() {


        int temp=DatabaseManager.getActiveAccount();

        //when(accPerMock.create(acc)).thenReturn(acc.getID());
        Account some = new Account(-1, "username1", "password1", 1);
        when(accPerMock.create(some)).thenReturn(1);
        when(accPerMock.get(0)).thenReturn(acc);
        Account newAcc=aa.createAccount("username1","password1",1);
        assertEquals("username1",newAcc.getUsername());

        DatabaseManager.setActiveAccount(temp);

    }

    @Test
    public void verifyLoginTest() {

    }
//
    @Test
    public void testDeleteAccount() {
        int del=2;
        aa.deleteAccount(del);
        verify(accPerMock).delete(del);
    }

    @Test
    public void getCurrPrivTest()
    {
        //when(DatabaseManager.getActiveAccount()).thenReturn(2);
        //aa.getCurrentPrivilege();

        int temp=DatabaseManager.getActiveAccount();

        when(accPerMock.get(temp)).thenReturn(acc);
        assertNotEquals(2,aa.getCurrentPrivilege());
    }


  //Uncomment if the method is public
//    @Test
//    public void testGetAllAccounts() {
//        Account[] toReturn={new Account(1,"test1","test1",1)};
//        when(accPerMock.getDB()).thenReturn(toReturn);
//        assertArrayEquals(toReturn,aa.getAllAccounts());
//    }


    @Test
    public void testDeleteAllAccounts() {
        aa.deleteAllAccounts();
        verify(accPerMock).clearDB();

    }





}