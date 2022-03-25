
package logic;


import static org.junit.Assert.*;

import database.IDBLayer;
import database.PersistenceException;
import objects.Item;

import org.junit.Before;
import org.junit.Test;
import database.Database;
import database.IDBLayer;

public class ItemAccesserTest {

    ItemAccesser im;

    @Before
    public void setUp()
    {
        im=new ItemAccesser();
        assertNotNull(im);

        ItemAccesser withCons=new ItemAccesser(new Database());
        assertNotNull(withCons);
    }

    @Test
    public void testCreateItem() {
        Item newItem=im.createItem("default","desc",2,"metric",5);
        assertNotNull(newItem);
        assertEquals(newItem.getName(),"default");
    }

    @Test
    public void testAddItem() {
        Item newItem=im.createItem("default","desc",2,"metric",5);
        Item changeQ=im.addItem(newItem.getID(), 3);
        assertNotEquals(changeQ.getQuantity(),2);
        assertEquals(changeQ.getQuantity(),5);
    }

    @Test
    public void testRemoveItem() {
        Item newItem=im.createItem("default","desc",2,"metric",5);
        Item changeQ=im.removeItem(newItem.getID(), 1);
        assertNotEquals(changeQ.getQuantity(),2);
        assertEquals(changeQ.getQuantity(),1);
    }

    @Test
    public void testGetItem() {
        Item newItem=im.createItem("default","desc",2,"metric",5);
        int id= newItem.getID();
        assertEquals(newItem.getID(),im.getItem(newItem.getID()).getID());
    }

    @Test
    public void testGetAllItems() {

        Item newItem=im.createItem("default","desc",2,"metric",5);
        assertEquals(im.getAllItems()[0].getName(),"default");

    }

    @Test (expected = PersistenceException.class)
    public void testRemoveAllItems() {
        Item newItem=im.createItem("default","desc",2,"metric",5);
        im.removeAllItems();
        im.removeAllItems();
    }
}