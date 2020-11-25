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


public class cart extends AppCompatActivity {

    List<String> ids;

    RecyclerView recyclerView;
    cartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.recycleCart);
        ids = new ArrayList<>();

        try {
            // DB
            final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
            // check item in DB, if true UPDATE else INSERT
            Cursor c = db.rawQuery("SELECT id FROM cart ;", null);
            while (c.moveToNext()) {
                ids.add(c.getString(0));
                Log.i("dbinfos", String.valueOf(ids));
                int position = c.getPosition();
                if (c.moveToNext()) {
                    c.moveToPosition(position);
                }
            }
            c.close();
            Log.i("dbinfos", String.valueOf(ids));
            cartAdapter = new cartAdapter(ids, db, this);
            recyclerView.setAdapter(cartAdapter);
        } catch (Exception e) {
            Toast.makeText(cart.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void emptycart(int size) {
        if (size == 0) {
            Toast.makeText(cart.this, "Cart is Empty", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}