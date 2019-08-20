package com.example.android.securelogin;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.Socket;
import android.util.Base64;

import com.google.gson.Gson;

import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class Client extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    String hash;
    String passw;
    String secondpacket;
    int dstPort;
    String password1;
    PrintWriter out;
    String response = "";
    TextView textResponse;
    KeyPairGenerator kpg;
    KeyPair kp;
    PublicKey publicKey;
    PrivateKey privateKey;
    byte [] encryptedBytes,decryptedBytes;
    Cipher cipher,cipher1;
    String encrypted,decrypted;
    String loginid ="54237";
    String firstpacket;
    String onetimekey;
    //String account;
    String OTP;

    Client(String addr, int port, TextView textResponse, String account,String OTP, String password, String hundredhash) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
        passw = password;
        loginid = account;
        this.OTP = OTP;
        onetimekey = hundredhash;

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


    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];
            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            OutputStream outStream = socket.getOutputStream();
            PrintWriter out =
                    new PrintWriter(outStream, true);
            //password1=RSAEncrypt(passw);
            //ByteBuffer bb = ByteBuffer.allocate(4);
            //bb.putInt(publicKey.getEncoded().length);
            //Gson gson = new Gson();
            //String pubkey = gson.toJson(publicKey);
            //out.println(pubkey);

            firstpacket = OTP+ loginid + passw;
            String firstpacketsend;
            String publkey = "001100010011000100110000001100110111";
            long a = Long.parseLong(firstpacket);
            long b = Long.parseLong(publkey,2);
            //firstpacketsend = Long.toString(a^b) + onetimekey ;
            int option;

           /* StringBuilder sb = new StringBuilder();
            for(int i = 0; i < firstpacket.length(); i++)
                sb.append((firstpacket.charAt(i) ^ publkey.charAt(i)));
            firstpacketsend = sb.toString();*/
            //System.out.println("Packet to be sent "+firstpacketsend);
            //System.out.println(onetimekey);
            String stringtohash = passw+OTP;
            hash = md5(stringtohash);
            secondpacket = OTP + loginid + hash + onetimekey;
            option = 1;
            System.out.println(secondpacket);
            System.out.println("Hashed "+md5("98451103"));
            out.println(option);
            try {
                //TimeUnit.SECONDS.sleep(1);
                //out.println(firstpacketsend);
                TimeUnit.SECONDS.sleep(1);
                out.println(secondpacket);
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
        textResponse.setText(response);
        super.onPostExecute(result);
    }

}