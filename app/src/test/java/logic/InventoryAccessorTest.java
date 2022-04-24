package logic;


import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.Before;
import org.junit.Test;

import database.DatabaseManager;
import database.InventoryPersistence;
import database.PersistenceException;
import objects.Inventory;

public class InventoryAccessorTest {

    InventoryAccessor ia;
    InventoryPersistence invPerMock;
    DatabaseManager dbMock;

    @Before
    public void setUp()
    {
        invPerMock=mock(InventoryPersistence.class);
        dbMock=mock(DatabaseManager.class);
        ia=new InventoryAccessor(invPerMock);
    }

    @Test
    public void testCreateInventory() {

        Inventory inv=new Inventory(1,"newInv");
        when(invPerMock.create(anyObject())).thenReturn(1);
        when(invPerMock.get(1)).thenReturn(inv);
        Inventory getFromFakeDB=ia.createInventory("newInv");
        assertEquals(getFromFakeDB.getID(),1);

    }

    @Test
    public void testGetInventory() {

        Inventory inv=new Inventory(1,"newInv2");
        when(invPerMock.get(1)).thenReturn(inv);
        assertEquals(ia.getInventory(1).getName(),"newInv2");
    }

    @Test
    public void testDeleteInventory() {
        int del=2;
        ia.deleteInventory(del);
        verify(invPerMock).delete(del);
    }

    @Test
    public void testGetAllInventories() {
        Inventory[] allInv={new Inventory(1,"newInv3")};
        when(invPerMock.getDB()).thenReturn(allInv);
        assertEquals(ia.getAllInventories()[0].getName(),"newInv3");
    }

    @Test
    public void testDeleteAllInventories() {
        ia.deleteAllInventories();
        verify(invPerMock).clearDB();
    }


    @Test
    public void testGetActiveID() {

        int temp=DatabaseManager.getActiveAccount();
        assertEquals(ia.getActiveID(),temp);

    }

    @Test
    public void testSetActiveID() {
        int temp=DatabaseManager.getActiveAccount();
        Inventory inv=new Inventory(1,"newInv5");
        when(invPerMock.get(1)).thenReturn(inv);
        ia.setActiveID(1);
        assertEquals(ia.getActiveID(),1);


        inv=new Inventory(temp,"newInv6");
        when(invPerMock.get(temp)).thenReturn(inv);
        ia.setActiveID(temp);


    }
}