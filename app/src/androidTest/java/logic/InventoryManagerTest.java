package logic;

import junit.framework.TestCase;

import org.junit.Test;

public class InventoryManagerTest extends TestCase {

    @Test
    void constructorT()
    {
        InventoryManager im=new InventoryManager();
        assertNotNull(im);
    }

    @Test
    void addItemT()
    {
        InventoryManager im=new InventoryManager();
        Item item1;
        item1=im.addItem(1,2);
        assertNotNull(item1);

        //will confirm id and amount if we will have getter item Class
        //or once the view item is implemented


    }

    @Test
    void removeItemT()
    {
        InventoryManager im=new InventoryManager();
        Item item1=null;
        item1=im.removeItem(1,2);
        assertNull(item1);

        im.addItem(1,2);
        item1=im.removeItem(1,2);
        assertNotNull(im);
        //will confirm id and amount if we will have getter item Class

    }

    @Test
    void addMultipleItemsT()
    {
        InventoryManager im=new InventoryManager();
        //Item[] fewItems = {};


        //im.addMultipleItems()
    }

    @Test
    void removeMultipleItemsT()
    {


    }

    @Test
    void viewItem()
    {

    }

    @Test
    void viewInventory()
    {


    }


}