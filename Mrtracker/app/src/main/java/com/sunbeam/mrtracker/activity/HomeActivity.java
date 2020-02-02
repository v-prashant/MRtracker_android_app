package com.sunbeam.mrtracker.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.strictmode.NonSdkApiUsedViolation;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.sunbeam.mrtracker.R;
import com.sunbeam.mrtracker.adapter.ProductAdapter;
import com.sunbeam.mrtracker.model.Product;
import com.sunbeam.mrtracker.utils.urls;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;



public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ProductAdapter.ContactAdapterActionListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private CarouselView carouselView;
    private EditText edittext;

    private int[] imageArray = new int[]{
            R.drawable.c, R.drawable.b, R.drawable.d, R.drawable.a
    };

    RecyclerView recyclerView;
    ProductAdapter adapter;

    ArrayList<Product> products = new ArrayList <>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // for side showing image

        carouselView = findViewById(R.id.carousel);
        carouselView.setPageCount(imageArray.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                    imageView.setImageResource(imageArray[position]);
            }
        });
//        carouselView.setImageClickListener(new ImageClickListener() {
//            @Override
//            public void onClick(int position) {
//
//            }
//        });


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerXml);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.recyclerview);

        adapter = new ProductAdapter(this,products,this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("blue")));

        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);


        edittext = findViewById(R.id.search);

        setEdittext(edittext);
        setRecyclerView(recyclerView);



    }

    @Override
    protected void onResume() {
        super.onResume();
        loadproducts();
    }

    private void loadproducts(){
        products.clear();


        String url = urls.home();

        Ion.with(this).load("GET",url).asJsonObject().setCallback(new FutureCallback <JsonObject>() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int status = preferences.getInt("login_status",0);
        String username = preferences.getString("username","");

        menu.add("My Oredrs");
        menu.add("My Cart");

        if(status == 1){
            username = "Logout("+username+")";
            menu.add(username);
        }
        else {
            menu.add("LogIn/signUp");
        }

        inflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString("username","");
        username = "Logout("+username+")";


        int status = preferences.getInt("login_status",0);

        if(item.getItemId() == R.id.menuShopping){
            if(status == 1){
                Intent intent = new Intent(this,Cart.class);
                startActivity(intent);
            }else{
                Toast.makeText(this,"You need first logged in",Toast.LENGTH_SHORT).show();
            }

        }
        else if(item.getItemId() == R.id.menuNotification){
            Toast.makeText(this,"notification box is empty",Toast.LENGTH_SHORT).show();
        }
        else if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        else if(item.getTitle().equals("My Oredrs")){

            if(status == 1){
                Intent intent = new Intent(this,OrderList.class);
                startActivity(intent);
            }else{
                Toast.makeText(this,"You need first logged in",Toast.LENGTH_SHORT).show();
            }

        }
        else if(item.getTitle().equals("My Cart")){

            if(status == 1){
                Intent intent = new Intent(this,Cart.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this,"you need first logged in",Toast.LENGTH_SHORT).show();
            }
        }
        else if(item.getTitle().equals("LogIn/signUp")){
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
        } else if(item.getTitle().equals(username)) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Conformation");
            builder.setMessage("Are you confirm to logout");

           // Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("login_status",0);
                    editor.putString("username",null);
                    editor.putInt("id",0);
                    editor.apply();

                    Intent refresh = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(refresh);
                    finish();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            builder.show();

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.home1){

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        }
        else if(item.getItemId() == R.id.allopathic){
            Intent intent = new Intent(this,Allopathic.class);
            startActivity(intent);

        }
        else if(item.getItemId() == R.id.homopathic){
            Intent intent = new Intent(this,Homoeopathy.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.ayurvedic){
            Intent intent = new Intent(this,Ayurvedic.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.contactUs){
            Intent intent = new Intent(this,ContactUS.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.sendFeedback){
            Intent intent = new Intent(this,Send_Feedback.class);
            startActivity(intent);
        }

        return false;
    }


    //for searching lisner
    public void setEdittext(final EditText edittext) {
        this.edittext = edittext;
        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = edittext.getText().toString();
                   // Log.e("HomeActivity", search);
                    performSearch(search);
                    return true;
                }
                return false;
            }
        });


    }


    private void performSearch(String search) {
        Intent intent = new Intent(this,Search.class);
        intent.putExtra("search",search);
        startActivity(intent);

    }


    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;

    };

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

