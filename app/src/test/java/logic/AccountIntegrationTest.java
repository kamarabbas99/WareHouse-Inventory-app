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
import database.InventoryPersistence;
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

    @Test
    public void createNewAccountTest() {

        Account newAcc=accountAccessor.createAccount("createNewTest","pass1",0);
        assertEquals(newAcc.getUsername(),"createNewTest");
        assertEquals(newAcc.getPrivilege(),0);

    }

    @Test
    public void createExistingAccountTest(){
        Account newAccount = accountAccessor.createAccount("existingAccount", "1234", 0);
        Account existingAccount = accountAccessor.createAccount("existingAccount", "1234", 0);
        assertNull(existingAccount);
        DatabaseManager.setActiveAccount(1);
        accountAccessor.deleteAccount(newAccount.getID());
    }

    @Test
    public void getAccountPrivTest()
    {

        Account newAcc=accountAccessor.createAccount("getPrivilegeTest","pass1",3);
        assertEquals(3,accountAccessor.getCurrentPrivilege());
    }

    @Test
    public void deleteTest()
    {
        Account newAcc=accountAccessor.createAccount("userD","passD",5);
        DatabaseManager.setActiveAccount(1);
        accountAccessor.deleteAccount(newAcc.getID());
        assertNotEquals(5,accountAccessor.getCurrentPrivilege());
    }

    @Test
    public void deleteActiveAccountTest()
    {
        Account newAcc=accountAccessor.createAccount("Test","test",5);
        DatabaseManager.setActiveAccount(newAcc.getID());
        accountAccessor.deleteAccount(newAcc.getID());
        assertNotEquals(5,accountAccessor.getCurrentPrivilege());
    }

    @Test
    public void verifyTest()
    {
        Account newAcc=accountAccessor.createAccount("userC","passC",3);
        assertTrue(accountAccessor.verifyLogin("userC","passC"));
    }

    @Test
    public void deleteAll()
    {
        //assertTrue(accountAccessor.verifyLogin("userC","passC"));
        accountAccessor.deleteAllAccounts();
        //assertEquals(0,accountAccessor.getCurrentPrivilege());
        //assertFalse(accountAccessor.verifyLogin("userC","passC"));
    }
}
