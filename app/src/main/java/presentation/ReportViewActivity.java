package presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.warehouseinventorysystem.R;

import logic.TransactionAccessor;

public class ReportViewActivity extends AppCompatActivity {
    TransactionAccessor transactions = new TransactionAccessor();

    //Stores a inventories id and name
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

        //Accesses the transactions depending on the type
        switch (type) {
            case "inventory":
                transaction.setText(transactions.getInventoryTransactions(id));
                break;
            case "item":
                transaction.setText(transactions.getItemTransactions(id));
                break;
            case "account":
                transaction.setText(transactions.getAccountTransactions(id));
                break;
            default:
                transaction.setText(transactions.getAllTransactions());
                break;
        }
    }

    //Override standard parent activity so that it can be dynamically selected
    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl(); }
    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImpl(); }

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
            case "account":
                parent = null;
                break;
            default:
                parent = null;
                break;
        }

        return parent;
    }
}