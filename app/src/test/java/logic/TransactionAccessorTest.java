package logic;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import database.TransactionPersistence;
import objects.Transaction;

import org.junit.Before;
import org.junit.Test;

public class TransactionAccessorTest {

    TransactionAccessor ta;
    TransactionPersistence trPerMock;

    @Before
    public void setUp(){
        trPerMock=mock(TransactionPersistence.class);
        ta=new TransactionAccessor(trPerMock);
    }

    @Test
    public void testGetAccountTransactions()
    {
        Transaction[] newTr={new Transaction(1,2,3,"Type",3)};
        int accountId=1;
        when(trPerMock.getAccountTransactions(accountId)).thenReturn(newTr);
        assertEquals(ta.getAccountTransactions(accountId).contains("accountID:1"),true);
    }

    @Test
    public void testGetItemTransactions() {
        Transaction[] newTr={new Transaction(3,4,5,"Type2",8)};
        int itemId=1;
        when(trPerMock.getItemTransactions(itemId)).thenReturn(newTr);
        assertEquals(ta.getItemTransactions(itemId).contains("itemID:5"),true);

    }

    @Test
    public void testGetInventoryTransactions() {
        Transaction[] newTr={new Transaction(6,7,8,"Type3",9)};
        int inventoryId=1;
        when(trPerMock.getInventoryTransactions(inventoryId)).thenReturn(newTr);
        assertEquals(ta.getInventoryTransactions(inventoryId).contains("inventoryID:7"),true);

    }

    @Test
    public void testGetAllTransactions() {
        Transaction[] newTr={new Transaction(6,7,8,"All Transaction",9)};;
        when(trPerMock.getDB()).thenReturn(newTr);
        assertEquals(ta.getAllTransactions().contains("transactionType:All Transaction"),true);

    }
}