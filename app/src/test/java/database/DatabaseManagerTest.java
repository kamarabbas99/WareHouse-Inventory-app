package database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// testing for the Singleton class
public class DatabaseManagerTest {
    DatabaseManager dbManager;

    @Before
    public void setUp() throws Exception {
        dbManager = DatabaseManager.getInstance();
        assertNotNull(dbManager);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setDBFilePath() {
        dbManager.setDBFilePath("C:\\Users\\Curtis\\Documents\\GitLab\\warehouse-inventory-system\\app\\src\\main\\assets\\db\\WIS.script");
    }

    @Test
    public void getDBFilePath() {
        String filePath = dbManager.getDBFilePath();
        assertNotNull(filePath);
        assertEquals(filePath, "C:\\Users\\Curtis\\Documents\\GitLab\\warehouse-inventory-system\\app\\src\\main\\assets\\db\\WIS.script");
    }
}