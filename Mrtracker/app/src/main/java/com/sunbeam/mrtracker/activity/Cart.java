package com.sunbeam.mrtracker.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.sunbeam.mrtracker.R;
import com.sunbeam.mrtracker.adapter.CartAdapter;
import com.sunbeam.mrtracker.adapter.ProductAdapter;
import com.sunbeam.mrtracker.model.Mycart;
import com.sunbeam.mrtracker.model.Product;
import com.sunbeam.mrtracker.utils.urls;

import java.util.ArrayList;


public class Cart extends AppCompatActivity implements CartAdapter.ContactAdapterActionListener1 {

    RecyclerView recyclerView;

    ArrayList<Mycart> products = new ArrayList <>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);



        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("blue")));
        bar.setTitle("Cart");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);


        recyclerView = findViewById(R.id.recyclerview1);

        adapter = new CartAdapter(this,products,this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
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

        String url = urls.CartList();

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        int mrid = preference.getInt("id",0);

        JsonObject body = new JsonObject();
        body.addProperty("mrid",mrid);

        Ion.with(this).load(url).setJsonObjectBody(body).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                String status = result.get("status").getAsString();

                if(status.equals("success")){

                    JsonArray array = result.get("data").getAsJsonArray();

                    for(int i=0;i < array.size(); i++){
                        JsonObject object = array.get(i).getAsJsonObject();

                        int id = object.get("id").getAsInt();
                        String name = object.get("name").getAsString();
                        int Quantity = object.get("Quantity").getAsInt();
                        int totalAmount = object.get("totalAmount").getAsInt();
                        int totalDiscount = object.get("totalDiscount").getAsInt();
                        int MRid = object.get("MRid").getAsInt();
                        int productID = object.get("ProductID").getAsInt();
                        String image = object.get("image").getAsString();


                        products.add(new Mycart(id,Quantity,totalAmount,totalDiscount,MRid,productID,image,name));


                    }

                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Somethig went worng",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onDetails(int i) {

    }
}
