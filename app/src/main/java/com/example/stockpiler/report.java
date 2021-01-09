package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class report extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // initialize ViewByID
        final TextView profit = findViewById(R.id.profitvalue);
        final TextView inventoryvalue = findViewById(R.id.inventoryvaluevalue);
        final TextView salesqty = findViewById(R.id.salesqtyvalue);
        final TextView inventoryqty = findViewById(R.id.inventoryqtyvalue);
        float profitval = 0;
        int salesqtyval = 0;
        int inventoryqtyval = 0;
        float inventoryval = 0;


        final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
        try {
            // check item in DB, if true UPDATE else INSERT
            Cursor cs = db.rawQuery("SELECT * FROM sales ;", null);
            while (cs.moveToNext()) {
                profitval = profitval + Float.parseFloat(cs.getString(5));
                salesqtyval = salesqtyval + Integer.parseInt(cs.getString(1));

                int position = cs.getPosition();
                if (cs.moveToNext()) {
                    cs.moveToPosition(position);
                }
            }
            cs.close();
            profit.setText(Float.toString(profitval));
            salesqty.setText(Integer.toString(salesqtyval));

            Cursor ci = db.rawQuery("SELECT * FROM inventory ;", null);
            while (ci.moveToNext()) {
                inventoryqtyval = inventoryqtyval + Integer.parseInt(ci.getString(4));
                float item_val = Float.parseFloat(ci.getString(4)) * Float.parseFloat(ci.getString(6));
                inventoryval = inventoryval + item_val;

                int position = ci.getPosition();
                if (ci.moveToNext()) {
                    ci.moveToPosition(position);
                }
            }
            ci.close();
            inventoryqty.setText(Integer.toString(inventoryqtyval));
            inventoryvalue.setText(Float.toString(inventoryval));

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}