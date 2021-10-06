package com.example.bt2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    //UUID for blutoth devices
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private byte[] mmBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get the all paired blutooth devices in the mobile
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        System.out.println(btAdapter.getBondedDevices());

        //print the name of the bloototh device that currently connected
        BluetoothDevice hco6 = btAdapter.getRemoteDevice("FC:A8:9A:00:20:F1");
        System.out.println(hco6.getName());

        //create socket for communication (do wile is for try it for 3 tymes)
        BluetoothSocket btsocket = null;
        int count = 0;
        do {
            try {
                btsocket = hco6.createRfcommSocketToServiceRecord(mUUID);
                System.out.println(btsocket);
                btsocket.connect();
                System.out.println(btsocket.isConnected());
                System.out.println("fucke fukiida");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(!btsocket.isConnected() && count < 3 );



        //send the signal that we have to et the output from the ardino(ardino eken ganna
        // one value eka apita one kiyala yawana signal eka)
        try {
            OutputStream outputStream = btsocket.getOutputStream();
            outputStream.write(48); //this is the place that you have to change.
        } catch (IOException e) {
            e.printStackTrace();
        }


        mmBuffer = new byte[50];

        //receve the data from the ardino board
        InputStream inputStream = null;
        try {
            inputStream = btsocket.getInputStream();
//            Log.i("ggggggg ", inputStream.read() + "");
            Log.i("alphbet",  "no");
            inputStream.skip(inputStream.available());

            for(int i=0; i< 26; i++) {
                try {
                    byte b =(byte) inputStream.read();
                    mmBuffer[i] = b;
                    System.out.println(b);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.i("alphbet", mmBuffer[20] + "");
            Log.i("alphbet",  "");
            Log.i("123",  "ddhuihd");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("alphbet",  "hufshud "+ mmBuffer[2]);


        try {
            btsocket.close();
            System.out.println(btsocket.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
