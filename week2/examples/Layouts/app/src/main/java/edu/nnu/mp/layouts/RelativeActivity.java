package edu.nnu.mp.layouts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RelativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative);

        Spinner sp_colors = (Spinner) findViewById(R.id.colors);
        Spinner sp_colors2 = (Spinner) findViewById(R.id.color2);

         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_colors, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_colors.setAdapter(adapter);
        sp_colors2.setAdapter(adapter);


    }


}
