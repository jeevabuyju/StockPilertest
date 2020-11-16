package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class sales extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        // initialize viewById
        final EditText id = findViewById(R.id.id);
        final TextView itemname = findViewById(R.id.itemName);
        final EditText quantity = findViewById(R.id.quantity);
        final TextView sellingprice = findViewById(R.id.sellingprice);
        final TextView totalamt=findViewById(R.id.totalamt);
        final Button qtyadd = findViewById(R.id.qtyadd);
        final Button qtysub = findViewById(R.id.qtysub);
        final Button addbtn = findViewById(R.id.add);
        final Button clear = findViewById(R.id.clear);



        // id Listener
        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // Check id in DB
                try {
                    final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
                    if (id.getText().toString().trim().length() == 0) {
                        return;
                    }
                    Cursor c = db.rawQuery("SELECT * FROM inventory WHERE id='" + id.getText().toString().trim().toUpperCase() + "';", null);
                    if (c.moveToFirst()) {
                        // change visibility
                        findViewById(R.id.details).setVisibility(View.VISIBLE);

                        itemname.setText(c.getString(1));
                        sellingprice.setText(c.getString(6));
                        // Double total = (Double.parseDouble(c.getString(6))) * (Double.parseDouble(quantity.getText().toString()));

                        c.close();
                    } else {
                        findViewById(R.id.details).setVisibility(View.INVISIBLE);
                    }
                }
                catch (Exception e){
                    Toast.makeText(sales.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });


        // Quantity ADD button code
        qtyadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int qty = 0;
                    if (quantity.getText().toString().length() != 0) {
                        qty = Integer.parseInt(quantity.getText().toString());
                    }
                    quantity.setText(String.valueOf(++qty));
                } catch (Exception e) {
                    Toast.makeText(sales.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });


        // Quantity SUB button code
        qtysub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int qty = 0;
                    if (quantity.getText().toString().length() != 0) {
                        qty = Integer.parseInt(quantity.getText().toString());
                        if(qty>0) {
                            quantity.setText(String.valueOf(--qty));
                        }
                        quantity.setText(String.valueOf(qty));
                    }
                    else{
                        quantity.setText(String.valueOf(qty));
                    }
                } catch (Exception e) {
                    Toast.makeText(sales.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

            // quantity listener
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // Check id in DB
                try {
                    if (Integer.parseInt(quantity.getText().toString().trim())==0) {
                        Toast.makeText(sales.this,"Minimum Quantity is 1",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double total = (Double.parseDouble(sellingprice.getText().toString())) * (Double.parseDouble(quantity.getText().toString()));
                    totalamt.setText(Double.toString(total));
                    }
                catch (NumberFormatException e){
                    return;
                }
                catch (Exception e){
                    Toast.makeText(sales.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

        // Clear code
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setText("");
                itemname.setText("");
                sellingprice.setText("");
                quantity.setText("");
                totalamt.setText("");
                findViewById(R.id.details).setVisibility(View.INVISIBLE);
            }
        });

    }
}