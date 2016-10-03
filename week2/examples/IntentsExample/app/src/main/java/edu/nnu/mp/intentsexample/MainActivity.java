package edu.nnu.mp.intentsexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
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


        //initialize views
        final EditText name_et = (EditText)findViewById(R.id.et_name);
        final EditText byear_et = (EditText)findViewById(R.id.et_year);
        final EditText msg = (EditText)findViewById(R.id.et_msg);

        Button sendMsg = (Button) findViewById(R.id.btn_msg);
        Button calculate = (Button)findViewById(R.id.btn_calculate);
        Button openSite = (Button) findViewById(R.id.btn_openSite);
        final Button camera = (Button)findViewById(R.id.btn_camera);


        //you can find more information about asset in
        //http://www.codejava.net/java-core/the-java-language/java-keyword-assert
        assert sendMsg != null;
        //set onclick function to sendMsg button
        //send message to message activity
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // define explicit intent ( source , destination )
                Intent intent = new Intent(MainActivity.this, message.class);
                // pass data to new message activity
                intent.putExtra(KEY_MSG , msg.getText().toString() );
                //message activity will return data for me so I will start an activity for result
                startActivityForResult(intent,MESSAGE_REQUEST_CODE);

            }
        });



        //set onclick function to calculate age button
        //send name , birth year to agecalculator activity
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


        camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent cameraIntent = new Intent();
                //"android.media.action.IMAGE_CAPTURE"
                cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent , 0);
            }
        });

        assert openSite != null;
        openSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://sites.google.com/site/ntipfall2016/");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                // or we can define all in one step using the constructor
                // Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CALCULATE_REQUEST_CODE)
        {
            String msg = data.getStringExtra("message");
            Toast.makeText(getBaseContext(),msg, Toast.LENGTH_LONG).show();

        }
        else if(requestCode == MESSAGE_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK) {
                String str = data.getStringExtra(message.TEST_MSG);
                Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
            }
            
        }
    }
}
