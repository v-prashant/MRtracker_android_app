package com.sunbeam.mrtracker.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.sunbeam.mrtracker.R;
import com.sunbeam.mrtracker.activity.Cart;
import com.sunbeam.mrtracker.model.Mycart;
import com.sunbeam.mrtracker.model.Product;
import com.sunbeam.mrtracker.utils.urls;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Mycart> products;


    public interface ContactAdapterActionListener1 {
        void onDetails(int i);
    }

    ContactAdapterActionListener1 listener;
    public CartAdapter(Context context, ArrayList <Mycart> products, ContactAdapterActionListener1 listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.cart_items,null);

        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        final Mycart product = products.get(i);


        String url = urls.images();
        url = url + product.getImage();

        Ion.with(this.context).load(url).withBitmap().intoImageView(holder.imageView);

        holder.textName.setText(product.getName());
        holder.textQuantity.setText(""+product.getQuantity());
        holder.textShaving.setText("₹"+product.getTotalDiscount()+"/-");
        holder.textTotalAmount.setText("₹"+product.getTotalAmount()+"/-");


        holder.onEdit.setOnClickListener(new View.OnClickListener() {
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

        Button onEdit;
        LinearLayout imageView1;
        ImageView imageView;
        TextView textName,textShaving,textTotalAmount,textQuantity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

          //  imageView1 = itemView.findViewById(R.id.Image1);
            onEdit = itemView.findViewById(R.id.edit);
            imageView = itemView.findViewById(R.id.Image);
            textName = itemView.findViewById(R.id.name);

            textQuantity = itemView.findViewById(R.id.Quantity);
            textShaving = itemView.findViewById(R.id.saving);
            textTotalAmount = itemView.findViewById(R.id.amount);
        }



    }

}
