package presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehouseinventorysystem.R;

import logic.TransactionAccessor;

//REPORT VIEW ACTIVITY:
// Shows the database transactions for a specific object in the system
// Currently can show transactions for:
//      --> Items
//      --> Inventories
//  Calls to this activity MUST bundle in:
//      --> Integer database id tagged with "ID"
//      --> String name of object tagged with "name"
//      --> String type of object tagged with "type" (currently only "item" and "inventory" are implemented)
public class ReportViewActivity extends AppCompatActivity {
    TransactionAccessor transactions = new TransactionAccessor();

    //Stores an objects id and name and type
    int id;
    String invName;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_view);

        //Gets passed variables
        id = getIntent().getIntExtra("ID", -1);
        invName = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");

        //Gets textview variables rom layout
        TextView transactionTitle = findViewById(R.id.transactionViewText);
        TextView transaction = findViewById(R.id.transactionText);

        //Assigns text for title
        transactionTitle.setText("Viewing report for " + invName);

        //Sets the transaction list to scroll and puts the text into the box
        transaction.setMovementMethod(new ScrollingMovementMethod());

        try {
            //Accesses the transactions depending on the type
            switch (type) {
                case "inventory":
                    transaction.setText(transactions.getInventoryTransactions(id));
                    break;
                case "item":
                    transaction.setText(transactions.getItemTransactions(id));
                    break;
            }
        }
        //If there are no transactions, prints an empty message
        catch(NullPointerException e){
            transaction.setText("There is nothing to report yet");
        }
    }

    //Override standard parent activity so that it can be dynamically selected depending on the page that called it
    @Override
    public Intent getSupportParentActivityIntent() { return getParentActivityIntentImpl(); }
    @Override
    public Intent getParentActivityIntent() { return getParentActivityIntentImpl(); }

    //Changes parent activity based on what type was passed
    private Intent getParentActivityIntentImpl(){
        Intent parent = null;

        switch (type) {
            case "inventory":
                parent = new Intent(this, MainMenuActivity.class);
                break;
            case "item":
                parent = new Intent(this, ItemViewActivity.class);
                parent.putExtra("itemID", id);
                break;
        }

        return parent;
    }
}