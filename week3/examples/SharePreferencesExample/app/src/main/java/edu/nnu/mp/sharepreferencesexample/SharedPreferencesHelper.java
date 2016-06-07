package edu.nnu.mp.sharepreferencesexample;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 6/6/2016.
 */
public class SharedPreferencesHelper {

    public static String SHARED_PREFERENCES_FILE = "mySharedPref" ;
    public static void setSharePref(Context context, String key, int value, int mode)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE ,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(key, value);

        editor.commit();
    }


    public static void setSharePref(Context context, String key, String value , int mode)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE ,mode);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(key, value);

        editor.commit();
    }


    public static void setSharePref(Context context, String key, boolean value , int mode)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE ,mode);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putBoolean(key, value);

        editor.commit();
    }


    public  static int getIntSharedPref(Context context , String key, int mode)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE , mode);
        int defaultValue = 0;
        return sharedPref.getInt(key, defaultValue);

    }



    public  static String getStringSharedPref(Context context , String key, int mode)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE , mode);
        String defaultValue = "";
        return sharedPref.getString(key, defaultValue);

    }

    public  static boolean getBoolSharedPref(Context context , String key, int mode)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE , mode);
        boolean defaultValue = false;
        return sharedPref.getBoolean(key, defaultValue);

    }
}
