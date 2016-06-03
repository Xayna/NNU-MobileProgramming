package edu.nnu.mp.intentsexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class message extends AppCompatActivity {

    public static final String TEST_MSG = "testmsg" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        TextView showMsg = (TextView) findViewById(R.id.tv_msg);
/*
        Bundle bundle = getIntent().getExtras();
        String msg = bundle.getString(MainActivity.KEY_MSG);

        Intent i = new Intent() ;
        i.putExtra(TEST_MSG , "كل عام وانت بخير ");
        showMsg.setText(msg);

        setResult(RESULT_OK, i);

*/
        ///

        //finish();

        Bundle bundle = getIntent().getExtras();
        String msg = bundle.getString("msg");

        Intent i = new Intent ();
        i.putExtra(TEST_MSG , "Done");
        setResult(RESULT_OK, i);

        showMsg.setText(msg);

       // finish();


    }
}
