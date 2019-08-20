package com.example.android.securelogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    String onetimekey = "";
    TextView response;
    String editTextAddress;
    Integer editTextPort;
    EditText passw;
    Button buttonConnect, buttonClear;
    ImageView im;
    Button btn;
    TextView ans;
    int count;
    final String randString = "ABC29401";
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



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
        im = (ImageView)findViewById(R.id.imageView1);
        btn = (Button)findViewById(R.id.button1);
        ans = (TextView)findViewById(R.id.textView1);

        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Captcha c = new MathCaptcha(300, 100, null,1234);
                //Captcha c = new TextCaptcha(300, 100, 5, TextOptions.NUMBERS_AND_LETTERS);
                im.setImageBitmap(c.image);
                im.setLayoutParams(new LinearLayout.LayoutParams(c.width *2, c.height *2));
                //ans.setText(c.answer);
            }
        });

        buttonConnect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onetimekey = locpass();
                com.example.android.securelogin.Client myClient = new com.example.android.securelogin.Client(editTextAddress, editTextPort, response, passw.getText().toString(), onetimekey);
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

    public String locpass()
    {
        String hundredhash;
        SharedPreferences prefs = this.getSharedPreferences("pref_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        count = prefs.getInt("counter", 100);
        count-=1;
        editor.putInt("counter", count);
        editor.commit();
        hundredhash = randString;
        for(int i=1;i<=count;i++)
        {
            hundredhash = md5(hundredhash);
        }
        return hundredhash;
    }

    public void openBankActivity(View view){

        Intent i = new Intent(this, bankPageActivity.class);
        startActivity(i);

    }
}
