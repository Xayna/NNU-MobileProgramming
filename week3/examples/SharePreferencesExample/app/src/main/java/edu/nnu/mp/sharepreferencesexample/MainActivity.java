package edu.nnu.mp.sharepreferencesexample;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    public static  String SWITCH_WIFI = "SWITCH_WIFI";
    public static String APP_LANG = "APP_LANG";
    public static String APP_NOTIFICATION = "APP_NOTIFICATION"    ;

    Switch switch_wifi ;
    CheckBox cb_notification;
    Spinner sp_lang ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set views
        switch_wifi = (Switch) findViewById(R.id.switch_wifi);
        cb_notification = (CheckBox) findViewById(R.id.cb_notification);
        sp_lang = (Spinner) findViewById(R.id.sp_lang);

        //set listners
        setViewsListeners();


        //set values
        setViewsValues();
    }

    protected  void setViewsListeners()
    {
        switch_wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesHelper.setSharePref(getBaseContext(),SWITCH_WIFI,isChecked , getBaseContext().MODE_PRIVATE);

            }
        });



        cb_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesHelper.setSharePref(getBaseContext(),APP_NOTIFICATION,isChecked , getBaseContext().MODE_PRIVATE);
            }
        });

        sp_lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lang = sp_lang.getSelectedItem().toString();
                SharedPreferencesHelper.setSharePref(getBaseContext(),APP_LANG,position , getBaseContext().MODE_PRIVATE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
    }



    protected void setViewsValues()
    {
        sp_lang.setSelection(SharedPreferencesHelper.getIntSharedPref(getBaseContext() , APP_LANG , MODE_PRIVATE));
        switch_wifi.setChecked(SharedPreferencesHelper.getBoolSharedPref(getBaseContext(),SWITCH_WIFI,MODE_PRIVATE));
        cb_notification.setChecked(SharedPreferencesHelper.getBoolSharedPref(getBaseContext(),APP_NOTIFICATION,MODE_PRIVATE));
    }


}
