package edu.nnu.mp.currencyconverter;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.nnu.mp.currencyconverter.classes.CurrencyExchange;
import edu.nnu.mp.currencyconverter.util.Helper;

public class MainActivity extends AppCompatActivity {

    Spinner sp_from ;
    Spinner sp_to ;
    Button btn_convert ;
    ImageButton btn_swap;
    EditText et_amount ;
    TextView tv_convertedAmt;
    TextView tv_created;
    TextView tv_resumed;

    ListView lv_history;


    ArrayList<String> _history = new ArrayList<String>();
    String convertedAmt = "";
    String [] currValues;

    int createdCounter = 0 ;
    int restoredCounter = 0 ;
    int resumedCounter = 0 ;

    String selectedLang = "en";

    static final String SAVED_HISTORY_KEY = "SAVED_HISTORY";
    static final String CONVERTED_AMT_KEY = "CONVERTED_AMT";

    static final String RESUME_COUNTER="RESUME_COUNTER";
    static final String CREATE_COUNTER="CREATE_COUNTER";
    static final String RESTORE_COUNTER="RESTORE_COUNTER";

    static final String SELECTED_LANG_KEY="SELECETED_LANG";

    static final String TAG = "lifecycle" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(savedInstanceState != null)
        {
            //restore elements if exists
            if(savedInstanceState.containsKey(SAVED_HISTORY_KEY))
            {
                _history = savedInstanceState.getStringArrayList(SAVED_HISTORY_KEY);
            }
            if(savedInstanceState.containsKey(CONVERTED_AMT_KEY))
            {
                //set the label
                convertedAmt = savedInstanceState.getString(CONVERTED_AMT_KEY);
            }
            //no need to check if there is associated value or not , cause it will return default value if nothing is found
            restoredCounter = savedInstanceState.getInt(RESTORE_COUNTER,0);
            createdCounter = savedInstanceState.getInt(CREATE_COUNTER, 0);
            resumedCounter = savedInstanceState.getInt(RESUME_COUNTER, 0);


        }
        else
            selectedLang="en";

      //  Log.i ("selectedlang" , selectedLang);
    //   Helper.updateResources(getBaseContext() , selectedLang);

        //set the view based on screen orientation
        setContentView(R.layout.activity_main);
       /*
        if (Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation)
            setContentView(R.layout.activity_main);
        else
        {
            setContentView(R.layout.activity_main_land);
        }
*/
        try {

            //initiate widgets
            sp_from = (Spinner) findViewById(R.id.sp_from);
            sp_to = (Spinner) findViewById(R.id.sp_to);

            btn_convert = (Button) findViewById(R.id.btn_convert);
            btn_swap = (ImageButton) findViewById(R.id.btn_swap);

            et_amount = (EditText) findViewById(R.id.et_amount);
            tv_convertedAmt = (TextView) findViewById(R.id.tv_convertedAmt);

            tv_created = (TextView)findViewById(R.id.created_tv);
            tv_resumed = (TextView) findViewById(R.id.resumed_tv);

            lv_history = (ListView) findViewById(R.id.lv_history);

            //get currencies values
            currValues = getResources().getStringArray(R.array.currencyValue);

            //set spinners values
            ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.
                    createFromResource(getBaseContext(), R.array.currenciesList,
                            R.layout.support_simple_spinner_dropdown_item);
            currencyAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

            sp_from.setAdapter(currencyAdapter);
            sp_to.setAdapter(currencyAdapter);

            //fill history list data
            ArrayAdapter<String> historyAdapter =
                    new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item,_history);
            lv_history.setAdapter(historyAdapter);



            //set Listeners
            setListeners();

            //set converted amount
            tv_convertedAmt.setText(convertedAmt);
            createdCounter = createdCounter + 1; ;
            tv_created.setText(createdCounter + "");
            tv_resumed.setText(resumedCounter + "");

            Log.d(TAG, " oncreate :" +createdCounter) ;
         //   Log.d(TAG, "oncreate - onresume :" +resumedCounter) ;
           // Log.d(TAG, "oncreate - onrestore :" +restoredCounter) ;

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e("@create" ,"there is an Exception");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ar) {
            Helper.updateResources(getBaseContext(),"ar");
         //   selectedLang = "ar";
            recreate();
        }

        //about course
        if (id ==R.id.action_en){
           Helper.updateResources(getBaseContext(),"en");
           // selectedLang="en";
            recreate();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected  void onResume()
    {
        super.onResume();
        resumedCounter ++ ;
        tv_resumed.setText(resumedCounter + "");
        Log.d(TAG , " onreasume :" +resumedCounter) ;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //this function is being called when stoping
        super.onSaveInstanceState(outState);
        // saving the historylist and convertAmt state
        createdCounter = Integer.parseInt(tv_created.getText().toString());
        outState.putStringArrayList(SAVED_HISTORY_KEY, _history);
        outState.putString(CONVERTED_AMT_KEY, convertedAmt);
        outState.putInt(RESUME_COUNTER, resumedCounter);
        outState.putInt(CREATE_COUNTER, createdCounter);
        outState.putString(SELECTED_LANG_KEY , selectedLang);
        outState.putInt(RESTORE_COUNTER, restoredCounter);

        Log.d(TAG , " onSavedInstance ") ;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore history
        if (savedInstanceState != null ) {
            if (savedInstanceState.containsKey(SAVED_HISTORY_KEY)) {

                _history = savedInstanceState.getStringArrayList(SAVED_HISTORY_KEY);
            }

            // Restore converted amount
            if (savedInstanceState.containsKey(CONVERTED_AMT_KEY)){
                convertedAmt = savedInstanceState.getString(CONVERTED_AMT_KEY);
            }

            createdCounter = savedInstanceState.getInt(CREATE_COUNTER, 0);
            resumedCounter = savedInstanceState.getInt(RESUME_COUNTER, 0);
            restoredCounter = savedInstanceState.getInt(RESTORE_COUNTER,0);
            restoredCounter ++ ;

            selectedLang = savedInstanceState.getString(SELECTED_LANG_KEY);


        }

        Log.d(TAG, " onrestore :" +restoredCounter) ;

    }


    protected  void setListeners()
    {
        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //if there is network connection fetch the rates on line,
                    //else use the rates in the resources calculate the rates

                    String fromCurrency = sp_from.getSelectedItem().toString();
                    String toCurrency = sp_to.getSelectedItem().toString();
                    String amount = et_amount.getText().toString();

                    int fromPos = sp_from.getSelectedItemPosition();
                    int toPos = sp_to.getSelectedItemPosition();

                    /*
                    //convert the amount using local ratio values stored in strings.xml file
                    Double result = (Double.parseDouble(amount) /
                            Double.parseDouble(currValues[fromPos]) *
                            Double.parseDouble( currValues[toPos])) * 100;

                    //rounding the final result into nearest 2 decimals
                    Long roundedResult = Math.round(result);
                    String resultstr  = ""+ Double.parseDouble(roundedResult.toString()) / 100.00;
                    */
                    CurrencyExchange onlineCurrObj =
                            new CurrencyExchange(getBaseContext(),
                                    fromCurrency, toCurrency,
                                    amount,
                                    currValues[fromPos], currValues[toPos]);
                    String finalResult = onlineCurrObj.execute().get();

                    convertedAmt = finalResult;
                    tv_convertedAmt.setText(finalResult);

                    // format history string and add it to the list
                    String historyStr = String.format("%s %s = %s %s", amount, fromCurrency, finalResult, toCurrency);
                    if (_history.contains(historyStr)) {
                        _history.remove(historyStr);
                    }
                    _history.add(0, historyStr);
                    lv_history.invalidateViews();
                }
                catch (Exception ex)
                {
                    Log.e("covertBtn", "onClick: "+ex.getMessage());
                }


            }
        });

        btn_swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int toPosition = sp_to.getSelectedItemPosition();
                int fromPosition = sp_from.getSelectedItemPosition();

                sp_to.setSelection(fromPosition, true);
                sp_from.setSelection(toPosition, true);

            }

        });

    }
}
