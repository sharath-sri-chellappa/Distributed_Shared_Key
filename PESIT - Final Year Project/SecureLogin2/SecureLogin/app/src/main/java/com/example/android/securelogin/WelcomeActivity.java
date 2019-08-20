package com.example.android.securelogin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements CaptchaClient.AsyncResponse{
    String number;
    String accountno;
    //String macadd = this.getWifiMacAddress();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void openOTPactivity(View view){
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        TextView response = null;
        EditText account = (EditText) findViewById(R.id.accountNo_field);
        accountno = account.getText().toString();

        if(name.length()<6){
            Context context = getApplicationContext();
            CharSequence text = "Name too Short";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        if(accountno.length()<5)
        {
            Context context = getApplicationContext();
            CharSequence text = "Account Number too Short!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        else{
            //com.example.android.securelogin.LoginClient LoginClient = new com.example.android.securelogin.LoginClient("192.168.43.226",9999 ,name,num,macadd);
            //LoginClient.execute();
            new CaptchaClient("192.168.43.226",9999,this).execute();
            try {
                //Thread.sleep(3000);
                Intent i = new Intent(this, OTPactivity.class);
                startActivity(i);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }

        }

    }
    @Override
    public void processFinish(String output)
    {
        number = output;
        System.out.println("I got the number right" +number);
        try {
            //Thread.sleep(1000);
            Intent i = new Intent(this, OTPactivity.class);
            //Thread.sleep(2000);
            i.putExtra("Value_for_OTP", number);
            i.putExtra("text",accountno);
            System.out.println(accountno);
            System.out.println("Come till here " + number);
            startActivity(i);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }

    public static String getWifiMacAddress() {
        try {
            String interfaceName = "wlan0";
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (!intf.getName().equalsIgnoreCase(interfaceName)){
                    continue;
                }

                byte[] mac = intf.getHardwareAddress();
                if (mac==null){
                    return "";
                }

                StringBuilder buf = new StringBuilder();
                for (byte aMac : mac) {
                    buf.append(String.format("%02X:", aMac));
                }
                if (buf.length()>0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                return buf.toString();
            }
        } catch (Exception ex) { }
        return "";
    }
}
