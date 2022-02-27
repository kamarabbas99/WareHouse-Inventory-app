package logic;

public class InventoryManager {

    private IDBLayer ItemDB;

    public InventoryManager() {
    }

    public Item addItem(int id, int amount) {
        Item addedItem = null;

        if (ItemDB.verifyID(id)) {
            addedItem = ItemDB.addItem(id, amount);
        }

        return addedItem;
    }

    public Item removeItem(int id, int amount) {
        Item removedItem = null;

        if (ItemDB.verifyID(id) && amount > 0) {
            removedItem = ItemDB.removeItem(id, amount);
        }

        return removedItem;
    }

    public Item[] addMultipleItems(int[] ids, int[] amounts) {
        Item[] addedItems = new Item[ids.length];

        for (int i = 0; i < ids.length; i++) {
            if (ItemDB.verifyID(ids[i]) && amounts[i] > 0) {
                addedItems[i] = ItemDB.addItem(ids[i], amounts[i]);
            }
        }

        return addedItems;
    }
}