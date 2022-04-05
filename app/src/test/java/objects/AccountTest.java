package objects;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractCollection;

public class AccountTest {
    private Account newAccount;
    private Account defaultAccount;

    @Before
    public void setUp() throws Exception {
        defaultAccount = new Account();
        newAccount = new Account(1, "bugs-bunny", "whatsupdoc?", 1);

        assertNotNull(defaultAccount);
        assertNotNull(newAccount);
    }

    @After
    public void tearDown() throws Exception {
        defaultAccount = null;
        newAccount = null;
    }

    @Test
    public void nullConstructor(){
        Account testAccount = new Account();
        assertTrue(testAccount.getID() == -1);
        assertTrue(testAccount.getUsername().equals("default"));
        assertTrue(testAccount.getPrivilege() == 1);
        assertTrue(testAccount.verifyPassword("123"));
        assertNotNull(testAccount.getDateCreated());
    }

    @Test
    public void constructor(){
        Account testAccount = new Account(10, "porky-pig", "thatsAllFolks", 1);
        assertTrue(testAccount.getID() == 10);
        assertTrue(testAccount.getUsername().equals("porky-pig"));
        assertTrue(testAccount.getPrivilege() == 1);
        assertTrue(testAccount.verifyPassword("thatsAllFolks"));
        assertNotNull(testAccount.getDateCreated());

        // testing exceptions
        try
        {
            Account nullUsernameAccount = new Account(10, null, "thatsAllFolks", 1);
        }
        catch(NullPointerException exception)
        {
            System.out.println(exception.getMessage());
            assertNotNull(exception.getMessage());
        }
        try
        {
            Account nullPasswordAccount = new Account(10, "porky-pig", null, 1);
        }
        catch(NullPointerException exception)
        {
            System.out.println(exception.getMessage());
            assertNotNull(exception.getMessage());
        }
    }

    @Test
    public void getID() {
        assertTrue(defaultAccount.getID() == -1);
        assertTrue(newAccount.getID() == 1);
    }

    @Test
    public void getUsername() {
        assertNotNull(defaultAccount.getUsername());
        assertNotNull(newAccount.getUsername());
        assertTrue(defaultAccount.getUsername().equals("default"));
        assertFalse(defaultAccount.getUsername().equals("bugs-bunny"));
        assertTrue(newAccount.getUsername().equals("bugs-bunny"));
        assertFalse(newAccount.getUsername().equals("default"));
    }

    @Test
    public void getDateCreated() {
        assertNotNull(defaultAccount.getDateCreated());
        assertNotNull(newAccount.getDateCreated());
    }

    @Test
    public void getPrivilege(){
        assertTrue(defaultAccount.getPrivilege() == 1);
        assertFalse(defaultAccount.getPrivilege() == 0);
        assertTrue(newAccount.getPrivilege() == 1);
        assertFalse(newAccount.getPrivilege() == 0);
    }

    @Test
    public void verifyPassword() {
        assertFalse(defaultAccount.verifyPassword(null));
        assertFalse(defaultAccount.verifyPassword("whatsupdoc?"));
        assertTrue(defaultAccount.verifyPassword("123"));
        assertFalse(newAccount.verifyPassword(null));
        assertTrue(newAccount.verifyPassword("whatsupdoc?"));
        assertFalse(newAccount.verifyPassword("123"));
    }

    @Test
    public void changePassword() {
        assertFalse(defaultAccount.changePassword(null, null));
        assertFalse(defaultAccount.changePassword(null, "123"));
        assertTrue(defaultAccount.changePassword("123", "whatsupdoc?"));
        assertTrue(defaultAccount.verifyPassword("whatsupdoc?"));
        assertFalse(newAccount.changePassword(null, null));
        assertFalse(newAccount.changePassword(null, "123"));
        assertFalse(newAccount.changePassword("whatsupdoc?", null));
        assertTrue(newAccount.changePassword("whatsupdoc?", "123"));
        assertTrue(newAccount.verifyPassword("123"));
    }

    @Test
    public void testEquals() {
        Account defaultTestAccount = new Account();
        Account newTestAccount = new Account(1, "Porky Pig", "thatsAllFolks", 1);
        assertTrue(defaultAccount.equals(defaultTestAccount));
        assertFalse(newAccount.equals(defaultTestAccount));
        assertTrue(newAccount.equals(newTestAccount));
        assertFalse(defaultAccount.equals(newTestAccount));
    }
}