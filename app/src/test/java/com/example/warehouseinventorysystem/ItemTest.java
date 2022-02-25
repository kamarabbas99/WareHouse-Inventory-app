package com.example.warehouseinventorysystem;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import objects.Item;

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
        assertTrue(defaultItem.getID() == -1);
        assertNull(defaultItem.getName());
        assertNull(defaultItem.getDescription());
        assertTrue(defaultItem.getQuantity() == -1);
        assertNull(defaultItem.getQuantityMetric());
        assertTrue(defaultItem.getLowThreshold() == -1);
    }

    @Test
    public void constructorTest(){
        assertTrue(newItem.getID() == 1);
        assertTrue(newItem.getName().equals("test"));
        assertTrue(newItem.getDescription().equals("test description"));
        assertTrue(newItem.getQuantity() == 10);
        assertTrue(newItem.getQuantityMetric().equals("pkg"));
        assertTrue(newItem.getLowThreshold() == 2);
    }

    @Test
    public void getID() {
        assertTrue(newItem.getID() == 1);
        assertFalse(newItem.getID() == -1);
    }

    @Test
    public void getName() {
        assertTrue(newItem.getName().equals("test"));
        assertFalse(newItem.getName().equals("test0"));
    }

    @Test
    public void getDescription() {
        assertTrue(newItem.getDescription().equals("test description"));
        assertFalse(newItem.getDescription().equals("Test Description"));
    }

    @Test
    public void getQuantity() {
        assertTrue(newItem.getQuantity() == 10);
        assertFalse(newItem.getQuantity() == 0);
    }

    @Test
    public void getQuantityMetric() {
        assertTrue(newItem.getQuantityMetric().equals("pkg"));
        assertFalse(newItem.getQuantityMetric().equals("box"));
    }

    @Test
    public void getLowThreshold() {
        assertTrue(newItem.getLowThreshold() == 2);
        assertFalse(newItem.getLowThreshold() == 0);
    }

    @Test
    public void isEmpty() {
        assertTrue(defaultItem.isEmpty());
        assertFalse(newItem.isEmpty());
    }

    @Test
    public void isLow() {
        assertTrue(defaultItem.isLow());
        assertFalse(newItem.isLow());
    }
}