package com.sunbeam.mrtracker.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

    int finalAmount,finalSaving;
    ArrayList<Mycart> products = new ArrayList <>();
    CartAdapter adapter;
    ArrayList<Product> products1 = new ArrayList <>();

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

        finalAmount = 0;
        finalSaving = 0;

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

                        finalAmount = finalAmount + totalAmount;
                        finalSaving = finalSaving + totalDiscount;


                        products.add(new Mycart(id,Quantity,totalAmount,totalDiscount,MRid,productID,image,name));


                    }

                    Log.e("Cart","finalAmount = "+finalAmount);
                    // to calculate total Amount and total shaving;
                    TextView a1,a2;

                    a1 = findViewById(R.id.totalShaving1);
                    a2 = findViewById(R.id.totalAmount1);

                    a1.setText("₹"+finalSaving+"/-");
                    a2.setText("₹"+finalAmount+"/-");

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

       //final int id,price,discount,priceWithDiscount;
       //final String name,image,description;

        Mycart product1 = products.get(i);
        int productID = product1.getProductID();

        final int orderDetailsTableID = product1.getId();

        final int Quantity = product1.getQuantity();

        String url = urls.home1();

        JsonObject body = new JsonObject();
        body.addProperty("productID",productID);

        Ion.with(this).load(url).setJsonObjectBody(body).asJsonObject().setCallback(new FutureCallback <JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                String status = result.get("status").getAsString();

                if (status.equals("success")) {

                    JsonArray array = result.get("data").getAsJsonArray();

                    for (int i = 0; i < array.size(); i++) {
                        JsonObject object = array.get(i).getAsJsonObject();

                        int id = object.get("id").getAsInt();
                        String name = object.get("name").getAsString();
                        int  price = object.get("price").getAsInt();
                        int discount = object.get("discount").getAsInt();
                        String image = object.get("image").getAsString();
                        int priceWithDiscount = object.get("priceWithDiscount").getAsInt();
                        String description = object.get("description").getAsString();

                        //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                       // products1.add(new Product(id, name, price, discount, image, priceWithDiscount, description));


                        Intent intent = new Intent(getApplicationContext(),EditCart.class);
                     //   intent.putExtra("count",q)
                        intent.putExtra("name",name);
                        intent.putExtra("price",price);
                        intent.putExtra("discount",discount);
                        intent.putExtra("description",description);
                        intent.putExtra("image",image);
                        intent.putExtra("priceWithDiscount",priceWithDiscount);
                        intent.putExtra("id",id);
                        intent.putExtra("quantity",Quantity);
                        intent.putExtra("orderDetailsTableID",orderDetailsTableID);
                        startActivity(intent);

                        finish();
                    }


                }
            }

        });


    }


    public void placeorder(View view) {
        //Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),PlaceOrder.class);
        startActivity(intent);

    }


}
