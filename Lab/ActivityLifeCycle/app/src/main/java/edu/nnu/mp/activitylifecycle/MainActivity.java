package edu.nnu.mp.activitylifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public  static  final  String TAG = "LifeCycle";
    public static  final String CLASS_TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, CLASS_TAG+" : onCreate");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d(TAG, "MainActivity : OnRestart");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "MainActivity : Onstart");

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG, "MainActivity : onResume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(TAG, "MainActivity : onPause");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(TAG, "MainActivity : OnStop");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "MainActivity : OnDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.menu_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_dialog:
                Log.i(TAG , "dialog activity clicked");
                Intent intent = new Intent(MainActivity.this , DialogActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_activity:
                Log.i(TAG,"basic activity clicked");
                startActivity(new Intent(MainActivity.this , BasicActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
