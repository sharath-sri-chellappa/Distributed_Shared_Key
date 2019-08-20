package com.example.android.securelogin;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Sharath on 4/5/2017.
 */

public class OTPClient extends AsyncTask<Void, Void, Void> {
    int dstPort;
    String  dstAddress;
    PrintWriter out;
    String response = "";
    String st = null;
    //TextView textResponse;

    OTPClient(String addr, int port) {
        dstAddress = addr;
        dstPort = port;

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
            int num1;
            InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());
            OutputStream outStream = socket.getOutputStream();
            PrintWriter out =
                    new PrintWriter(outStream, true);
            BufferedReader reader = new BufferedReader(inputStream);
            int option = 2;
            out.println(option);
            st = reader.readLine();
            //System.out.println("Mac Address "+MACId);
            System.out.println("Thing Recieved "+st);
            System.out.println(st);

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
