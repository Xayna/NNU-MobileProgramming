package edu.nnu.mp.customgestures;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements GestureOverlayView.OnGesturePerformedListener {

    public final static String TAG = "CustomGestures";


    private int mBgX = Color.RED;
    private int mBgV = Color.GREEN;
    private int mStartBgColor = Color.WHITE;

    // Library for detection
    private GestureLibrary mLibrary;

    // Gestures
    private String CHECK = "check";
    private String CROSS = "cross";
    private String CIRCLE = "circle";
    private String CIRCLE2 = "circle2";
    private String CIRCLE3 = "circle3";

    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = (RelativeLayout) findViewById(R.id.main);

        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!mLibrary.load()) {
            finish();
        }

        // Make this the target of gesture detection callbacks
        GestureOverlayView gestureView = (GestureOverlayView) findViewById(R.id.gestures_overlay);
        gestureView.addOnGesturePerformedListener(this);

    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        // Get gesture predictions
        ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

        // Get highest-ranked prediction
        if (predictions.size() > 0) {
            Prediction prediction = predictions.get(0);

            // Ignore weak predictions
            if (prediction.score > 2.0) {
                if (prediction.name.equals(CHECK)) {
                    mLayout.setBackgroundColor(mBgV);
                    Toast.makeText(this, "Check!", Toast.LENGTH_SHORT)
                            .show();
                } else if (prediction.name.equals(CROSS)) {
                    mLayout.setBackgroundColor(mBgX);
                    Toast.makeText(this, "Cross!", Toast.LENGTH_SHORT)
                            .show();
                } else if (prediction.name.equals(CIRCLE) || prediction.name.equals(CIRCLE2) || prediction.name.equals(CIRCLE3))
                {
                    mLayout.setBackgroundColor(mStartBgColor);

                    Toast.makeText(this, "Circle!", Toast.LENGTH_SHORT)
                            .show();

                }else
                {
                    Toast.makeText(this, "Unsupported gesture", Toast.LENGTH_SHORT)
                            .show();
                }
            } else {
                Toast.makeText(this, "No prediction", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
