package logic;

import static org.junit.Assert.*;

import android.provider.ContactsContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import database.DatabaseManager;
import database.InventoryManagerPersistence;
import database.InventoryPersistence;
import database.PersistenceException;
import objects.Account;
import objects.Inventory;

/* INVENTORYINTEGRATIONTEST
PURPOSE: To test the integration between the logic layer and the data layer with respect to the
Inventory table.
NOTES: This will perform tests in the DatabaseTest class.
 */
public class InventoryIntegrationTest {
    InventoryAccessor inventoryAccessor;
    InventoryPersistence inventoryPersistence;
    InventoryManagerAccessor inventoryManagerAccessor;
    InventoryManagerPersistence inventoryManagerPersistence;
    DatabaseManager dbManager;
    String dbFilePath;

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
        inventoryPersistence = DatabaseManager.getInventoryPersistence();
        assertNotNull(inventoryPersistence);
        inventoryAccessor = new InventoryAccessor(inventoryPersistence);
        assertNotNull(inventoryAccessor);
        inventoryManagerPersistence = DatabaseManager.getInventoryManagerPersistence();
        assertNotNull(inventoryManagerPersistence);
        inventoryManagerAccessor = new InventoryManagerAccessor(inventoryManagerPersistence);
        assertNotNull(inventoryManagerAccessor);
        // endregion
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createInventoryTest()
    {
        Inventory newInventory = inventoryAccessor.createInventory("createTest");
        assertEquals(newInventory.getName(),"createTest");
    }

    @Test
    public void createExistingInventoryTest()
    {
        Inventory newInventory = inventoryAccessor.createInventory("existingInventoryTest");
        Inventory existingInventory = inventoryAccessor.createInventory("existingInventoryTest");
        assertNull(existingInventory);
        inventoryAccessor.deleteInventory(newInventory.getID());
    }

    @Test
    public void getInventoryTest()
    {
        Inventory newInventory = inventoryAccessor.createInventory("getTest");
        Inventory inventory = inventoryAccessor.getInventory(newInventory.getID());
        assertNotNull(inventory);
    }

    @Test
    public void deleteInventoryTest()
    {
        Inventory newInventory = inventoryAccessor.createInventory("deleteTest");
        int id = newInventory.getID();
        inventoryAccessor.deleteInventory(id);
        assertNull(inventoryAccessor.getInventory(id));
    }

    @Test
    public void deleteInventoryWithItemsTest()
    {
        Inventory newInventory = inventoryAccessor.createInventory("deleteWithItemsTest");
        int id = newInventory.getID();
        DatabaseManager.setActiveInventory(newInventory.getID()); // change active account
        inventoryManagerAccessor.createItem("createTest1", "test Item", 10, "unit", 0);
        inventoryManagerAccessor.createItem("createTest2", "test Item", 10, "unit", 0);
        inventoryManagerAccessor.createItem("createTest3", "test Item", 10, "unit", 0);
        DatabaseManager.setActiveAccount(1); // reset active account
        inventoryAccessor.deleteInventory(id);
        assertNull(inventoryAccessor.getInventory(id));
    }

    @Test (expected = PersistenceException.class)
    public void deleteAllInventory()
    {
        inventoryAccessor.deleteAllInventories();
    }
}