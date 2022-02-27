package datastub;



import java.util.ArrayList;
import java.util.List;

import src.main.java.objects.Item;

public class Datastub
{
    private String name="fakeDatabase";
    private ArrayList<Item> items;


    public Datastub()
    {
        Item temp;
        items= new ArrayList<Student>();
        temp= new Item(1, "Item1", "lorem ipsum1",50,"Metric1",100);
        items.add(temp);
        temp= new Item(2, "Item1", "lorem ipsum1",50,"Metric2",100);
        items.add(temp);
        temp= new Item(3, "Item1", "lorem ipsum1",50,"Metric3",100);
        items.add(temp);
        temp= new Item(4, "Item1", "lorem ipsum1",50,"Metric4",100);
        items.add(temp);
        temp= new Item(5, "Item1", "lorem ipsum1",50,"Metric5",100);
        items.add(temp);
        temp= new Item(6, "Item1", "lorem ipsum1",50,"Metric6",100);
        items.add(temp);
        System.out.println("fakeDatabase created " );
    }


    public void AddItem(int id, int qty){

        for(int i=0;i<items.size();i++){
            if(item.get(i).getID()==id){
                item.get(i).setQuantity(item.get(i).getQuantity() + qty);
            }
        }


    }

    public void RemoveItem(int id, int qty){

        for(int i=0;i<items.size();i++){
            if(item.get(i).getID()==id){
                if(item.get(i).getQuantity() > qty){
                    item.get(i).setQuantity(item.get(i).getQuantity() - qty);
                }
                else{
                    item.get(i).setQuantity(0);
                }
            }
        }

    }

    public Item getItem(int id) {
        Item r=null;
        for (int i = 0; i < items.size(); i++) {
            if (item.get(i).getID() == id) {
                r = item.get(i);
            }
        }
        return r;
    }
        public void close ()
        {
            System.out.println("Database closed");
        }

}