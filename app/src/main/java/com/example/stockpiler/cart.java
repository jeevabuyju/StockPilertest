package com.example.stockpiler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


public class cart extends AppCompatActivity {

    String[] item = {"a", "b", "c", "d", "e", "f"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView recycleCart = findViewById(R.id.recycleCart);

        cartAdapter cartAdapter = new cartAdapter(this, item);
        recycleCart.setAdapter(cartAdapter);
        recycleCart.setLayoutManager(new LinearLayoutManager(this));

    }
}