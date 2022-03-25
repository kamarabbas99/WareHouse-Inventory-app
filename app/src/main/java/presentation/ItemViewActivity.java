package presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.warehouseinventorysystem.R;

import database.Database;
import logic.ItemAccesser;
import objects.Item;

//This activity displays all the information about a passed item object
//The amount of the item can be increased/decreased
//The item can also be deleted from the system entirely
public class ItemViewActivity extends AppCompatActivity {
    ItemAccesser inventory = new ItemAccesser();
    Item item;

    //Text views and buttons on page
    TextView itemName;
    TextView itemAmount;
    TextView itemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Gets the item according to the passed item id
        item = inventory.getItem(getIntent().getIntExtra("itemID", -1));

        setContentView(R.layout.activity_item_view);

        //Gets the ids of activity text and button elements
        itemName = this.findViewById(R.id.ItemWelcomeMessage);
        itemAmount = this.findViewById(R.id.itemAmount);
        itemDescription = this.findViewById(R.id.itemDesc);

        try {
            //Sets screen content based on the passed item
            itemName.append(item.getName());
            itemAmount.append(item.getQuantity() + " " + item.getQuantityMetric());
            itemDescription.setText(item.getDescription());
        }
        catch (Exception e){
            System.out.println("Id not found");
        }
    }

    //On click of the delete item button, the item is removed
    public void deleteItem(View view){
        inventory.removeItem(item.getID(), item.getQuantity());
    }

    public void addStock(View view) {

    }

    public void removeStock(View view) {

    }
}