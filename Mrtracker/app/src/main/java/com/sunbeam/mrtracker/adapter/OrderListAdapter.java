package com.sunbeam.mrtracker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunbeam.mrtracker.R;
import com.sunbeam.mrtracker.activity.OrderList;
import com.sunbeam.mrtracker.model.OrderListClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{


    Context context;
    ArrayList<OrderListClass> products;

    public OrderListAdapter(Context context, ArrayList <OrderListClass> products) {
        this.context = context;
        this.products = products;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.orders_items,null);

        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {

        final OrderListClass product = products.get(i);


        String my_date = product.getDeliveryDate();


        Date d1 = null;
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");

        try {
            d1 = sm.parse(my_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentDate = new Date();


        if (d1.compareTo(currentDate) > 0) {
            holder.textDeliveryStatus.setText(""+"In Process");
            holder.textDeliveryStatus.setTextColor(Color.parseColor("#ff0000"));
        }

        else if (d1.compareTo(currentDate) < 0) {

            holder.textDeliveryStatus.setText(""+"Done");
            holder.textDeliveryStatus.setTextColor(Color.parseColor("#00ff00"));
        }

        Log.e("currentDate",""+d1);

        holder.textName.setText(""+product.getName());
        holder.textQuantity.setText(""+product.getQuantity());
        holder.textDiscount.setText(""+product.getTotalDiscount());
        holder.textAmount.setText(""+product.getTotalAmount());
        holder.textdrame.setText(""+product.getDrname());
        holder.textPhoneno.setText(""+product.getDrphoneno());
        holder.textPaymentMode.setText(""+product.getPaymentMode());
        holder.textOrderDate.setText(""+product.getOrderDate());
        holder.textDeliveryDate.setText(""+product.getDeliveryDate());

        holder.textFullAddress.setText(""+product.getAddressOFdr());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textName,textQuantity,textDiscount,textAmount,textdrame,textPhoneno,textPaymentMode,textOrderDate,textDeliveryDate,textDeliveryStatus,textFullAddress;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.Name);
            textQuantity = itemView.findViewById(R.id.Quantity);
            textDiscount = itemView.findViewById(R.id.discount);

            textAmount = itemView.findViewById(R.id.Amount);
            textdrame = itemView.findViewById(R.id.drname);
            textPhoneno = itemView.findViewById(R.id.phoneno);

            textPaymentMode = itemView.findViewById(R.id.paymentMode);
            textOrderDate = itemView.findViewById(R.id.OrderDate);
            textDeliveryDate = itemView.findViewById(R.id.DeliveryDate);

            textDeliveryStatus = itemView.findViewById(R.id.deliveryStatus);
            textFullAddress = itemView.findViewById(R.id.FullAddress);

        }


    }


}
