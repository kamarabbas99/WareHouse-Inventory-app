package logic;

import android.provider.ContactsContract;

import java.sql.SQLOutput;

import database.IDBLayer;
import database.DatabaseManager;
import database.PersistenceException;
import objects.IDSO;
import objects.Inventory;

public class InventoryAccessor {
    private IDBLayer InventoryDB;

    // default constructor
    public InventoryAccessor() {
        this.InventoryDB = DatabaseManager.getInventoryPersistence();
    }

    // constructor
    public InventoryAccessor(IDBLayer db) {
        this.InventoryDB = db;
    }

    // method that creates a new Inventory using the given name and returns it
    public Inventory createInventory(String name) {
        Inventory newInv = null;

        try {
            newInv = new Inventory(-1, name);
            int id = InventoryDB.create(newInv);
            if (id < 0) { // if inventory with given name cannot be created
                newInv = null;
            } else {
                newInv = (Inventory) InventoryDB.get(id);
            }
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }

        if (newInv == null) {
            System.out.println("Null Inventory");
        }
        return newInv;
    }

    // method that returns the inventory with given id from the database if possible
    public Inventory getInventory(int id) {
        Inventory gotItem = null;

        try {
            gotItem = (Inventory) InventoryDB.get(id);
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }

        if (gotItem == null) { // if there is no inventory with given id
            System.out.println("Null Inventory");
        }
        return gotItem;
    }

    // method that deletes the inventory with given id
    public void deleteInventory(int id) {
        try {
            InventoryDB.delete(id);
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }

    }

    // method that returns all the inventories
    public Inventory[] getAllInventories() {
        try {
            IDSO[] invsAsIDSO = InventoryDB.getDB();
            Inventory[] inventories = new Inventory[invsAsIDSO.length];

            // stores all elements of invsAsIDSO in inventories after typecasting to
            // Inventory
            for (int i = 0; i < invsAsIDSO.length; i++) {
                inventories[i] = (Inventory) invsAsIDSO[i];
            }

            return inventories;
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
            return null;
        }

    }

    // method that deletes all inventories
    public void deleteAllInventories() {
            InventoryDB.clearDB();

    }

    // method that clears the inventory with given id
    public void clearInventory(int id) {
        try {
            IDBLayer InventoryManagerDB = DatabaseManager.getInventoryManagerPersistence();
            int prevActiveID = DatabaseManager.getActiveInventory();
            DatabaseManager.setActiveInventory(id);
            InventoryManagerDB.clearDB();
            DatabaseManager.setActiveInventory(prevActiveID);
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }

    }

    // method that returns the id of the active inventory
    public int getActiveID() {
        return DatabaseManager.getActiveInventory();
    }

    // sets the active inventory to be the inventory corresponding togiven id
    public void setActiveID(int id) {
        try {
            if (InventoryDB.get(id) != null) {
                DatabaseManager.setActiveInventory(id);
            }
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }
    }

}
