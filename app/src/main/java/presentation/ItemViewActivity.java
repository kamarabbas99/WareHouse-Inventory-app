package presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.warehouseinventorysystem.R;

import logic.InventoryManagerAccessor;
import objects.Item;

//This activity displays all the information about a passed item object
//The amount of the item can be increased/decreased
//The item can also be deleted from the system entirely
public class ItemViewActivity extends AppCompatActivity implements AlertBox.AlertListener, AmountBox.AmountListener {
    InventoryManagerAccessor inventory = new InventoryManagerAccessor();
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

    //On an add stock button press, a AmountBox with id 'add' is created and shown to user
    public void addStock(View view) {
        DialogFragment add = new AmountBox();
        add.show(getSupportFragmentManager(), "add");
    }

    //On an remove stock button press, a AmountBox with id 'remove' is created and shown to user
    public void removeStock(View view) {
        DialogFragment add = new AmountBox();
        add.show(getSupportFragmentManager(), "remove");
    }

    //On positive button click, the items amount is set to 0, and the page returns to stock view
    @Override
    public void onPositiveClick(DialogFragment dialog) {
        Intent back = new Intent(this, StockViewActivity.class);
        inventory.removeItem(item.getID(), item.getQuantity());
        startActivity(back);
    }

    //On a call from a AmountBox this method fires
    //Figures out what action needs to be performed based on the id of the box
    //      add --> the given amount integer needs to be added to the current items stock
    //      remove --> the given amount integer needs to be removed to the current items stock
    @Override
    public void onChangeClick(DialogFragment dialog, int amount) {
        //Adds item to current stock
        if(dialog.getTag().equals("add")){
            inventory.addItem(item.getID(), amount);
        }

        //Removes item from current stock
        else if(dialog.getTag().equals("remove")){
            inventory.removeItem(item.getID(), amount);
        }

        //Reloads the activity to have the page updated
        finish();
        startActivity(getIntent());
    }
}