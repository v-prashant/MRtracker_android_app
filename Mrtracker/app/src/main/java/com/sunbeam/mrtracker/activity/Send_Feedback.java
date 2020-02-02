package com.sunbeam.mrtracker.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sunbeam.mrtracker.R;

public class Send_Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__feedback);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setTitle("Feedback");
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("blue")));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendfeedback(View view) {

        EditText text = findViewById(R.id.feedback);
        String text1 = text.getText().toString();

        if(text1.length() < 50 ){
            Toast.makeText(this,"length of character should be atleast 50",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Your feedback has been sent",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
