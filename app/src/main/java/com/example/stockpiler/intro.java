package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;


public class intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // create DB and table
        final SQLiteDatabase db = openOrCreateDatabase("stockpilerDB", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user(username VARCHAR UNIQUE,password VARCHAR);");
        try {
            db.execSQL("INSERT INTO user VALUES('admin','admin');");
        }
        catch (Exception e){}



        final Intent intent=new Intent(this,login.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        },1500);

    }

    // Back button code
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}