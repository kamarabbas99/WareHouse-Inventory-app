
package logic;


import static org.junit.Assert.*;

import database.DatabaseManager;
import database.IDBLayer;
import objects.Item;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import database.Database;

public class InventoryManagerTest {
    InventoryManagerAccessor im;

    @Before
    public void constructorT()
    {

        im=new InventoryManagerAccessor(new Database());
        assertNotNull(im);

    }


    @Test
    public void createT()
    {
        Item get=im.createItem("test","desc",2,"met",2);
        assertEquals("desc",get.getDescription());
    }

    @Test
    public void addItemT()
    {

        Item item1=im.getItem(1);
        int totalQuantity= item1.getQuantity();
        item1=im.addItem(1,2);
        assertNotNull(item1);


        assertEquals(item1.getID(),1 );
        assertSame(item1,im.getItem(1));
        assertEquals(item1.getQuantity(),totalQuantity+2 );



    }


    @Test
    public void removeItemT()
    {
        Item item1=im.getItem(1);
        int totalQuantity= item1.getQuantity();

        item1=im.removeItem(1,2);
        assertEquals(item1.getQuantity(),totalQuantity-2 );



    }



    @Test
    public void getItemT()
    {
        Item i= im.getItem(2);
        assertEquals(i.getName(),"Item2");
        Item nullItem=im.getItem(90);
        assertNull(nullItem);

    }

    @Test
    public void getAllItemsTest()
    {
        assertEquals("Item2",im.getAllItems()[1].getName());
    }

    @Test
    public void removeAllItems()
    {
        im.removeAllItems();
        assertNull(im.getItem(2));
    }

}