package database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import objects.Account;
import objects.IDSO;
import objects.Item;

public class InventoryManagerPersistenceTest {

    InventoryManagerPersistence inventoryManagerDB;
    DatabaseManager dbManager;
    //TODO: currently hard-coded, will need to change for others to test
    String dbFilePath = "C:\\Users\\Curtis\\Documents\\GitLab\\warehouse-inventory-system\\app\\src\\main\\assets\\db\\WIS";

    @Before
    public void setUp() throws Exception {
        // initialize dbManager
        dbManager = DatabaseManager.getInstance();
        // set db file path to hardcoded value
        dbManager.setDBFilePath(dbFilePath);
        // set active inventory to debug inventory
        //dbManager.setActiveInventory(0);
        // create a new ItemPersistence
        inventoryManagerDB = new InventoryManagerPersistence(dbManager.getDBFilePath());
        assertNotNull(inventoryManagerDB);
    }

    @After
    public void tearDown() throws Exception {
        // set active inventory back to default
        //dbManager.setActiveInventory(1);
        // set dbFilePath back to default
        //dbManager.setDBFilePath("WIS");
        // free memory allocated to itemDB
        inventoryManagerDB = null;
    }

    @Test
    public void testItemFound() {
        // relies on the first Item in the Games Inventory to exist and remain unchanged
        Item result = (Item) inventoryManagerDB.get(1);
        assertNotNull(result);
        assertEquals(1, result.getID());
        assertEquals("ELDEN RING", result.getName());
        assertEquals("Top game on Steam.", result.getDescription());
        assertEquals(5, result.getQuantity());
        assertEquals("unit", result.getQuantityMetric());
        assertEquals(0, result.getLowThreshold());
    }

    @Test
    public void testItemNotFound()
    {
        // there should never be an item with an id of -1
        Item nullItem = (Item) inventoryManagerDB.get(-1);
        assertNull(nullItem);
    }

    @Test
    public void testCreatingItem() {
        Item testItem = new Item(0, "Test", "Entry for testing", 1, "unit", 0);
        int itemID = inventoryManagerDB.create(testItem);
        assertTrue(itemID > 0);
    }

    @Test
    public void testCreatingNonItem()
    {
        // this should return a -1 since Account is not an instance of Item
        int id = inventoryManagerDB.create(new Account());
        assertEquals(-1, id);
    }

    @Test (expected = PersistenceException.class)
    public void testCreatingExistingItem()
    {
         Item testItem = new Item(1, "ELDEN RING", "Top game on Steam.", 0, "unit", 0);
         inventoryManagerDB.create(testItem);
    }

    @Test
    public void deleteItem() {
        inventoryManagerDB.delete(9);
    }

    @Test
    public void getDB() {
        IDSO[] items = inventoryManagerDB.getDB();
        assertNotNull(items);
        for (IDSO item: items)
        {
            assertNotNull(item);
        }
    }

    // a dangerous test (will remove data)
    @Test
    public void clearDB() {
        // switch to testInventory
        // add a pile of Items to the testInventory
        // test clearDB
        // switch back to default Inventory
    }

    @Test
    public void addItem() {

        int itemID = 1;
        int amountToAdd = 5;
        Item itemToAdd = (Item) inventoryManagerDB.get(itemID);
        int previousQty = itemToAdd.getQuantity();
        itemToAdd = (Item) inventoryManagerDB.add(itemID, amountToAdd);
        assertEquals(itemToAdd.getQuantity(), previousQty + amountToAdd);
    }

    @Test
    public void removeItem() {
        int itemID = 1;
        int amountToRemove = 5;
        Item itemToRemove = (Item) inventoryManagerDB.get(itemID);
        int previousQty = itemToRemove.getQuantity();
        itemToRemove = (Item) inventoryManagerDB.remove(itemID, amountToRemove);
        // if the quantity goes below 0, there will be no update
        if (previousQty - amountToRemove < 0)
        {
            assertEquals(itemToRemove.getQuantity(), previousQty);
        }
        // if the quantity does not go below 0, then it will be fine
        else
        {
            assertEquals(itemToRemove.getQuantity(), previousQty - amountToRemove);
        }
    }
}