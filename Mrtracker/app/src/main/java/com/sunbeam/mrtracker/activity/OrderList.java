package com.sunbeam.mrtracker.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.sunbeam.mrtracker.R;
import com.sunbeam.mrtracker.adapter.CartAdapter;
import com.sunbeam.mrtracker.adapter.OrderListAdapter;
import com.sunbeam.mrtracker.model.Mycart;
import com.sunbeam.mrtracker.model.OrderListClass;
import com.sunbeam.mrtracker.utils.urls;

import java.util.ArrayList;

public class OrderList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<OrderListClass> products = new ArrayList<>();
    OrderListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        recyclerView = findViewById(R.id.recyclerview3);

        adapter = new OrderListAdapter(this,products);
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);


        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setTitle("Orders List");
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("blue")));

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    protected void onResume() {
        super.onResume();
        loadproducts();
    }

    private void loadproducts(){
        products.clear();


        String url = urls.listOfOrders();

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        int mrid = preference.getInt("id",0);

        JsonObject body = new JsonObject();
        body.addProperty("mrid",mrid);

        Ion.with(this).load(url).setJsonObjectBody(body).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                String status = result.get("status").getAsString();

                if(status.equals("success")){

                    Log.e("Prashant verma","hello");
                    JsonArray array = result.get("data").getAsJsonArray();

                    for(int i=0;i < array.size(); i++){
                        JsonObject object = array.get(i).getAsJsonObject();

                        int id = object.get("id").getAsInt();
                        String name = object.get("name").getAsString();
                        int Quantity = object.get("Quantity").getAsInt();
                        int totalAmount = object.get("totalAmount").getAsInt();
                        int totalDiscount = object.get("totalDiscount").getAsInt();
                        String drname = object.get("drname").getAsString();
                        String drphoneno = object.get("drphoneno").getAsString();
                        String image = object.get("image").getAsString();
                        String PaymentMode = object.get("PaymentMode").getAsString();
                        String OrderDate = object.get("OrderDate").getAsString();
                        String deliveryDate = object.get("deliveryDate").getAsString();
                        String addressOFdr = object.get("addressOFdr").getAsString();

                        products.add(new OrderListClass(name,image,id,Quantity,totalDiscount,totalAmount,drname,drphoneno,PaymentMode,OrderDate,deliveryDate,addressOFdr));


                    }

                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Somethig went worng",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
