package logic;
import database.Database;
import database.IDBLayer;
import objects.Item;

public class InventoryManager {

    private IDBLayer ItemDB;

    //default constructor
    public InventoryManager() {

        this.ItemDB= new Database();
    }

    //constructor
    public InventoryManager(IDBLayer db)
    {
        this.ItemDB = db;
    }

    //method that adds item to the quantity
    public Item addItem(int id, int amount) {
        Item addedItem = null;

        if (ItemDB.verifyID(id)) {
            addedItem = (Item) ItemDB.addItem(id, amount);
        }

        return addedItem;
    }

    //method that removes item from quantity
    public Item removeItem(int id, int amount) {
        Item removedItem = null;

        if (ItemDB.verifyID(id) && amount > 0) {
            removedItem = (Item) ItemDB.removeItem(id, amount);
        }

        return removedItem;
    }

    //method that add multiple item amount to the quantity together
    public Item[] addMultipleItems(int[] ids, int[] amounts) {
        Item[] addedItems = new Item[ids.length];

        for (int i = 0; i < ids.length; i++) {
            if (ItemDB.verifyID(ids[i]) && amounts[i] > 0) {
                addedItems[i] = (Item) ItemDB.addItem(ids[i], amounts[i]);
            }
        }

        return addedItems;
    }

    //method that removes multiple item amount to the quantity together
    public Item[] removeMultipleItems(int[] ids, int[] amounts)
    {
        Item[] removedItems = new Item[ids.length];

        for(int i=0;i<ids.length;i++)
            removedItems[i]= (Item) ItemDB.removeItem(ids[i],amounts[i]);

        return removedItems;

    }

    //method that return the item requested
    public Item getItem(int id){
        Item toGet = null;

        if (ItemDB.verifyID(id)) {
            toGet = (Item) ItemDB.get(id);
        }

        return toGet;
    }

    //method that return an array of all item in the system
    public Item[] getInventory(){
        return (Item[]) ItemDB.getDB();
    }
}
