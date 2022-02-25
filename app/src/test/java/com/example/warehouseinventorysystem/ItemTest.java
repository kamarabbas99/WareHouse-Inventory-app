package com.example.warehouseinventorysystem;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ItemTest {
    Item newItem;
    Item defaultItem;

    @Before
    public void setUp() throws Exception {
        defaultItem = new Item();
        newItem = new Item(1, "test", "test description", 10, "pkg", 2);

        assertNotNull(defaultItem);
        assertNotNull(newItem);
    }

    @After
    public void tearDown() throws Exception {
        defaultItem = null;
        newItem = null;
    }

    @Test
    public void nullConstructorTest(){
        assertTrue(defaultItem.GetID() == -1);
        assertNull(defaultItem.GetName());
        assertNull(defaultItem.GetDescription());
        assertTrue(defaultItem.GetQuantity() == -1);
        assertNull(defaultItem.GetQuantityMetric());
        assertTrue(defaultItem.GetLowThreshold() == -1);
    }

    @Test
    public void constructorTest(){
        assertTrue(newItem.GetID() == 1);
        assertTrue(newItem.GetName().equals("test"));
        assertTrue(newItem.GetDescription().equals("test description"));
        assertTrue(newItem.GetQuantity() == 10);
        assertTrue(newItem.GetQuantityMetric().equals("pkg"));
        assertTrue(newItem.GetLowThreshold() == 2);
    }

    @Test
    public void getID() {
        assertTrue(newItem.GetID() == 1);
        assertFalse(newItem.GetID() == -1);
    }

    @Test
    public void getName() {
        assertTrue(newItem.GetName().equals("test"));
        assertFalse(newItem.GetName().equals("test0"));
    }

    @Test
    public void getDescription() {
        assertTrue(newItem.GetDescription().equals("test description"));
        assertFalse(newItem.GetDescription().equals("Test Description"));
    }

    @Test
    public void getQuantity() {
        assertTrue(newItem.GetQuantity() == 10);
        assertFalse(newItem.GetQuantity() == 0);
    }

    @Test
    public void getQuantityMetric() {
        assertTrue(newItem.GetQuantityMetric().equals("pkg"));
        assertFalse(newItem.GetQuantityMetric().equals("box"));
    }

    @Test
    public void getLowThreshold() {
        assertTrue(newItem.GetLowThreshold() == 2);
        assertFalse(newItem.GetLowThreshold() == 0);
    }

    @Test
    public void isEmpty() {
        assertTrue(defaultItem.IsEmpty());
        assertFalse(newItem.IsEmpty());
    }

    @Test
    public void isLow() {
        assertTrue(defaultItem.IsLow());
        assertFalse(newItem.IsLow());
    }
}