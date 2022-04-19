package logic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import database.TransactionPersistence;
import database.DatabaseManager;
import objects.Transaction;

/* TRANSACTIONINTEGRATIONTEST
    PURPOSE: To test the integration between the logic layer and the data layer with respect to the
    Transactions table.
    NOTES: This will perform tests in the DatabaseTest class.
     */
public class TransactionIntegrationTest {

    TransactionPersistence transPersistence; // used only in tear down to remove added data
    DatabaseManager dbManager;
    String dbFilePath;
    int testID;
    TransactionAccessor ta;
    int accountID=0;
    int inventoryID=0;
    int itemID=0;
    String transType="test";
    int quantity=0;

    @Before
    public void setUp() throws Exception {
        // region $dbConnectionSetup
        // setup database connection for integration test
        dbManager = DatabaseManager.getInstance();
        Path path = Paths.get("src\\main\\assets\\db\\DatabaseTest.script");
        try {
            dbFilePath = path.toRealPath().toString(); // get the absolute file path of the db file
            dbFilePath = dbFilePath.substring(0, dbFilePath.lastIndexOf('.')); // remove the file extension ".script"
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbManager.setDBFilePath(dbFilePath);
        // endregionSet
        // region $inventoryManagerSetup
        transPersistence = DatabaseManager.getTransactionPersistence();
        assertNotNull(transPersistence);
        //transAccessor = new TransactionAccessor(transPersistence);
        //assertNotNull(transAccessor);
        // endregion

        transType="test";
        ta=new TransactionAccessor(transPersistence);

    }

    @After
    public void tearDown() throws Exception {
        //imPersistence.clearDB(); // clear all data add to the InventoryManagers table
        //itemPersistence.clearDB(); // clear all data add to the Items table
        //transPersistence.clearDB();
    }

    @Test
    public void createAndGetNewTransaction()
    {

        testID = transPersistence.create(new Transaction(1, 2, 3, "add", 10));
        Transaction testTrans = (Transaction) transPersistence.get(testID);
        assertNotNull(testTrans);
        assertEquals(testTrans.getAccountID(), 1);
        assertEquals(testTrans.getInventoryID(), 2);
        assertEquals(testTrans.getItemID(), 3);

    }

    @Test
    public void testGetAccountTransactions(){

        accountID++;
        inventoryID++;
        itemID++;
        quantity++;
        testID = transPersistence.create(new Transaction(accountID, inventoryID, itemID, transType, quantity));
        assertEquals(ta.getAccountTransactions(accountID).contains("accountID:"+accountID),true);
    }

    @Test
    public void testGetItemTransactions() {
        accountID++;
        inventoryID++;
        itemID++;
        quantity++;
        testID = transPersistence.create(new Transaction(accountID, inventoryID, itemID, transType, quantity));
        assertEquals(ta.getItemTransactions(itemID).contains("itemID:"+itemID),true);
    }

    @Test
    public void testGetInventoryTransactions() {
        accountID++;
        inventoryID++;
        itemID++;
        quantity++;
        testID = transPersistence.create(new Transaction(accountID, inventoryID, itemID, transType, quantity));
        assertEquals(ta.getInventoryTransactions(inventoryID).contains("inventoryID:"+inventoryID),true);
    }

    @Test
    public void testGetAllTransactions() {
        accountID++;
        inventoryID++;
        itemID++;
        quantity++;
        testID = transPersistence.create(new Transaction(accountID, inventoryID, itemID, transType, quantity));
        assertEquals(ta.getAllTransactions().contains("itemID:"+itemID),true);
    }

}