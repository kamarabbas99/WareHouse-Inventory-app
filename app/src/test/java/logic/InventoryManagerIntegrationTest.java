package logic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import database.DatabaseManager;
import database.InventoryManagerPersistence;
import database.ItemPersistence;
import objects.Inventory;
import objects.Item;

/* INVENTORYMANAGERINTEGRATIONTEST
PURPOSE: To test the integration between the logic layer and the data layer with respect to the
InventoryManagers table.
NOTES: This will perform tests in the DatabaseTest class.
 */
public class InventoryManagerIntegrationTest {
    InventoryManagerPersistence imPersistence;
    InventoryManagerAccessor imAccessor;
    ItemPersistence itemPersistence; // used only in tear down to remove added data
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
        imPersistence = DatabaseManager.getInventoryManagerPersistence();
        assertNotNull(imPersistence);
        itemPersistence = DatabaseManager.getItemPersistence();
        assertNotNull(itemPersistence);
        imAccessor = new InventoryManagerAccessor(imPersistence);
        assertNotNull(imAccessor);
        // endregion
    }

    @After
    public void tearDown() throws Exception {
        //imPersistence.clearDB(); // clear all data add to the InventoryManagers table
        //itemPersistence.clearDB(); // clear all data add to the Items table
    }


    @Test
    public void createNewItem() {

        //assertEquals(1,1);
        Item check = imAccessor.createItem("Item1", "Item1:desc", 2, "Item1:Met", 1);
        assertEquals("Item1", check.getName());

        Item fromDatabase = imAccessor.getItem(check.getID());
        assertEquals(fromDatabase.getName(), "Item1");


    }

    @Test
    public void createExistingItem() {
        //Inventory newInventory = imAccessor.createItem("existingInventoryTest");
        //Inventory existingInventory = imAccessor.createItem("existingInventoryTest");
        //assertNull(existingInventory);
        //imAccessor.deleteItem(newInventory.getID());

//        Item check = imAccessor.getItem(1);
//        imAccessor.addItem(1, 3);
//        Item check2 = imAccessor.getItem(1);
//        assertEquals(check.getQuantity() + 3, check2.getQuantity());
    }

    @Test
    public void removeExistingItem() {
        Item check = imAccessor.getItem(1);
        imAccessor.removeItem(1, 3);
        Item check2 = imAccessor.getItem(1);
        assertEquals(check.getQuantity() - 3, check2.getQuantity());
        imAccessor.addItem(1, 3);

    }

    @Test
    public void getItemTest() {
        Item check = imAccessor.createItem("Item2", "Item2:desc", 2, "Item2:Met", 7);
        Item fromDatabase = imAccessor.getItem(check.getID());
        assertEquals(fromDatabase.getName(), "Item2");
        assertEquals(fromDatabase.getQuantity(), 2);
        //assertEquals(fromDatabase.getLowThreshold(),7);
    }

    @Test
    public void getAllItemTest() {
        Item check = imAccessor.createItem("ItemLast", "ItemLast:desc", 2, "ItemLast:Met", 3);
        Item[] allItems = imAccessor.getAllItems();
        int len = allItems.length;
        assertEquals(allItems[len - 1].getName(), "ItemLast");
    }

}

