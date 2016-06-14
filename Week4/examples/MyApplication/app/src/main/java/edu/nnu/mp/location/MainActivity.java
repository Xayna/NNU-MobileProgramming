package edu.nnu.mp.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //location object to hold best location
    private Location myBestLocation;

    //Location listener
    private LocationListener myLocListener;

    //Location manager
    private LocationManager myLocManager;

    private static float MIN_ACCURACY = 10;
    private static float MIN_DISTANCE = 10;
    private static float TWO_MINUTES = 120000;

    private static int POLLING_FREQ = 60000;
    private static int MEASURE_TIME = 60000;


    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.tv);
        textView.setText("");
        //get location manager , if it is does not exists or not supported apport the programme , no need to contiune
        myLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (myLocManager == null)
            finish();


        //set location listener
        myLocListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //is new location better than current myBestLocation ?
                if (myBestLocation == null || location.getAccuracy() < myBestLocation.getAccuracy()) {
                    myBestLocation = location;

                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        List<String> matchingProviders = myLocManager.getAllProviders();
        String str = "number of providers " + matchingProviders.size() + " \n";
        Log.i("oncreate" , str) ;
        for (String provider : matchingProviders) {
            Log.i("oncreate" , provider) ;
            str += " " + provider + " : ";
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("oncreate" , "no permission") ;
                return;
            }
            Location location = myLocManager.getLastKnownLocation(provider);
            if (location != null) {
                Log.i("oncreate" , "location not null") ;
               str += " (" + location.getLongitude() + "," + location.getLatitude() + "," +location.getAltitude() +")\n" ;
            }
        }

        Log.i("oncreate" , str) ;

        textView.setText(str);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //check if permission are not rejected by the user
        if (ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //no need to update the listener while we are on pause , this will save buttery from being drained
        myLocManager.removeUpdates(myLocListener);
    }

    protected void onResume() {
        super.onResume();
        // Is initial reading "good enough”?
        // Otherwise, register for further location updates.
        if (null == myBestLocation
                || myBestLocation.getAccuracy() > MIN_ACCURACY
                || myBestLocation.getTime() < System.currentTimeMillis() - TWO_MINUTES) {
            // Register for network location updates
            if (null != myLocManager
                    .getProvider(LocationManager.NETWORK_PROVIDER)) {

            }
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                myLocManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, POLLING_FREQ,
                        MIN_DISTANCE, myLocListener);
            }

            // Register for GPS location updates
            if (null != myLocManager
                    .getProvider(LocationManager.GPS_PROVIDER)) {
                myLocManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, POLLING_FREQ,
                        MIN_DISTANCE, myLocListener);
            }

        // Unregister location listeners after specified amount of time
            Executors.newScheduledThreadPool(1).schedule(new Runnable() {
            @Override
            public void run() {

                if (ActivityCompat.checkSelfPermission
                        (getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission
                                (getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
                }
                    Log.i("onResume", "location updates removed");

                        myLocManager.removeUpdates(myLocListener);
            }
             }, MEASURE_TIME, TimeUnit.MILLISECONDS);

        }


}
