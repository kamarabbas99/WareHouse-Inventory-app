package presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
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
public class ItemViewActivity extends AppCompatActivity implements AlertBox.AlertListener {
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

    //On click of the delete item button, a warning box is shown
    //If yes is selected onPositiveClick is called and the item is removed
    //If no is selected, then nothing happens
    public void deleteItem(View view){
        //Declares and shows warning to user
        DialogFragment warning = new AlertBox();
        warning.show(getSupportFragmentManager(), "warn");
    }

    public void addStock(View view) {

    }

    public void removeStock(View view) {

    }

    //On positive button click, the items amount is set to 0, and the page returns to stock view
    @Override
    public void onPositiveClick(DialogFragment dialog) {
        inventory.removeItem(item.getID(), item.getQuantity());
        finish();
    }
}