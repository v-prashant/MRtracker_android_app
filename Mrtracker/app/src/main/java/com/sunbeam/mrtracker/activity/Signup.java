package com.sunbeam.mrtracker.activity;

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

            String url = urls.signUp();

            JsonObject body = new JsonObject();
            body.addProperty("username",username);
            body.addProperty("firstname",firstname);
            body.addProperty("lastname",lastname);
            body.addProperty("joindate","1990-01-01");
            body.addProperty("phoneno",phoneno);
            body.addProperty("email",email);
            body.addProperty("password",password);


            Ion.with(this).load(url).setJsonObjectBody(body).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {

                    String status = result.get("status").getAsString();

                    if(status.equals("success")){


                        Toast.makeText(getApplicationContext(),"successfully Registration",Toast.LENGTH_SHORT).show();

                        finish();


                        // Intent refresh = new Intent(getApplicationContext(), HomeActivity.class);
                       //  startActivity(refresh);
                        // finish();
                    }
                    else{
                        String error = result.get("error").getAsString();
                        Log.e("Signup",error);

                        Toast.makeText(getApplicationContext(),"Somthing went wrong",Toast.LENGTH_SHORT).show();
                    }
                }
            });




        }
    }


}
