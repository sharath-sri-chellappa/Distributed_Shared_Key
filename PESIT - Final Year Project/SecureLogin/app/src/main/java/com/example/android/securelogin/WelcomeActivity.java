package com.example.android.securelogin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void openOTPactivity(View view){
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        EditText account = (EditText) findViewById(R.id.accountNo_field);
        String num = account.getText().toString();


        if(name.length()<7){
            Context context = getApplicationContext();
            CharSequence text = "Name too Short";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        if(num.length()<10)
        {
            Context context = getApplicationContext();
            CharSequence text = "Number too Short!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        else{
            Intent i = new Intent(this, OTPactivity.class);
            startActivity(i);
        }   }

}
