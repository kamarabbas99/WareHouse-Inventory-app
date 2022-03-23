package database;

import java.util.ArrayList;

import objects.IDSO;
import objects.Item;

public class Database implements IDBLayer{
    private String name = "fakeDatabase";
    private ArrayList<Item> items;


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


        System.out.println("fakeDatabase created ");
    }


    public Item add(int id, int quantity) {
        Item toReturn = null;
        int ind = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getID() == id) {
                ind = i;
            }
        }
        if (ind != -1) {
            int newID = items.get(ind).getID();
            String newName = items.get(ind).getName();
            String newDesc = items.get(ind).getDescription();
            int newQuan = items.get(ind).getQuantity() + quantity;
            String newQuanMet = items.get(ind).getQuantityMetric();
            int newThres = items.get(ind).getLowThreshold();
            toReturn = new Item(newID, newName, newDesc, newQuan, newQuanMet, newThres);
            items.remove(ind);
            items.add(toReturn);
        }

        return toReturn;

    }


    public Item remove(int id, int quantity) {

        return add(id, -quantity);

    }

    public Item get(int id) {
        Item r=null;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getID() == id) {
                r = items.get(i);
            }
        }
        return r;
    }

    public int create(IDSO object){
        items.add((Item) object);

        return object.getID();
    }
    public void delete(int id){
        Item toReturn = null;
        int ind = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getID() == id) {
                ind = i;
            }
        }
        if(ind!=-1){
            toReturn= items.get(ind);
            items.remove(ind);
        }
    }
    public Item[] getDB(){
        Item[] toReturn = new Item[items.size()];
        for (int i = 0; i < items.size(); i++) {
            toReturn[i]= items.get(i);

        }
        return toReturn;
    }
    public void clearDB(){
        items=null;
        System.out.println("Cleared the databse!");
    }

}