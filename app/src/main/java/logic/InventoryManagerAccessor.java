package logic;

import database.IDBLayer;
import database.DatabaseManager;
import database.PersistenceException;
import objects.IDSO;
import objects.Item;

public class InventoryManagerAccessor {

    private IDBLayer ItemDB;

    // default constructor
    public InventoryManagerAccessor() {
        this.ItemDB = DatabaseManager.getInventoryManagerPersistence();
    }

    // constructor
    public InventoryManagerAccessor(IDBLayer db) {
        this.ItemDB = db;
    }

    // adds first instance of given item to database
    public Item createItem(String name, String description, int quantity, String quantityMetric, int lowThreshold) {
        Item newItem = new Item(-1, name, description, quantity, quantityMetric, lowThreshold);
        int id = ItemDB.create(newItem);
        newItem = (Item) ItemDB.get(id);
        if (newItem == null) {
            System.out.println("Null Item at id: " + id);
        }
        return newItem;
    }

    // method that adds item to the quantity
    public Item addItem(int id, int amount) {
        Item addedItem = (Item) ItemDB.add(id, amount);
        if (addedItem == null) {
            System.out.println("Null Item");
        }
        return addedItem;
    }

    // method that removes item from quantity
    public Item removeItem(int id, int amount) {
        Item removedItem = (Item) ItemDB.remove(id, amount);
        if (removedItem == null) {
            System.out.println("Null Item");
        }
        return removedItem;
    }

    // method that return the item requested
    public Item getItem(int id) {
        try {
            Item gotItem = (Item) ItemDB.get(id);
            if (gotItem == null) {
                System.out.println("Null Item");
            }
            return gotItem;
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    // method that return an array of all item in the system
    public Item[] getAllItems() {
        try {
            IDSO[] itemsAsIDSO = ItemDB.getDB();
            Item[] items = new Item[itemsAsIDSO.length];

            for (int i = 0; i < itemsAsIDSO.length; i++) {
                items[i] = (Item) itemsAsIDSO[i];
            }

            return items;
        }
        catch (final NullPointerException nullException) {
            nullException.printStackTrace();
            return null;
        }
    }

    // removes all items from active inventory
    public void removeAllItems() {
        try {
            ItemDB.clearDB();
        } catch (final PersistenceException exception) {
            exception.printStackTrace();
        }
    }
}
