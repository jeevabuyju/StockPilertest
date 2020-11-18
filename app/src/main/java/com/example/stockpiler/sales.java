package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;


public class sales extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        // initialize viewById
        final AutoCompleteTextView id = findViewById(R.id.id);
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


        try {
            String[] ids;
            final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
            // check item in DB, if true UPDATE else INSERT
            Cursor c = db.rawQuery("SELECT id FROM inventory ;", null);
            ids = new String[c.getCount()];
            int i = 0;
            while (c.moveToNext()) {
                ids[i] = c.getString(0);
                Log.i("dbinfo", ids[i]);
                int position = c.getPosition();
                if (c.moveToNext()) {
                    i++;
                    c.moveToPosition(position);
                }
            }
            c.close();
            Log.i("dbinfo", Arrays.toString(ids));
            // Id adapter
            final ArrayAdapter<String> idadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ids);
            id.setAdapter(idadapter);
        } catch (Exception e) {
            Toast.makeText(sales.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findViewById(R.id.details).getVisibility()==View.VISIBLE){
                    clear();
                }else {
                    id.setText("");
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
                try {

                    // quantity check in db for productId
                    final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
                    Cursor c = db.rawQuery("SELECT * FROM inventory WHERE id='" + id.getText().toString().toUpperCase() + "';", null);
                    if (c.moveToFirst()) {
                        int qty = Integer.parseInt(c.getString(4));
                        if(qty<Integer.parseInt(quantity.getText().toString()) && Integer.parseInt(quantity.getText().toString())==1){
                            Toast.makeText(sales.this, "Out of Stock,Product Unavailable.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(qty<Integer.parseInt(quantity.getText().toString())) {
                            Toast.makeText(sales.this, "Out of Stock,Reduce Quantity.", Toast.LENGTH_SHORT).show();
                            c.close();
                        }
                    }
                    else{
                        Toast.makeText(sales.this,"Product Unavailable",Toast.LENGTH_SHORT).show();
                        clear();
                        return;
                    }

                    // validation & update amount
                    if (Integer.parseInt(quantity.getText().toString().trim())==0) {
                        totalamt.setText("");
                        Toast.makeText(sales.this,"Minimum Quantity is 1",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double total = (Double.parseDouble(sellingprice.getText().toString())) * (Double.parseDouble(quantity.getText().toString()));
                    totalamt.setText(Double.toString(total));
                }

                catch (NumberFormatException ignored){
                    totalamt.setText("");
                    // Toast.makeText(sales.this,"Minimum Quantity is 1",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(sales.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
                    // validation
                    if (id.getText().toString().trim().length()==0) {
                        Toast.makeText(sales.this,"ERROR, Please Enter the ProductId",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (Integer.parseInt(quantity.getText().toString().trim())==0) {
                        Toast.makeText(sales.this,"Minimum Quantity is 1",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // check item in DB, if true UPDATE else INSERT
                    Cursor c = db.rawQuery("SELECT * FROM inventory WHERE id='" + id.getText().toString().toUpperCase() + "';", null);
                    if (c.moveToFirst()) {
                        int prevqty = Integer.parseInt(c.getString(4));
                        if(prevqty>=Integer.parseInt(quantity.getText().toString())) {
                            int newqty = prevqty - Integer.parseInt(quantity.getText().toString());
                            db.execSQL("UPDATE inventory SET quantity ='" + newqty + "' WHERE id ='" + id.getText().toString().toUpperCase() + "';");
                            db.execSQL("INSERT INTO cart VALUES('" + id.getText().toString().toUpperCase() + "','" + itemname.getText() + "'," + quantity.getText() + "," + sellingprice.getText() + "," + totalamt.getText() + ");");
                            Toast.makeText(sales.this, "Product ADDED to Cart", Toast.LENGTH_SHORT).show();
                            clear();
                            c.close();
                        }
                        else if(prevqty<Integer.parseInt(quantity.getText().toString()) && Integer.parseInt(quantity.getText().toString())==1){
                            Toast.makeText(sales.this, "Out of Stock,Product Unavailable.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(sales.this, "Out of Stock,Reduce Quantity.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(sales.this,"Product Unavailable",Toast.LENGTH_SHORT).show();
                        clear();
                    }
                }
                catch (NumberFormatException ignored){
                    Toast.makeText(sales.this,"Minimum Quantity is 1",Toast.LENGTH_SHORT).show();
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
                itemname.setText("");
                sellingprice.setText("");
                quantity.setText("");
                totalamt.setText("");
                findViewById(R.id.details).setVisibility(View.INVISIBLE);
                id.setText("");
            }
        });


    }


    // Clear code
    public void clear(){
        ((TextView)findViewById(R.id.itemName)).setText("");
        ((EditText) findViewById(R.id.quantity)).setText("");
        ((TextView) findViewById(R.id.sellingprice)).setText("");
        findViewById(R.id.details).setVisibility(View.INVISIBLE);
        ((AutoCompleteTextView) findViewById(R.id.id)).setText("");
    }


}