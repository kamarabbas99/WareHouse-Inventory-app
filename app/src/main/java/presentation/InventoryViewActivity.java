package presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.warehouseinventorysystem.R;

import database.DatabaseManager;
import database.PersistenceException;
import logic.AccountAccessor;
import logic.InventoryAccessor;
import objects.Inventory;

public class InventoryViewActivity extends AppCompatActivity implements InventoryAdapter.OnInventoryListener, InventoryNameBox.NameListener{
    //Holds every inventory object currently in the database
    Inventory[] inventories;

    //Creates new inventory accessor and Account accessor
    InventoryAccessor inv = new InventoryAccessor();
    AccountAccessor account = new AccountAccessor();

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

    //Moves to page to create a new inventory
    public void createInvOnClick(View view){
        //Checks if the current account is an admin
        //If so opens dialog box to create new inventory
        if(account.getCurrentPrivilege() == 0) {
            DialogFragment newInv = new InventoryNameBox();
            newInv.show(getSupportFragmentManager(), "name");
        }

        //Else, shows error message
        else {
            Messages.invalidClearance(this, "Please ask an administrator");
        }
    }

    //Creates a new inventory with the name inputted by the user
    @Override
    public void onChangeClick(DialogFragment dialog, String name){
        try{
            //Creates new inventory with the entered name
            if(inv.createInventory(name) != null) {
                //Reloads the page to get the updates
                finish();
                startActivity(getIntent());
            }

            //If the name is already taken, an error is shown to user
            else{
                Messages.invalidName(this, "Inventory name", "Please enter a different name");
            }
        }

        catch(PersistenceException e){
            Messages.itemFailAdd(this, "Inventory", e.getMessage() + "\nPlease try restarting the application");
        }
    }

    //When an inventory is clicked, sets the current inventory
    @Override
    public void onInventoryClick(int index){
        Intent getMain = new Intent(this, MainMenuActivity.class);
        getMain.putExtra("invID", inventories[index].getID());
        DatabaseManager.setActiveInventory(inventories[index].getID());
        startActivity(getMain);
    }
}