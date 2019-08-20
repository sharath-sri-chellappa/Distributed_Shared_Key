package com.example.android.securelogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {
    int regcounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStart()
    {
        super.onStart();
        Button btn = (Button)findViewById(R.id.signup);
        SharedPreferences prefs = this.getSharedPreferences("pref_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        regcounter = prefs.getInt("Regcounter", 0);

        try {
            regcounter = getIntent().getExtras().getInt("tag");
            editor.putInt("Regcounter", 100);
            editor.commit();

        } catch (NullPointerException e ) {
            regcounter = prefs.getInt("Regcounter", 0);
        }

        if(regcounter==100){
            btn.setEnabled(false);
        }
        else{
            btn.setEnabled(true);
        }
    }

    public void openLoginActivity(View view){
        String number, account;
        number = getIntent().getExtras().getString("Value_for_OTP");
        account = getIntent().getExtras().getString("text");
        System.out.println(account);
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("Value_for_OTP", number);
        i.putExtra ("text", account);
        startActivity(i);
    }

    public void openWelcomeActivity(View view){
        //String number,account;
        //number = getIntent().getExtras().getString("Value_for_OTP");
        //account = getIntent().getExtras().getString("text");
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i);
        //i.putExtra("Value_for_OTP", number);
        //i.putExtra ("text", account);
    }


}
