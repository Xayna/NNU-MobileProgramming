package edu.nnu.mp.fragments;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by User on 2/12/2017.
 */


public class Fragment1 extends Fragment {
    public static String TAG ="Fragment 1";
    //override onCreateView to load the xml layout.
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    )
    {
        Log.i(TAG , "onCreateView");
        //inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment1,
                container,false);
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.i(TAG , "onAttach");

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(TAG , "onCreate");
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState)
    {
        super.onActivityCreated(saveInstanceState);
        Log.i(TAG , "onActivityCreated");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(TAG, "onResume");
    }
    @Override
    public void onPause()
    {
        super.onPause();
        Log.i(TAG, "onPause");
    }
    @Override
    public void onStop()
    {
        super.onStop();
        Log.i(TAG, "onStop");
    }
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Log.i(TAG , "onDestroyView");
    }

    @Override
    public void onDestroy ()
    {
        super.onDestroy();
        Log.i(TAG , "onDestroy");
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.i(TAG, "onDetach");
    }
}
