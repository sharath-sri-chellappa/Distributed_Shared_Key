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


    Client(String addr, int port, TextView textResponse, String password) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
        passw = password;

    }

    /* public static String encryptRSAToString(String text, String strPublicKey) {
        byte[] cipherText = null;
        String strEncryInfoData="";
        try {

            KeyFactory keyFac = KeyFactory.getInstance("RSA");
            KeySpec keySpec = new X509EncodedKeySpec(Base64.decode(strPublicKey.trim().getBytes(), Base64.DEFAULT));
            Key publicKey = keyFac.generatePublic(keySpec);

            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipherText = cipher.doFinal(text.getBytes("UTF-16"));
            strEncryInfoData = new String(Base64.encode(cipherText,Base64.DEFAULT));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return strEncryInfoData.replaceAll("(\\r|\\n)", "");
    }
    */

    /*public  String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }*/

    /*public void keygenerate() throws NoSuchAlgorithmException {
        kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        kp = kpg.genKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();
    }*/
    /*public String RSAEncrypt (final String plain)
    {
        try {
            keygenerate();
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedBytes = cipher.doFinal(plain.getBytes());
            encrypted = bytesToString(encryptedBytes);
            System.out.println("Key"+privateKey);
            System.out.println("Public Key"+publicKey);
            System.out.println("EEncrypted?????"+ encrypted);
            return encrypted;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return encrypted;
        }

    }*/

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

            firstpacket = "1103"+ loginid + passw;
            String firstpacketsend;
            String publkey = "001100010011000100110000001100110111";
            long a = Long.parseLong(firstpacket);
            long b = Long.parseLong(publkey,2);
            firstpacketsend = Long.toString(a^b) ;
            int option;

           /* StringBuilder sb = new StringBuilder();
            for(int i = 0; i < firstpacket.length(); i++)
                sb.append((firstpacket.charAt(i) ^ publkey.charAt(i)));
            firstpacketsend = sb.toString();*/
            System.out.println("Packet to be sent "+firstpacketsend);
            String stringtohash = passw+"1103";
            hash = md5(stringtohash);
            secondpacket = "1103" + loginid + hash;
            option = 1;
            System.out.println(secondpacket);
            System.out.println("Hashed "+md5("98451103"));
            out.println(option);
            try {
                TimeUnit.SECONDS.sleep(1);
                out.println(firstpacketsend);
                TimeUnit.SECONDS.sleep(1);
                out.println(secondpacket);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
			/*
             * notice: inputStream.read() will block if no data return
			 */
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