package database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import objects.IDSO;
import objects.Item;

public class ItemPersistenceTest {
    ItemPersistence itemDB;
    //TODO: currently hard-coded, will need to change for others to test
    String dbFilePath = "C:\\Users\\Curtis\\Documents\\GitLab\\warehouse-inventory-system\\app\\src\\main\\assets\\db\\WIS";

    @Before
    public void setUp() throws Exception {
        try
        {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        itemDB = new ItemPersistence(dbFilePath);
        assertNotNull(itemDB);
    }

    @After
    public void tearDown() throws Exception {
        itemDB = null;
    }

    @Test
    public void testItemFound() {
        // relies on the first Item in the Games Inventory to exist and remain unchanged
        Item result = (Item) itemDB.get(1);
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
        // there should be no item with an id of -1
        Item nullItem = (Item) itemDB.get(-1);
        assertNull(nullItem);
    }

    @Test
    public void testCreatingItem() {
        Item testItem = new Item("Test", "Test item", 1, "unit", 0);
        int itemID = itemDB.create(testItem);
        assertTrue(itemID > 0);
    }

    @Test
    public void delete() {
        int deleteID = 11;
        assertNotNull(itemDB.get(11));
        itemDB.delete(deleteID);
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
    @Test
    public void clearDB() {
        itemDB.clearDB();
    }

    @Test
    public void verifyID() {

    }

    @Test
    public void addItem() {
        int itemID = 1;
        int amountToAdd = 5;
        Item itemToAdd = (Item) itemDB.get(itemID);
        int previousQty = itemToAdd.getQuantity();
        itemToAdd = (Item) itemDB.add(itemID, amountToAdd);
        assertEquals(itemToAdd.getQuantity(), previousQty + amountToAdd);
    }

    @Test
    public void removeItem() {
        int itemID = 1;
        int amountToRemove = 5;
        Item itemToRemove = (Item) itemDB.get(itemID);
        int previousQty = itemToRemove.getQuantity();
        itemToRemove = (Item) itemDB.remove(itemID, amountToRemove);
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