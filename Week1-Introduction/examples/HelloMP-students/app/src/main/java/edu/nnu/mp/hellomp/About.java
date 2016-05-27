package edu.nnu.mp.hellomp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This method is used to set the UI
        setContentView(R.layout.activity_about);

        //set toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);

        //set FloatingActionButton fab_outline along with its listener


    }

    //complete the function
    private  void startCourseOutline (){

    }

}
