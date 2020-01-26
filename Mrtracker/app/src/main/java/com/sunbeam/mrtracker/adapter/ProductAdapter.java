package com.sunbeam.mrtracker.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.sunbeam.mrtracker.R;
import com.sunbeam.mrtracker.model.Product;

import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Product> products;


    public ProductAdapter(Context context, ArrayList <Product> products) {
        this.context = context;
        this.products = products;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.recyclerview_product_items,null);

        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
            Product product = products.get(i);

            holder.textPrice1.setPaintFlags(holder.textPrice1.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
         // 192.168.2.10
            String url = "http://192.168.2.10:4000/";
            url = url + product.getImage();

            Ion.with(this.context).load(url).withBitmap().intoImageView(holder.imageView);

            holder.textName.setText(product.getName());
            holder.textPrice1.setText("MRP ₹"+product.getPrice());
            holder.textDiscount.setText("  "+product.getDiscount()+"% off");
            holder.textWithDiscount.setText("₹"+product.getPriceWithDiscount());

    }


    @Override
    public int getItemCount() {
        return products.size();
    }


     class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        TextView textName, textPrice1, textDiscount, textWithDiscount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.Image);
            textName = itemView.findViewById(R.id.name);
            textPrice1 = itemView.findViewById(R.id.textPrice);
            textDiscount = itemView.findViewById(R.id.textdiscount);
            textWithDiscount = itemView.findViewById(R.id.priceWithDiscount);
        }
    }



}

