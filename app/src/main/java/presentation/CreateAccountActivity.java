package presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.warehouseinventorysystem.R;

import database.PersistenceException;
import logic.AccountAccessor;

public class CreateAccountActivity extends AppCompatActivity implements AccountConfirmationBox.AccountListener{
    AccountAccessor accounts = new AccountAccessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void createAccount(View view){
        try {
            DialogFragment created = new AccountConfirmationBox();

            //Sets clearance to the lowest value, only changed if admin checkbox is checked
            int clearance = 1;

            //Gets text and checkbox fields from layout
            EditText name = (EditText) findViewById(R.id.inputUsername);
            EditText password = (EditText) findViewById(R.id.inputPasword);
            EditText verify = (EditText) findViewById(R.id.verifyPassword);
            CheckBox admin = findViewById(R.id.isAdmin);

            //Checks if the two password boxes match
            if(verify.getText().toString().equals(password.getText().toString())) {
                //Checks if the new user will be an admin, if so changes its clearance
                if (admin.isChecked()) {clearance = 0;}

                //Adds user to the system
                accounts.createAccount(name.getText().toString(), password.getText().toString(), clearance);

                //Shows success dialog box
                created.show(getSupportFragmentManager(), "created");
            }

            //Passwords do not match, shows error to user and asks them to try again
            else{
                Messages.matchPassword(this, "Please look at passwords and try again");
            }
        }

        //In case of critical database error, user is asked to restart application
        catch(PersistenceException e){
            Messages.itemFailAdd(this, "Account",e.getMessage() + "\nPlease try restarting the application");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onPositiveClick(DialogFragment dialog){
        finish();
    }
}