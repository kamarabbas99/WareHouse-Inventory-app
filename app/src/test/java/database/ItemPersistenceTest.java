package database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import objects.Item;

public class ItemPersistenceTest {
    ItemPersistence itemDB;
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
    public void get() {
        Item result = (Item) itemDB.get(1);
        assertNotNull(result);
        assertEquals(1, result.getID());
        assertEquals("ELDEN RING", result.getName());
        assertEquals("Top game on Steam.", result.getDescription());
        assertEquals(10, result.getQuantity());
        assertEquals("unit", result.getQuantityMetric());
        assertEquals(0, result.getLowThreshold());
    }

    @Test
    public void create() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getDB() {
    }

    @Test
    public void clearDB() {
    }

    @Test
    public void verifyID() {
    }

    @Test
    public void addItem() {
    }

    @Test
    public void removeItem() {
    }
}