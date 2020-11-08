package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class purchase extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        // initialize ViewByID
        final EditText id = findViewById(R.id.slugid);
        final EditText itemname = findViewById(R.id.itemName);
        final EditText category = findViewById(R.id.category);
        final EditText description = findViewById(R.id.description);
        final EditText quantity = findViewById(R.id.quantity);
        final EditText costprice = findViewById(R.id.costprice);
        final EditText sellingprice = findViewById(R.id.sellingprice);
        Spinner spinner = findViewById(R.id.categoryspinner);
        final Button qtyadd = findViewById(R.id.qtyadd);
        final Button qtysub = findViewById(R.id.qtysub);
        final Button add = findViewById(R.id.add);
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
                    Cursor c = db.rawQuery("SELECT * FROM inventory WHERE id='" + id.getText() + "';", null);
                    if (c.moveToFirst()) {
                        id.setText(c.getString(0));
                        itemname.setText(c.getString(1));
                        category.setText(c.getString(2));
                        description.setText(c.getString(3));
                        costprice.setText(c.getString(5));
                        sellingprice.setText(c.getString(6));
                        c.close();
                    }
                    Toast.makeText(purchase.this,"Partial success",Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(purchase.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });


        // ADD Button, Insert,Update into Table Inventory
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(purchase.this,"Partial success",Toast.LENGTH_LONG).show();

            }
        });


        // Category Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


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
                    Toast.makeText(purchase.this, e.toString(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(purchase.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });


        // Clear code
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(R.id.slugid)).setText("");
                ((EditText) findViewById(R.id.itemName)).setText("");
                ((EditText) findViewById(R.id.category)).setText("");
                ((EditText) findViewById(R.id.description)).setText("");
                ((EditText) findViewById(R.id.quantity)).setText("");
                ((EditText) findViewById(R.id.costprice)).setText("");
                ((EditText) findViewById(R.id.sellingprice)).setText("");
            }
        });

    }


    // Spinner code
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final EditText category = findViewById(R.id.category);
        if(position>0) {
            String categoryItem = parent.getItemAtPosition(position).toString();
            category.setText(categoryItem);
        }
    }

    // Spinner code
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(purchase.this, "Select Category", Toast.LENGTH_SHORT).show();
    }

}