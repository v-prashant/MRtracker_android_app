package com.sunbeam.mrtracker.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.sunbeam.mrtracker.R;
import com.sunbeam.mrtracker.adapter.ProductAdapter;
import com.sunbeam.mrtracker.model.Product;
import com.sunbeam.mrtracker.utils.urls;

import java.util.ArrayList;


                public class Allopathic extends AppCompatActivity implements ProductAdapter.ContactAdapterActionListener {

    RecyclerView recyclerView;
    ProductAdapter adapter;
    ArrayList<Product> products = new ArrayList <>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allopathic);


        recyclerView = findViewById(R.id.recyclerview);

        adapter = new ProductAdapter(this,products,this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);


        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setTitle("Allopathic");
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("blue")));

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

        // to getting  url
        String url = urls.allopathic();

        Ion.with(this).load("GET",url).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                String status = result.get("status").getAsString();

                if(status.equals("success")){

                    JsonArray array = result.get("data").getAsJsonArray();

                    for(int i=0;i < array.size(); i++){
                        JsonObject object = array.get(i).getAsJsonObject();

                        int id = object.get("id").getAsInt();
                        String name = object.get("name").getAsString();
                        int price = object.get("price").getAsInt();
                        int discount = object.get("discount").getAsInt();
                        String image = object.get("image").getAsString();
                        int priceWithDiscount = object.get("priceWithDiscount").getAsInt();
                        String description = object.get("description").getAsString();

                        products.add(new Product(id, name, price, discount, image, priceWithDiscount,description));


                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });

    }


    @Override
    public void onDetails(int i) {

        Product product = products.get(i);
        //Toast.makeText(this,product.getName(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,ProductDetails.class);
        intent.putExtra("name",product.getName());
        intent.putExtra("price",product.getPrice());
        intent.putExtra("discount",product.getDiscount());
        intent.putExtra("description",product.getDescription());
        intent.putExtra("image",product.getImage());
        intent.putExtra("priceWithDiscount",product.getPriceWithDiscount());
        intent.putExtra("id",product.getId());
        startActivity(intent);
    }
}




