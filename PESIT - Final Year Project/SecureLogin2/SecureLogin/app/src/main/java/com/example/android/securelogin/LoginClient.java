package com.example.android.securelogin;

import android.os.AsyncTask;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sharath on 4/5/2017.
 */

public class LoginClient extends AsyncTask<Void, Void, Void>{

    String username;
    String accnumber;
    int dstPort;
    String  dstAddress;
    String MACId;
    int option;
    PrintWriter out;
    String response = "";
    //TextView textResponse;

    LoginClient(String addr, int port, String name, String number, String Id) {
        dstAddress = addr;
        dstPort = port;
        username = name;
        accnumber = number;
        MACId = Id;
        //this.textResponse = textResponse;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);
            System.out.println("It is reaching this stage");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];
            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            OutputStream outStream = socket.getOutputStream();
            PrintWriter out =
                    new PrintWriter(outStream, true);
            option = 2;
            out.println(option);
            try {
                TimeUnit.SECONDS.sleep(1);
                out.println(username);
                System.out.println("Username " + username);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Account Number " + accnumber);
                out.println(accnumber);
                TimeUnit.SECONDS.sleep(1);
                out.println(MACId);
                System.out.println("MAC ID " + MACId);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        finally {
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
        super.onPostExecute(result);
    }
}