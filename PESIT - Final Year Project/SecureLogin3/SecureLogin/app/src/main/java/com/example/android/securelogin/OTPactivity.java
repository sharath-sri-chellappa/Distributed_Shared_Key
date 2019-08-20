package com.example.android.securelogin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class OTPactivity extends AppCompatActivity {
    ImageView im;
    Button btn;
    TextView ans;
    String number;
    String account;
    TextView textResponse;
    int otp6;
    String otprcv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        //im = (ImageView)findViewById(R.id.imageView4);
        btn = (Button)findViewById(R.id.button2);
        ans = (TextView)findViewById(R.id.textView1);
        textResponse = (TextView)findViewById(R.id.TextView2);
        //Intent myIntent = getIntent();
        /*if (null!= myIntent)
        {
            number = myIntent.getStringExtra("Value_for_OTP");

        }*/
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                number = getIntent().getExtras().getString("Value_for_OTP");
                otp6 = Integer.parseInt(number);
                otprcv4 = Integer.toString(otp6);

                account = getIntent().getExtras().getString("text");
                System.out.println("Number for OTP is " + number);
                System.out.println(Integer.parseInt(number));
                textResponse.setText(otprcv4);
                //Captcha c = new MathCaptcha(300, 100, null, Integer.parseInt(number));
                //Captcha c = new TextCaptcha(300, 100, 5, TextOptions.NUMBERS_AND_LETTERS);
                //im.setImageBitmap(c.image);
                //im.setLayoutParams(new LinearLayout.LayoutParams(c.width *2, c.height *2));
                //ans.setText(c.answer);
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btn.setEnabled(true);
                            }
                        });
                    }
                }, 30000);
            }
        });
    }

    public void openPassActivity(View view){
        EditText otp = (EditText) findViewById(R.id.signOTP_field);
        String num = otp.getText().toString();
        int otpentr4 = Integer.parseInt(num);

        EditText ans = (EditText) findViewById(R.id.securityQn_field);
        String num1 = ans.getText().toString();

        //Use this to access mac add.


        if(num.length()<4)
        {
            Context context = getApplicationContext();
            CharSequence text = "OTP too Short!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        else if(!(otprcv4.equals(otpentr4))){
            Context context = getApplicationContext();
            CharSequence text = "OTP Mismatch!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        else if(!(num1.equals("tommy")))
        {
            Context context = getApplicationContext();
            CharSequence text = "Security Answer Entered is wrong!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        else{
            //com.example.android.securelogin.OTPClient OTPClient = new com.example.android.securelogin.OTPClient("192.168.43.226",9999);
            //OTPClient.execute();
            Intent i = new Intent(this, PasswordActivity.class);
            i.putExtra("Value_for_OTP", number);
            i.putExtra ("text", account);
            startActivity(i);
    }
    }
}

