package com.example.kisaangoi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GoiAdapter extends RecyclerView.Adapter<GoiAdapter.GoiViewHolder> {
    private final Context mContext;
    private final ArrayList<GoiDetail> goiDetails;

    public GoiAdapter(Context context, ArrayList<GoiDetail> details){
        this.goiDetails = details;
        this.mContext = context;
    }

    @NonNull
    @Override
    public GoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.goiitem, parent, false);
        return new GoiViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GoiViewHolder holder, int position) {
        holder.itemName.setText(goiDetails.get(position).getGroceryName());
        holder.itemPlace.setText(goiDetails.get(position).getGroceryPlace());
        holder.itemPrice.setText("₹" + goiDetails.get(position).getGroceryPrice());
    }

    @Override
    public int getItemCount() {
        return goiDetails.size();
    }

    public static class GoiViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPlace, itemPrice;
        public GoiViewHolder(@NonNull View itemView){
            super(itemView);
            itemName = itemView.findViewById(R.id.ItemNameView);
            itemPlace = itemView.findViewById(R.id.Itemplace);
            itemPrice = itemView.findViewById(R.id.Price);
        }
    }
}