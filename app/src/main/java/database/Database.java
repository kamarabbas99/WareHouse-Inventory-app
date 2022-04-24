package database;

import java.util.ArrayList;

import objects.IDSO;
import objects.Item;

/*
This class is the fake database class and is only used in our unit testing.
 */
public class Database implements IDBLayer{
    private String name = "fakeDatabase";
    private ArrayList<Item> items;

    // Creates an array and populates with fake Item data
    public Database() {
        Item temp;
        items = new ArrayList<Item>();
        temp = new Item(1, "Item1", "lorem ipsum1", 50, "Metric1", 100);
        items.add(temp);
        temp = new Item(2, "Item2", "lorem ipsum1", 50, "Metric2", 100);
        items.add(temp);
        temp = new Item(3, "Item3", "lorem ipsum1", 50, "Metric3", 100);
        items.add(temp);
        temp = new Item(4, "Item4", "lorem ipsum1", 50, "Metric4", 100);
        items.add(temp);
        temp = new Item(5, "Item5", "lorem ipsum1", 50, "Metric5", 100);
        items.add(temp);
        temp = new Item(6, "Item6", "lorem ipsum1", 50, "Metric6", 100);
        items.add(temp);
    }

    // Adds an Item to the arraylist.
    // If the Item already exists in the arraylist, then just change the quantity.
    // If the Item does not already exist in the arraylist, the add it to the list and
    // set it's quantity to the provided quantity input parameter.
    public Item add(int id, int quantity) {
        // determine if the item exists in the arraylist
        Item toReturn = get(id);
        // case: Item already exists in the arraylist
        if (toReturn != null) {
            toReturn = new Item(toReturn.getID(), toReturn.getName(), toReturn.getDescription(), toReturn.getQuantity() + quantity,
                    toReturn.getQuantityMetric(), toReturn.getLowThreshold());
        }
        return toReturn;
    }


    // removes the quantity of the item from the arraylist
    public Item remove(int id, int quantity) {
        Item removeItem = get(id);
        // case: Item was found
        if (removeItem != null)
        {
            int prevQty = removeItem.getQuantity();
            // case: quantity change makes the quantity 0
            if (prevQty - quantity == 0)
            {
                delete(removeItem.getID());
            }
            // case: quantity change is still positive
            else if (prevQty - quantity > 0)
            {
                removeItem = new Item(removeItem.getID(), removeItem.getName(), removeItem.getDescription(), removeItem.getQuantity() - quantity,
                        removeItem.getQuantityMetric(), removeItem.getLowThreshold());
            }
            // case: quantity change does not become negative
        }
        return removeItem;

    }

    // retrieves the Item in the arraylist
    public Item get(int id) {
        Item r = null;
        for (Item item : items)
        {
            if (item.getID() == id)
            {
                r = item;
                break;
            }
        }

        return r;
    }

    // creates a new Item in the arraylist
    public int create(IDSO object)
    {
        int id = -1;
        // make sure the object is not null
        if (object != null)
        {
            // make sure the object is an instance of Item
            if (object instanceof Item)
            {
                Item itemToAdd = (Item) object;
                items.add(itemToAdd);
                id = itemToAdd.getID();
            }
        }
        return object.getID();
    }

    // deletes an existing Item in the arraylist
    public void delete(int id){
        // find existing item
        Item toReturn = get(id);
        // case: item was found
        if(toReturn!= null){
            items.remove(toReturn.getID());
        }
    }

    // retrieve all of the Items from the arraylist
    public Item[] getDB(){
        Item[] toReturn = null;
        if (items != null) {
            toReturn = new Item[items.size()];
            for (int i = 0; i < items.size(); i++) {
                toReturn[i] = items.get(i);

            }
        }
        return toReturn;
    }

    public void clearDB(){
        items=null;
    }

}
