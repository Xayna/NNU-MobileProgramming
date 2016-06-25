package edu.nnu.mp.currencyconverter.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * Created by User on 6/2/2016.
 */
public class Helper {


    static final String TO_TAG = "To";
    static final String FROM_TAG = "From";
    static final String RATE_TAG = "Rate";


    static final String CURR_URL = "http://rate-exchange.herokuapp.com/fetchRate?from=%s&to=%s&q=%s";
            //this link is not working any more
            //"http://rate-exchange.appspot.com/currency?from=%s&to=%s&q=%s";
    /*
     *  @param context, Parent activity context
     *  @param fromCurr, currency to convert from
     *  @param toCurr, currency to convert to
     *  @param amount, amount to be converted
     *
     *  @return, return the converted amount as string
     */
    public static String getCurrencyRate (Context context , String fromCurr, String toCurr, String amount) {
        try {
            //format the online service URL
            String fullUrl = String.format(CURR_URL, fromCurr, toCurr, amount);

            //initialise json object from the returned json string
            JSONObject currObj = new JSONObject(getJsonCurrencyObject(fullUrl));
            //get the rate form the json object
            return currObj.getString(RATE_TAG);
        } catch (Exception ex)
        {
            //  Toast.makeText(context,"Error while creating JSON OBJECT" , Toast.LENGTH_SHORT).show();
            Log.e("getCurrencyRate-helper" , ex.getMessage());
            ex.printStackTrace();
        }

        return null;

    }
    /*
     * @param url, the online service url
     *
     * @return, the parsed json string from service url
     */
    public static String getJsonCurrencyObject (String myurl)
    {
        StringBuilder strBuilder = new StringBuilder();

        try {
            URL url = new URL(myurl);
            HttpURLConnection connection =(HttpURLConnection) url.openConnection();

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                strBuilder.append(line);
            }
            inputStream.close();


        }catch(MalformedURLException ex) {
            Log.d("HttpURLConnection", "getJsonCurrencyObject: " + ex.getMessage());
        }

        catch (IOException e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
            e.printStackTrace();
        }
        catch (Exception ex)
        {
            Log.d("GeneralException" , ex.getMessage());
        }

        return strBuilder.toString();
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            //define object of ConnectivityManager
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //define object of NetworkInfo and get detailed info about
            // the currently active default data network from ConnectivityManager
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            //check connection
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        catch (Exception ex)
        {
            Log.e("Helper", "isNetworkAvailable: " + ex.getMessage() );
            return false;
        }
    }

    public static void updateResources(Context context, String language) {
        //set the language of the app
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
