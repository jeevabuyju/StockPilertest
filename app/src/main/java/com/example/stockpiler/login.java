package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize ViewByID
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);

        // login onClick
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // validation
                if (username.getText().toString().trim().length() == 0) {
                    Toast.makeText(login.this, "Access Denied, Please Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (password.getText().toString().trim().length() == 0){
                    Toast.makeText(login.this, "Access Denied, Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    if ((username.getText().toString().trim().equals("admin")) && (password.getText().toString().trim().equals("admin"))) {
                        Toast.makeText(login.this, "Access Granted, Welcome " + username.getText().toString().trim().toUpperCase() + " !!!", Toast.LENGTH_SHORT).show();
                    } else {
                        clear();
                        Toast.makeText(login.this, "Invalid Username And Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // DB check (to be integrated later)
//                final SQLiteDatabase db = openOrCreateDatabase("user", Context.MODE_PRIVATE,null);
//                Cursor c = db.rawQuery("SELECT * FROM user WHERE username='" + username.getText() + "' AND password='" + password.getText() + "';", null);
//                if(c.moveToFirst()){
//                    final Intent intent=new Intent(this,home.class);
//                    startActivity(intent);
//                    c.close();
//                } else{
//                    Toast.makeText(login.this, "Access Denied, Invalid Username And Password", Toast.LENGTH_SHORT).show();
//                }

            }
        });

    }


// Back button code
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(login.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void clear(){
        ((EditText) findViewById(R.id.username)).setText("");
        ((EditText) findViewById(R.id.password)).setText("");
    }

}