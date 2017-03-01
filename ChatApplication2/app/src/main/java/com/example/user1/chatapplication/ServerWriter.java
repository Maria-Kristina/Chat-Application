package com.example.user1.chatapplication;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by User1 on 6.10.2016.
 */
public class ServerWriter implements Runnable {
    private Socket s;
    public String message;

    public ServerWriter(Socket s, String message) throws IOException {
        this.message = message;
        this.s = s;
    }

    @Override
    public void run() {


            Log.d("ServerWriter run()", message);
            try {
                DataOutputStream outToServer = new DataOutputStream(s.getOutputStream());
                if(outToServer != null){
                    outToServer.writeBytes(message+"\n");
                    Log.d("ServerWriter", message);
                }
            }
            catch (Exception e) {

            }

    }

    /*public void toServer (String message){
        this.message = message;
        Log.d("toServer()", message);

    }*/
}