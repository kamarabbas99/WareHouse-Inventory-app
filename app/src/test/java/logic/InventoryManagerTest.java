
package logic;


import static org.junit.Assert.*;

import database.IDBLayer;
import objects.Item;
import org.junit.Test;
import database.Database;
import database.IDBLayer;

public class InventoryManagerTest {


    @Test
    public void constructorT()
    {

        InventoryManager im=new InventoryManager();
        assertNotNull(im);
        IDBLayer ItemDB=null;
        InventoryManager withCons=new InventoryManager(ItemDB);
        assertNotNull(withCons);

    }

    @Test
    public void addItemT()
    {
        InventoryManager im=new InventoryManager();

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
        InventoryManager im=new InventoryManager();
        Item item1=im.getItem(1);
        int totalQuantity= item1.getQuantity();

        item1=im.removeItem(1,2);
        assertEquals(item1.getQuantity(),totalQuantity-2 );



    }

    @Test
    public void addMultipleItemsT()
    {
        InventoryManager im=new InventoryManager();
        Item item2=im.getItem(3);
        int totalQuantityI2= item2.getQuantity();
        Item[] i=im.addMultipleItems(new int[]{1,2,3}, new int[]{3,1,1});
        assertNotNull(im);
        assertEquals(i[0].getID(),1);
        assertEquals(i[1].getID(),2);
        assertEquals(i[2].getID(),3);
        assertEquals(i[1].getQuantity(),totalQuantityI2+1);


    }

    @Test
    public void removeMultipleItemsT()
    {
        InventoryManager im=new InventoryManager();
        Item item2=im.getItem(3);
        int totalQuantityI2= item2.getQuantity();
        Item[] i=im.removeMultipleItems(new int[]{2,3},new int[]{3,4});
        assertNotNull(im);
        assertEquals(i[0].getID(),2);
        assertEquals(i[1].getID(),3);
        assertEquals(i[1].getQuantity(),totalQuantityI2-4);

    }


    @Test
    public void getItemT()
    {
        InventoryManager im=new InventoryManager();
        Item i= im.getItem(2);
        assertEquals(i.getName(),"Item2");
        Item nullItem=im.getItem(90);
        assertNull(nullItem);

    }

    @Test
    public void viewInventoryT()
    {

        InventoryManager im=new InventoryManager();
        Item[] i=im.getInventory();
        assertEquals(i[0].getID(),1);
        assertEquals(i[1].getID(),2);
        assertEquals(i[2].getID(),3);
        assertEquals(i[3].getID(),4);
        Item check=i[0];
        assertEquals(check.getQuantity(),50);



    }


}