package com.sunbeam.mrtracker.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import com.sunbeam.mrtracker.R;

public class PlaceOrder extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {


    String MYDATE,deliveryDate1,deliveryDate2,paymentMode;

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

        EditText orderdate = findViewById(R.id.editOrderDate);
        EditText deliverydate = findViewById(R.id.deliverydate1);

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


        EditText drname = findViewById(R.id.input_username);
        EditText drphoneno = findViewById(R.id.input_phone);
        EditText state = findViewById(R.id.input_State);
        EditText city = findViewById(R.id.input_City);
        EditText pincode = findViewById(R.id.input_Pincode);
        EditText address = findViewById(R.id.input_Address);


        String addressOFdr = address + ", " + state + ", " + city + ", " + pincode;
        String OrderDate = deliveryDate1;
        String deliveryDate = deliveryDate2;
        String PaymentMode = paymentMode;

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        int mrid = preference.getInt("id",0);

         Log.e("check values"," "+drname+" "+drphoneno+" "+addressOFdr+" "+OrderDate+" "+deliveryDate+" "+PaymentMode);

//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setTitle("Conformation");
//        builder.setMessage("Once oreded can not be canceled  ?");
//
//        // Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
//        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//
//
//            }
//        });
//
//        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                dialog.dismiss();
//            }
//        });
//
//        builder.show();
    }





}
