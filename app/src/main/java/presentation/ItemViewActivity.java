package presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.warehouseinventorysystem.R;

import objects.Item;

//This activity displays all the information about a passed item object
//The amount of the item can be increased/decreased
//The item can also be deleted from the system entirely
public class ItemViewActivity extends AppCompatActivity {
    Item item;
    String test;

    //Text views and buttons on page
    TextView itemName;
    TextView itemAmount;
    TextView itemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Gets the passed object
        test = getIntent().getStringExtra("test");

        setContentView(R.layout.activity_item_view);

        //Gets the ids of activity text and button elements
        itemName = this.findViewById(R.id.ItemWelcomeMessage);
        itemAmount = this.findViewById(R.id.itemAmount);
        itemDescription = this.findViewById(R.id.itemDesc);

        //Sets screen content based on the passed item
        itemName.append(test);
        itemAmount.append(test + " " + test);
        itemDescription.setText(test);
    }
}