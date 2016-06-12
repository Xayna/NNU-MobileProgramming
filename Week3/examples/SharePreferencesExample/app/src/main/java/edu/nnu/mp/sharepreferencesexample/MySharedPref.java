package edu.nnu.mp.sharepreferencesexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class MySharedPref extends AppCompatActivity {

    public static  final String MY_SHARED_PREF_FILE_NAME = "mysharedPref";
    public static final String IS_ROBOT_KEY="isrobot" ;
    CheckBox cb_robot ;
    EditText et_account ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shared_pref);

        //initialize views
        cb_robot = (CheckBox) findViewById(R.id.cb_robot);
        et_account = (EditText) findViewById(R.id.et_account);

        //set listeners
        cb_robot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //save the values in shared preferences
                SharedPreferences myPref = getBaseContext().getSharedPreferences(MY_SHARED_PREF_FILE_NAME , Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = myPref.edit();

                editor.putBoolean(IS_ROBOT_KEY , isChecked);

                editor.commit();


                //do action
            }
        });


        //setValues
        setViewsValues ();
    }


    protected  void setViewsValues() {
        SharedPreferences readPref = getBaseContext().getSharedPreferences(MY_SHARED_PREF_FILE_NAME , MODE_PRIVATE);
        boolean defaultValue = false ;
        boolean  result = readPref.getBoolean(IS_ROBOT_KEY, defaultValue);
        cb_robot.setChecked(result);
    }


}
