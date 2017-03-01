package com.example.user1.chatapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by User1 on 4.10.2016.
 */


public class ServerReader implements Runnable { //reads lines from a socket’s output stream
    private Socket s;
    private BufferedReader reader;
    public String message;
    public ChatHistory h;



    public ServerReader (Socket s) throws IOException {
        this.h = ChatHistory.getInstance();
        this.s = s;
        this.reader = new BufferedReader(new InputStreamReader(s.getInputStream()));//määritellään mistä input haetaan
    }

    @Override
    public void run() {

        while (true){ //looppi joka lukee syötettä rivin ja tulostaa sen logiin
            try {
                message = reader.readLine();
                h.insert(message); //lähettää chat historyyn
                Log.d("Input Loop", message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
