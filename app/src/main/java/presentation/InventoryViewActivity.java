package presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.warehouseinventorysystem.R;

import database.DatabaseManager;
import database.PersistenceException;
import logic.InventoryAccessor;
import objects.Inventory;

public class InventoryViewActivity extends AppCompatActivity implements InventoryAdapter.OnInventoryListener{
    //Holds every inventory object currently in the database
    Inventory[] inventories;

    //Creates new inventory manager
    InventoryAccessor inv = new InventoryAccessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_view);

        try{
            //Gets the recycler view instance in inventory view layout
            RecyclerView inventoryRV = findViewById(R.id.InventoryRV);

            //Gets the list of all inventory objects in the system
            inventories = inv.getAllInventories();

            //Creates new inventory adapter and passes it the array of inventories
            InventoryAdapter adapter = new InventoryAdapter(inventories, this);

            //Sets recycle view layout to vertical scroll and attaches adapter
            inventoryRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            inventoryRV.setAdapter(adapter);
        }
        catch(PersistenceException e) {
            Messages.itemNotFound(this, e.getMessage());
        }
    }

    public void createInvOnClick(View view){

    }

    //When an inventory is clicked, sets the current inventory
    @Override
    public void onInventoryClick(int index){
        Intent getMain = new Intent(this, MainMenuActivity.class);
        DatabaseManager.setActiveInventory(inventories[index].getID());
        startActivity(getMain);
    }
}