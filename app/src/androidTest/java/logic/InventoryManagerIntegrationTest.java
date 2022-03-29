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
        imAccessor = new InventoryManagerAccessor(imPersistence);
        assertNotNull(imAccessor);
        // endregion
    }

    @After
    public void tearDown() throws Exception {
        imPersistence.clearDB(); // clear all data add to the InventoryManagers table
        itemPersistence.clearDB(); // clear all data add to the Items table
    }

    @Test
    public void createNewItem() {

    }

    @Test
    public void createExistingItem() {

    }

    @Test // tries to create an IDSO that is not an Item (should fail)
    public void createNonItem(){

    }

    @Test
    public void addNewItem() {
    }

    @Test
    public void addExistingItem(){

    }

    @Test // tries to add to a IDSO that is not an Item (should fail)
    public void addNonItem(){

    }

    @Test
    public void removeExistingItem() {

    }

    @Test
    public void removeNonExistingItem() {

    }

    @Test // tries to remove from a IDSO that is not an Item (should fail)
    public void removeNonItem() {

    }

    @Test
    public void getExistingItem() {
    }

    @Test
    public void getNonExistingItem() {
    }

    @Test // tries to get an IDSO that is not an Item (should fail)
    public void getNonItem() {
    }

    @Test
    public void getAllItems() {
    }

    @Test
    public void getAllFromEmptyDB() {
    }

    @Test
    public void removeAllItems() {
    }
}