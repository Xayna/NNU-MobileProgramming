package edu.nnu.mp.files;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;

public class MainActivity extends AppCompatActivity {

    TextView tv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.textview) ;
        EditText et = (EditText)findViewById(R.id.editText);
        et.getText().toString();
     //  writeToFile();
        readFromFile();


        if(isExternalStorageWritable())
            writeToSD();
        if(isExternalStorageReadable())
            readFromSD();

    }


    protected void writeToFile ()
    {
        try{
            Writer input = null;
            Log.d("Files" , getFilesDir().getPath());
            Log.d("Files" , getFilesDir().getAbsolutePath());
            Log.d("Files" , getFilesDir().getCanonicalPath());

            File file = new File(getFilesDir() , "myfiles.txt");

            input = new BufferedWriter(new FileWriter(file));

            input.append("Hello ,");
            input.write("This is test, go ahead and play with files");
            input.flush();

            input.close();
          Log.d("Files" , "File has been written");

        }catch(Exception e){
            Log.e("Files", "Could not create file" + e.getMessage());
        }
    }


    protected void readFromFile ()
    {
        try{
            Reader output = null;
            File file = new File(getFilesDir() , "myfiles.txt");

            char [] buffer = new char[100] ;

            output = new BufferedReader(new FileReader(file));

            int charsNum = output.read(buffer);

            tv.setText(new String (buffer));

            Log.d("Files", "readFromFile: num of read chars" + charsNum);

            output.close();
            Log.d("Files","File has been read");

        }catch(Exception e){
            Log.d("Files" ,"Could not open file" + e.getMessage());
        }
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    protected void writeToSD() {
        try {
            Writer input = null;
            Log.d("Files" , "external files path :" +Environment.getExternalStorageDirectory().getPath());
            File file = new File(Environment.getExternalStorageDirectory(), "myExternalFiles.txt");
            input = new BufferedWriter(new FileWriter(file));

            input.append("Hello");
            input.write("Writing to external file is done");
            input.flush();

            input.close();
            Log.d("Files" , "File has been written");
        }catch (Exception ex)
        {
            Log.d("Files", "writeToSD: " + ex.getMessage());
        }
    }
    /* Checks if external storage is available to at least read */

    protected void readFromSD ()
    {
        try{
            Reader output = null;
            File file = new File(Environment.getExternalStorageDirectory() , "myExternalFiles.txt");

            char [] buffer = new char[100] ;

            output = new BufferedReader(new FileReader(file));

            int charsNum = output.read(buffer);


            tv.setText(new String (buffer));

            Log.d("Files", "readFromFile: num of read chars" + charsNum);

            output.close();
            Log.d("Files","File has been read");

        }catch(Exception e){
            Log.d("Files" ,"Could not open file" + e.getMessage());
        }
    }

}
