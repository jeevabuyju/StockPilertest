package com.example.stockpiler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.cartViewHolder> {

    String[] data;
    Context context;

    public cartAdapter(Context ct, String[] item) {
        context = ct;
        data = item;
    }

    @NonNull
    @Override
    public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cartcard, parent, false);
        return new cartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cartViewHolder holder, int position) {
        holder.itemname.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class cartViewHolder extends RecyclerView.ViewHolder {

        TextView itemname;

        public cartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);

        }
    }
}
