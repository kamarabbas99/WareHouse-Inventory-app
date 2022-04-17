package presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.warehouseinventorysystem.R;

import logic.AccountAccessor;
import logic.InventoryAccessor;

public class MainMenuActivity extends AppCompatActivity implements AlertBox.AlertListener{

    //Gets current active inventory and user
    InventoryAccessor inventory = new InventoryAccessor();
    AccountAccessor account = new AccountAccessor();

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Gets the ID of the current active inventory
        id = getIntent().getIntExtra("invID", -1);
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

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        Intent back = new Intent(this, InventoryViewActivity.class);
        //TODO Insert clear call
        startActivity(back);
    }
}