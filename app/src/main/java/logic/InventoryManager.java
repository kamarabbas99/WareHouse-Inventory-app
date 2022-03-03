package logic;
import database.Database;
import database.IDBLayer;
import objects.Item;

public class InventoryManager {

    private IDBLayer ItemDB;

    public InventoryManager(IDBLayer db)
    {
        this.ItemDB = db;
    }
    public InventoryManager() {

        this.ItemDB= new Database();
    }

    public Item addItem(int id, int amount) {
        Item addedItem = null;

        if (ItemDB.verifyID(id)) {
            addedItem = (Item) ItemDB.addItem(id, amount);
        }

        return addedItem;
    }

    public Item removeItem(int id, int amount) {
        Item removedItem = null;

        if (ItemDB.verifyID(id) && amount > 0) {
            removedItem = (Item) ItemDB.removeItem(id, amount);
        }

        return removedItem;
    }

    public Item[] addMultipleItems(int[] ids, int[] amounts) {
        Item[] addedItems = new Item[ids.length];

        for (int i = 0; i < ids.length; i++) {
            if (ItemDB.verifyID(ids[i]) && amounts[i] > 0) {
                addedItems[i] = (Item) ItemDB.addItem(ids[i], amounts[i]);
            }
        }

        return addedItems;
    }

    public Item[] removeMultipleItems(int[] ids, int[] amounts)
    {
        Item[] removedItems = new Item[ids.length];

        for(int i=0;i<ids.length;i++)
            removedItems[i]= (Item) ItemDB.removeItem(ids[i],amounts[i]);

        return removedItems;

    }

    public Item getItem(int id){
        Item toGet = null;

        if (ItemDB.verifyID(id)) {
            toGet = (Item) ItemDB.get(id);
        }

        return toGet;
    }

    public Item[] getInventory(){
        return (Item[]) ItemDB.getDB();
    }
}
