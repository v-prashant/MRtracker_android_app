package com.sunbeam.mrtracker.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.sunbeam.mrtracker.R;
import com.sunbeam.mrtracker.utils.urls;

public class ProductDetails extends AppCompatActivity {


    int count = 1;
    int amount;
    int temp;


      ImageView imageView;
      TextView textName;
      TextView textPrice1;
      TextView textDiscount,textDtext,textDescription;
      TextView textWithDiscount,quantity,textAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("blue")));
        bar.setTitle("Product Details");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);


         imageView = findViewById(R.id.Image);
         textName = findViewById(R.id.name);
         textPrice1 = findViewById(R.id.textPrice);
         textDiscount = findViewById(R.id.textdiscount);
         textWithDiscount = findViewById(R.id.priceWithDiscount);
         textAmount = findViewById(R.id.discountAmount);
         textDtext = findViewById(R.id.dName);
         textDescription = findViewById(R.id.description);


         Intent intent = getIntent();


         String url = urls.images();
         url = url + intent.getStringExtra("image");
         Ion.with(this).load(url).withBitmap().intoImageView(imageView);

          textName.setText(""+intent.getStringExtra("name"));
          textPrice1.setText("MRP ₹"+intent.getIntExtra("price",0));
          textDiscount.setText("  "+intent.getIntExtra("discount",0)+"% off");
          textWithDiscount.setText("₹"+intent.getIntExtra("priceWithDiscount",0));

          amount = intent.getIntExtra("priceWithDiscount",0);
          textAmount.setText("₹"+amount);

          temp = amount;

          textDtext.setText("Information about "+intent.getStringExtra("name")+":-");
          textDescription.setText(""+intent.getStringExtra("description"));

        textDescription.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onIncrement(View view) {

        quantity = findViewById(R.id.countAmount);
        count++;

        quantity.setText(""+count);

        amount = temp * count;
        textAmount.setText("₹"+amount);

    }

    public void ondecrement(View view) {

        quantity = findViewById(R.id.countAmount);
        if(count == 1){
            Toast.makeText(this, "Can not be decremented", Toast.LENGTH_SHORT).show();
        }
        else{
            count--;
            quantity.setText(""+count);

            amount = temp * count;
            textAmount.setText("₹"+amount);
        }

    }

    public void onAddtoCart(View view) {

        //shared prefrence
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int status = preferences.getInt("login_status",0);

        if(status != 1){
            Toast.makeText(this,"You are not logged in first login yourself",Toast.LENGTH_LONG).show();
        }
    }

}
