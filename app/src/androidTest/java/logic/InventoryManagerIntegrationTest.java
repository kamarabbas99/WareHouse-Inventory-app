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
import objects.Item;

/*
    PURPOSE
 */
public class InventoryManagerIntegrationTest {
    InventoryManagerPersistence imPersistence;
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
        // done setup database connection for integration test
        // endregionSet
        // region $inventoryManagerSetup
        imPersistence = DatabaseManager.getInventoryManagerPersistence();
        assertNotNull(imPersistence);
        // endregion
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createItem() {

    }

    @Test
    public void addItem() {
    }

    @Test
    public void removeItem() {
    }

    @Test
    public void getItem() {
    }

    @Test
    public void getAllItems() {
    }

    @Test
    public void removeAllItems() {
    }
}