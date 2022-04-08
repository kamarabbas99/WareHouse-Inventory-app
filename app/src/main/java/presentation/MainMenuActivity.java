package presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.warehouseinventorysystem.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
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
        Intent addView = new Intent(this, ItemAddActivity.class);
        startActivity(addView);
    }
}