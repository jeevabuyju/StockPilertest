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

import java.util.List;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.ViewHolder> {

    SQLiteDatabase db;
    List<String> id;
    Context ct;

    public cartAdapter(List<String> ids, SQLiteDatabase dbs, Context cts) {
        this.id = ids;
        this.db = dbs;
        this.ct = cts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cartcard, parent, false);
        ViewHolder viewHolder;
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Check id in DB
        try {
            Cursor c = db.rawQuery("SELECT * FROM cart WHERE id='" + id.get(position) + "';", null);
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
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemname;
        TextView qty;
        TextView totalamt;
        TextView removebtn;
        Button qtyadd;
        Button qtysub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            qty = itemView.findViewById(R.id.qty);
            totalamt = itemView.findViewById(R.id.totalamt);
            removebtn = itemView.findViewById(R.id.removebtn);
            qtyadd = itemView.findViewById(R.id.qtyadd);
            qtysub = itemView.findViewById(R.id.qtysub);

            removebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        final String data = id.get(getAdapterPosition());
                        final int datanotify = getAdapterPosition();
                        Log.i("dbcheck", data + "," + datanotify);
                        boolean c = db.delete("cart", "id='" + data + "'", null) > 0;
                        if (c) {
                            Toast.makeText(v.getContext(), data + " Removed from Cart", Toast.LENGTH_SHORT).show();
                            id.remove(data);
                            notifyItemRemoved(datanotify);
                            if (ct instanceof cart) {
                                ((cart) ct).emptycart(id.size());
                            }
                        }
                    } catch (Exception e) {
                        Log.i("dbcheck", e.toString());
                    }
                }
            });
        }

    }
}
