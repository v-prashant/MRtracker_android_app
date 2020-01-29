package com.sunbeam.mrtracker.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.sunbeam.mrtracker.R;
import com.sunbeam.mrtracker.model.Product;
import com.sunbeam.mrtracker.utils.urls;

public class Signup extends AppCompatActivity {


    EditText textEmail,textPassword,textUsername,textFirstname,textLastname,textPhonenno;
    TextView textLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        ActionBar bar = getSupportActionBar();

        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("blue")));
        bar.setTitle("SignUp");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);

        textLogIn = findViewById(R.id.link_signup);
        setTextSignIn(textLogIn);

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTextSignIn(TextView textLogIn) {

        textLogIn = findViewById(R.id.link_login);
        textLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onSignUp(View view) {

        textEmail =findViewById(R.id.input_email);
        textPassword = findViewById(R.id.input_password);
        textUsername =findViewById(R.id.input_username);
        textFirstname = findViewById(R.id.input_firstname);
        textLastname = findViewById(R.id.input_lastname);
        textPhonenno = findViewById(R.id.input_phone);


        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();
        String username = textUsername.getText().toString();
        String firstname = textFirstname.getText().toString();
        String lastname = textLastname.getText().toString();
        String phoneno = textPhonenno.getText().toString();


        if (username.isEmpty()) {
            textUsername.setError("Username required");
        }
        else if (firstname.isEmpty()) {
            textFirstname.setError("firstname required");
        }
        else if (lastname.isEmpty()) {
            textLastname.setError("lastname required");
        }
        else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textEmail.setError("enter a valid email address");
        }
        else if (password.isEmpty()) {
            textPassword.setError("requred password");
        }
        else if(phoneno.isEmpty() || phoneno.length()!=10 ){
            textPhonenno.setError("invaild phone number");
        }
        else {

            // for user registing method


        }
    }


}
