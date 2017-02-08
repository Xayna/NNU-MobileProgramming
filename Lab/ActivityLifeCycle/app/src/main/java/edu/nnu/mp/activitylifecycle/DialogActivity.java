package edu.nnu.mp.activitylifecycle;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DialogActivity extends Activity {

    public  static  final  String TAG = "LifeCycle";
    public static  final String CLASS_TAG = "DialogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

    }
}
