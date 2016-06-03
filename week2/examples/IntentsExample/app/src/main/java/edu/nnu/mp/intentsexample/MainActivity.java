package edu.nnu.mp.intentsexample;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int CALCULATE_REQUEST_CODE= 200;
    public static final int MESSAGE_REQUEST_CODE = 10 ;

    public static final String KEY_MSG = "myKey" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendMsg = (Button) findViewById(R.id.btn_msg);

        final EditText name_et = (EditText)findViewById(R.id.et_name);
        final EditText byear_et = (EditText)findViewById(R.id.et_year);

        final EditText msg = (EditText)findViewById(R.id.et_msg);



        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, message.class);
                intent.putExtra(KEY_MSG , msg.getText().toString() );
                startActivityForResult(intent,MESSAGE_REQUEST_CODE);

            }
        });


        Button calculate = (Button)findViewById(R.id.btn_calculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_et.getText().toString() ;
                String byear_str = byear_et.getText().toString();
                int byear = 0 ;

                if(byear_str != null && !byear_str.isEmpty())
                     byear = Integer.parseInt (byear_str);

                Intent i = new Intent(MainActivity.this, AgeCalculator.class);

                i.putExtra("personName" , name);
                i.putExtra("personAge" , byear);

                startActivityForResult(i ,CALCULATE_REQUEST_CODE  );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CALCULATE_REQUEST_CODE)
        {
            String msg = data.getStringExtra("message");
            Toast.makeText(getBaseContext(),msg, Toast.LENGTH_LONG).show();

        }
        else if(requestCode == MESSAGE_REQUEST_CODE)
        {
            String str = data.getStringExtra(message.TEST_MSG);
            Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
        }
    }
}
