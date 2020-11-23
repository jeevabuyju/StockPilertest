package com.example.stockpiler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.cartViewHolder> {

    String[] id;
    Context context;
    SQLiteDatabase db;

    public cartAdapter(Context ct, String[] ids, SQLiteDatabase dbs) {
        context = ct;
        id = ids;
        db = dbs;
    }

    @NonNull
    @Override
    public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cartcard, parent, false);
        return new cartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final cartViewHolder holder, final int position) {
        // Check id in DB
        try {
            Cursor c = db.rawQuery("SELECT * FROM cart WHERE id='" + id[position] + "';", null);
            if (c.moveToFirst()) {
                holder.itemname.setText(c.getString(0));
                holder.qty.setText(c.getString(2));
                double total = Double.parseDouble(c.getString(3)) * Double.parseDouble(c.getString(2));
                holder.totalamt.setText(String.valueOf(total));
                c.close();
            }
        } catch (Exception e) {
            Log.i("dbcheck", e.toString());
        }

        // Cart qtyadd
        holder.qtyadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "qtyadd", Toast.LENGTH_SHORT).show();
            }
        });

        // Cart qtysub
        holder.qtysub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "qtysub", Toast.LENGTH_SHORT).show();
            }
        });

        // Cart remove
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cursor c = db.rawQuery("DELETE FROM cart WHERE id='" + id[position] + "';", null);
                    if (c.moveToFirst()) {
                        Toast.makeText(v.getContext(), "Item Removed from Cart", Toast.LENGTH_SHORT).show();
                        c.close();
                    }
                } catch (Exception e) {
                    Log.i("dbcheck", e.toString());
                }
                Toast.makeText(v.getContext(), "Item Removed from Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.length;
    }

    public static class cartViewHolder extends RecyclerView.ViewHolder {

        TextView itemname;
        TextView qty;
        TextView totalamt;
        TextView remove;
        Button qtyadd;
        Button qtysub;

        public cartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            qty = itemView.findViewById(R.id.qty);
            totalamt = itemView.findViewById(R.id.totalamt);
            remove = itemView.findViewById(R.id.remove);
            qtyadd = itemView.findViewById(R.id.qtyadd);
            qtysub = itemView.findViewById(R.id.qtysub);

        }
    }
}
