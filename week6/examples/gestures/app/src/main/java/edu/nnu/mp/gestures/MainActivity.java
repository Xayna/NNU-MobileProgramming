package edu.nnu.mp.gestures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    // UI widgets
    private ViewFlipper mFlipper;
    private TextView mTextView1, mTextView2;

    // State of the UI
    private int mCurrentLayoutState, mCount;

    // For detecting gestures
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initial state
        mCurrentLayoutState = 0;

        mFlipper = (ViewFlipper) findViewById(R.id.main_flipper);
        mTextView1 = (TextView) findViewById(R.id.textView1);
        mTextView2 = (TextView) findViewById(R.id.textView2);
        mTextView1.setText(String.valueOf(mCount));

        mGestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        if (velocityX < -10.0f) {
                            // Left fling!
                            mCurrentLayoutState = mCurrentLayoutState == 0 ? 1
                                    : 0;
                            // Switch layout and increase count
                            switchLayoutStateTo(mCurrentLayoutState);
                        }
                        return true;
                    }
                });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Pass on touch events to gesture detector
        return mGestureDetector.onTouchEvent(event);
    }

    public void switchLayoutStateTo(int switchTo) {
        mCurrentLayoutState = switchTo;

        mFlipper.setInAnimation(inFromRightAnimation());
        mFlipper.setOutAnimation(outToLeftAnimation());

        mCount++;
        Log.i("switch" , switchTo + "");
        if (switchTo == 0) {
            mTextView1.setText(String.valueOf(mCount));
        } else {

            mTextView2.setText(String.valueOf(mCount));
        }

        mFlipper.showPrevious();
    }

    private Animation inFromRightAnimation() {
        // In animation
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f, // +100% fromXValue
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(500);
        inFromRight.setInterpolator(new LinearInterpolator());
        return inFromRight;
    }

    private Animation outToLeftAnimation() {
        // Out animation
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f, // -100% toXValue
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(500);
        outtoLeft.setInterpolator(new LinearInterpolator());
        return outtoLeft;
    }

}
