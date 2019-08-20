package com.example.android.securelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView response;
    String editTextAddress;
    Integer editTextPort;
    EditText passw;
    Button buttonConnect, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextAddress = "192.168.43.226";
        editTextPort = 9999;
        passw = (EditText) findViewById(R.id.passwEditText);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        buttonClear = (Button) findViewById(R.id.clearButton);
        response = (TextView) findViewById(R.id.responseTextView);

        buttonConnect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                com.example.android.securelogin.Client myClient = new com.example.android.securelogin.Client(editTextAddress, editTextPort, response, passw.getText().toString());
                myClient.execute();
            }
        });

        buttonClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                response.setText("");
            }
        });
    }
    public void openWelcomeActivity(View view){
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i);
    }
}
