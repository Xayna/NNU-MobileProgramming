package edu.nnu.mp.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class SimpleActivity extends AppCompatActivity
        implements SensorEventListener{

    // to create a compass we need to use 2 types of sensors and
    // get the rotation around the z axis
    SensorManager mySensorManager ;

    Sensor myMagnetometer;
    Sensor myAccelerometer;

    //array to store accelerometer values
    float gravity [] ;
    //array to store magnetometer values
    float geomagnetic [];

    // Rotation around Z axis
    private double rotationInDegrees;

    // Main View
    private RelativeLayout myFrame;

    // View showing compass arrow
    private CompassView myCompassView;

    static final String TAG = "SimpleCompass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        myFrame = (RelativeLayout) findViewById(R.id.frame);
        myCompassView = new CompassView(getApplicationContext());
        myFrame.addView(myCompassView);

        mySensorManager = (SensorManager)
                getSystemService(SENSOR_SERVICE);

        myAccelerometer = mySensorManager.
                getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        myMagnetometer = mySensorManager.
                getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (myMagnetometer == null || myAccelerometer == null)
        {
            Log.i(TAG , "Sensors not available can't continue in compass example");
            finish();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // Accelerometer
        mySensorManager.registerListener
                (this,myAccelerometer,SensorManager.SENSOR_DELAY_UI);
        // magnetometer
        mySensorManager.registerListener
                (this,myMagnetometer,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            //store the accelerometer values in array
            Log.i(TAG, "gravity("+event.values[0]+","+event.values[1]+","+event.values[2] + ")");
            gravity = new float[3];
            System.arraycopy(event.values,0,gravity,0,3);
        }else
            if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            {
                //store the magnetometer values in array
                geomagnetic = new float[3];
                System.arraycopy(event.values,0,geomagnetic,0,3);
            }

        //check if we got readings from the 2 sensors
        if(geomagnetic != null && gravity != null)
        {
            float rotationMatrix[] = new float[9];
            //get the rotation matrix
            //first param for returned rotation matrix
            //the second param for returned inclination matrix
            float inclinationMaxtrix [] = new float[9];
            boolean success = mySensorManager.
                    getRotationMatrix(rotationMatrix,
                            null,gravity,geomagnetic);

            if(success)
            {
                //get screen orientation and axis
                Display display = ((WindowManager)
                        getSystemService(Context.WINDOW_SERVICE)).
                        getDefaultDisplay();
                int rotation = display.getRotation();

                // Initialize axes for remapCoordinateSystem
                int axisX = SensorManager.AXIS_X;
                int axisY = SensorManager.AXIS_Y;

                //we said in coordination system that the axis are rotated wi
                // th the device when we rotate it
                // but this will afect the compass and give us invalid results when we rotate the device
                // to solve this problem we need to remap the rotated axis and set it as it is the axis in the default device stand
                // Determine axes for remapping
                switch (rotation) {
                    // No rotation, typically portrait
                    case Surface.ROTATION_0:
                        axisX = SensorManager.AXIS_X;
                        axisY = SensorManager.AXIS_Y;
                        break;
                    // Landscape typically
                    case Surface.ROTATION_90:
                        axisX = SensorManager.AXIS_Y;
                        axisY = SensorManager.AXIS_MINUS_X;
                        break;
                    // Portrait upside down typically
                    case Surface.ROTATION_180:
                        axisX = SensorManager.AXIS_MINUS_X;
                        axisY = SensorManager.AXIS_MINUS_Y;
                        break;
                    // Landscape upside down typically
                    case Surface.ROTATION_270:
                        axisX = SensorManager.AXIS_MINUS_Y;
                        axisY = SensorManager.AXIS_X;
                        break;
                }

                // Remap coordinate system
                float rotationMatrixB[] = new float[9];
                success = SensorManager.
                        remapCoordinateSystem(rotationMatrix,
                                axisX,
                                axisY, rotationMatrixB);

                if(success) {
                    float orientationMatrix[] = new float[3];

                    SensorManager.
                            getOrientation(rotationMatrixB,
                                    orientationMatrix);

                    //if we set the app orientation into protraite or disabled the rotation
                    // from the device manually used the line below  to get the results and there is no need to
                    //do the mapping
                   //SensorManager.getOrientation(rotationMatrix, orientationMatrix);

                    float rotationInRadians = orientationMatrix[0];

                    //get rotation in degrees
                    rotationInDegrees = Math.toDegrees(rotationInRadians);

                    // redraw
                    myCompassView.invalidate();

                    gravity = null;
                    geomagnetic = null;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //NA , don't need it for now
    }


    private class CompassView extends View {
        private Paint   mPaint = new Paint();
        private Path mPath = new Path();

        // Height, width & center of main View
        int mParentWidth;
        int mParentHeight;
        int mParentCenterX;
        int mParentCenterY;

        public CompassView(Context context) {
            super(context);

            // Wedge-shaped path for the compass
            mPath.moveTo(0, -50);
            mPath.lineTo(-20, 60);
            mPath.lineTo(0, 50);
            mPath.lineTo(20, 60);
            mPath.close();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            // Compute main View size
            mParentWidth = myFrame.getWidth();
            mParentHeight = myFrame.getHeight();
            mParentCenterX = mParentWidth / 2;
            mParentCenterY = mParentHeight / 2;
        }

        @Override protected void onDraw(Canvas canvas) {
            Paint paint = mPaint;

            canvas.drawColor(Color.WHITE);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);

            // Draw everything in the middle of the canvas
            canvas.translate(mParentCenterX, mParentCenterY);
            // Rotate canvas to show orientation to the magnetic North Pole
            canvas.rotate((float) -rotationInDegrees);
            // Draw the arrow
            canvas.drawPath(mPath, mPaint);
        }
    }
}
