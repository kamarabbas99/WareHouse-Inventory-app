package logic;

import database.IDBLayer;
import objects.IDSO;
import objects.Item;

public class ItemAccesser {

    private IDBLayer ItemDB;

    // default constructor needs to be updated after the singleton class for getting ItemPersistence instance is complete

    // constructor
    public ItemAccesser(IDBLayer db) {
        this.ItemDB = db;
    }

    // adds first instance of given item to database
    public int createItem(Item toCreate) {
        return ItemDB.create(toCreate);
    }

    // method that adds item to the quantity
    public Item addItem(int id, int amount) {
        Item addedItem = null;

        if (ItemDB.verifyID(id)) {
            addedItem = (Item) ItemDB.add(id, amount);
        }

        return addedItem;
    }

    // method that removes item from quantity
    public Item removeItem(int id, int amount) {
        Item removedItem = null;

        if (ItemDB.verifyID(id) && amount > 0) {
            removedItem = (Item) ItemDB.remove(id, amount);
        }

        return removedItem;
    }

    // method that return the item requested
    public Item getItem(int id) {
        Item toGet = null;

        if (ItemDB.verifyID(id)) {
            toGet = (Item) ItemDB.get(id);
        }

        return toGet;
    }

    // method that return an array of all item in the system
    public Item[] getAllItems() {
        IDSO[] itemsAsIDSO = ItemDB.getDB();
        Item[] items = new Item[itemsAsIDSO.length];

        for (int i = 0; i < itemsAsIDSO.length; i++) {
            items[i] = (Item) itemsAsIDSO[i];
        }

        return items;
    }

    // removes all items from active inventory
    public void removeAllItems() {
        ItemDB.clearDB();
    }
}
