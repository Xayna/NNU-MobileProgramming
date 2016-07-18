package edu.nnu.mp.wakelock;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by User on 7/13/2016.
 */

public class Service extends android.app.Service implements SensorEventListener {

    static final String  TAG = "wakeLock";
    SensorManager sensorManager ;
    Sensor lightSensor;

    PowerManager powerManager;
    PowerManager.WakeLock wakeLock ;

    float prevouisLux = 1;
    float currentLux  = -1;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.i(TAG, "ServiceSarted");

        //initiat the sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //get the light sensor
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //register the sensor with the sensoreventlistener
        sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);


        //initiate the power manager
        powerManager = (PowerManager)getSystemService(POWER_SERVICE);
        //get the wake lock
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"LightingWakeLock");
        wakeLock.acquire();

        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {

        Log.i(TAG, "ServiceDestroyed");

        sensorManager.unregisterListener(this);

        if(wakeLock != null)
            wakeLock.release();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i(TAG, "SensorChanged");
        if(event.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            currentLux = event.values[0];

            Log.d(TAG, "Current Lux: " + String.valueOf(currentLux));
            Log.d(TAG, "Previous Lux: " + String.valueOf(prevouisLux));
        }

        if (currentLux == 0 && prevouisLux != 0) {
            // Turned dark!
            Log.d(TAG, "Dark!!");
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        }

        prevouisLux = currentLux;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
