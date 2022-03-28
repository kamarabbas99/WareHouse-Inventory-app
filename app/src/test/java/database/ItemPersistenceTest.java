package database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import objects.*;

public class ItemPersistenceTest {
    ItemPersistence itemDB;
    DatabaseManager dbManager;
    //TODO: currently hard-coded, will need to change for others to test
    String dbFilePath = "C:\\Users\\Curtis\\Documents\\GitLab\\warehouse-inventory-system\\app\\src\\main\\assets\\db\\WIS";

    @Before
    public void setUp() throws Exception {
        // initialize dbManager
        dbManager = DatabaseManager.getInstance();
        // set db file path to hardcoded value
        dbManager.setDBFilePath(dbFilePath);
        // create a new ItemPersistence
        itemDB = new ItemPersistence(dbManager.getDBFilePath());
        assertNotNull(itemDB);
    }

    @After
    public void tearDown() throws Exception {
        // free memory allocated to itemDB
        itemDB = null;
    }

    @Test // not currently working
    public void testItemFound() {
        // relies on the first Item in the Games Inventory to exist and remain unchanged
        Item result = (Item) itemDB.get(1);
        assertNotNull(result);
        assertEquals(1, result.getID());
        assertEquals("ELDEN RING", result.getName());
        assertEquals("Top game on Steam.", result.getDescription());
        assertEquals(0, result.getQuantity());
        assertEquals("unit", result.getQuantityMetric());
        assertEquals(0, result.getLowThreshold());
    }

    @Test
    public void testItemNotFound()
    {
        // there should never be an item with an id of -1
        Item nullItem = (Item) itemDB.get(-1);
        assertNull(nullItem);
    }

    @Test
    public void testCreatingItem() {
        Item testItem = new Item("Test", "Entry for testing", 1, "unit", 0);
        int itemID = itemDB.create(testItem);
        assertTrue(itemID > 0);
    }

    @Test
    public void testCreatingNonItem()
    {
        // this should return a -1 since Account is not an instance of Item
        int id = itemDB.create(new Account());
        assertEquals(-1, id);
    }

    @Test (expected = PersistenceException.class)
    public void deleteItem() {
        // add test item to DB if not already exists
        itemDB.delete(1);
    }

    @Test
    public void getDB() {
        IDSO[] items = itemDB.getDB();
        assertNotNull(items);
        for (IDSO item: items)
        {
            assertNotNull(item);
        }
    }

    // a dangerous test (will remove data)
    @Test (expected = PersistenceException.class)
    public void clearDB() {
        itemDB.clearDB();
    }

    @Test (expected = PersistenceException.class)
    public void addItem() {
        itemDB.add(1, 5);
    }

    @Test (expected = PersistenceException.class)
    public void removeItem() {
        itemDB.remove(1, 5);
    }
}