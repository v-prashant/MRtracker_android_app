package com.sunbeam.mrtracker.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.sunbeam.mrtracker.R;
import com.sunbeam.mrtracker.utils.urls;

public class PlaceOrder extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {


    String MYDATE,deliveryDate1="",deliveryDate2="",paymentMode;
    EditText orderdate,deliverydate;
    Boolean flag = false;
    private Spinner spinner;
    private static final String[] paths = {"Cash", "Cheque", "Upi"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);


        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("blue")));
        bar.setTitle("Place Order");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);



        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(PlaceOrder.this,android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                    paymentMode = "Cash";
                break;
            case 1:
                    paymentMode = "Cheque";
                break;
            case 2:
                    paymentMode = "Upi";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView <?> parent) {





    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        orderdate = findViewById(R.id.editOrderDate);
        deliverydate = findViewById(R.id.deliverydate1);

        MYDATE = year+"-"+(month+1)+"-"+dayOfMonth;

        if(flag){
            deliveryDate1 = MYDATE;
            orderdate.setText(MYDATE);
        }else{
            deliveryDate2 = MYDATE;
            deliverydate.setText(MYDATE);
        }

    }

    public void orderdate(View view) {
        DatePickerDialog datePickerDialog1 = new DatePickerDialog(
                this, PlaceOrder.this, 2020, 01, 01);

         flag = true;
          datePickerDialog1.show();

    }

    public void deliveryDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, PlaceOrder.this, 2020, 01, 01);

        flag = false;
        datePickerDialog.show();

    }
    public void confirmOrder(View view) {


        EditText drname = findViewById(R.id.input_username1);
        EditText drphoneno = findViewById(R.id.input_phone);
        EditText state = findViewById(R.id.input_State);
        EditText city = findViewById(R.id.input_City);
        EditText pincode = findViewById(R.id.input_Pincode);
        EditText address = findViewById(R.id.input_Address);

        final String drname1 = drname.getText().toString();
        final String drphoneno1 = drphoneno.getText().toString();
        String state1 = state.getText().toString();
        String city1 = city.getText().toString();
        String pincode1 = pincode.getText().toString();
        String address1 = address.getText().toString();

        final String addressOFdr = address1 + ", " + state1 + ", " + city1 + ", " + pincode1;
        final String OrderDate = deliveryDate1;
        final String deliveryDate = deliveryDate2;
        final String PaymentMode = paymentMode;

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        final int mrid = preference.getInt("id", 0);

        if(drname1.isEmpty()){
            drname.setError("Required Field");
        }else if(drphoneno1.isEmpty()){
            drphoneno.setError("Required Field");
        }else if(state1.isEmpty()){
            state.setError("Required Field");
        }else if(city1.isEmpty()){
            city.setError("Required Field");
        }else if(pincode1.isEmpty() || pincode1.length() != 6){
            pincode.setError("Invaild pin code");
        }else if(address1.isEmpty()) {
            address.setError("Required Field");
        } else if(deliveryDate1.isEmpty()){
            Toast.makeText(this,"set the date by button",Toast.LENGTH_SHORT).show();
        }else if(deliveryDate2.isEmpty()){
            Toast.makeText(this,"set the date by button",Toast.LENGTH_SHORT).show();
        }
         else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Conformation");
            builder.setMessage("Once confirmed can not be canceled");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String url = urls.confirmToOrder();

                    JsonObject body = new JsonObject();
                    body.addProperty("OrderDate",OrderDate);
                    body.addProperty("deliveryDate",deliveryDate);
                    body.addProperty("PaymentMode",PaymentMode);
                    body.addProperty("mrid",mrid);
                    body.addProperty("drname",drname1);
                    body.addProperty("addressOFdr",addressOFdr);
                    body.addProperty("drphoneno",drphoneno1);


                    Ion.with(getApplicationContext()).load("PUT",url).setJsonObjectBody(body).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            String status = result.get("status").getAsString();

                            if(status.equals("success")){


                                Toast.makeText(getApplicationContext(),"Order Confirmed",Toast.LENGTH_SHORT).show();
                                finish();

                            }
                            else{
                                String error = result.get("error").getAsString();
                                Log.e("PlaceOrder",error);

                                Toast.makeText(getApplicationContext(),"Somthing went wrong",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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



    }





}

