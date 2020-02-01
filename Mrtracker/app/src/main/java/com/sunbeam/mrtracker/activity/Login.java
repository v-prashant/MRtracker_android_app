package com.sunbeam.mrtracker.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
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
import com.sunbeam.mrtracker.utils.urls;

public class Login extends AppCompatActivity {


    EditText textEmail,textPassword;
    TextView textSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ActionBar bar = getSupportActionBar();

        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("blue")));
        bar.setTitle("Login");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);

        textSignIn = findViewById(R.id.link_signup);
        setTextSignIn(textSignIn);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onLogin(View view) {

        textEmail =findViewById(R.id.input_email);
        textPassword = findViewById(R.id.input_password);

        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textEmail.setError("enter a valid email address");
        }
        else if (password.isEmpty() ) {
            textPassword.setError("required password");
        } else {

            // for user login method

            String url = urls.login();

            JsonObject body = new JsonObject();
            body.addProperty("email",email);
            body.addProperty("password",password);

            Ion.with(this).load(url).setJsonObjectBody(body).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {

                    String status = result.get("status").getAsString();

                    if(status.equals("success")){

                        JsonArray array = result.get("data").getAsJsonArray();
                        JsonObject object = array.get(0).getAsJsonObject();
                        int id = object.get("id").getAsInt();
                        String name = object.get("username").getAsString();

                        Toast.makeText(getApplicationContext(),"successfully login",Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("login_status",1);
                        editor.putString("username",name);
                        editor.putInt("id",id);

                        editor.apply();

                        finish();
                        Intent refresh = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(refresh);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"invaild username or password",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    public void setTextSignIn(TextView textSignIn) {
        this.textSignIn = textSignIn;

        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Signup.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
