package com.example.android.securelogin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ACKactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ackactivity);
    }
    public void openMainActivity(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
