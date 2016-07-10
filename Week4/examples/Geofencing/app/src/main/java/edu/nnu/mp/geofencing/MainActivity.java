package edu.nnu.mp.geofencing;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient myClient;


    private  static String TAG = "myGeofence";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    @Override
    public void onStart() {
        super.onStart();
        myClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        myClient.disconnect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //define a geofence
        Geofence myGeofence = new Geofence.Builder()
                .setRequestId("An-Najah Uni.")
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                // university location
                .setCircularRegion(32.227364, 35.222238,100 )
                .build();

        //define a geofence request
        GeofencingRequest myRequest = new GeofencingRequest.Builder()
                .addGeofence(myGeofence)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build();


        //Define pendingintent for the service
        Intent intent = new Intent(this, ReceiveGeofenceTransitionService.class);
        PendingIntent pendingIntent = PendingIntent.getService(getBaseContext(), 0, intent, 0);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationServices.GeofencingApi.addGeofences(myClient, myRequest, pendingIntent);

        myClient.disconnect();

    }

    @Override
    public void onConnectionSuspended(int i) {
        myClient.disconnect();
        Log.i(TAG, "connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "connection failed");
        Log.e(TAG, connectionResult.getErrorMessage());
    }
}
