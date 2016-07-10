package edu.nnu.mp.geofencing;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ReceiveGeofenceTransitionService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "edu.nnu.mp.geofencing.action.FOO";
    private static final String ACTION_BAZ = "edu.nnu.mp.geofencing.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "edu.nnu.mp.geofencing.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "edu.nnu.mp.geofencing.extra.PARAM2";

    public ReceiveGeofenceTransitionService() {
        super("ReceiveGeofenceTransitionService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        GeofencingEvent event = GeofencingEvent.fromIntent(intent);

        if (event.hasError())
        {
            //do something to handle
        }
        else
        {
            int transition = event.getGeofenceTransition() ;

            if(transition == Geofence.GEOFENCE_TRANSITION_ENTER)
            {
                //define manager
                NotificationManager myManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                //define pending intent that opens najah.edu site
                String url = "https://www.najah.edu/ar/";
                Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
                notificationIntent.setData(Uri.parse(url));

                PendingIntent pendingIntent = PendingIntent.
                        getActivity(getApplicationContext(), 0, notificationIntent, 0);

                //we entered a geofence
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Notification myNotification = new Notification.Builder(this.getApplicationContext())
                            .setContentTitle("Near Uni")
                            .setSmallIcon(R.drawable.ic_media_play)
                            .setContentText("You are near the uni. don't be late to your class")
                            .setContentIntent(pendingIntent)
                            .build();

                    myManager.notify(1 , myNotification);
                }


            }
        }


    }

}
