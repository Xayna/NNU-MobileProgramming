package edu.nnu.mp.hellomp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MyActivity";

    //This is the first method to be called when the app is started
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This function will set and initialize the UI for the activity
        setContentView(R.layout.activity_main);

        //define toolbar object and set it to the activity toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //set the toolbar as the app bar for the activity.
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Complete this function to open a website mentioned in the comments", Snackbar.LENGTH_LONG)
                       .show();
                //learn more about snackbar
                //complete this code to open the following link when clicking on this FloatingActionButton
                //https://developer.android.com/training/snackbar/index.html

                //you can get more information about do's and don'ts in snackbar usage
                //https://www.google.com/design/spec/components/snackbars-toasts.html

                //check this link about activities to help you in completing this task
                //https://developer.android.com/training/basics/intents/sending.html

            }
        });

        final EditText et_name = (EditText) findViewById(R.id.et_name);

        Button btn_submit = (Button)findViewById(R.id.btn_submit_name);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view){
                String studentName = (et_name.getText().toString().isEmpty()? getString(R.string.alter_student_name) : et_name.getText().toString());
                String welcome = String.format(getString(R.string.welcome_student_msg), studentName) ;
                Toast.makeText(getBaseContext(), welcome , Toast.LENGTH_LONG).show();


            }
        });

        Log.i(TAG, "onCreate: I am created");
    }

    public void startAboutActivity() {
        Intent intent = new Intent(MainActivity.this, About.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //about course
        if (id ==R.id.action_about){
            startAboutActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: I am started");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: I am restarting");
    }

    //add the rest of the activity lifecycle states and test it
    // find out how it works
}
