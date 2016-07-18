package edu.nnu.mp.broadcasting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final static String MY_INTENT = "edu.nnu.mp.MainActivity.broadCast_example" ;


    MyReceiver receiver ;
    LocalBroadcastManager manager ;
    IntentFilter filter ;

    Button broadcast_btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // define an object of my custom receiver
        receiver = new MyReceiver();

        //define intent filter
        filter = new IntentFilter(MY_INTENT);

        //get localBroadcaseManager instance
        manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.registerReceiver(receiver , filter);

        broadcast_btn = (Button)findViewById(R.id.button);
        broadcast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.sendBroadcast(new Intent(MY_INTENT));
            }
        });

    }


    @Override
    public void onStop ()
    {
        super.onStop();
        manager.unregisterReceiver(receiver);

    }
}
