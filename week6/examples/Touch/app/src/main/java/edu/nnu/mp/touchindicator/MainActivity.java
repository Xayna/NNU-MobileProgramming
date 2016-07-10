package edu.nnu.mp.touchindicator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    protected static final String TAG = "TouchIndicator";

    // Minimum distance for move
    private static final int MIN_DXDY = 2;

    // We only have so many fingers ;)
    final private static int MAX_TOUCHES = 20;

    // List of TouchViews
    final private static LinkedList<TouchView> mInactiveTouches = new LinkedList<TouchView>();

    // Set of TouchViews currently visible on the display
    @SuppressLint("UseSparseArrays")
    final private static Map<Integer, TouchView> mActiveTouches = new HashMap<>();

    // Layout used to draw in
    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = (RelativeLayout) findViewById(R.id.main);

        // Initialize pool of TouchViews.
        initViews();


        // Create and set on touch listener
        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getActionMasked()) {
                    // Show new TouchView
                    case MotionEvent.ACTION_DOWN:
                        Log.i(TAG,"ACTION_DOWN");
                    case MotionEvent.ACTION_POINTER_DOWN: {
                        Log.i(TAG,"ACTION_POINTER_DOWN");
                        int pointerIndex = event.getActionIndex();
                        int pointerID = event.getPointerId(pointerIndex);
                        // Grab one if available (head of list)
                        TouchView marker = mInactiveTouches.remove();

                        if (null != marker) {
                            mActiveTouches.put(pointerID, marker);

                            marker.setXLoc(event.getX(pointerIndex));
                            marker.setYLoc(event.getY(pointerIndex));

                            // Update size
                            updateTouches(mActiveTouches.size());
                            mLayout.addView(marker);
                        }
                        break;
                    }

                    // Remove one MarkerView
                    case MotionEvent.ACTION_UP:
                        Log.i(TAG,"ACTION_UP");
                    case MotionEvent.ACTION_POINTER_UP: {
                        Log.i(TAG,"ACTION_POINTER_UP");
                        int pointerIndex = event.getActionIndex();
                        int pointerID = event.getPointerId(pointerIndex);

                        TouchView marker = mActiveTouches.remove(pointerID);

                        if (null != marker) {
                            mInactiveTouches.add(marker);
                            // Update size
                            updateTouches(mActiveTouches.size());

                            mLayout.removeView(marker);
                        }
                        break;
                    }

                    // Move all currently active MarkerViews
                    case MotionEvent.ACTION_MOVE: {
                        Log.i(TAG,"ACTION_MOVE");
                        // Iterate over Pointer IDs
                        for (int idx = 0; idx < event.getPointerCount(); idx++) {
                            int ID = event.getPointerId(idx);
                            TouchView marker = mActiveTouches.get(ID);
                            if (null != marker) {
                                // Redraw only if over minimum distance
                                if (Math.abs(marker.getXLoc() - event.getX(idx)) > MIN_DXDY
                                        || Math.abs(marker.getYLoc()
                                        - event.getY(idx)) > MIN_DXDY) {
                                    // Set new location
                                    marker.setXLoc(event.getX(idx));
                                    marker.setYLoc(event.getY(idx));

                                    // Request redraw
                                    marker.invalidate();
                                }
                            }
                        }
                        break;
                    }

                    case MotionEvent.ACTION_CANCEL:
                        Log.i(TAG , "ACTION_CANCEL");
                    default:
                        Log.i(TAG, "unhandled action");
                }
                return true;
            }

            // update number of touches on each active TouchView
            private void updateTouches(int numActive) {
                for (TouchView marker : mActiveTouches.values()) {
                    marker.setTouches(numActive);
                }
            }
        });
    }

    private void initViews() {
        // Initialize the list with 20 views
        for (int idx = 0; idx < MAX_TOUCHES; idx++) {
            mInactiveTouches.add(new TouchView(this, -1, -1));
        }
    }

    private class TouchView extends View {
        private float mX, mY;
        final static private int MAX_SIZE = 250; // use ~400 for tablets
        private int mTouches = 0;
        final private Paint mPaint = new Paint();

        public TouchView(Context context, float x, float y) {
            super(context);
            mX = x;
            mY = y;
            mPaint.setStyle(Paint.Style.FILL);

            // Random background color
            Random rnd = new Random();
            mPaint.setARGB(255, rnd.nextInt(256), rnd.nextInt(256),
                    rnd.nextInt(256));
        }

        float getXLoc() {
            return mX;
        }

        void setXLoc(float x) {
            mX = x;
        }

        float getYLoc() {
            return mY;
        }

        void setYLoc(float y) {
            mY = y;
        }

        void setTouches(int touches) {
            mTouches = touches;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(mX, mY, MAX_SIZE / mTouches, mPaint);
        }
    }
}
