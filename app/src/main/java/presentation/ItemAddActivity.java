package presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.warehouseinventorysystem.R;

import database.Database;
import logic.ItemAccesser;


//This class allows a user to add a brand new item to the system
//User inputs Name, amount, quantity label, and description into EditText fields
//User can then hit an add button and the fields are taken into the system and passed to the logic layer to be added to the database
public class ItemAddActivity extends AppCompatActivity {
    ItemAccesser items = new ItemAccesser(new Database());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);
    }

    //Accessed upon the Create Item button being tapped
    //Assigns local labels to each of the EditText fields, then gets the text from them and creates a new item
    public void createItem(View view){
        EditText name = (EditText) findViewById(R.id.inputName);
        EditText amount = (EditText) findViewById(R.id.inputAmount);
        EditText qtyLabel = (EditText) findViewById(R.id.inputQty);
        EditText description = (EditText) findViewById(R.id.inputDescription);

        items.createItem(name.getText().toString(),description.getText().toString(), Integer.parseInt(amount.getText().toString()), qtyLabel.getText().toString(), -1);
    }
}