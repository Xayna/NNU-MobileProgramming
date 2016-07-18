package edu.nnu.mp.broadcasting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by User on 7/9/2016.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Vibrator vibrator = (Vibrator)context.
                getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);

        Toast.makeText(context, "Intent received by My Receiver", Toast.LENGTH_LONG).show();
    }
}
