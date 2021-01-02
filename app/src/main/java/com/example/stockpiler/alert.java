package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class alert extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> ids;
    alertAdapter alertAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        //alert adapter code
        recyclerView = findViewById(R.id.recycleAlert);
        ids = new ArrayList<>();
        final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
        try {
            // check item in DB, if true UPDATE else INSERT
            Cursor c = db.rawQuery("SELECT id FROM inventory where quantity<5 ;", null);
            if (c.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "Items in Stock", Toast.LENGTH_SHORT).show();
                finish();
            }
            while (c.moveToNext()) {
                ids.add(c.getString(0));
                Log.i("dbalertinfos", String.valueOf(ids));
                int position = c.getPosition();
                if (c.moveToNext()) {
                    c.moveToPosition(position);
                }
            }
            c.close();
            Log.i("dbalertinfos", String.valueOf(ids));
            alertAdapter = new alertAdapter(ids, db, this);
            recyclerView.setAdapter(alertAdapter);
        } catch (Exception e) {
            Toast.makeText(alert.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}