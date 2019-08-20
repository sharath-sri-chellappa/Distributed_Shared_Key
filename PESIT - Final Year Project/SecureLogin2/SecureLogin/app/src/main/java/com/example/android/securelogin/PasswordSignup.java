package com.example.android.securelogin;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sharath on 4/7/2017.
 */

public class PasswordSignup extends AsyncTask<Void, Void, Void> {
/**
 * Created by Sharath on 4/6/2017.
 */
    String password;
    //String accnumber;
    int dstPort;
    String  dstAddress;
    PrintWriter out1;
    String response = "";
    String macadd = this.getWifiMacAddress();
    String loginid;
    String firstpacket;
    String OTKey;
    String OTP;
    //TextView textResponse;
    Socket socket = null;
    PasswordSignup(String addr, int port, String number, String account, String name, String onetimekey) {
        super();
        dstAddress = addr;
        dstPort = port;
        password = name;
        OTP = number;
        loginid = account;
        OTKey = onetimekey;
        System.out.println(dstAddress +","+ dstPort+","+password);
        System.out.println("It is reaching this stage 2ca");
        //this.textResponse = textResponse;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        long OTK;

        System.out.println("It is reaching this stage 2cb");
        try {
            socket = new Socket(dstAddress, dstPort);
            firstpacket = OTP+ loginid + password;
            String firstpacketsend;
            String publkey = "001100010011000100110000001100110111";
            long a = Long.parseLong(firstpacket);
            long b = Long.parseLong(publkey,2);
            firstpacketsend = Long.toString(a^b) ;
            int option;
            System.out.println("It is reaching this stage 2c");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];
            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            OutputStream outStream = socket.getOutputStream();
            System.out.println("Packet to be sent "+firstpacketsend);
            out1 = new PrintWriter(outStream, true);
            try {
                TimeUnit.SECONDS.sleep(2);
                out1.println(OTKey);
                TimeUnit.SECONDS.sleep(2);
                out1.println(firstpacketsend);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //textResponse.setText(response);
        out1.flush();
        System.out.println("How the hell are you calling me");
        super.onPostExecute(result);
    }

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