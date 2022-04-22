package presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.warehouseinventorysystem.R;

import logic.AccountAccessor;
import logic.InventoryAccessor;

//MAIN MENU ACTIVITY:
// The main view of an inventory, users can view stock of an inventory and add new items to the current active inventory
// When calling this activity, MUST bundle in an integer id, tagged with "invID"
public class MainMenuActivity extends AppCompatActivity implements AlertBox.AlertListener{

    //Gets current active inventory and user
    InventoryAccessor inventory = new InventoryAccessor();
    AccountAccessor account = new AccountAccessor();

    //Id and name of the current inventory instance
    int id;
    String invName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Gets the ID of the current active inventory
        id = getIntent().getIntExtra("invID", -1);
        invName = inventory.getInventory(id).getName();

        TextView textName = findViewById(R.id.currentInventory);
        textName.setText("Viewing " + invName);
    }

    //Called when user taps the "View Stock" Button
    //Creates a new Intent to move activities to Stock View, and immediately moves there
    public void stockViewSwitch(View view){
        Intent stockView = new Intent(this, StockViewActivity.class);
        startActivity(stockView);
    }

    //Called when user taps the "Add Item" Button
    //Creates a new Intent to move activities to ItemAddActivity, and immediately moves there
    public void addItemSwitch(View view) {
        //Checks if user is admin, if so opens the add item activity
        if(account.getCurrentPrivilege() == 0) {
            Intent addView = new Intent(this, ItemAddActivity.class);
            startActivity(addView);
        }
        //Else, error message is printed
        else {
            Messages.invalidClearance(this, "Please contact an administrator");
        }
    }

    //Moves to the report view for the current inventory
    public void viewReport(View view){
        //Checks if user is admin, if so opens the add item activity
        if(account.getCurrentPrivilege() == 0) {
            Intent report = new Intent(this, ReportViewActivity.class);

            //Puts the name, id, and type of item as extras
            report.putExtra("ID", id);
            report.putExtra("name", invName);
            report.putExtra("type", "inventory");

            //Starts the activity
            startActivity(report);
        }
        //Else, error message is printed
        else {
            Messages.invalidClearance(this, "Please contact an administrator");
        }
    }

    //Clears the current selected inventory
    public void clearInventory(View view) {
        //Checks if user is admin, if so clears the inventory
        if(account.getCurrentPrivilege() == 0) {
            DialogFragment warning = new AlertBox();
            warning.show(getSupportFragmentManager(), "warn");
        }
        //Else, error message is printed
        else {
            Messages.invalidClearance(this, "Please contact an administrator");
        }
    }

    //On positive dialog clear dialog box click, the inventory is cleared
    @Override
    public void onPositiveClick(DialogFragment dialog) {
        inventory.clearInventory(id);
    }
}