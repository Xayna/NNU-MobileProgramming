package edu.nnu.mp.intentsexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class email extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText et_from = ( EditText) findViewById(R.id.et_from);
        EditText et_to = (EditText) findViewById(R.id.ed_to);
        EditText et_msg = (EditText) findViewById(R.id.et_message);

        String msg = getIntent().getExtras().getString(MainActivity.KEY_FROM);
        et_from.setText(msg);
        /*
        Bundle bundle = getIntent().getExtras();
        String msg = bundle.getString(MainActivity.KEY_MSG);


        //getIntent() : Return the intent that started this activity.
        Bundle extras = getIntent().getExtras();

        //Extract the data (name , age ) send to AgeCalculator activity
        String name = extras.getString("personName").toString();
*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.
                        make(view, "Replace with your own action",
                                Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/
                setResult(RESULT_OK);
                finish();
            }
        });
    }

}
