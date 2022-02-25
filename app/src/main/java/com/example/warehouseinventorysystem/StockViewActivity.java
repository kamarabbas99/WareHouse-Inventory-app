package com.example.warehouseinventorysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//Stock View Activity:
//  Shows a list of every item currently in the database and displays their data
//  Receives an array upon creation from database for every item, loops through it and dynamically adds data for each at runtime
//  Uses RecycleView for dynamic scroll
public class StockViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_view);
    }
}