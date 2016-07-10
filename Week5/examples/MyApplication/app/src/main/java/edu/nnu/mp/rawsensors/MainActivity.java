package edu.nnu.mp.rawsensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager mySensorManager;
    Sensor myAccelerometer ;

    TextView tv_x ;
    TextView tv_y ;
    TextView tv_z ;

    TextView tv_accuracy;

    long myLastUpdate ;
    static final String TAG = "RowSensor" ;

    // 1000 milles are too long so lets use 300
    static final int THRESHOLD = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get textviews
        tv_x = (TextView) findViewById(R.id.x);
        tv_y = (TextView) findViewById(R.id.y);
        tv_z = (TextView) findViewById(R.id.z);

        tv_accuracy = (TextView) findViewById(R.id.accuracy);
        //get sensor manager
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //get the accelerometer sensor
        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //check if the sensor is available or not
        if (myAccelerometer == null) {
            Log.i("RowData" , "Accelerometer is found , Exiting the app ..");
            finish();
        }
    }

    @Override
    protected  void onResume(){
        super.onResume();

        mySensorManager.registerListener(this ,myAccelerometer,SensorManager.SENSOR_DELAY_UI);

        myLastUpdate = SystemClock.currentThreadTimeMillis();

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //check which sensor fired the event
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            // the data keeps changing instantly so we need to reduce the readings by using time threshold
            Long currentTime = SystemClock.currentThreadTimeMillis();
            Log.i(TAG  ,  currentTime-myLastUpdate + "") ;
          if(currentTime-myLastUpdate > THRESHOLD ) {
                myLastUpdate = currentTime ;

                //get the data using event.values
                float x = event.values[0], y = event.values[1], z = event.values[2];

                //show the data
                tv_x.setText(String.valueOf(x));
                tv_y.setText(y + "");
                tv_z.setText(z + "");


           }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.i(TAG , accuracy + "");
            String status = "";
            switch (accuracy)
            {
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH :
                    status = "SENSOR_STATUS_ACCURACY_HIGH";
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                    status = "SENSOR_STATUS_ACCURACY_LOW";
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                    status = "SENSOR_STATUS_ACCURACY_MEDIUM";
                    break;
                case SensorManager.SENSOR_STATUS_NO_CONTACT:
                    status = "SENSOR_STATUS_NO_CONTACT";
                    break;
                case SensorManager.SENSOR_STATUS_UNRELIABLE:
                    status = "SENSOR_STATUS_UNRELIABLE";
                    break;

            }
            tv_accuracy.setText(status + " : " +accuracy + "");
        }
    }
}
