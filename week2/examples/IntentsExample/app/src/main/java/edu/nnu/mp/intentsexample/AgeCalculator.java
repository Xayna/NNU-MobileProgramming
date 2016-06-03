package edu.nnu.mp.intentsexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class AgeCalculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_calculator);

        //getIntent() : Return the intent that started this activity.
        Bundle extras = getIntent().getExtras();

        //Extract the data (name , age ) send to AgeCalculator activity
        String name = extras.getString("personName").toString();
        int byear = extras.getInt("personAge");

        int age = Calendar.getInstance().get(Calendar.YEAR)- byear ;

        TextView msg = (TextView) findViewById(R.id.tv_msg);
        Button btn = (Button)findViewById(R.id.button);

        //set the msg text view
        msg.setText("Hello " + name + " , you are " +age + " old !" );

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("message" , "Thanks for using may app");

                setResult(MainActivity.CALCULATE_REQUEST_CODE ,i );

                finish();
            }
        });
    }
}
