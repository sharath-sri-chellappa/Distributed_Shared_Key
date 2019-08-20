package com.example.android.securelogin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class OTPactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
    }
    public void openPassActivity(View view){
        EditText otp = (EditText) findViewById(R.id.signOTP_field);
        String num = otp.getText().toString();

        EditText ans = (EditText) findViewById(R.id.securityQn_field);
        String num1 = ans.getText().toString();

        //Use this to access mac add.
        //String macadd = this.getWifiMacAddress();

        if(num.length()<5)
        {
            Context context = getApplicationContext();
            CharSequence text = "OTP too Short!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        if(num1.length()<2)
        {
            Context context = getApplicationContext();
            CharSequence text = "Security Answer too small!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        else{
            Intent i = new Intent(this, PasswordActivity.class);
            startActivity(i);
    }   }

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
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }


}

