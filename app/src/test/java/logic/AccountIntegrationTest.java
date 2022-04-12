package logic;

import static org.junit.Assert.*;

import android.provider.ContactsContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import database.DatabaseManager;
import database.AccountPersistence;
import database.InventoryManagerPersistence;
import database.ItemPersistence;
import objects.Account;
import objects.Item;

/* ACCOUNTINTEGRATIONTEST
PURPOSE: To test the integration between the logic layer and the data layer with respect to the
Accounts table.
NOTES: This will perform tests in the DatabaseTest class.
 */
public class AccountIntegrationTest {
    AccountAccessor accountAccessor;
    AccountPersistence accountPersistence; // used only in tear down to remove added data
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
        accountPersistence = DatabaseManager.getAccountPersistence();
        assertNotNull(accountPersistence);
        accountAccessor = new AccountAccessor(accountPersistence);
        assertNotNull(accountAccessor);
        // endregion
    }

    @After
    public void tearDown() throws Exception {
        //imPersistence.clearDB(); // clear all data add to the InventoryManagers table
        //itemPersistence.clearDB(); // clear all data add to the Items table
    }

    @Test
    public void createNewAccountTest() {

        Account newAcc=accountAccessor.createAccount("user1","pass1",0);
        assertEquals(newAcc.getUsername(),"user1");
        assertEquals(newAcc.getPrivilege(),0);

        //assertEquals(1,1);
        //accountPersistence.create(new Account(-1, "test", "1234", 0));
//        Account account = (Account) accountPersistence.get(6);
//        Account newAccount = new Account(-1, "test", "1234", 0);
//        int id = accountPersistence.create(newAccount);
//        DatabaseManager.setActiveAccount(id);
//        newAccount = (Account) accountPersistence.get(id);
////        assertNotNull(newAccount);
//        Account check = accountAccessor.createAccount("test", "1234", 0);
//        //Account account = (Account) accountPersistence.get(6);
//        System.out.println("New id = " + check.getID());
//        assertNotNull(check);
//        System.out.println("Username = " + check.getUsername());
//        System.out.println("Password = " + check.getPassword());
//        System.out.println("Privilege = " + check.getPrivilege());
//        assertEquals("test", check.getUsername());
//
//        Account fromDatabase = accountAccessor.getAccount(check.getID());
//        assertEquals(fromDatabase.getName(), "Item1");
    }

    @Test
    public void getAccountPrivTest()
    {

        Account newAcc=accountAccessor.createAccount("user1","pass1",3);
        assertEquals(3,accountAccessor.getCurrentPrivilege());
    }

    @Test
    public void deleteTest()
    {
        //int currID=DatabaseManager.getActiveInventory();

        Account newAcc=accountAccessor.createAccount("userD","passD",5);
        //System.out.println("Active "+newAcc.getID());
        accountAccessor.deleteAccount(newAcc.getID());
        assertNotEquals(5,accountAccessor.getCurrentPrivilege());
    }

    @Test
    public void verifyTest()
    {

        Account newAcc=accountAccessor.createAccount("userC","passC",3);
        assertTrue(accountAccessor.verifyLogin("userC","passC"));
    }
}
