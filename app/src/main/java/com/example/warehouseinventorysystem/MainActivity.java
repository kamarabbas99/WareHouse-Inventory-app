package com.example.warehouseinventorysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Called when user taps the "View Stock" Button
    //Creates a new Intent to move activities to Stock View, and immediately moves there
    public void stockViewSwitch(View view){
        Intent stockView = new Intent(this, StockViewActivity.class);
        startActivity(stockView);
    }
}