package presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.warehouseinventorysystem.R;

import logic.AccountAccessor;

public class CreateAccountActivity extends AppCompatActivity {
    AccountAccessor accounts = new AccountAccessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }
}