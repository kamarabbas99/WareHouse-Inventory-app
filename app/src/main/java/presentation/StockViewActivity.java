package presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.warehouseinventorysystem.R;

import database.Database;
import logic.InventoryManager;
import objects.Item;

//Stock View Activity:
//  Shows a list of every item currently in the database and displays their data
//  Receives an array upon creation from database for every item
//  Uses RecycleView for dynamically adding objects to the view screen
//  Uses ItemsAdapter, which loops through very item and prints it to the screen dynamically
public class StockViewActivity extends AppCompatActivity implements ItemsAdapter.OnItemListener {
    //Holds every item object currently in the database
    Item[] items;

    //Creates new inventory manager
    InventoryManager inventory = new InventoryManager(new Database());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_view);

        //Gets the recycler view in stock activity layout
        RecyclerView itemRV = findViewById(R.id.itemRV);

        //Gets the list of all item objects in database
        items = inventory.getInventory();

        //Creates new item adapter and passes it the array of items
        ItemsAdapter adapter = new ItemsAdapter(items, this);

        //Sets recycle view layout to vertical scroll, and attached adapter to recycle view to display items
        itemRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        itemRV.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int index) {
        Intent getItemView = new Intent(this, ItemViewActivity.class);
        getItemView.putExtra("itemID", items[index].getID());
        startActivity(getItemView);
    }
}