package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class inventory extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> ids;
    inventoryAdapter inventoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        //inventory adapter code
        recyclerView = findViewById(R.id.recycleInventory);
        ids = new ArrayList<>();
        final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
        try {
            // check item in DB, if true UPDATE else INSERT
            Cursor c = db.rawQuery("SELECT id FROM inventory ;", null);
            while (c.moveToNext()) {
                ids.add(c.getString(0));
                Log.i("dbinvinfos", String.valueOf(ids));
                int position = c.getPosition();
                if (c.moveToNext()) {
                    c.moveToPosition(position);
                }
            }
            c.close();
            Log.i("dbinvinfos", String.valueOf(ids));
            inventoryAdapter = new inventoryAdapter(ids, db, this);
            recyclerView.setAdapter(inventoryAdapter);
        } catch (Exception e) {
            Toast.makeText(inventory.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}