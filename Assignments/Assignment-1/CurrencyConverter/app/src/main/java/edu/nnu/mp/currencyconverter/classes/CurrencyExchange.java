package edu.nnu.mp.currencyconverter.classes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import edu.nnu.mp.currencyconverter.util.Helper;

/**
 * Created by User on 6/2/2016.
 */
public class CurrencyExchange extends AsyncTask<Void,Void,String> {

    String finalResult ;
    String from ;
    String to ;
    String amt;

    String localFromValue;
    String localToValue;
    Context context;
    /*
     * @param context : parent activity context
     * @param from : currency to exchange from
     * @param to : currency to exchange to
     * @param amt : amount to be exchanged
     * @param localFromValue : the local ratio currency to transfer from in case there no internet connection
     * @param LocalToValue : the local ratio currency to transfer to in case there no internet connection
     */
    public CurrencyExchange(Context context, String from, String to, String amt, String localFromValue, String localToValue) {
        this.context = context;
        this.from = from;
        this.to = to;
        this.amt = amt;
        this. localFromValue = localFromValue;
        this. localToValue = localToValue;
    }

    /*
     *@return , return the final result of the converted amount
     */
    @Override
    protected String doInBackground(Void... params) {

        if (Helper.isNetworkAvailable(context))
            //convert the amount using online service
            finalResult = Helper.getCurrencyRate(context, from, to, amt);
        else
            finalResult = null;

        if (finalResult != null) {
            //rounding the final result into nearest 2 decimals
            finalResult = "" + (Math.round(Double.parseDouble(finalResult) * 100)) / 100.00  ;
        }
        else {

            Log.i("finalResult", "NULL");
            //convert the amount using local ratio values stored in strings.xml file
            Double result = (Double.parseDouble(amt) / Double.parseDouble(localFromValue) *
                    Double.parseDouble(localToValue)) * 100;

            //rounding the final result into nearest 2 decimals
            Long roundedResult = Math.round(result);
            finalResult = ""+ Double.parseDouble(roundedResult.toString()) / 100.00;
        }
        return finalResult;
    }


}
