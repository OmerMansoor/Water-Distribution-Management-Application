package com.example.water_distribution_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ExampleViewHolder>
{
    private Context context;
    private ArrayList<Orders> mData;
    private OrdersAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OrdersAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivBottleImage;
        private TextView tvVendorName, tvBottleType, tvPrice, tvStickQuant;

        public ExampleViewHolder(View itemView, final OrdersAdapter.OnItemClickListener listener) {
            super(itemView);

            ivBottleImage=itemView.findViewById(R.id.ivBottleImage);
            tvBottleType=itemView.findViewById(R.id.tvBottleSize);
            tvVendorName=itemView.findViewById(R.id.tvVendorName);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvStickQuant=itemView.findViewById(R.id.tvItemsInStock);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public OrdersAdapter(Context context, ArrayList<Orders> mData)
    {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public OrdersAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_vendor, parent, false);
        OrdersAdapter.ExampleViewHolder evh = new OrdersAdapter.ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(OrdersAdapter.ExampleViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Orders model = mData.get(position);
        
//
//        holder.tvVendorName.setText("Vendor Name: "+model.getOwner_name());
//        holder.tvBottleType.setText("Size: "+model.getType());
//        holder.tvStickQuant.setText("Stock Quantity: "+String.valueOf(model.getStockQuantity()));
//        holder.tvPrice.setText("Price: "+String.valueOf(model.getPrice()));
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
    public int getItemCount() {
        return mData.size();
    }
}
