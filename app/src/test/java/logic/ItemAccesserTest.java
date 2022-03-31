
package logic;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import database.Database;
import objects.Item;


public class ItemAccesserTest {

    ItemAccesser im;

    @Before
    public void setUp()
    {
        im=new ItemAccesser(new Database());
        assertNotNull(im);


        //ItemAccesser withCons=new ItemAccesser(new Database());
        //assertNotNull(withCons);
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
        assertEquals(im.getAllItems()[0].getName(),"Item1");

    }

    /*
    @Test
    public void testRemoveAllItems() {
        Item newItem=im.createItem("default","desc",2,"metric",5);
        im.removeAllItems();
        assertNull(im.getAllItems());
    }
     */
}