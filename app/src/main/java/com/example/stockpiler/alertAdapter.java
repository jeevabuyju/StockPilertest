package com.example.stockpiler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class alertAdapter extends RecyclerView.Adapter<alertAdapter.ViewHolder> {

    SQLiteDatabase db;
    List<String> id;
    Context ct;

    public alertAdapter(List<String> ids, SQLiteDatabase dbs, Context cts) {
        this.id = ids;
        this.db = dbs;
        this.ct = cts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.alertcard, parent, false);
        ViewHolder viewHolder;
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Check id in DB
        try {
            Cursor c = db.rawQuery("SELECT * FROM inventory WHERE id='" + id.get(position) + "';", null);
            if (c.moveToFirst()) {
                holder.itemId.setText(c.getString(0));
                holder.itemname.setText(c.getString(1));
                holder.qty.setText(c.getString(4));
                c.close();
            }
        } catch (Exception e) {
            Log.i("dbcheck", e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemname;
        TextView qty;
        TextView itemId;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            qty = itemView.findViewById(R.id.qty);
            itemId = itemView.findViewById(R.id.itemid);
        }

    }
}
