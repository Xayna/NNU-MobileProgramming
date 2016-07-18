package edu.nnu.mp.wakelock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Intent serviceIntent ;
    Button startService_btn ;
    Button stopService_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceIntent = new Intent(getApplicationContext(),Service.class);

        startService_btn = (Button) findViewById(R.id.startBtn);
        stopService_btn = (Button) findViewById(R.id.stopBtn);

        startService_btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Log.i("wakeLock" , "start") ;
                 startService(serviceIntent);
            }
        });
        stopService_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serviceIntent);
            }
        });

      //  startService(serviceIntent);
    }
}
