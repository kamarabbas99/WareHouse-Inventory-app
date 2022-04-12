package objects;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InventoryTest {
    Inventory newInv;
    Inventory defaultInv;

    @Before
    public void setUp() throws Exception {
        defaultInv = new Inventory();
        newInv = new Inventory(1, "testName", "testCom");

        assertNotNull(defaultInv);
        assertNotNull(newInv);
    }

    @After
    public void tearDown() throws Exception {
        defaultInv = null;
        newInv = null;
    }

    @Test
    public void nullConstructorTest(){
        assertTrue(defaultInv.getID() == -1);
        assertTrue(defaultInv.getName().equals("default"));
        assertTrue(defaultInv.getCompany().equals("default"));
    }

    @Test
    public void constructorTest(){
        assertTrue(newInv.getID() == 1);
        assertTrue(newInv.getName().equals("testName"));
        assertTrue(newInv.getCompany().equals("testCom"));
    }

    @Test
    public void getID() {
        assertTrue(newInv.getID() == 1);
        assertFalse(newInv.getID() == -1);
    }

    @Test
    public void getName() {
        assertTrue(newInv.getName().equals("testName"));
        assertFalse(newInv.getName().equals("default"));
    }

    @Test
    public void getCompany() {
        assertTrue(newInv.getCompany().equals("testCom"));
        assertFalse(newInv.getCompany().equals("default"));
    }

}