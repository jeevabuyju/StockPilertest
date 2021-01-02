package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class inventoryInfo extends AppCompatActivity {

    EditText slugid;
    EditText itemname;
    EditText category;
    EditText description;
    EditText quantity;
    EditText costprice;
    EditText sellingprice;
    Button add;
    Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_info);

        slugid = findViewById(R.id.slugid);
        itemname = findViewById(R.id.itemName);
        category = findViewById(R.id.category);
        description = findViewById(R.id.description);
        quantity = findViewById(R.id.quantity);
        costprice = findViewById(R.id.costprice);
        sellingprice = findViewById(R.id.sellingprice);
        add = findViewById(R.id.add);
        clear = findViewById(R.id.clear);

        Intent intent = getIntent();
        String id = intent.getStringExtra(inventory.id);
        slugid.setText(id);

        // Check id in DB
        try {
            final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
            if (id.length() == 0) {
                return;
            }
            Cursor c = db.rawQuery("SELECT * FROM inventory WHERE id='" + id + "';", null);
            if (c.moveToFirst()) {
                itemname.setText(c.getString(1));
                category.setText(c.getString(2));
                description.setText(c.getString(3));
                quantity.setText(c.getString(4));
                costprice.setText(c.getString(5));
                sellingprice.setText(c.getString(6));
                c.close();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}