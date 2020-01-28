package com.sunbeam.mrtracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.sunbeam.mrtracker.R;
import com.sunbeam.mrtracker.activity.ProductDetails;
import com.sunbeam.mrtracker.model.Product;
import com.sunbeam.mrtracker.utils.urls;

import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Product> products;


    public interface ContactAdapterActionListener {
        void onDetails(int i);
    }

    ContactAdapterActionListener listener;
    public ProductAdapter(Context context, ArrayList <Product> products, ContactAdapterActionListener listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.recyclerview_product_items,null);

        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
            final Product product = products.get(i);

            holder.textPrice1.setPaintFlags(holder.textPrice1.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
         // 192.168.2.10
            String url = urls.images();
            url = url + product.getImage();

            Ion.with(this.context).load(url).withBitmap().intoImageView(holder.imageView);

            holder.textName.setText(product.getName());
            holder.textPrice1.setText("MRP ₹"+product.getPrice());
            holder.textDiscount.setText("  "+product.getDiscount()+"% off");
            holder.textWithDiscount.setText("₹"+product.getPriceWithDiscount());


            holder.imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Log.e("ProductAdapter", "hello there prashant");
                   // Toast.makeText(v.getContext(),product.getName(),Toast.LENGTH_SHORT).show();

                    listener.onDetails(i);

                }
            });

    }



    @Override
    public int getItemCount() {
        return products.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder
    {

        LinearLayout imageView1;
        ImageView imageView;
        TextView textName, textPrice1, textDiscount, textWithDiscount;

           ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView1 = itemView.findViewById(R.id.Image1);
            imageView = itemView.findViewById(R.id.Image);
            textName = itemView.findViewById(R.id.name);
            textPrice1 = itemView.findViewById(R.id.textPrice);
            textDiscount = itemView.findViewById(R.id.textdiscount);
            textWithDiscount = itemView.findViewById(R.id.priceWithDiscount);
        }



    }



}

