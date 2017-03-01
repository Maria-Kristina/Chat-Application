package com.example.user1.chatapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements Observer{ // this will be an observer of the history
    public ChatHistory h;
    private Socket s;
    public int port = 57632;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.h = ChatHistory.getInstance();
        h.register(this);

        final EditText e = (EditText) findViewById(R.id.EditText); //Luo editText kentän


        ((ImageButton)findViewById(R.id.Button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Main", "On Click () called");
                try {
                    new Thread(new ServerWriter(s, e.getText().toString())).start();
                    e.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    s = new Socket("10.0.2.2", port);
                    new Thread(new ServerReader(s)).start();//luodaan uusi thread, joka ajaa serverReaderin run(){
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Main", "IOException");
                }
            }

        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.list) {
            try {
                new Thread(new ServerWriter(s, "/list")).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (id == R.id.history) {
            try {
                new Thread(new ServerWriter(s, "/history")).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (id == R.id.user) {
            try {
                new Thread(new ServerWriter(s, "/user")).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (id == R.id.quit) {
            onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void update(final String message) { //Laittaa viestit näkyviin userinterfaceen, saa viestit chathistorystä, joka saa viestit serveriltä
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("UI", message);
                TextView t = (TextView) findViewById(R.id.TextView);
                t.setMovementMethod(new ScrollingMovementMethod());
                t.append(message + "\n");
            }
        });
    }





    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.titleDialog);
        alert.setMessage(R.string.messageDialog);
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //This code will be executed when the user clicks "ACCEPT"
                // this will close the app
                try {
                    new Thread(new ServerWriter(s, "/quit")).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                moveTaskToBack(true);
                Process.killProcess(Process.myPid());
                System.exit(1);
            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //This code will be executed when the user clicks "CANCEL"
            }
        });
        alert.show();
    }
}
