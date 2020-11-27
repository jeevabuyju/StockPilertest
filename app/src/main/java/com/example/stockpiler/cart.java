package com.example.stockpiler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class cart extends AppCompatActivity {

    List<String> ids;

    RecyclerView recyclerView;
    cartAdapter cartAdapter;
    TextView netamt;
    Button checkout;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.recycleCart);
        checkout = findViewById(R.id.checkout);
        ids = new ArrayList<>();
        final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
        total();

        try {
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


        // Checkout Button
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder(cart.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Confirm SALES!!");
                builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            String id;
                            int qty;
                            double cp, sp, totalamt, profit, cptot;
                            // check item in DB, if true UPDATE else INSERT
                            Cursor c = db.rawQuery("SELECT * FROM cart ;", null);
                            boolean ins;
                            while (c.moveToNext()) {
                                id = c.getString(0);
                                qty = Integer.parseInt(c.getString(2));
                                sp = Double.parseDouble(c.getString(3));
                                totalamt = Double.parseDouble(c.getString(4));
                                cp = getcp(id);
                                cptot = qty * cp;
                                profit = totalamt - cptot;

                                ContentValues cv = new ContentValues();
                                cv.put("id", id);
                                cv.put("quantity", qty);
                                cv.put("costprice", cp);
                                cv.put("sellingprice", sp);
                                cv.put("totalamt", totalamt);
                                cv.put("profit", profit);

                                ins = db.insert("sales", null, cv) >= 0;

                                int position = c.getPosition();
                                if (c.moveToNext()) {
                                    c.moveToPosition(position);
                                }

                                if (!ins) {
                                    throw new Exception("insert table error");
                                }
                            }
                            boolean del = db.delete("cart", null, null) > 0;
                            if (del) {
                                Toast.makeText(cart.this, "SOLD!!", Toast.LENGTH_SHORT).show();
                                // salescheck();
                                finish();
                            }
                            c.close();

                        } catch (Exception e) {
                            Toast.makeText(cart.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });


    }

    public void emptycart(int size) {
        if (size == 0) {
            Toast.makeText(cart.this, "Cart is Empty", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void total() {
        try {
            netamt = findViewById(R.id.netamt);
            // DB
            final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
            double tot = 0;
            // check item in DB, if true UPDATE else INSERT
            Cursor c = db.rawQuery("SELECT totalamt FROM cart ;", null);
            while (c.moveToNext()) {
                tot = tot + Double.parseDouble(c.getString(0));
                int position = c.getPosition();
                if (c.moveToNext()) {
                    c.moveToPosition(position);
                }
            }
            c.close();
            netamt.setText(String.valueOf(tot));
        } catch (Exception e) {
            Log.i("catinfo", e.toString());

            Toast.makeText(cart.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public double getcp(String id) {
        // DB
        double rt;
        final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
        Cursor ci = db.rawQuery("SELECT costprice FROM inventory WHERE id ='" + id + "';", null);
        if (ci.moveToFirst()) {
            rt = Double.parseDouble(ci.getString(0));
            return rt;
        }
        ci.close();
        return 0;
    }

//    public void salescheck(){
//        try {
//            final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
//            String[] ids;
//            // check item in DB, if true UPDATE else INSERT
//            Cursor cl = db.rawQuery("SELECT * FROM sales ;", null);
//            ids = new String[cl.getCount()];
//            int i = 0;
//            while (cl.moveToNext()) {
//                ids[i] = cl.getString(0)+" | "+cl.getString(1)+" | "+cl.getString(2)+" | "+cl.getString(3)+" | "+cl.getString(4)+" | "+cl.getString(5);
//                Log.i("salescheck", ids[i]);
//                int position = cl.getPosition();
//                if (cl.moveToNext()) {
//                    i++;
//                    cl.moveToPosition(position);
//                }
//            }
//            cl.close();
//        } catch (Exception e) {
//            Toast.makeText(cart.this, e.toString(), Toast.LENGTH_LONG).show();
//        }
//    }

}