package com.example.water_distribution_final_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BottlesAdapter extends RecyclerView.Adapter<BottlesAdapter.Viewholder>
{


    private Context context;
    private ArrayList<BottleMode> mData;

    // Constructor
    public BottlesAdapter(Context context, ArrayList<BottleMode> mData)
    {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public BottlesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new BottlesAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottlesAdapter.Viewholder holder, int position)
    {
        // to set data to textview and imageview of each card layout
        BottleMode model = mData.get(position);

        holder.tvVendorName.setText("Vendor Name: "+model.getOwner_name());
        holder.tvBottleType.setText("Size: "+model.getType());
        holder.tvStickQuant.setText("Stock Quantity: "+String.valueOf(model.getStockQuantity()));
        holder.tvPrice.setText("Price: "+String.valueOf(model.getPrice()));
        if(model.getType()=="small")
        {
            holder.ivBottleImage.setImageResource(R.drawable.small);
        }
        if(model.getType().equalsIgnoreCase("medium"))
        {
            holder.ivBottleImage.setImageResource(R.drawable.medium);
        }
        if(model.getType()=="large")
        {
            holder.ivBottleImage.setImageResource(R.drawable.large);
        }

    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder
    {

        private ImageView ivBottleImage;
        private TextView tvVendorName, tvBottleType, tvPrice, tvStickQuant;


        public Viewholder(@NonNull View itemView)
        {
            super(itemView);

            ivBottleImage=itemView.findViewById(R.id.ivBottleImage);
            tvBottleType=itemView.findViewById(R.id.tvBottleSize);
            tvVendorName=itemView.findViewById(R.id.tvVendorName);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvStickQuant=itemView.findViewById(R.id.tvItemsInStock);
        }
    }
}
