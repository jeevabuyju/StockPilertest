package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class inventoryInfo extends AppCompatActivity {

    EditText slugid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_info);
        Intent intent = getIntent();
        String id = intent.getStringExtra(inventory.id);
        slugid = findViewById(R.id.slugid);
        slugid.setText(id);
    }
}