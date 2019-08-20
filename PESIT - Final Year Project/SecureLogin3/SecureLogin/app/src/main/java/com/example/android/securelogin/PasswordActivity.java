package com.example.android.securelogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PasswordActivity extends AppCompatActivity {
    String entrpass;
    String confpass;
    String onetimekey;
    int count;
    Socket socket;
    public Socket client;
    public PrintWriter printwriter;
    TextView response;
    String number, account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        number = getIntent().getExtras().getString("Value_for_OTP");
        account = getIntent().getExtras().getString("text");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        SharedPreferences prefs = this.getSharedPreferences("pref_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        count = prefs.getInt("counter", 100);
        count-=1;
        editor.putInt("counter", count);
        editor.commit();
    }
    public void openACKactivity(View view) throws IOException {
        EditText pass1 = (EditText) findViewById(R.id.entrpass_field);
        entrpass = pass1.getText().toString();
        EditText pass2 = (EditText) findViewById(R.id.confpass_field);
        confpass = pass2.getText().toString();


        System.out.println("It reaches here 1");
        if(!(entrpass.equals(confpass))){
            Context context = getApplicationContext();
            CharSequence text = "Pins do not match!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        else {
            System.out.println("It reaches here 2a");
            System.out.println(account);
            onetimekey = locpass();
            com.example.android.securelogin.PasswordSignup passwordsignup = new com.example.android.securelogin.PasswordSignup("192.168.43.226",9999,number,account,confpass+onetimekey);
            passwordsignup.execute();
            try {
                TimeUnit.SECONDS.sleep(4);
                System.out.println("It reaches here 2d");
                Intent i = new Intent(this, ACKactivity.class);
                i.putExtra("Value_for_OTP", number);
                i.putExtra ("text", account);
                startActivity(i);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
       }
    }

    public String locpass()
    {
        String hundredhash;
        hundredhash = randString;
        for(int i=1;i<=100;i++)
        {
            hundredhash = md5(hundredhash);
        }
        return hundredhash;
    }

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


}
