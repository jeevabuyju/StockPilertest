package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;


public class cart extends AppCompatActivity {

    String[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView recycleCart = findViewById(R.id.recycleCart);

        try {
            // DB
            final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
            // check item in DB, if true UPDATE else INSERT
            Cursor c = db.rawQuery("SELECT id FROM cart ;", null);
            ids = new String[c.getCount()];
            int i = 0;
            while (c.moveToNext()) {
                ids[i] = c.getString(0);
                Log.i("dbinfos", ids[i]);
                int position = c.getPosition();
                if (c.moveToNext()) {
                    i++;
                    c.moveToPosition(position);
                }
            }
            c.close();
            Log.i("dbinfos", Arrays.toString(ids));
            cartAdapter cartAdapter = new cartAdapter(this, ids, db);
            recycleCart.setAdapter(cartAdapter);
            recycleCart.setLayoutManager(new LinearLayoutManager(this));
        } catch (Exception e) {
            Toast.makeText(cart.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}