package edu.nnu.mp.fragments;


import android.app.PendingIntent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class PFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager
                = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction
                = fragmentManager.beginTransaction();

        Fragment fragment ;

        if(getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT)
        {
            fragment = new Fragment1();

            fragmentTransaction.replace(android.R.id.content , fragment);

        }else{
            fragment = new Fragment2();

            fragmentTransaction.replace(android.R.id.content , fragment);

        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
