package com.example.android.securelogin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
    }
    public void openACKactivity(View view){
        EditText pass1 = (EditText) findViewById(R.id.entrpass_field);
        String entrpass = pass1.getText().toString();

        EditText pass2 = (EditText) findViewById(R.id.confpass_field);
        String confpass = pass2.getText().toString();


        if(!(entrpass.equals(confpass))){
            Context context = getApplicationContext();
            CharSequence text = "Pins do not match!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        else {
            Intent i = new Intent(this, ACKactivity.class);
            startActivity(i);
       }
    }

    public static boolean isValid(String password) {
        //return true if and only if password:
        //1. have at least eight characters.
        //2. consists of only letters and digits.
        //3. must contain at least two digits.
        if (password.length() < 8) {
            return false;
        } else {
            char c;
            int count = 1;
            for (int i = 0; i < password.length() - 1; i++) {
                c = password.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    return false;
                } else if (Character.isDigit(c)) {
                    count++;
                    if (count < 2)   {
                        return false;
                    }
                }
                else if(c=='@')
                {
                    return false;
                }
            }
        }
        return true;
    }
}
