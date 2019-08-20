package com.example.android.securelogin;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Sharath on 4/6/2017.
 */
public class CaptchaClient extends AsyncTask<Void, Void, String> {
    int num1,num2,num3,num4;
    int dstPort;
    String  dstAddress;
    PrintWriter out;
    String response = "";
    int value;
    String st = null;
    //TextView textResponse;

    CaptchaClient(String addr, int port,AsyncResponse delegate) {
        super();
        dstAddress = addr;
        dstPort = port;
        this.delegate = delegate;
        //this.value = value;
    }
    public interface AsyncResponse{
        void processFinish(String output);
    }

    public AsyncResponse delegate  = null;

    @Override
    protected String doInBackground(Void... arg0) {

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
            //out.println(MACId);
            //System.out.println("Mac Address "+MACId);
            BufferedReader reader = new BufferedReader(inputStream);
            int option = 2;
            out.println(option);
            st = reader.readLine();
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
        return st;
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(result);
        delegate.processFinish(result);
        System.out.println("Result is " +result);
    }
}
