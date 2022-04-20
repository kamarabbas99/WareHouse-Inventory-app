package objects;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.sql.Timestamp;

public class TransactionTest {
    private Transaction newTransaction;
    private Transaction defaultTransaction;

    @Before
    public void setUp() throws Exception
    {
        newTransaction = new Transaction(1, 1, 1, "test", 0);
        assertNotNull(newTransaction);
        newTransaction = new Transaction(1, 1, 1, 1, "test", 0, new Timestamp(new Date().getTime()));
        assertNotNull(newTransaction);
        defaultTransaction = new Transaction();
        assertNotNull(defaultTransaction);
    }

    @Test (expected = NullPointerException.class)
    public void invalidTypeTest()
    {
        Transaction nullType = new Transaction(1, 1, 1, null, 0);
    }

    @Test
    public void getIDTest()
    {
        assertEquals(defaultTransaction.getID(), -1);
        assertEquals(newTransaction.getID(), 1);
    }

    @Test
    public void getAccountIDTest()
    {
        assertEquals(defaultTransaction.getAccountID(), -1);
        assertEquals(newTransaction.getAccountID(), 1);
    }

    @Test
    public void getInventoryIDTest()
    {
        assertEquals(defaultTransaction.getInventoryID(), -1);
        assertEquals(newTransaction.getInventoryID(), 1);
    }

    @Test
    public void getItemIDTest()
    {
        assertEquals(defaultTransaction.getItemID(), -1);
        assertEquals(newTransaction.getItemID(), 1);
    }

    @Test
    public void getTransactionTypeTest()
    {
        assertNotNull(defaultTransaction.getTransactionType());
        assertNotNull(newTransaction.getTransactionType());
        assertEquals(defaultTransaction.getTransactionType(), "default");
        assertEquals(newTransaction.getTransactionType(), "test");
    }

    @Test
    public void getQuantityTest()
    {
        assertEquals(defaultTransaction.getQuantity(), -1);
        assertEquals(newTransaction.getQuantity(), 0);
    }

    @Test
    public void getDateCreatedTest()
    {
        assertNotNull(defaultTransaction.getDateCreated());
        assertNotNull(newTransaction.getDateCreated());
    }

    @Test
    public void toStringTest()
    {
        assertNotNull(defaultTransaction.toString());
        assertNotNull(newTransaction.toString());
    }
}
