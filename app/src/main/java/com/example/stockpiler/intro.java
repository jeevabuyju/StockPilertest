package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;


public class intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // create DB and table
        final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user(username VARCHAR UNIQUE,password VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS inventory(id VARCHAR UNIQUE NOT NULL PRIMARY KEY, itemname VARCHAR NOT NULL, category VARCHAR , description VARCHAR, quantity INTEGER, costprice DECIMAL(6,2) NOT NULL, sellingprice DECIMAL(6,2) NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS cart(id VARCHAR, itemname VARCHAR NOT NULL, quantity INTEGER NOT NULL, sellingprice DECIMAL(6,2) NOT NULL, totalamt DECIMAL(6,2) NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS sales(id VARCHAR NOT NULL, quantity INTEGER NOT NULL, costprice DECIMAL(6,2) NOT NULL, sellingprice DECIMAL(6,2) NOT NULL, totalamt DECIMAL(6,2) NOT NULL, profit DECIMAL(6,2));");
        try {
            db.execSQL("INSERT INTO user VALUES('admin','admin');");
        } catch (SQLiteConstraintException ignored) {
        } catch (Exception e) {
            Toast.makeText(intro.this, e.toString(), Toast.LENGTH_LONG).show();
        }


        final Intent intent = new Intent(this, login.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 1500);

    }

    // Back button code
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}