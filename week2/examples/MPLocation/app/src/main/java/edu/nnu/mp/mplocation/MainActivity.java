package edu.nnu.mp.mplocation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;

import java.security.Key;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_HISTORY = "searchHistory";

    //private ArrayList<String> _history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if(savedInstanceState !=null && savedInstanceState.containsKey(KEY_HISTORY) )
        {
            _history = savedInstanceState.getStringArrayList(KEY_HISTORY);
        }
        else
        {
            _history = new ArrayList<String>();
        }
        */
        setContentView(R.layout.activity_main);

        //Define Array to keep a list of searching history
        //_history = new ArrayList<String>();

        // Initialize widgets
        final EditText queryEditText = (EditText) findViewById(R.id.queryEditText);
        final Button goButton = (Button) findViewById(R.id.goButton);
        //final ListView historyView = (ListView) findViewById(R.id.historyView);

        // Set adapter for ListView
        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, _history);
        historyView.setAdapter(adapter);
        */

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Process text
                    String query = queryEditText.getText().toString().trim();
                    if (!query.isEmpty()) {
                        // Let' start a Google Maps search for that

                        //add the search query to history
                        //_history.add(query);

                        // Replace spaces with '+' in URL
                        query.replace(' ', '+');
                        // Create a Uri from intent string , we used  Google Maps query format
                        Uri myUri = Uri.parse("geo:0,0?q=" + query);

                        //create an intent from gmmUri , set the action to view
                        Intent mapSearchIntent = new Intent(
                                android.content.Intent.ACTION_VIEW,
                                myUri);
                        // Make the Intent explicit by setting the Google Maps package
                        mapSearchIntent.setPackage("com.google.android.apps.maps");

                        // use resolveActivity to check if there is a receiving system for the intent
                        if(mapSearchIntent.resolveActivity(getPackageManager())!= null)
                        {
                            //start the activity
                            startActivity(mapSearchIntent);
                        }
                    }

                } catch (Exception ex) {
                    Log.e(TAG, "onClick:  " +
                             ex.toString() );
                }
            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "The activity is visible and about to be started.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "The activity is visible and about to be restarted.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "The activity is visible and has focus (it is \"resumed\").");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Another activity is taking focus (this one is \"paused\").");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "The activity is no longer visible (it is \"stopped\").");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "The activity is about to be destroyed.");
    }

    /*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "The activity's state is about to be saved.");
        outState.putStringArrayList(KEY_HISTORY, _history);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "The activity's state is about to be restored.");
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_HISTORY)) {
            // Restore history
            _history = savedInstanceState.getStringArrayList(KEY_HISTORY);
        }
    }
    */
}
